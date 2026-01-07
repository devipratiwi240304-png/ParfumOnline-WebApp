/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @author Devi Pratiwi
 */
public class PasswordUtil {
     // Method untuk hash password dengan salt
    public static String hashPassword(String password) {
        try {
            // Generate random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            // Hash password dengan salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            // Gabungkan salt dan hashed password
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);
            
            return Base64.getEncoder().encodeToString(combined);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    // Method untuk verifikasi password
    public static boolean verifyPassword(String password, String storedHash) {
        // Validasi input
        if (password == null || storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        
        try {
            // Decode stored hash
            byte[] combined = Base64.getDecoder().decode(storedHash);
            
            // Validasi panjang hash (minimal 16 bytes untuk salt + hash)
            if (combined.length <= 16) {
                // Hash tidak valid, mungkin password plain text - coba compare langsung
                return password.equals(storedHash);
            }
            
            // Ekstrak salt dan hash dari stored hash
            byte[] salt = new byte[16];
            byte[] storedPasswordHash = new byte[combined.length - 16];
            System.arraycopy(combined, 0, salt, 0, 16);
            System.arraycopy(combined, 16, storedPasswordHash, 0, combined.length - 16);
            
            // Hash password yang dimasukkan dengan salt yang sama
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            // Bandingkan hasil hash
            return MessageDigest.isEqual(hashedPassword, storedPasswordHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error verifying password", e);
        } catch (IllegalArgumentException e) {
            // Base64 decode error - mungkin plain text password
            return password.equals(storedHash);
        }
    }
    
    // Method sederhana untuk MD5 (jika diperlukan)
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    } 
}
