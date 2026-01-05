package controller;

import dto.UserDto;
import dto.UserProfile;
import jakarta.mail.Session;
import mailservice.EmailOTPService;
import model.UserModel;
import service.UserService;
import util.OTPGenerator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 5,       // 5MB
        maxRequestSize = 1024 * 1024 * 10    // 10MB
)

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
            String uid = String.valueOf((int) session.getAttribute("uid"));
            UserDto userDto = userService.getUserById(uid);
            String imgpath = userModel.getUserProfilePath(uid);
            userDto.imagepath = imgpath;
//            userDto.setPassword(null); // password front end ma kaile pani janu hudaina
            req.setAttribute("userDto", userDto);
            req.setAttribute("photo",imgpath);
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
        } else if (action.equalsIgnoreCase("verify")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("verification.jsp");
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
            }else{
//                req.setAttribute("success","OTP sent. Please use it within 5 minutes.");
                req.setAttribute("error","Invalid OTP");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("verification.jsp");
                requestDispatcher.forward(req, res);
            }

        }

        if (action.equalsIgnoreCase("resendOtp")) {
            String email = req.getParameter("email");
            UserDto foundUser = userService.getUserByEmail(email);
            if (foundUser != null) {
                try {
                    String otp = OTPGenerator.generateOtp();
                    foundUser.setOtp(otp);
                    userModel.createUserOtp(foundUser);             // save otp to database
                    EmailOTPService.sendOTP(foundUser.getEmail(), otp);
                    req.setAttribute("otpsuccess","OTP sent. Please use it within 5 minutes.");
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("verification.jsp");
                    requestDispatcher.forward(req, res);

                } catch (Exception e) {
                    req.setAttribute("otperror","Something went wrong!");//send otp to email
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("verification.jsp");
                    requestDispatcher.forward(req, res);
                }
            }

        }

        if (action.equalsIgnoreCase("login")) {

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String remember = req.getParameter("remember");

            UserDto foundUser = userService.login(email, password);

            if (foundUser == null) {
                req.setAttribute("error", "Invalid email or password");
                RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                rd.forward(req, res);
                return;
            }

            if ("on".equals(remember)) {
                Cookie userCookie = new Cookie("email", email);
                userCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                res.addCookie(userCookie);
            } else {
                // Delete cookie if exists
                Cookie userCookie = new Cookie("email", "");
                userCookie.setMaxAge(0);
                res.addCookie(userCookie);
            }

            if (!"VERIFIED".equals(foundUser.getStatus())) {
                req.setAttribute("notVerified", true);
                RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                rd.forward(req, res);
                return;
            }


            HttpSession session = req.getSession();
            session.setAttribute("SessionLogin", true);
            session.setAttribute("uid", foundUser.getId());

            req.setAttribute("user", foundUser);
            RequestDispatcher rd = req.getRequestDispatcher("Dashboard.jsp");
            rd.forward(req, res);
        }


        if (action.equalsIgnoreCase("profilepic")) {

            Part filePart = req.getPart("profilePic");
            String fileName = filePart.getSubmittedFileName();


            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            req.setAttribute("photo", "uploads/"+ fileName);
            req.setAttribute("photoPath",filePath);

            HttpSession session = req.getSession();
            String uid = String.valueOf((int) session.getAttribute("uid"));
//            UserDto userDto = userService.getUserById(uid);
            try {
                userModel.saveUserProfile(uid,"uploads/"+ fileName);             // save otp to database
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("profile.jsp");
                requestDispatcher.forward(req, res);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
