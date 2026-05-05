package controller;

import dto.UserDto;
import mailservice.EmailOTPService;

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

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, 
        maxFileSize = 1024 * 1024 * 5, 
        maxRequestSize = 1024 * 1024 * 10 
)

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    
    private final UserService userService = new UserService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        String action = req.getParameter("action");

        if (action.equalsIgnoreCase("profile")) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("uid") == null) {
                res.sendRedirect("login.jsp");
                return;
            }
            String uid = String.valueOf(session.getAttribute("uid")); 
            

            UserDto userDto = userService.getUserById(uid);
            String email = (userDto != null) ? userDto.getEmail() : "NULL";
            System.out.println("UserServlet: Loading profile for UID [" + uid + "], Email from DB: [" + email + "]");

            String imgpath = userService.getUserProfilePath(uid);
            userDto.setProfilePicPath(imgpath);
            req.setAttribute("userDto", userDto);
            req.setAttribute("photo", imgpath);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("profile.jsp");
            requestDispatcher.forward(req, res);
        } else if (action.equalsIgnoreCase("logout")) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(req, res);
        } else if (action.equalsIgnoreCase("signup")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("signup.jsp");
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
            userDto.setRole("LIBRARIAN"); 
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
            } else {
                
                req.setAttribute("error", "Invalid OTP");
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
                    userService.userDao.createUserOtp(foundUser); 
                    EmailOTPService.sendOTP(foundUser.getEmail(), otp);
                    req.setAttribute("otpsuccess", "OTP sent. Please use it within 5 minutes.");
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("verification.jsp");
                    requestDispatcher.forward(req, res);

                } catch (Exception e) {
                    req.setAttribute("otperror", "Something went wrong!");
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
                userCookie.setMaxAge(7 * 24 * 60 * 60); 
                res.addCookie(userCookie);
            } else {
                
                Cookie userCookie = new Cookie("email", "");
                userCookie.setMaxAge(0);
                res.addCookie(userCookie);
            }

            if (!"VERIFIED".equalsIgnoreCase(foundUser.getStatus())
                    && !"ACTIVE".equalsIgnoreCase(foundUser.getStatus())) {
                req.setAttribute("notVerified", true);
                RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                rd.forward(req, res);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("SessionLogin", true);
            session.setAttribute("uid", foundUser.getId());
            session.setAttribute("role", foundUser.getRole());
            session.setAttribute("user", foundUser);

            
            String role = foundUser.getRole();
            if (role != null && role.equalsIgnoreCase("ADMIN")) {
                res.sendRedirect("admin?action=dashboard");
            } else if (role != null && role.equalsIgnoreCase("LIBRARIAN")) {
                res.sendRedirect("librarian?action=dashboard");
            } else {
                req.setAttribute("error", "Access denied: Only staff can log in.");
                session.invalidate();
                RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
                rd.forward(req, res);
            }
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

            req.setAttribute("photo", "uploads/" + fileName);
            req.setAttribute("photoPath", filePath);

            HttpSession session = req.getSession();
            String uid = String.valueOf((int) session.getAttribute("uid"));
            
            try {
                userService.saveUserProfile(uid, "uploads/" + fileName); 
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("profile.jsp");
                requestDispatcher.forward(req, res);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (action.equalsIgnoreCase("sendResetOtp")) {
            String email = req.getParameter("email");
            System.out.println("UserServlet: Received sendResetOtp request for email parameter: [" + email + "]");

            if (email == null || email.trim().isEmpty()) {
                HttpSession session = req.getSession();
                UserDto sessionUser = (UserDto) session.getAttribute("user");
                if (sessionUser != null) {
                    email = sessionUser.getEmail();
                    System.out.println("UserServlet: Using session email: [" + email + "]");
                }
            }

            boolean sent = userService.sendPasswordResetOtp(email);
            res.setContentType("text/plain");
            res.getWriter().write(sent ? "OTP Sent Successfully" : "Error Sending OTP");
        }

        if (action.equalsIgnoreCase("verifyResetOtp")) {
            String email = req.getParameter("email");
            String otp = req.getParameter("otp");

            System.out.println("UserServlet: Verifying Reset OTP [" + otp + "] for [" + email + "]");
            boolean verified = userService.verifyResetOtp(email, otp);

            res.setContentType("text/plain");
            res.getWriter().write(verified ? "Verified" : "Invalid OTP");
        }

        if (action.equalsIgnoreCase("changePassword")) {
            String email = req.getParameter("email");
            if (email == null || email.trim().isEmpty()) {
                UserDto sessionUser = (UserDto) req.getSession().getAttribute("user");
                if (sessionUser != null)
                    email = sessionUser.getEmail();
            }
            String newPassword = req.getParameter("newPassword");
            boolean updated = userService.updatePassword(email, newPassword);
            res.setContentType("text/plain");
            res.getWriter().write(updated ? "Password Updated Successfully" : "Error Updating Password");
        }
    }
}
