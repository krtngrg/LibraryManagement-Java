package controller;

import dto.Book;
import dto.IssuedBookView;
import dto.UserDto;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private final dao.BookDao bookDao = new dao.BookDao();
    private final dao.UserDao userDao = new dao.UserDao();
    private final dao.TransactionDao transactionDao = new dao.TransactionDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        String action = req.getParameter("action");
        if (action == null)
            action = "view"; 

        if (action.equalsIgnoreCase("view")) {
            String query = req.getParameter("query");
            List<Book> books;
            if (query != null && !query.trim().isEmpty()) {
                books = bookDao.searchBooks(query);
                req.setAttribute("searchQuery", query);
            } else {
                books = bookDao.getAllBooks();
            }
            req.setAttribute("books", books);
            req.setAttribute("searchUrl", "book");
            req.setAttribute("searchAction", "view");
            req.getRequestDispatcher("books.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("reader")) {
            String query = req.getParameter("query");
            List<UserDto> readers;
            if (query != null && !query.trim().isEmpty()) {
                readers = userDao.searchUsers(query, "STUDENT");
                req.setAttribute("searchQuery", query);
            } else {
                readers = userDao.getUsers("STUDENT");
            }
            req.setAttribute("users", readers); 
            req.setAttribute("pageTitle", "Manage Students");
            req.setAttribute("addUrl", "book"); 
            req.setAttribute("addActionValue", "addStudent"); 
            req.setAttribute("searchUrl", "book");
            req.setAttribute("searchAction", "reader");
            req.setAttribute("updateUrl", "book");
            req.getRequestDispatcher("members.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("issue")) {
            List<Book> books = bookDao.getAllBooks();
            List<UserDto> readers = userDao.getUsers("LIBRARIAN"); 

            readers = userDao.getUsers("STUDENT");

            String searchIssued = req.getParameter("searchIssued");
            List<IssuedBookView> issuedBooks;
            if (searchIssued != null && !searchIssued.trim().isEmpty()) {
                issuedBooks = transactionDao.searchIssuedBooks(searchIssued);
                req.setAttribute("searchIssuedQuery", searchIssued);
            } else {
                issuedBooks = transactionDao.getAllIssuedBooks();
            }

            req.setAttribute("books", books);
            req.setAttribute("readers", readers);
            req.setAttribute("issuedBooks", issuedBooks);

            req.getRequestDispatcher("issue.jsp").forward(req, res);
        } else if (action.equalsIgnoreCase("delete")) {
            
            if (!"ADMIN".equalsIgnoreCase(role) && !"LIBRARIAN".equalsIgnoreCase(role)) {
                res.sendRedirect("book?action=view&error=Access+Denied");
                return;
            }
            
            int bookId = Integer.parseInt(req.getParameter("id"));
            bookDao.deleteBook(bookId);
            res.sendRedirect("book?action=view");
        } else if (action.equalsIgnoreCase("return")) {
            
            int issueId = Integer.parseInt(req.getParameter("id"));
            transactionDao.returnBook(issueId);
            res.sendRedirect("book?action=issue");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        String action = req.getParameter("action");
        if (action == null) {
            res.sendRedirect("book?action=view");
            return;
        }

        if (action.equalsIgnoreCase("add")) {
            Book book = new Book();
            book.setTitle(req.getParameter("title"));
            book.setAuthor(req.getParameter("author"));
            book.setCategory(req.getParameter("category"));
            book.setCategory(req.getParameter("category"));

            String isbn = req.getParameter("isbn");
            if (isbn != null && isbn.trim().isEmpty()) {
                isbn = null;
            }
            book.setIsbn(isbn);
            book.setQuantity(Integer.parseInt(req.getParameter("quantity")));
            book.setAvailableQuantity(Integer.parseInt(req.getParameter("quantity"))); 

            bookDao.addBook(book);
            res.sendRedirect("book?action=view");
        } else if (action.equalsIgnoreCase("update")) {
            
            if (!"ADMIN".equalsIgnoreCase(role) && !"LIBRARIAN".equalsIgnoreCase(role)) {
                res.sendRedirect("book?action=view&error=Access+Denied");
                return;
            }
            Book book = new Book();
            String idStr = req.getParameter("bookId");
            book.setBookId(Integer.parseInt(idStr));
            book.setTitle(req.getParameter("title"));
            book.setAuthor(req.getParameter("author"));
            book.setCategory(req.getParameter("category"));
            book.setCategory(req.getParameter("category"));

            String isbn = req.getParameter("isbn");
            if (isbn != null && isbn.trim().isEmpty()) {
                isbn = null;
            }
            book.setIsbn(isbn);

            int newQuantity = Integer.parseInt(req.getParameter("quantity"));
            book.setQuantity(newQuantity);

            
            Book oldBook = bookDao.getBookById(book.getBookId());
            if (oldBook != null) {
                int quantityDiff = newQuantity - oldBook.getQuantity();
                int newAvailable = oldBook.getAvailableQuantity() + quantityDiff;

                
                if (newAvailable < 0)
                    newAvailable = 0;

                book.setAvailableQuantity(newAvailable);
            } else {
                
                book.setAvailableQuantity(newQuantity);
            }

            bookDao.updateBook(book);
            res.sendRedirect("book?action=view");
        } else if (action.equalsIgnoreCase("updateUser")) {
            
            if (!"ADMIN".equalsIgnoreCase(role) && !"LIBRARIAN".equalsIgnoreCase(role)) {
                res.sendRedirect("book?action=reader&error=Access+Denied");
                return;
            }
            UserDto user = new UserDto();
            user.setId(Integer.parseInt(req.getParameter("userId")));
            user.setFirstname(req.getParameter("firstname"));
            user.setLastname(req.getParameter("lastname"));
            user.setEmail(req.getParameter("email"));
            user.setPhone(req.getParameter("phone"));
            user.setAddress(req.getParameter("address"));
            user.setRole(req.getParameter("role"));

            userDao.updateUser(user);
            String referer = req.getHeader("Referer");
            res.sendRedirect(referer != null ? referer : "book?action=reader");
        }

        if (action.equalsIgnoreCase("addStudent")) {
            UserDto u = new UserDto();
            u.setFirstname(req.getParameter("firstname"));
            u.setLastname(req.getParameter("lastname"));
            u.setEmail(req.getParameter("email"));
            u.setPhone(req.getParameter("phone"));
            u.setAddress(req.getParameter("address"));
            u.setPassword(req.getParameter("password"));
            u.setRole("STUDENT");
            u.setStatus("ACTIVE");

            userDao.createUser(u);
            res.sendRedirect("book?action=reader");
        }

        if (action.equalsIgnoreCase("reader")) {
            String edit = req.getParameter("edit");

            
            if ("delete".equalsIgnoreCase(edit) && !"ADMIN".equalsIgnoreCase(role) && !"LIBRARIAN".equalsIgnoreCase(role)) {
                res.sendRedirect("book?action=reader&error=Access+Denied");
                return;
            }

            if ("delete".equals(edit)) {
                int userId = Integer.parseInt(req.getParameter("user_id"));
                userDao.deleteUser(userId);
            }

            res.sendRedirect("book?action=reader");
        }

        if (action.equalsIgnoreCase("issue")) {
            int bookId = Integer.parseInt(req.getParameter("bookId"));
            int userId = Integer.parseInt(req.getParameter("userId"));
            Date dueDate = Date.valueOf(req.getParameter("dueDate"));

            
            String eligibilityError = transactionDao.checkBorrowingEligibility(userId);
            if (eligibilityError != null) {
                req.setAttribute("error", eligibilityError);
                List<Book> books = bookDao.getAllBooks();
                List<UserDto> readers = userDao.getUsers("STUDENT");
                List<IssuedBookView> issuedBooks = transactionDao.getAllIssuedBooks();
                req.setAttribute("books", books);
                req.setAttribute("readers", readers);
                req.setAttribute("issuedBooks", issuedBooks);
                req.getRequestDispatcher("issue.jsp").forward(req, res);
                return;
            }

            String result = transactionDao.issueBook(userId, bookId, dueDate);

            if (!"success".equals(result)) {
                req.setAttribute("error", result);
                List<Book> books = bookDao.getAllBooks();
                List<UserDto> readers = userDao.getUsers("STUDENT");
                List<IssuedBookView> issuedBooks = transactionDao.getAllIssuedBooks();
                req.setAttribute("books", books);
                req.setAttribute("readers", readers);
                req.setAttribute("issuedBooks", issuedBooks);
                req.getRequestDispatcher("issue.jsp").forward(req, res);
                return;
            }

            res.sendRedirect("book?action=issue");
        }
    }
}
