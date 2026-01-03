package service;

import constants.UserStatus;
import dto.UserDto;
import mailservice.EmailOTPService;
import model.UserModel;
import util.LMSUtils;
import util.OTPGenerator;

import java.sql.Timestamp;

public class UserService {
    public UserModel userModel = new UserModel();

    public boolean signup(UserDto userDto) {
        String hashedPassword = LMSUtils.md5Hash(userDto.getPassword());
        userDto.setPassword(hashedPassword);
        UserDto user = getUserByEmail(userDto.getEmail());
        if (user != null && user.getEmail() != null) {
            System.out.println("Invalid Email....");
            throw new RuntimeException("Email already register,Use different email.");
        }

        UserDto savedUser = userModel.createUser(userDto);
        if (savedUser != null) {
            try {
                String otp = OTPGenerator.generateOtp();
                savedUser.setOtp(otp);
                userModel.createUserOtp(savedUser);             // save otp to database
                EmailOTPService.sendOTP(savedUser.getEmail(), otp);    //send otp to email
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public UserDto getUserById(String id) {
        return userModel.getUserById(id);
    }

    public UserDto getUserByEmail(String email) {
        return userModel.getUserByEmail(email);
    }

    public boolean verifyUser(String email, String otp, Timestamp verifyTime) {
        return userModel.verifyOTP(email, otp, verifyTime);
    }

    public UserDto login(String email, String password) {
        UserDto user = getUserByEmail(email);
        if (user == null) {
            System.out.println("Invalid Email....");
//            throw new RuntimeException("Invalid Email.");
            return null;
        }
//        if (!user.getStatus().equalsIgnoreCase(String.valueOf(UserStatus.VERIFIED))) {
//            System.out.println("Email verification pending.");
//            throw new RuntimeException("Email verification pending.");
//        }
        boolean isValidUser = LMSUtils.verifyMD5(password, user.getPassword());
        if (isValidUser) {
            return user;
        } else {
            return null;
//            throw new RuntimeException("Incorrect password.");
        }

    }

    public void verifyUser(UserDto userDto) {

    }

    public void modifyUser(UserDto userDto) {

    }

    public void disableUser(int id) {

    }
}
