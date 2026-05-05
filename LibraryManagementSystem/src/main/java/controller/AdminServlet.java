package controller;

import dto.UserDto;
import service.UserService;
import dao.BookDao;
import dao.TransactionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.OverdueNotifierTask;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final BookDao bookDao = new BookDao();
    private final TransactionDao transactionDao = new TransactionDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equalsIgnoreCase(role)) {
            res.sendRedirect("index.jsp"); 
            return;
        }

        String action = req.getParameter("action");
        if (action == null)
            action = "dashboard";

        if (action.equalsIgnoreCase("dashboard")) {
            
            int bookCount = bookDao.getAllBooks().size();
            int librarianCount = userService.userDao.getUsers("LIBRARIAN").size();
            int studentCount = userService.userDao.getUsers("STUDENT").size();
            int issuedCount = transactionDao.getAllIssuedBooks().size();

            req.setAttribute("bookCount", bookCount);
            req.setAttribute("librarianCount", librarianCount);
            req.setAttribute("studentCount", studentCount);
            req.setAttribute("issuedCount", issuedCount);

            req.getRequestDispatcher("admin_dashboard.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("viewLibrarians")) {
            String query = req.getParameter("query");
            List<UserDto> librarians;
            if (query != null && !query.trim().isEmpty()) {
                librarians = userService.userDao.searchUsers(query, "LIBRARIAN");
                req.setAttribute("searchQuery", query);
            } else {
                librarians = userService.userDao.getUsers("LIBRARIAN");
            }
            req.setAttribute("users", librarians);
            req.setAttribute("pageTitle", "Manage Librarians");
            req.setAttribute("addUrl", "admin"); 
            req.setAttribute("addActionValue", "addLibrarian");
            req.setAttribute("searchUrl", "admin"); 
            req.setAttribute("searchAction", "viewLibrarians");
            req.setAttribute("updateUrl", "admin");
            
            req.getRequestDispatcher("members.jsp").forward(req, res);

        } else if (action.equalsIgnoreCase("viewAllUsers")) {
            String query = req.getParameter("query");
            List<UserDto> allUsers;
            if (query != null && !query.trim().isEmpty()) {
                allUsers = userService.userDao.searchUsers(query, "STUDENT");
                req.setAttribute("searchQuery", query);
            } else {
                allUsers = userService.userDao.getUsers("STUDENT");
            }
            req.setAttribute("users", allUsers);
            req.setAttribute("pageTitle", "Students");
            req.setAttribute("addUrl", "admin");
            req.setAttribute("addActionValue", "addStudent");
            req.setAttribute("searchUrl", "admin");
            req.setAttribute("searchAction", "viewAllUsers");
            req.setAttribute("updateUrl", "admin");
            req.getRequestDispatcher("members.jsp").forward(req, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (!"ADMIN".equalsIgnoreCase(role)) {
            res.sendRedirect("index.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("addLibrarian".equalsIgnoreCase(action)) {
            UserDto librarian = new UserDto();
            librarian.setFirstname(req.getParameter("firstname"));
            librarian.setLastname(req.getParameter("lastname"));
            librarian.setEmail(req.getParameter("email"));
            librarian.setPhone(req.getParameter("phone"));
            librarian.setAddress(req.getParameter("address"));
            librarian.setPassword(req.getParameter("password"));
            librarian.setRole("LIBRARIAN");
            librarian.setStatus("ACTIVE");

            userService.userDao.createUser(librarian);
            res.sendRedirect("admin?action=viewLibrarians");

        } else if ("addStudent".equalsIgnoreCase(action)) {
            UserDto student = new UserDto();
            student.setFirstname(req.getParameter("firstname"));
            student.setLastname(req.getParameter("lastname"));
            student.setEmail(req.getParameter("email"));
            student.setPhone(req.getParameter("phone"));
            student.setAddress(req.getParameter("address"));
            student.setPassword(req.getParameter("password"));
            student.setRole("STUDENT");
            student.setStatus("ACTIVE");

            userService.userDao.createUser(student);
            res.sendRedirect("admin?action=viewAllUsers");

        } else if ("updateUser".equalsIgnoreCase(action)) {
            UserDto user = new UserDto();
            user.setId(Integer.parseInt(req.getParameter("userId")));
            user.setFirstname(req.getParameter("firstname"));
            user.setLastname(req.getParameter("lastname"));
            user.setEmail(req.getParameter("email"));
            user.setPhone(req.getParameter("phone"));
            user.setAddress(req.getParameter("address"));
            user.setRole(req.getParameter("role")); 

            userService.userDao.updateUser(user);
            String referer = req.getHeader("Referer");
            res.sendRedirect(referer != null ? referer : "admin?action=dashboard");

        } else if ("deleteUser".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            userService.userDao.deleteUser(userId);
            String referer = req.getHeader("Referer");
            res.sendRedirect(referer != null ? referer : "admin?action=dashboard");
        } else if ("forceNotifyOverdue".equalsIgnoreCase(action)) {
            new Thread(new OverdueNotifierTask()).start();
            res.sendRedirect("admin?action=dashboard&success=Notices+Sent");
        }
    }
}
