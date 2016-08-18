package com.tdil.d2d.utils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public final class PasswordEncrypterUtil {

    /** Don't let anyone instantiate this class */
    private PasswordEncrypterUtil() throws InstantiationException {
        // Constructor will never run
        throw new InstantiationException("Instances of this type are forbidden");
    }

    /**
     * This method returns an encrypted string. It is encrypted by MD5 algorithm.
     *
     * @param string to encrypt
     * @return Encrypted string
     */
    /*
     * public static String getMD5Digest(String input) { return getMessageDigest(input, "MD5", "UTF-8"); }
     */

    /**
     * This method returns an encrypted string using specific algorithm and specific encoding.
     *
     * @param input Text to encrypt
     * @param algorithm Encryption algorithm
     * @param encoding encoding
     * @return Encrypted string
     */
    /*
     * public static String getMessageDigest(String input, String algorithm, String encoding) { try { MessageDigest
     * messageDigest = MessageDigest.getInstance(algorithm); // Encryption algorithm byte[] digest =
     * messageDigest.digest(input.getBytes(encoding)); return new String(Hex.encode(digest)); // Encrypted string }
     * catch (NoSuchAlgorithmException e) { return null; } catch (UnsupportedEncodingException e) { return null; } }
     */

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

    public static String encrypt(String text, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), 0, 16, "AES");
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] ciphertext = aes.doFinal(text.getBytes());
        return toHexString(ciphertext);
    }

    public static String decrypt(String text, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), 0, 16, "AES");
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aes.init(Cipher.DECRYPT_MODE, key);
        byte[] ciphertext = aes.doFinal(toByteArray(text));
        return new String(ciphertext);
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
