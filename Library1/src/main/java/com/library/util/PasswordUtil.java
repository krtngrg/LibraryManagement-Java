package com.library.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Password utility class for hashing and verifying passwords using BCrypt
 */
public class PasswordUtil {

    // BCrypt work factor (number of rounds)
    // Higher value = more secure but slower
    // 10 is a good balance between security and performance
    private static final int WORK_FACTOR = 10;

    /**
     * Hash a plain text password using BCrypt
     * 
     * @param plainPassword Plain text password
     * @return Hashed password
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORK_FACTOR));
    }

    /**
     * Verify a plain text password against a hashed password
     * 
     * @param plainPassword  Plain text password to verify
     * @param hashedPassword Hashed password from database
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // Invalid hash format
            System.err.println("Invalid password hash format: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if a password meets minimum security requirements
     * 
     * @param password Password to validate
     * @return true if password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // Add more validation rules as needed
        // e.g., must contain uppercase, lowercase, number, special character
        return true;
    }

    /**
     * Generate a random password (useful for temporary passwords)
     * 
     * @param length Length of the password
     * @return Random password
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        java.security.SecureRandom random = new java.security.SecureRandom();

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }
}
