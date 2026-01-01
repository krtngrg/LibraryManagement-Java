import dto.Book;
import model.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
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
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

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

        res.sendRedirect("books");
    }
}