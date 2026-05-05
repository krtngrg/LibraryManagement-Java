package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.IssuedBookView;
import util.DBConnection;

public class TransactionDao {

    public List<IssuedBookView> getAllIssuedBooks() {
        return searchIssuedBooks(null);
    }

    public List<IssuedBookView> searchIssuedBooks(String query) {
        List<IssuedBookView> issuedBooks = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT r.first_name, r.last_name, r.email, b.title, b.isbn, ");
        sql.append("ib.issue_id, ib.issue_date, ib.due_date, ib.return_date, ib.penalty, ib.status ");
        sql.append("FROM issued_books ib ");
        sql.append("JOIN users r ON ib.user_id = r.user_id ");
        sql.append("JOIN books b ON ib.book_id = b.book_id ");

        boolean hasQuery = query != null && !query.trim().isEmpty();
        if (hasQuery) {
            sql.append("WHERE r.first_name ILIKE ? OR r.last_name ILIKE ? OR b.title ILIKE ? OR b.isbn ILIKE ? ");
        }
        sql.append("ORDER BY ib.issue_date DESC, ib.issue_id DESC");

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (hasQuery) {
                String keyword = "%" + query + "%";
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ps.setString(3, keyword);
                ps.setString(4, keyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String readerName = rs.getString("first_name") + " " + rs.getString("last_name");
                    IssuedBookView ib = new IssuedBookView(
                            readerName,
                            rs.getString("title"),
                            rs.getDate("issue_date"),
                            rs.getDate("due_date"),
                            rs.getString("status"));

                    ib.setId(rs.getInt("issue_id"));
                    ib.setBookIsbn(rs.getString("isbn"));
                    ib.setReturnDate(rs.getDate("return_date"));
                    ib.setPenalty(rs.getDouble("penalty"));
                    ib.setEmail(rs.getString("email"));

                    issuedBooks.add(ib);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issuedBooks;
    }

    public String issueBook(int userId, int bookId, Date dueDate) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null)
                return "Database error";
            con.setAutoCommit(false); 

            
            String checkUserSql = "SELECT COUNT(*) FROM issued_books WHERE user_id = ? AND book_id = ? AND status = 'ISSUED'";
            try (PreparedStatement ps = con.prepareStatement(checkUserSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, bookId);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    con.rollback();
                    return "User already has this book issued.";
                }
            }

            String checkResSql = "SELECT res_id FROM reservations WHERE user_id = ? AND book_id = ? AND status = 'HOLDED' LIMIT 1";
            boolean hadHold = false; // reserve ma holded vako lai issue garda clear
            try (PreparedStatement psRes = con.prepareStatement(checkResSql)) {
                psRes.setInt(1, userId);
                psRes.setInt(2, bookId);
                ResultSet rsRes = psRes.executeQuery();
                if (rsRes.next()) {
                    hadHold = true;
                    int resId = rsRes.getInt("res_id");
                    
                    String updateResSql = "UPDATE reservations SET status = 'COMPLETED' WHERE res_id = ?";
                    try (PreparedStatement psUR = con.prepareStatement(updateResSql)) {
                        psUR.setInt(1, resId);
                        psUR.executeUpdate();
                    }
                }
            }

