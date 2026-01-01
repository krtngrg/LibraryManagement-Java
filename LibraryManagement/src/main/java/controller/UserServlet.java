package controller;

import dto.UserDto;
import model.UserModel;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private final UserModel userModel = new UserModel();
    private final UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        System.out.println("Hello");
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("profile")) {
            HttpSession session = req.getSession();
            String uid = (String) session.getAttribute("uid");
            UserDto userDto = userService.getUserById(uid);
//            userDto.setPassword(null); // password front end ma kaile pani janu hudaina
            req.setAttribute("userDto", userDto);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("profile.jsp");
            requestDispatcher.forward(req, res);
        } else if (action.equalsIgnoreCase("logout")) {
            HttpSession session = req.getSession();
            session.invalidate();
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(req, res);
        } else if (action.equalsIgnoreCase("signup")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("user_signup.jsp");
            requestDispatcher.forward(req, res);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("signup")) {
            UserDto userDto = new UserDto();
            userDto.setFirstname(req.getParameter("firstname"));
            userDto.setLastname(req.getParameter("lastname"));
            userDto.setEmail(req.getParameter("email"));
            userDto.setPhone(req.getParameter("phone"));
            userDto.setAddress(req.getParameter("address"));
            userDto.setPassword(req.getParameter("password"));
            boolean created = userService.signup(userDto);
            if (created) {
                req.getRequestDispatcher("verification.jsp").forward(req, res);
            }
        }

        if (action.equalsIgnoreCase("verify")) {
            String email = req.getParameter("email");
            String otp = req.getParameter("otp");
            boolean found = userService.verifyUser(email, otp, new Timestamp(System.currentTimeMillis()));
            if (found) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
                requestDispatcher.forward(req, res);
            }

        }

        if (action.equalsIgnoreCase("login")) {
            String welcome = "Welcome to the dashboard! User";

//            session.setMaxInactiveInterval(1); seconds parameter
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            UserDto foundUser = userService.login(email, password);
            if(!foundUser.getStatus().equals("VERIFIED")){
                HttpSession session = req.getSession();
                String notverified="true";
                session.setAttribute("notVerified",notverified);
                return;
            }
            if (foundUser != null) {
                HttpSession session = req.getSession();
                String uid = Integer.toString(foundUser.getId());
                boolean login = true;
//                String name = foundUser.getFirstname();
//                session.setAttribute("SessionFirstName",foundUser.getFirstname());
//                session.setAttribute("SessionLastName",foundUser.getLastname());
//                session.setAttribute("SessionEmail",foundUser.getEmail());
//                session.setAttribute("SessionPassword",foundUser.getPassword());
                session.setAttribute("SessionLogin", login);
                session.setAttribute("uid", uid);
                req.setAttribute("user", foundUser);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("Dashboard.jsp");
                requestDispatcher.forward(req, res);

            }


        }

        if (action.equalsIgnoreCase("edit")) {
            String oldpassword = req.getParameter("currentPassword");


        }

    }
}
