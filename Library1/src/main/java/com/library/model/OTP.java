package com.library.model;

import java.sql.Timestamp;

/**
 * OTP model class for email verification
 * Corresponds to the 'otp_verification' table in the database
 */
public class OTP {
    private int otpId;
    private String email;
    private String otpCode;
    private String purpose; // SIGNUP or FORGOT_PASSWORD
    private boolean isVerified;
    private Timestamp expiryTime;
    private Timestamp createdAt;

    // Default constructor
    public OTP() {
    }

    // Constructor with essential fields
    public OTP(String email, String otpCode, String purpose, Timestamp expiryTime) {
        this.email = email;
        this.otpCode = otpCode;
        this.purpose = purpose;
        this.expiryTime = expiryTime;
        this.isVerified = false;
    }

    // Getters and Setters
    public int getOtpId() {
        return otpId;
    }

    public void setOtpId(int otpId) {
        this.otpId = otpId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Timestamp expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Utility methods
    public boolean isExpired() {
        return this.expiryTime != null && this.expiryTime.before(new Timestamp(System.currentTimeMillis()));
    }

    public boolean isValid() {
        return !isVerified && !isExpired();
    }

    @Override
    public String toString() {
        return "OTP{" +
                "otpId=" + otpId +
                ", email='" + email + '\'' +
                ", purpose='" + purpose + '\'' +
                ", isVerified=" + isVerified +
                ", expiryTime=" + expiryTime +
                '}';
    }
}
