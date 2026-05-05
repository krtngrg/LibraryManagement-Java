package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import constants.UserStatus;
import dto.UserDto;
import util.DBConnection;

public class UserDao {

    public boolean updateUser(UserDto userDto) {
        String updateQuery = "UPDATE users SET first_name=?, last_name=?, email=?, phone=?, address=?, role=? WHERE user_id=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(updateQuery)) {
            ps.setString(1, userDto.getFirstname());
            ps.setString(2, userDto.getLastname());
            ps.setString(3, userDto.getEmail());
            ps.setString(4, userDto.getPhone());
            ps.setString(5, userDto.getAddress());
            ps.setString(6, userDto.getRole());
            ps.setInt(7, userDto.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserDto createUser(UserDto userDto) {
        UserDto createdUser = null;
        String insertQuery = "INSERT INTO users (first_name, last_name, email, phone, address, password, status, role) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING user_id";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null)
                return null;
            PreparedStatement ps = con.prepareStatement(insertQuery);

            ps.setString(1, userDto.getFirstname());
            ps.setString(2, userDto.getLastname());
            ps.setString(3, userDto.getEmail());
            ps.setString(4, userDto.getPhone());
            ps.setString(5, userDto.getAddress());
            ps.setString(6, userDto.getPassword()); 
            ps.setString(7, UserStatus.CREATED.name());
            
            
            String role = (userDto.getRole() != null) ? userDto.getRole() : "LIBRARIAN";
            ps.setString(8, role);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                createdUser = new UserDto();
                createdUser.setId(userId);
                createdUser.setFirstname(userDto.getFirstname());
                createdUser.setLastname(userDto.getLastname());
                createdUser.setEmail(userDto.getEmail());
                createdUser.setPhone(userDto.getPhone());
                createdUser.setAddress(userDto.getAddress());
                createdUser.setPassword(userDto.getPassword());
                createdUser.setStatus(String.valueOf(UserStatus.CREATED));
                createdUser.setRole(role);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return createdUser;
    }

    public boolean createUserOtp(UserDto userDto) {
        
        String updateStatus = "UPDATE users SET status=? WHERE user_id=?";
        String insertOTP = "INSERT INTO user_otps (user_id, otp_code, purpose, expires_at) VALUES (?, ?, 'verify', NOW() + INTERVAL '5 minutes')";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null)
                return false;

            
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(updateStatus)) {
                ps.setString(1, String.valueOf(UserStatus.VERIFICATION_PENDING));
                ps.setInt(2, userDto.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(insertOTP)) {
                ps2.setInt(1, userDto.getId());
                ps2.setString(2, userDto.getOtp());
                ps2.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserDto getUserByEmail(String email) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=?");
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
                res.setRole(rs.getString("role")); 
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDto getUserById(int uid) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id=?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserDto res = new UserDto();
                res.setId(uid);
                res.setEmail(rs.getString("email"));
                res.setFirstname(rs.getString("first_name"));
                res.setLastname(rs.getString("last_name"));
                res.setStatus(rs.getString("status"));
                res.setRole(rs.getString("role"));
                
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verifyOTP(String email, String otp, Timestamp verifyTime) {
        String selectQuery = "SELECT * FROM users u JOIN user_otps o ON u.user_id = o.user_id " +
                "WHERE u.email = ? AND o.otp_code = ? AND o.is_used = false " +
                "AND ? BETWEEN o.created_at AND o.expires_at";
        String updateUserQuery = "UPDATE users SET status = ? WHERE user_id = ?";
        String updateOtpQuery = "UPDATE user_otps SET is_used = true WHERE otp_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null)
                return false;
            con.setAutoCommit(false);

            int userId = -1;
            int otpId = -1;

            try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
                ps.setString(1, email);
                ps.setString(2, otp);
                ps.setTimestamp(3, verifyTime);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    userId = rs.getInt("user_id");
                    otpId = rs.getInt("otp_id");
                } else {
                    return false; 
                }
            }

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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveUserProfile(int uid, String path) {
        String insertQuery = "INSERT INTO user_profile_images (user_id, image_path, uploaded_at) VALUES (?, ?, NOW())";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setInt(1, uid);
            ps.setString(2, path);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserProfilePath(int uid) {
        String selectQuery = "SELECT image_path FROM user_profile_images WHERE user_id=? ORDER BY uploaded_at DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(selectQuery);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("image_path");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    
    

    public boolean deleteUser(int userId) {
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserDto> getUsers(String role) {
        List<UserDto> users = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email, phone, address, role, status FROM users";
        if (role != null && !role.isEmpty()) {
            sql += " WHERE role = ?";
        }

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            if (role != null && !role.isEmpty()) {
                ps.setString(1, role);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDto u = new UserDto();
                u.setId(rs.getInt("user_id"));
                u.setFirstname(rs.getString("first_name"));
                u.setLastname(rs.getString("last_name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<UserDto> searchUsers(String query, String role) {
        List<UserDto> users = new ArrayList<>();
        String sql = "SELECT user_id, first_name, last_name, email, phone, address, role, status FROM users " +
                "WHERE (first_name ILIKE ? OR last_name ILIKE ? OR email ILIKE ?)";
        if (role != null && !role.isEmpty()) {
            sql += " AND role = ?";
        }

        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            String keyword = "%" + query + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            if (role != null && !role.isEmpty()) {
                ps.setString(4, role);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDto u = new UserDto();
                u.setId(rs.getInt("user_id"));
                u.setFirstname(rs.getString("first_name"));
                u.setLastname(rs.getString("last_name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<dto.Reader> getReadersByRole(String role) {
        List<dto.Reader> readers = new ArrayList<>();
        String sql = "SELECT user_id, first_name || ' ' || last_name as name, email FROM users WHERE role = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dto.Reader r = new dto.Reader();
                r.setUserId(rs.getInt("user_id"));
                r.setName(rs.getString("name"));
                r.setEmail(rs.getString("email"));
                readers.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readers;
    }

    public boolean createPasswordResetOtp(int userId, String otp) {
        String insertOTP = "INSERT INTO user_otps (user_id, otp_code, purpose, expires_at) VALUES (?, ?, 'reset', NOW() + INTERVAL '10 minutes')";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(insertOTP)) {
            ps.setInt(1, userId);
            ps.setString(2, otp);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error in createPasswordResetOtp: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyResetOTP(String email, String otp) {
        System.out.println("UserDao: Checking DB for OTP [" + otp + "] and email [" + email + "]");
        String selectQuery = "SELECT o.otp_id FROM users u JOIN user_otps o ON u.user_id = o.user_id " +
                "WHERE u.email = ? AND o.otp_code = ? AND o.purpose = 'reset' AND o.is_used = false " +
                "AND CURRENT_TIMESTAMP <= o.expires_at";
        String updateOtpQuery = "UPDATE user_otps SET is_used = true WHERE otp_id = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
                ps.setString(1, email);
                ps.setString(2, otp);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int otpId = rs.getInt("otp_id");
                    System.out.println("UserDao: OTP matched (ID: " + otpId + ")");
                    try (PreparedStatement psUpdate = con.prepareStatement(updateOtpQuery)) {
                        psUpdate.setInt(1, otpId);
                        psUpdate.executeUpdate();
                    }
                    con.commit();
                    return true;
                } else {
                    System.out.println("UserDao: No matching OTP found in DB (or expired/used)");
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.out.println("UserDao Error in verifyResetOTP: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newPassword); 
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
