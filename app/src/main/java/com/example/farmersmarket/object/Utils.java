package com.example.farmersmarket.object;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static final String ENCRYPT_ALGORITHM = "SHA-256";

    /**
     * Encrypt password base on ENCRYPT_ALGORITHM
     * @param password input password
     * @return encrypted password
     */

    public static String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
