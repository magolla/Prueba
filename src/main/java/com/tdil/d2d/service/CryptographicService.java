package com.tdil.d2d.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public interface CryptographicService {

	public String encrypt(String text, String password, String salt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
		IllegalBlockSizeException, BadPaddingException;
	
	public String decrypt(String text, String passsword, String salt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
    	IllegalBlockSizeException, BadPaddingException;
}
