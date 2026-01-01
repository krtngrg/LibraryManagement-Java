package util;

public class OTPGenerator {

    public static String generateOtp() {
        int randomPin = (int) (Math.random() * 9000) + 1000;
        String otp = String.valueOf(randomPin);
        return otp;
    }
}
