package com.serendipity.util;

import com.serendipity.dao.DAOUser;
import com.serendipity.model.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * 
 * A utility class for hashing and salting passwords
 * 
 * The stored password looks like this : Hash(hash(hash(hash(……….hash(password||salt)))))))))))))))
 * 
 * @author vladimir
 */
public class PasswordUtil {
    
    private final static int ITERATION_NUMBER = 1000;
    
    public String[] securePasswordOnCreateOrUpdate(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // Create an array that will return password and salt
        String[] securityArray = new String[2];
        
        // Uses a secure Random not a simple Random
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        // Salt generation 64 bits long
        byte[] bSalt = new byte[8];
        random.nextBytes(bSalt);
        // Digest computation
        byte[] bDigest = getHash(ITERATION_NUMBER,password,bSalt);
        String sDigest = byteToBase64(bDigest);
        String sSalt = byteToBase64(bSalt);
        
        securityArray[0] = sDigest;
        securityArray[1] = sSalt;
        
        return securityArray;        
    }
    
    public boolean authenticate(String username, String password)
                        throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException{ 
        // Compute the new DIGEST
        if(password.trim().length() <= 0 || username.trim().length() <= 0) {
            return false;
        }
        
        DAOUser daoUser = new DAOUser();
        User user = daoUser.getUserByUsername(username);
        if(user == null || user.getSalt().trim().length() <= 0 || user.getSalt().trim() == null ||
            user.getPassword().trim().length() <= 0 || user.getPassword().trim() == null) {
            return false;
        }
        
        byte[] proposedDigest = getHash(ITERATION_NUMBER, password, base64ToByte(user.getSalt()));
        return user.getPassword().equals(byteToBase64(proposedDigest));
   }
    
    
    /**
    * From a password, a number of iterations and a salt,
    * returns the corresponding digest
    * @param iterationNb int The number of iterations of the algorithm
    * @param password String The password to encrypt
    * @param salt byte[] The salt
    * @return byte[] The digested password
    * @throws NoSuchAlgorithmException If the algorithm doesn't exist
     * @throws java.io.UnsupportedEncodingException
    */
    public byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < iterationNb; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }
   
    /**
     * From a byte[] returns a base 64 representation
     * @param data byte[]
     * @return String
     */
    public static String byteToBase64(byte[] data){
        return new String(Base64.encodeBase64(data));
    }
    
    /**
     * From a base 64 representation, returns the corresponding byte[] 
     * @param data String The base64 representation
     * @return byte[]
     * @throws IOException
     */
    public static byte[] base64ToByte(String data) throws IOException {
        return Base64.decodeBase64(data);
    }
}