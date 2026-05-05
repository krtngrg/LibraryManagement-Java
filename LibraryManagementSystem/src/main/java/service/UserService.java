package service;

import dto.UserDto;
import mailservice.EmailOTPService;

import util.LMSUtils;
import util.OTPGenerator;

import java.sql.Timestamp;

public class UserService {
    public dao.UserDao userDao = new dao.UserDao();

    public boolean signup(UserDto userDto) {
        String hashedPassword = LMSUtils.md5Hash(userDto.getPassword());
        userDto.setPassword(hashedPassword);
        UserDto user = getUserByEmail(userDto.getEmail());
        if (user != null && user.getEmail() != null) {
            System.out.println("Invalid Email....");
            throw new RuntimeException("Email already register,Use different email.");
        }

        UserDto savedUser = userDao.createUser(userDto);
        if (savedUser != null) {
            try {
                String otp = OTPGenerator.generateOtp();
                savedUser.setOtp(otp);
                userDao.createUserOtp(savedUser); 
                EmailOTPService.sendOTP(savedUser.getEmail(), otp); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public UserDto getUserById(String id) {
        return userDao.getUserById(Integer.parseInt(id));
    }

    public UserDto getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public boolean verifyUser(String email, String otp, Timestamp verifyTime) {
        return userDao.verifyOTP(email, otp, verifyTime);
    }

    public UserDto login(String email, String password) {
        UserDto user = getUserByEmail(email);
        if (user == null) {
            System.out.println("Invalid Email....");
            
            return null;
        }
        
        boolean isValidUser = LMSUtils.verifyMD5(password, user.getPassword());
        if (isValidUser) {
            return user;
        } else {
            return null;
            
        }

    }

    public boolean saveUserProfile(String uid, String path) {
        return userDao.saveUserProfile(Integer.parseInt(uid), path);
    }

    public String getUserProfilePath(String uid) {

        return userDao.getUserProfilePath(Integer.parseInt(uid));
    }

    public boolean sendPasswordResetOtp(String email) {
        System.out.println("Processing sendPasswordResetOtp for: " + email);
        UserDto user = userDao.getUserByEmail(email);
        if (user != null) {
            String otp = OTPGenerator.generateOtp();
            System.out.println("Generated OTP: " + otp + " for User ID: " + user.getId());
            if (userDao.createPasswordResetOtp(user.getId(), otp)) {
                return EmailOTPService.sendOTP(email, otp);
            } else {
                System.out.println("Failed to create OTP in database for User ID: " + user.getId());
            }
        } else {
            System.out.println("User not found for email: " + email);
        }
        return false;
    }

    public boolean verifyResetOtp(String email, String otp) {
        System.out.println("UserService: Verifying OTP for " + email);
        return userDao.verifyResetOTP(email, otp);
    }

    public boolean updatePassword(String email, String newPassword) {
        UserDto user = userDao.getUserByEmail(email);
        if (user != null) {
            String hashedPassword = LMSUtils.md5Hash(newPassword);
            return userDao.updatePassword(user.getId(), hashedPassword);
        }
        return false;
    }
}
