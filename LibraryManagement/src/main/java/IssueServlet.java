import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import dto.Book;
import dto.Reader;
import dto.IssuedBookView;
import model.DBConnection;


@WebServlet("/issue")
public class
IssueServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Book> books = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();
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
                readers.add(new Reader(
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
    }

    // POST → Issue a book
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

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

        res.sendRedirect("issue");
    }
}

