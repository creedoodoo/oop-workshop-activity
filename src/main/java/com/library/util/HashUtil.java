package com.library.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    /**
     * Hashes a plain-text password using SHA-256.
     * Returns a 64-character lowercase hex string.
     * Passwords are NEVER stored or transmitted in plain text.
     */
    public static String hashPassword(String plainText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(plainText.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /** Compares a plain-text input against a stored SHA-256 hash. */
    public static boolean verify(String plainText, String storedHash) {
        return hashPassword(plainText).equals(storedHash);
    }
}
