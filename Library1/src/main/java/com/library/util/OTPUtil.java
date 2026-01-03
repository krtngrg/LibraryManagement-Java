package com.library.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * OTP utility class for generating and managing One-Time Passwords
 */
public class OTPUtil {

    private static final String NUMBERS = "0123456789";
    private static final int DEFAULT_OTP_LENGTH = 6;
    private static final Random random = new SecureRandom();

    /**
     * Generate a random OTP of specified length
     * 
     * @param length Length of OTP
     * @return Generated OTP as string
     */
    public static String generateOTP(int length) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        return otp.toString();
    }

    /**
     * Generate a 6-digit OTP (default)
     * 
     * @return Generated 6-digit OTP
     */
    public static String generateOTP() {
        return generateOTP(DEFAULT_OTP_LENGTH);
    }

    /**
     * Validate OTP format
     * 
     * @param otp            OTP to validate
     * @param expectedLength Expected length of OTP
     * @return true if OTP format is valid, false otherwise
     */
    public static boolean isValidOTPFormat(String otp, int expectedLength) {
        if (otp == null || otp.length() != expectedLength) {
            return false;
        }
        // Check if all characters are digits
        return otp.matches("\\d+");
    }

    /**
     * Validate 6-digit OTP format (default)
     * 
     * @param otp OTP to validate
     * @return true if OTP format is valid, false otherwise
     */
    public static boolean isValidOTPFormat(String otp) {
        return isValidOTPFormat(otp, DEFAULT_OTP_LENGTH);
    }

    /**
     * Get OTP expiry time in milliseconds from now
     * 
     * @param expiryMinutes Number of minutes until expiry
     * @return Expiry timestamp in milliseconds
     */
    public static long getExpiryTime(int expiryMinutes) {
        return System.currentTimeMillis() + (expiryMinutes * 60 * 1000L);
    }

    /**
     * Check if OTP has expired
     * 
     * @param expiryTime Expiry timestamp in milliseconds
     * @return true if expired, false otherwise
     */
    public static boolean isExpired(long expiryTime) {
        return System.currentTimeMillis() > expiryTime;
    }
}
