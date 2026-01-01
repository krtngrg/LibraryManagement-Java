package mailservice;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import util.OTPGenerator;

import java.util.Properties;

public class EmailOTPService {

    public static void sendOTP(String toEmail, String otp) {

        final String fromEmail = "trevorswallae@gmail.com"; // sender email
        final String appPassword = "bsssrbmkksoiucpo";  // Gmail App Password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("Your OTP Code");
            message.setText(
                    "Your OTP is: " + otp + "\n\n" +
                            "This OTP is valid for 5 minutes.\n" +
                            "Do not share it with anyone."
            );

            Transport.send(message);
            System.out.println("OTP sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


        public static void main(String[] args) {

            String otp = OTPGenerator.generateOtp();
            String userEmail = "arexxhuyn@gmail.com";

            EmailOTPService.sendOTP(userEmail, otp);
    }

}

