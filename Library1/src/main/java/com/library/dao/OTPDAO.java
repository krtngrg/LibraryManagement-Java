package com.library.dao;

import com.library.model.OTP;
import java.sql.SQLException;

/**
 * DAO interface for OTP operations
 */
public interface OTPDAO {

    /**
     * Create a new OTP
     * 
     * @param otp OTP object to create
     * @return Generated OTP ID
     * @throws SQLException if database error occurs
     */
    int createOTP(OTP otp) throws SQLException;

    /**
     * Get OTP by email and purpose
     * 
     * @param email   Email address
     * @param purpose Purpose (SIGNUP or FORGOT_PASSWORD)
     * @return OTP object or null if not found
     * @throws SQLException if database error occurs
     */
    OTP getOTPByEmailAndPurpose(String email, String purpose) throws SQLException;

    /**
     * Verify OTP
     * 
     * @param email   Email address
     * @param otpCode OTP code to verify
     * @param purpose Purpose (SIGNUP or FORGOT_PASSWORD)
     * @return true if OTP is valid and verified, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean verifyOTP(String email, String otpCode, String purpose) throws SQLException;

    /**
     * Mark OTP as verified
     * 
     * @param otpId OTP ID
     * @return true if update successful, false otherwise
     * @throws SQLException if database error occurs
     */
    boolean markOTPAsVerified(int otpId) throws SQLException;

    /**
     * Delete expired OTPs
     * 
     * @return Number of deleted OTPs
     * @throws SQLException if database error occurs
     */
    int deleteExpiredOTPs() throws SQLException;

    /**
     * Delete OTPs by email and purpose
     * 
     * @param email   Email address
     * @param purpose Purpose
     * @return Number of deleted OTPs
     * @throws SQLException if database error occurs
     */
    int deleteOTPsByEmailAndPurpose(String email, String purpose) throws SQLException;
}
