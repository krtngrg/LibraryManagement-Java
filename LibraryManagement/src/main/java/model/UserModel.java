package model;

import constants.UserStatus;
import dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class UserModel {
    public UserDto createUser(UserDto userDto) {
        UserDto createdUser = null;

        String insertQuery = "INSERT INTO users (first_name, last_name, email, phone, address, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING user_id"; // PostgreSQL RETURNING

        try (Connection con = DBConnection.getConnection()) {
            assert con != null;
            PreparedStatement ps = con.prepareStatement(insertQuery);

            ps.setString(1, userDto.getFirstname());
            ps.setString(2, userDto.getLastname());
            ps.setString(3, userDto.getEmail());
            ps.setString(4, userDto.getPhone());
            ps.setString(5, userDto.getAddress());

            // Hash the password before saving
            ps.setString(6, userDto.getPassword());

            // Set initial status
            ps.setString(7, UserStatus.CREATED.name());

            // Execute and get generated ID
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");

                // Build returned object
                createdUser = new UserDto();
                createdUser.setId(userId);
                createdUser.setFirstname(userDto.getFirstname());
                createdUser.setLastname(userDto.getLastname());
                createdUser.setEmail(userDto.getEmail());
                createdUser.setPhone(userDto.getPhone());
                createdUser.setAddress(userDto.getAddress());
                createdUser.setPassword(userDto.getPassword());
                createdUser.setStatus(String.valueOf(UserStatus.CREATED));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return createdUser;
    }

    public boolean createUserOtp(UserDto userDto) {
        boolean execute = true;
        String insertQuery = "update users set status=? where user_id=? ";
        String insertOTP = "INSERT INTO user_otps (user_id, otp_code, purpose, expires_at) VALUES (?, ?, 'verify', NOW() + INTERVAL '5 minutes') ";

        try (Connection con = DBConnection.getConnection()) {

            assert con != null;
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, String.valueOf(UserStatus.VERIFICATION_PENDING));
            ps.setInt(2, userDto.getId());
            ps.execute();

            PreparedStatement ps2 = con.prepareStatement(insertOTP);
            ps2.setInt(1, userDto.getId());
            ps2.setString(2, userDto.getOtp());
            ps2.execute();
            System.out.println("Line2");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return execute;
    }

    public UserDto getUserByEmail(String email) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE email=?"
            );
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserDto res = new UserDto();
                res.setId(rs.getInt("user_id"));
                res.setEmail(rs.getString("email"));
                res.setFirstname(rs.getString("first_name"));
                res.setLastname(rs.getString("last_name"));
                res.setPassword(rs.getString("password"));
                res.setStatus(rs.getString("status"));

                return res;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserDto getUserById(String id) {
        int uid= Integer.parseInt(id);
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE user_id=?"
            );
            ps.setInt(1, uid);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserDto res = new UserDto();
                res.setId(uid);
                res.setEmail(rs.getString("email"));
                res.setFirstname(rs.getString("first_name"));
                res.setLastname(rs.getString("last_name"));
//                res.setPassword(rs.getString("password"));
                res.setStatus(rs.getString("status"));
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean verifyOTP(String email, String otp, Timestamp verifyTime) {
        boolean success = false;

//        u.user_id, o.otp_id

        String selectQuery =
        "SELECT * " +
                        "FROM users u " +
                        "JOIN user_otps o ON u.user_id = o.user_id " +
                        "WHERE u.email = ? " +
                        "AND o.otp_code = ? " +
                        "AND o.is_used = false " +
                        "AND ? BETWEEN o.created_at AND o.expires_at";

        String updateUserQuery =
                "UPDATE users SET status = ? WHERE user_id = ?";

        String updateOtpQuery =
                "UPDATE user_otps SET is_used = true WHERE otp_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;

            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
                ps.setString(1, email);
                ps.setString(2, otp);
                ps.setTimestamp(3, verifyTime); // <-- passed date

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int otpId = rs.getInt("otp_id");

//                    Timestamp checktime = rs.getTimestamp("expires_at");
//                    System.out.println("expiry Time"+checktime);
//                    System.out.println("acutal time"+verifyTime);

                    try (PreparedStatement psUser = con.prepareStatement(updateUserQuery)) {
                        psUser.setString(1, UserStatus.VERIFIED.name());
                        psUser.setInt(2, userId);
                        psUser.executeUpdate();
                    }

                    try (PreparedStatement psOtp = con.prepareStatement(updateOtpQuery)) {
                        psOtp.setInt(1, otpId);
                        psOtp.executeUpdate();
                    }

                    con.commit();
                    success = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }


}
