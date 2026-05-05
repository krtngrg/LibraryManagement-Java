package mailservice;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import util.OTPGenerator;
import java.util.Properties;

public class EmailOTPService {
    static final String fromEmail = "";   // fill the blank with your sending email
    static final String appPassword = "";       // generate app password from the mail account
    public static boolean sendOTP(String toEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + "\n\n" +
                    "This OTP is valid for 5 minutes.\n" +
                    "Do not share it with anyone.");

            Transport.send(message);
            System.out.println("OTP sent successfully to " + toEmail);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendOverdueNotice(String toEmail, String bookTitle, String dueDate) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("OVERDUE: Library Book Return Reminder");
            message.setText("Dear Student,\n\n" +
                    "This is a reminder that the book '" + bookTitle + "' was due on " + dueDate + ".\n" +
                    "Please return it as soon as possible to avoid further penalties.\n\n" +
                    "Regards,\nLibrary Management System");

            Transport.send(message);
            System.out.println("Overdue notice sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendReservationReadyNotice(String toEmail, String bookTitle) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("BOOK AVAILABLE: Your Reserved Book is Ready");
            message.setText("Dear Student,\n\n" +
                    "The book '" + bookTitle + "' you reserved is now available.\n" +
                    "We have placed it on hold for you for the next 2 days.\n" +
                    "Please visit the library to issue it. If not issued within 48 hours, the reservation will expire.\n\n"
                    +
                    "Regards,\nLibrary Management System");

            Transport.send(message);
            System.out.println("Reservation availability notice sent to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
