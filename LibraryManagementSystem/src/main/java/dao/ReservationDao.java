package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.Reservation;
import util.DBConnection;

public class ReservationDao {

    public boolean addReservation(int userId, int bookId) {
        String sql = "INSERT INTO reservations (user_id, book_id, status) VALUES (?, ?, 'RESERVED')";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT r.res_id, r.user_id, r.book_id, r.reservation_date, r.notified_at, r.status, " +
                "u.first_name || ' ' || u.last_name as reader_name, b.title " +
                "FROM reservations r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN books b ON r.book_id = b.book_id " +
                "ORDER BY r.reservation_date DESC";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setResId(rs.getInt("res_id"));
                res.setUserId(rs.getInt("user_id"));
                res.setBookId(rs.getInt("book_id"));
                res.setReservationDate(rs.getTimestamp("reservation_date"));
                res.setNotifiedAt(rs.getTimestamp("notified_at"));
                res.setStatus(rs.getString("status"));
                res.setReaderName(rs.getString("reader_name"));
                res.setBookTitle(rs.getString("title"));
                list.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean expireReservations() {
        String fetchSql = "SELECT res_id, book_id FROM reservations WHERE status = 'HOLDED' AND notified_at < NOW() - INTERVAL '2 days'";
        String updateResSql = "UPDATE reservations SET status = 'RELEASED' WHERE res_id = ?";
        String updateBookSql = "UPDATE books SET available_quantity = available_quantity + 1 WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psF = con.prepareStatement(fetchSql);
                    PreparedStatement psUR = con.prepareStatement(updateResSql);
                    PreparedStatement psUB = con.prepareStatement(updateBookSql)) {

                ResultSet rs = psF.executeQuery();
                int count = 0;
                while (rs.next()) {
                    int resId = rs.getInt("res_id");
                    int bookId = rs.getInt("book_id");

                    psUR.setInt(1, resId);
                    psUR.executeUpdate();

                    psUB.setInt(1, bookId);
                    psUB.executeUpdate();
                    count++;
                }
                con.commit();
                if (count > 0) {
                    System.out.println("Expired and released " + count + " reservations.");
                }
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean releaseReservation(int resId) {
        String fetchSql = "SELECT book_id, status FROM reservations WHERE res_id = ?";
        String updateResSql = "UPDATE reservations SET status = 'RELEASED' WHERE res_id = ?";
        String updateBookSql = "UPDATE books SET available_quantity = available_quantity + 1 WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement psF = con.prepareStatement(fetchSql)) {
                psF.setInt(1, resId);
                ResultSet rs = psF.executeQuery();
                if (rs.next()) {
                    String status = rs.getString("status");
                    int bookId = rs.getInt("book_id");

                    
                    if ("HOLDED".equalsIgnoreCase(status)) {
                        try (PreparedStatement psUB = con.prepareStatement(updateBookSql)) {
                            psUB.setInt(1, bookId);
                            psUB.executeUpdate();
                        }
                    }

                    try (PreparedStatement psUR = con.prepareStatement(updateResSql)) {
                        psUR.setInt(1, resId);
                        psUR.executeUpdate();
                    }
                    con.commit();
                    return true;
                }
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int resId, String status) {
        String sql = "UPDATE reservations SET status = ? WHERE res_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, resId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReservation(int resId) {
        String sql = "DELETE FROM reservations WHERE res_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, resId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
