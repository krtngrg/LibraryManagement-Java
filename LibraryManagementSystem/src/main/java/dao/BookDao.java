package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.Book;
import util.DBConnection;

public class BookDao {

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT book_id, title, author, category, total_quantity, available_quantity, isbn FROM books");
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setQuantity(rs.getInt("total_quantity"));
                book.setAvailableQuantity(rs.getInt("available_quantity"));
                book.setIsbn(rs.getString("isbn"));
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
                        "INSERT INTO books (title, author, category, total_quantity, available_quantity, isbn) VALUES (?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getAvailableQuantity());
            ps.setInt(5, book.getAvailableQuantity());
            if (book.getIsbn() == null) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, book.getIsbn());
            }
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE books SET title=?, author=?, category=?, total_quantity=?, available_quantity=?, isbn=? WHERE book_id=?")) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());
            ps.setInt(5, book.getAvailableQuantity());
            ps.setInt(5, book.getAvailableQuantity());
            if (book.getIsbn() == null) {
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else {
                ps.setString(6, book.getIsbn());
            }
            ps.setInt(7, book.getBookId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM books WHERE book_id=?")) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Book getBookById(int bookId) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE book_id=?")) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setIsbn(rs.getString("isbn"));
                return book;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id, title, author, category, total_quantity, available_quantity, isbn FROM books " +
                "WHERE title ILIKE ? OR author ILIKE ? OR category ILIKE ? OR isbn ILIKE ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            String keyword = "%" + query + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setQuantity(rs.getInt("total_quantity"));
                book.setAvailableQuantity(rs.getInt("available_quantity"));
                book.setIsbn(rs.getString("isbn"));
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
