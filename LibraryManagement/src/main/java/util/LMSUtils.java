package util;

import java.security.MessageDigest;

public class LMSUtils {
    public static String md5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyMD5(String input, String existingHash) {
        String newHash = md5Hash(input);
        return newHash.equals(existingHash);

//        int i = newHash.compareTo(existingHash);    error
//        return i > 0;
    }
}
