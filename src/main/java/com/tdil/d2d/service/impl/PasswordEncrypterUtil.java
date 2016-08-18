package com.tdil.d2d.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.crypto.codec.Hex;

import com.tdil.d2d.utils.LoggerManager;



public abstract class PasswordEncrypterUtil {

    /**
     * This method returns an encrypted string. It is encrypted by MD5 algorithm.
     * 
     * @param input to encrypt
     * @return Encrypted string
     */
    public static String getMD5Digest(String input) {
        if (input == null) {
            return null;
        }
        return getMessageDigest(input, "MD5", "UTF-8");
    }

    public static String getSha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        return toHexString(result);
    }
    
    public static String getSha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        return toHexString(result);
    }

    /**
     * This method returns an encrypted string using specific algorithm and specific encoding.
     * 
     * @param input Text to encrypt
     * @param algorithm Encryption algorithm
     * @param encoding encoding
     * @return Encrypted string
     */
    public static String getMessageDigest(String input, String algorithm, String encoding) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm); // Encryption algorithm
            byte[] digest = messageDigest.digest(input.getBytes(encoding));
            return new String(Hex.encode(digest)); // Encrypted string
        } catch (NoSuchAlgorithmException e) {
            LoggerManager.error("Error encrypting user password using algorithm " + algorithm, e);
            return null;
        } catch (UnsupportedEncodingException e) {
        	LoggerManager.error(encoding + " not supported!", e);
            return null;
        }
    }

    public static String encrypt(String text, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(secretKey.getBytes());
        SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] ciphertext = aes.doFinal(text.getBytes());
        return toHexString(ciphertext);
    }

    public static String decrypt(String text, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(secretKey.getBytes());
        SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key);
        byte[] ciphertext = aes.doFinal(toByteArray(text));
        return new String(ciphertext);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
        IllegalBlockSizeException, BadPaddingException {
        
        System.out.println(encrypt("asdsds", "asdasd"));

    }


    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
