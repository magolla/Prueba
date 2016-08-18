package com.tdil.d2d.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import com.tdil.d2d.service.CryptographicService;


@Service
public class CryptographicServiceImpl implements CryptographicService {

	
	public String encrypt(String text, String password, String salt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	    IllegalBlockSizeException, BadPaddingException {
	    return PasswordEncrypterUtil.encrypt(text, password + salt);
	}

    public String encrypt(String text, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
        IllegalBlockSizeException, BadPaddingException {
        return PasswordEncrypterUtil.encrypt(text, password);
    }
    
    public String decrypt(String text, String password, String salt) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
	    IllegalBlockSizeException, BadPaddingException {
	    return PasswordEncrypterUtil.decrypt(text, password + salt);
	}

    public String decrypt(String text, String password) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
        IllegalBlockSizeException, BadPaddingException {
        return PasswordEncrypterUtil.decrypt(text, password);
    }


//    public String getSignature(User user) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
//        IllegalBlockSizeException, BadPaddingException {
//        String beneficiary = "";
//        if (!StringUtils.isEmpty(user.getBeneficiary())) {
//            beneficiary = this.decrypt(cupon.getBeneficiary());
//        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String toSign = this.decrypt(cupon) + "-" + beneficiary + "-" + dateFormat.format(cupon.getCreationDate());
//        return PasswordEncrypterUtil.getSha256(toSign);
//    }

}
