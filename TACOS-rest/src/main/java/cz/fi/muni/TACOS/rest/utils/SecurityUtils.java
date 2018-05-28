package cz.fi.muni.TACOS.rest.utils;

import cz.fi.muni.TACOS.dto.UserDTO;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class SecurityUtils {

    public static final String AUTH_USER = "authenticated-user";
    public static final String COOKIE = "tacos-cookie-token";
    public static final String INIT_VECTOR;
    public static final String KEY;

    static {
        try(InputStream input = SecurityUtils.class.getResourceAsStream("/server.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            INIT_VECTOR = properties.getProperty("initVector");
            KEY = properties.getProperty("key");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Cookie generateCookie(UserDTO user) {
        String userData = user.getId() + ";" + user.getEmail();

        String token = encrypt(KEY, INIT_VECTOR, userData);
        Cookie cookie = new Cookie(COOKIE, token);
        cookie.setMaxAge(600);
        cookie.setHttpOnly(false);
        return cookie;
    }

    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec sKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec sKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
