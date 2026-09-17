 
package java_console_app1;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


// run this to encrypt pw for Spring_MVC_IRT_App, Safety app, SpringMVCSecurityJavaConfig app
// just replace password text in main() 
public class HashPassword {

	
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    //private static final String OUTPUT_FORMAT = "%-20s:%s";

    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) { 
        String algorithm = "SHA-256";

        // replace welcome with any password 
        String pText = "welcome";   // hash    280d44ab1e9f79b5cce2dd4f58f5fe91f0fbacdac9f7447dffc318ceb79f2d02
        //System.out.println(String.format(OUTPUT_FORMAT, "unencrypted password text: ", pText));
        System.out.println("unencrypted password text: " + pText);

        byte[] shaInBytes = HashPassword.digest(pText.getBytes(UTF_8), algorithm);
        //System.out.println(String.format(OUTPUT_FORMAT, algorithm + " hashed pw: ", bytesToHex(shaInBytes)));
        System.out.println("encrypted password : " + bytesToHex(shaInBytes));


    }
    


}
