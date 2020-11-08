package com.example.farmersmarket.object;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
    public static final String ENCRYPT_ALGORITHM = "SHA-256";

    /**
     * Encrypt password base on ENCRYPT_ALGORITHM
     * @param password input password
     * @return encrypted password
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
