package controller;

import dao.ReservationDao;
import dao.BookDao;
import dao.UserDao;
import dto.Reservation;
import dto.Book;
import dto.Reader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
    private final ReservationDao reservationDao = new ReservationDao();
    private final BookDao bookDao = new BookDao();
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (!"ADMIN".equalsIgnoreCase(role) && !"LIBRARIAN".equalsIgnoreCase(role)) {
            res.sendRedirect("index.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "view";
        }

        if (action.equals("view")) {
            List<Reservation> reservations = reservationDao.getAllReservations();
            List<Book> allBooks = bookDao.getAllBooks();
            
            List<Book> unavailableBooks = allBooks.stream()
                    .filter(b -> b.getAvailableQuantity() <= 0)
                    .collect(java.util.stream.Collectors.toList());

            List<Reader> readers = userDao.getReadersByRole("STUDENT");

            req.setAttribute("reservations", reservations);
            req.setAttribute("books", unavailableBooks);
            req.setAttribute("readers", readers);
            req.getRequestDispatcher("reservations.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (!"ADMIN".equalsIgnoreCase(role) && !"LIBRARIAN".equalsIgnoreCase(role)) {
            res.sendRedirect("index.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            int bookId = Integer.parseInt(req.getParameter("bookId"));

            if (reservationDao.addReservation(userId, bookId)) {
                res.sendRedirect("reservation?action=view&success=Reservation+Added");
            } else {
                res.sendRedirect("reservation?action=view&error=Failed+to+add+reservation");
            }
        } else if ("delete".equals(action)) {
            int resId = Integer.parseInt(req.getParameter("id"));
            if (reservationDao.deleteReservation(resId)) {
                res.sendRedirect("reservation?action=view&success=Reservation+Deleted");
            } else {
                res.sendRedirect("reservation?action=view&error=Failed+to+delete+reservation");
            }
        } else if ("complete".equals(action)) {
            int resId = Integer.parseInt(req.getParameter("id"));
            if (reservationDao.updateStatus(resId, "COMPLETED")) {
                res.sendRedirect("reservation?action=view&success=Reservation+Marked+as+Completed");
            } else {
                res.sendRedirect("reservation?action=view&error=Failed+to+update+status");
            }
        } else if ("release".equals(action)) {
            int resId = Integer.parseInt(req.getParameter("id"));
            if (reservationDao.releaseReservation(resId)) {
                res.sendRedirect("reservation?action=view&success=Reservation+Released");
            } else {
                res.sendRedirect("reservation?action=view&error=Failed+to+release+reservation");
            }
        }
    }
}