            if (!hadHold) {
                
                String checkBookSql = "SELECT available_quantity FROM books WHERE book_id = ? FOR UPDATE";
                try (PreparedStatement ps = con.prepareStatement(checkBookSql)) {
                    ps.setInt(1, bookId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int available = rs.getInt("available_quantity");
                        if (available <= 0) {
                            con.rollback();
                            return "Book is out of stock.";
                        }
                    } else {
                        con.rollback();
                        return "Book not found.";
                    }
                }

                String updateBookSql = "UPDATE books SET available_quantity = available_quantity - 1 WHERE book_id = ?";
                try (PreparedStatement ps = con.prepareStatement(updateBookSql)) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }
            }

            String insertSql = "INSERT INTO issued_books (user_id, book_id, due_date, issue_date, status) VALUES (?, ?, ?, CURRENT_DATE, 'ISSUED')";
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, bookId);
                ps.setDate(3, dueDate);
                ps.executeUpdate();
            }

            con.commit();
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public boolean returnBook(int issueId) {
        try (Connection con = DBConnection.getConnection()) {
            if (con == null)
                return false;
            con.setAutoCommit(false);

            
            int bookId = -1;
            Date dueDate = null;

            Date returnDate = new Date(System.currentTimeMillis());

            String selectSql = "SELECT book_id, due_date FROM issued_books WHERE issue_id = ?";
            try (PreparedStatement ps = con.prepareStatement(selectSql)) {
                ps.setInt(1, issueId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bookId = rs.getInt("book_id");
                    dueDate = rs.getDate("due_date");
                } else {
                    con.rollback();
                    return false;
                }
            }

            
            double penalty = 0.0;
            if (returnDate.after(dueDate)) {
                long diffInMillies = Math.abs(returnDate.getTime() - dueDate.getTime());
                long diff = java.util.concurrent.TimeUnit.DAYS.convert(diffInMillies,
                        java.util.concurrent.TimeUnit.MILLISECONDS);

                
                
                
                if (diff <= 7) {
                    penalty = diff * 5.0;
                } else if (diff <= 14) {
                    penalty = (7 * 5.0) + ((diff - 7) * 10.0);
                } else {
                    penalty = (7 * 5.0) + (7 * 10.0) + ((diff - 14) * 20.0);
                }
            }

            
            String updateSql = "UPDATE issued_books SET return_date = ?, status = 'RETURNED', penalty = ? WHERE issue_id = ?";
            try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                ps.setDate(1, returnDate);
                ps.setDouble(2, penalty);
                ps.setInt(3, issueId);
                ps.executeUpdate();
            }

            
            String updateBookSql = "UPDATE books SET available_quantity = available_quantity + 1 WHERE book_id = ?";
            try (PreparedStatement ps = con.prepareStatement(updateBookSql)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            //reserve book when return holded in FIFO order
            String fetchReserveSql = "SELECT r.res_id, r.user_id, u.email, b.title " +
                    "FROM reservations r " +
                    "JOIN users u ON r.user_id = u.user_id " +
                    "JOIN books b ON r.book_id = b.book_id " +
                    "WHERE r.book_id = ? AND r.status = 'RESERVED' " +
                    "ORDER BY r.reservation_date ASC LIMIT 1";
            try (PreparedStatement psF = con.prepareStatement(fetchReserveSql)) {
                psF.setInt(1, bookId);
                try (ResultSet rsR = psF.executeQuery()) {
                    if (rsR.next()) {
                        int resId = rsR.getInt("res_id");
                        String email = rsR.getString("email");
                        String title = rsR.getString("title");

                        String updateResSql = "UPDATE reservations SET status = 'HOLDED', notified_at = CURRENT_TIMESTAMP WHERE res_id = ?";
                        try (PreparedStatement psU = con.prepareStatement(updateResSql)) {
                            psU.setInt(1, resId);
                            psU.executeUpdate();
                        }

                        String deductQtySql = "UPDATE books SET available_quantity = available_quantity - 1 WHERE book_id = ?";
                        try (PreparedStatement psD = con.prepareStatement(deductQtySql)) {
                            psD.setInt(1, bookId);
                            psD.executeUpdate();
                        }

                        if (email != null && !email.isEmpty()) {
                            mailservice.EmailOTPService.sendReservationReadyNotice(email, title);
                        }
                        System.out.println("Reservation holded and email sent for book_id: " + bookId);
                    }
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public String checkBorrowingEligibility(int userId) {
        String sql = "SELECT COUNT(*) AS total, " +
                     "SUM(CASE WHEN due_date < CURRENT_DATE THEN 1 ELSE 0 END) AS overdue " +
                     "FROM issued_books WHERE user_id = ? AND status = 'ISSUED'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total   = rs.getInt("total");
                    int overdue = rs.getInt("overdue");

                    if (overdue > 0) {
                        return "Borrowing blocked: this member has " + overdue +
                               " overdue book(s). Please return them before issuing a new book.";
                    }
                    if (total >= 5) {
                        return "Borrowing limit reached: this member already holds " + total +
                               " book(s). Maximum allowed is 5.";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while checking borrowing eligibility: " + e.getMessage();
        }
        return null; 
    }

    public List<IssuedBookView> getOverdueTransactions() {
        List<IssuedBookView> overdueList = new ArrayList<>();
        String sql = "SELECT r.first_name, r.last_name, r.email, b.title, ib.issue_id, ib.issue_date, ib.due_date " +
                "FROM issued_books ib " +
                "JOIN users r ON ib.user_id = r.user_id " +
                "JOIN books b ON ib.book_id = b.book_id " +
                "WHERE ib.status = 'ISSUED' AND ib.due_date < CURRENT_DATE";

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String readerName = rs.getString("first_name") + " " + rs.getString("last_name");
                IssuedBookView ib = new IssuedBookView(
                        readerName,
                        rs.getString("title"),
                        rs.getDate("issue_date"),
                        rs.getDate("due_date"),
                        "ISSUED");
                ib.setId(rs.getInt("issue_id"));
                ib.setEmail(rs.getString("email"));
                overdueList.add(ib);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return overdueList;
    }
}
