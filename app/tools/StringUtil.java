package tools;

import java.security.MessageDigest;

public class StringUtil {
    public static String encrypt(String algo, String secret, String seed) {

        String ret = "";
        String se = secret + seed;

        byte[] defaultBytes = se.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(algo);
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            ret = hexString + "";
        } catch (Exception e) {

        }
        return ret;
    }
}
