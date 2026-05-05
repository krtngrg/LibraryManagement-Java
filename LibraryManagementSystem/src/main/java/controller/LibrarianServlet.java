package controller;

import dao.BookDao;
import dao.TransactionDao;
import dao.UserDao;
import dto.IssuedBookView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/librarian")
public class LibrarianServlet extends HttpServlet {

    private final BookDao bookDao = new BookDao();
    private final UserDao userDao = new UserDao();
    private final TransactionDao transactionDao = new TransactionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"LIBRARIAN".equalsIgnoreCase(role) && !"ADMIN".equalsIgnoreCase(role)) { 
                                                                                      
            res.sendRedirect("index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null)
            action = "dashboard";

        if (action.equalsIgnoreCase("dashboard")) {
            
            int totalBooks = bookDao.getAllBooks().size();

            
            int totalMembers = userDao.getUsers("STUDENT").size();

            
            List<IssuedBookView> allTransactions = transactionDao.getAllIssuedBooks();
            int booksTaken = 0;
            int pastDue = 0;
            LocalDate today = LocalDate.now();

            for (IssuedBookView ib : allTransactions) {
                if ("ISSUED".equalsIgnoreCase(ib.getStatus())) {
                    booksTaken++;
                    if (ib.getDueDate() != null && ib.getDueDate().toLocalDate().isBefore(today)) {
                        pastDue++;
                    }
                }
            }
            req.setAttribute("totalBooks", totalBooks);
            req.setAttribute("totalMembers", totalMembers);
            req.setAttribute("booksTaken", booksTaken);
            req.setAttribute("pastDue", pastDue);

            req.getRequestDispatcher("librarian_dashboard.jsp").forward(req, res);
        }
    }
}
