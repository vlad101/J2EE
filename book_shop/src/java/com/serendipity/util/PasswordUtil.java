package com.serendipity.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;

/**
 * A utility class for hashing passwords
 * @author vladimir
 */
public class PasswordUtil {
    
    /**
     * The method uses SHA-256 password encoding.
     * The code that uses this method:
     * 
     * try {
     *      String hashedPassword = PasswordUtil.hashPassword("password");
     * } catch(NoSuchAlgorithmException e) { System.out.println(e); }
     *
     * @param password
     * @return 
     * @throws java.security.NoSuchAlgorithmException 
     */
    public static String hashPassword(String password)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for(byte b : mdArray) {
            int v = b & 0xff;
            if(v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    
    /**
     * Salt the password.
     * @return 
     */
    public static String getSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        // Java 8 return Base64.getEncoder().encodeToString(saltBytes);
        return DatatypeConverter.printBase64Binary(saltBytes);
    }
    
    /**
     * Hashing and salting of the password
     * @param password
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String hashAndSaltPassword(String password) 
    throws NoSuchAlgorithmException {
        String salt = getSalt();
        return hashPassword(password + salt);
    }
    /**
     * A method that enforces password strength.
     * 
     * The code that uses this method:
     * try {
     *  checkPasswordStrength("password");
     *  System.out.prinln("Password is valid.");
     * } catch(Exception e) { System.out.println(e.getMessage()); }
     * 
     * @param password
     * @throws Exception 
     */
    public static void checkPasswordStrength(String password)
            throws Exception {
        if(password == null || password.trim().isEmpty()) {
            throw new Exception("Password cannot be empty.");
        } else if (password.length() < 8) {
            throw new Exception("Password is too short. " 
                                + "Must be at least 8 characters long");
        }
    }
    
    /**
     *  This code tests the functionality of this class.
     */
//    public static void main(String[] args) {
//        try {
//            System.out.println("Hash for 'password': " + hashPassword("password"));
//            System.out.println("Random salt: " + getSalt());
//            System.out.println("Salted hash for 'password': " + hashAndSaltPassword("password"));
//        } catch (NoSuchAlgorithmException ex) {
//            System.out.println(ex);
//        }
//    }
}