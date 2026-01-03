package com.library.dao;

import com.library.model.User;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for User operations
 */
public interface UserDAO {

    /**
     * Create a new user
     * 
     * @param user User object to create
     * @return Generated user ID
     * @throws SQLException if database error occurs
     */
    int createUser(User user) throws SQLException;

    /**
     * Get user by ID
     * 
     * @param userId User ID
     * @return User object or null if not found
     * @throws SQLException if database error occurs
     */
    User getUserById(int userId) throws SQLException;

    /**
     * Get user by email
     * 
     * @param email User email
     * @return User object or null if not found
     * @throws SQLException if database error occurs
     */
    User getUserByEmail(String email) throws SQLException;

    /**
     * Get all users
     * 
     * @return List of all users
     * @throws SQLException if database error occurs
     */
    List<User> getAllUsers() throws SQLException;

    /**
     * Get users by role
     * 
     * @param role User role (ADMIN or LIBRARIAN)
     * @return List of users with specified role
     * @throws SQLException if database error occurs
     */
    List<User> getUsersByRole(String role) throws SQLException;

    /**
     * Update user
     * 
     * @param user User object with updated data
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean updateUser(User user) throws SQLException;

    /**
     * Delete user
     * 
     * @param userId User ID to delete
     * @return true if deletion successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean deleteUser(int userId) throws SQLException;

    /**
     * Activate/Deactivate user
     * 
     * @param userId   User ID
     * @param isActive Active status
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean setUserActiveStatus(int userId, boolean isActive) throws SQLException;

    /**
     * Authenticate user
     * 
     * @param email    User email
     * @param password Plain text password
     * @return User object if authentication successful, null otherwise
     * @throws SQLException if database error occurs
     */
    User authenticateUser(String email, String password) throws SQLException;

    /**
     * Update user password
     * 
     * @param userId          User ID
     * @param newPasswordHash New password hash
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean updatePassword(int userId, String newPasswordHash) throws SQLException;

    /**
     * Check if email exists
     * 
     * @param email Email to check
     * @return true if email exists, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean emailExists(String email) throws SQLException;
}
