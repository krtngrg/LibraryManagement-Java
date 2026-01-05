package controller;

import dto.Book;
import dto.IssuedBookView;
import dto.Reader;
import model.BookModel;

// import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private final BookModel bookModel = new BookModel();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null)
            action = "view"; // default

        if (action.equalsIgnoreCase("view")) {
            List<Book> books = bookModel.getAllBooks();
            req.setAttribute("books", books);
            req.getRequestDispatcher("books.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("reader")) {
            List<Reader> readers = bookModel.getAllReaders();
            req.setAttribute("readers", readers);
            req.getRequestDispatcher("members.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("issue")) {
            List<Book> books = bookModel.getAllBooks();
            List<Reader> readers = bookModel.getAllReaders();
            List<IssuedBookView> issuedBooks = bookModel.getIssuedBooks();

            req.setAttribute("books", books);
            req.setAttribute("readers", readers);
            req.setAttribute("issuedBooks", issuedBooks);

            req.getRequestDispatcher("issue.jsp").forward(req, res);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("add")) {
            Book book = new Book();
            book.setTitle(req.getParameter("title"));
            book.setAuthor(req.getParameter("author"));
            book.setCategory(req.getParameter("category"));

            bookModel.addBook(book);
            res.sendRedirect("book?action=view");
        }

        if (action.equalsIgnoreCase("reader")) {
            String edit = req.getParameter("edit");

            if ("add".equals(edit)) {
                Reader reader = new Reader();
                reader.setName(req.getParameter("name"));
                reader.setEmail(req.getParameter("email"));
                reader.setPhone(req.getParameter("phone"));
                reader.setAddress(req.getParameter("address"));
                String password = req.getParameter("password");

                bookModel.addReader(reader, password);

            } else if ("delete".equals(edit)) {
                int userId = Integer.parseInt(req.getParameter("user_id"));
                bookModel.deleteReader(userId);
            }

            res.sendRedirect("book?action=reader");
        }

        if (action.equalsIgnoreCase("issue")) {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            int userId = Integer.parseInt(req.getParameter("userId"));
            Date dueDate = Date.valueOf(req.getParameter("dueDate"));

            String result = bookModel.issueBook(userId, bookId, dueDate);

            if (!"success".equals(result)) {
                // Determine how to show error. For now, maybe session attribute or forward?
                // Using session for simplicity on redirect, or forward to keep context.
                // Let's forward to show error.
                req.setAttribute("error", result);
                doGet(req, res); // Reload data
                return;
            }

            res.sendRedirect("book?action=issue");
        }
    }
}
