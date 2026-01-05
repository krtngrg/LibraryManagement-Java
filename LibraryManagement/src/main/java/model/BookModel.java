package model;

import dto.Book;
import dto.IssuedBookView;
import dto.Reader;
// import model.DBConnection; // Not needed as it's in the same package

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookModel {

    // --- Book Operations ---

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT book_id, title, author, category FROM books");
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean addBook(Book book) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO books (title, author, category) VALUES (?, ?, ?)")) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Reader Operations ---

    public List<Reader> getAllReaders() {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con
                        .prepareStatement("SELECT user_id, name, email, phone, address, created_at FROM readers");
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reader r = new Reader();
                r.setUserId(rs.getInt("user_id"));
                r.setName(rs.getString("name"));
                r.setEmail(rs.getString("email"));
                r.setPhone(rs.getString("phone"));
                r.setAddress(rs.getString("address"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                readers.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readers;
    }

    public boolean addReader(Reader reader, String password) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO readers (name, email, phone, address, password, created_at) VALUES (?, ?, ?, ?, ?, NOW())")) {

            ps.setString(1, reader.getName());
            ps.setString(2, reader.getEmail());
            ps.setString(3, reader.getPhone());
            ps.setString(4, reader.getAddress());
            ps.setString(5, password); // TODO: Hash this password in future
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReader(int userId) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM readers WHERE user_id = ?")) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Issue Operations ---

    public List<IssuedBookView> getIssuedBooks() {
        List<IssuedBookView> issuedBooks = new ArrayList<>();
        String sql = "SELECT r.name AS reader_name, b.title AS book_title, " +
                "ib.id, ib.issue_date, ib.due_date, ib.status, ib.return_date " +
                "FROM issued_books ib " +
                "JOIN readers r ON ib.user_id = r.user_id " +
                "JOIN books b ON ib.book_id = b.book_id " +
                "ORDER BY ib.issue_date DESC"; // Assuming 'id' is the PK of issued_books

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Assuming IssuedBookView has a constructor or setters match this
                IssuedBookView ib = new IssuedBookView(
                        rs.getString("reader_name"),
                        rs.getString("book_title"),
                        rs.getDate("issue_date"),
                        rs.getDate("due_date"),
                        rs.getString("status"));
                // If IssuedBookView doesn't have ID or return_date, we might need to update
                // DTO,
                // but for now reusing existing DTO structure based on BookServlet usage.
                issuedBooks.add(ib);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return issuedBooks;
    }

    public String issueBook(int userId, int bookId, Date dueDate) {
        // 1. Check if book is already issued and not returned
        // Assuming 1 copy per book_id.

        try (Connection con = DBConnection.getConnection()) {

            // Check availability
            String checkSql = "SELECT COUNT(*) FROM issued_books WHERE book_id = ? AND return_date IS NULL";
            try (PreparedStatement startPs = con.prepareStatement(checkSql)) {
                startPs.setInt(1, bookId);
                ResultSet rs = startPs.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return "Book is already issued to someone else.";
                }
            }

            // Issue
            String insertSql = "INSERT INTO issued_books (user_id, book_id, due_date, issue_date, status) VALUES (?, ?, ?, CURRENT_DATE, 'ISSUED')";
            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, bookId);
                ps.setDate(3, dueDate);
                ps.executeUpdate();
                return "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public boolean returnBook(int issueId) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE issued_books SET return_date = CURRENT_DATE, status = 'RETURNED' WHERE id=?")) { // Assuming
                                                                                                                 // 'id'
                                                                                                                 // column
                                                                                                                 // exists
            ps.setInt(1, issueId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
