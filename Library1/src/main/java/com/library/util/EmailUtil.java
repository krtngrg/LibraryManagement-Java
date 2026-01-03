package com.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Email utility class for sending emails using JavaMail API
 * Used for sending OTP verification emails
 */
public class EmailUtil {

    private static String SMTP_HOST;
    private static String SMTP_PORT;
    private static String SMTP_USERNAME;
    private static String SMTP_PASSWORD;
    private static String FROM_EMAIL;
    private static String FROM_NAME;
    private static boolean SMTP_AUTH;
    private static boolean SMTP_STARTTLS;

    // Static block to load email configuration
    static {
        loadEmailConfig();
    }

    /**
     * Load email configuration from config.properties file
     */
    private static void loadEmailConfig() {
        Properties props = new Properties();
        try (InputStream input = EmailUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Unable to find config.properties for email configuration");
                return;
            }

            props.load(input);
            SMTP_HOST = props.getProperty("email.smtp.host");
            SMTP_PORT = props.getProperty("email.smtp.port");
            SMTP_USERNAME = props.getProperty("email.smtp.username");
            SMTP_PASSWORD = props.getProperty("email.smtp.password");
            FROM_EMAIL = props.getProperty("email.from");
            FROM_NAME = props.getProperty("email.from.name");
            SMTP_AUTH = Boolean.parseBoolean(props.getProperty("email.smtp.auth", "true"));
            SMTP_STARTTLS = Boolean.parseBoolean(props.getProperty("email.smtp.starttls.enable", "true"));

            System.out.println("Email configuration loaded successfully");

        } catch (IOException e) {
            System.err.println("Error loading email configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Send OTP verification email
     * 
     * @param toEmail Recipient email address
     * @param otpCode OTP code to send
     * @param purpose Purpose of OTP (SIGNUP or FORGOT_PASSWORD)
     * @return true if email sent successfully, false otherwise
     */
    public static boolean sendOTPEmail(String toEmail, String otpCode, String purpose) {
        String subject = getSubject(purpose);
        String body = getEmailBody(otpCode, purpose);
        return sendEmail(toEmail, subject, body);
    }

    /**
     * Send a generic email
     * 
     * @param toEmail Recipient email address
     * @param subject Email subject
     * @param body    Email body (HTML supported)
     * @return true if email sent successfully, false otherwise
     */
    public static boolean sendEmail(String toEmail, String subject, String body) {
        // Configure mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", SMTP_STARTTLS);

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
            return true;

        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get email subject based on purpose
     */
    private static String getSubject(String purpose) {
        if ("SIGNUP".equals(purpose)) {
            return "Verify Your Email - Library Management System";
        } else if ("FORGOT_PASSWORD".equals(purpose)) {
            return "Password Reset OTP - Library Management System";
        }
        return "OTP Verification - Library Management System";
    }

    /**
     * Get email body HTML based on purpose
     */
    private static String getEmailBody(String otpCode, String purpose) {
        String message = "";
        if ("SIGNUP".equals(purpose)) {
            message = "Thank you for signing up! Please use the following OTP to verify your email address:";
        } else if ("FORGOT_PASSWORD".equals(purpose)) {
            message = "You requested to reset your password. Please use the following OTP to proceed:";
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }" +
                ".content { background-color: #f9f9f9; padding: 30px; border: 1px solid #ddd; }" +
                ".otp-box { background-color: #fff; border: 2px dashed #4CAF50; padding: 20px; text-align: center; margin: 20px 0; }"
                +
                ".otp-code { font-size: 32px; font-weight: bold; color: #4CAF50; letter-spacing: 5px; }" +
                ".footer { text-align: center; padding: 20px; color: #777; font-size: 12px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>Library Management System</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p>" + message + "</p>" +
                "<div class='otp-box'>" +
                "<div class='otp-code'>" + otpCode + "</div>" +
                "</div>" +
                "<p><strong>Important:</strong></p>" +
                "<ul>" +
                "<li>This OTP is valid for 10 minutes</li>" +
                "<li>Do not share this OTP with anyone</li>" +
                "<li>If you didn't request this, please ignore this email</li>" +
                "</ul>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>This is an automated email. Please do not reply.</p>" +
                "<p>&copy; 2026 Library Management System. All rights reserved.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}
