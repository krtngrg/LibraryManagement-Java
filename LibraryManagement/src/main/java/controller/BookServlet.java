package controller;

import dto.Book;
import dto.IssuedBookView;
import dto.UserDto;
import mailservice.EmailOTPService;
import model.DBConnection;
import model.UserModel;
import service.UserService;
import util.OTPGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final UserModel userModel = new UserModel();
    private final UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        System.out.println("Hello");
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("view")) {
            List<Book> books = new ArrayList<>();

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT book_id, title, author, category FROM books");
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

            req.setAttribute("books", books);
            req.getRequestDispatcher("books.jsp").forward(req, res);
        } else if (action.equalsIgnoreCase("reader")) {
            List<Reader> readers = new ArrayList<>();

            String sql = " SELECT user_id, name, email, phone, address, created_at FROM readers";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
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

            req.setAttribute("readers", readers);
            req.getRequestDispatcher("members.jsp").forward(req, res);
        } else if (action.equalsIgnoreCase("issue")) {
            List<Book> books = new ArrayList<>();
            List<dto.Reader> readers = new ArrayList<>();
            List<IssuedBookView> issuedBooks = new ArrayList<>();

            try (Connection con = DBConnection.getConnection()) {

                // Load books
                ResultSet rsBooks = con.prepareStatement(
                        "SELECT book_id, title FROM books"
                ).executeQuery();

                while (rsBooks.next()) {
                    Book book = new Book();
                    book.setBookId(rsBooks.getInt("book_id"));
                    book.setTitle(rsBooks.getString("title"));
                    books.add(book);
                }

                // Load readers
                ResultSet rsReaders = con.prepareStatement(
                        "SELECT user_id, name FROM readers"
                ).executeQuery();

                while (rsReaders.next()) {
                    readers.add(new dto.Reader(
                            rsReaders.getInt("user_id"),
                            rsReaders.getString("name")
                    ));
                }

                // Load issued books
                String issuedSql =
                        "SELECT r.name AS reader_name, b.title AS book_title, " +
                                "ib.issue_date, ib.due_date, ib.status " +
                                "FROM issued_books ib " +
                                "JOIN readers r ON ib.user_id = r.user_id " +
                                "JOIN books b ON ib.book_id = b.book_id " +
                                "ORDER BY ib.issue_date DESC";

                ResultSet rsIssued =
                        con.prepareStatement(issuedSql).executeQuery();

                while (rsIssued.next()) {
                    issuedBooks.add(new IssuedBookView(
                            rsIssued.getString("reader_name"),
                            rsIssued.getString("book_title"),
                            rsIssued.getDate("issue_date"),
                            rsIssued.getDate("due_date"),
                            rsIssued.getString("status")
                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            req.setAttribute("books", books);
            req.setAttribute("readers", readers);
            req.setAttribute("issuedBooks", issuedBooks);

            req.getRequestDispatcher("issue.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("verify")) {

        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("add")) {
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "INSERT INTO books (title, author, category) " +
                                 "VALUES (?, ?, ?)")) {

                String title = req.getParameter("title");
                String author = req.getParameter("author");
                String category = req.getParameter("category");
//            int copies = Integer.parseInt(req.getParameter("copies"));

                ps.setString(1, title);
                ps.setString(2, author);
                ps.setString(3, category);
//            ps.setInt(4, copies);
//            ps.setInt(5, copies); // initially available = total

                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

            res.sendRedirect("book?action=view");
        }

        if (action.equalsIgnoreCase("reader")) {
            String edit = req.getParameter("edit");

            try (Connection con = DBConnection.getConnection()) {
                if ("add".equals(edit)) {

                    String sql = "INSERT INTO readers (name, email, phone, address, password, created_at) " +
                            "VALUES (?, ?, ?, ?, ?, NOW())";

                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, req.getParameter("name"));
                    ps.setString(2, req.getParameter("email"));
                    ps.setString(3, req.getParameter("phone"));
                    ps.setString(4, req.getParameter("address"));
                    ps.setString(5, req.getParameter("password"));
                    ps.executeUpdate();

                } else if ("delete".equals(edit)) {

                    String sql = "DELETE FROM readers WHERE user_id = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(req.getParameter("user_id")));
                    ps.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            res.sendRedirect("book?action=reader");

        }

        if (action.equalsIgnoreCase("issue")) {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            int userId = Integer.parseInt(req.getParameter("userId"));
            Date dueDate = Date.valueOf(req.getParameter("dueDate"));

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(
                         "INSERT INTO issued_books (user_id, book_id, due_date) VALUES (?, ?, ?)")) {

                ps.setInt(1, userId);
                ps.setInt(2, bookId);
                ps.setDate(3, dueDate);
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

            res.sendRedirect("book?action=issue");

        }

        if (action.equalsIgnoreCase("login")) {


        }


        if (action.equalsIgnoreCase("edit")) {


        }

    }
}
