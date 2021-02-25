package com.cratediggers.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides a static method used to hash strings.
 */
public class HashGenerator {
	/**
	 * Ensures hashGenerator objects cannot be constructed.
	 */
	private HashGenerator() {
   	 
    }
 
    /**
     * Applies SHA-512 algorithm to hash a string.
     * @param message to be hashed
     * @return Hashed message
     * @throws HashGenerationException
     */
    public static String generateHash(String message) throws HashGenerationException {
        return hashString(message, "SHA-512");
    }
 
    /**
     * Applies a provided algorithm to hash a string.
     * @param message to be hashed
     * @param algorithm applied to message to generate its hash
     * @return Hashed message
     * @throws HashGenerationException
     */
    private static String hashString(String message, String algorithm)
            throws HashGenerationException {
 
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
 
            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new HashGenerationException(
                    "Could not generate hash from String", ex);
        }
    }
 
    /**
     * Converts an array of bytes to a hexadecimal string
     * @param arrayBytes to be converted
     * @return Converted hexadecimal string
     */
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

}
