package com.tdil.d2d.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequest;
import com.tdil.d2d.controller.api.response.RegistrationResponse;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.security.RuntimeContext;
import com.tdil.d2d.service.AndroidNotificationService;
import com.tdil.d2d.service.CryptographicService;
import com.tdil.d2d.service.EmailService;
import com.tdil.d2d.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CryptographicService cryptographicService;
	
	@Autowired
	private AndroidNotificationService androidNotificationService;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public User getUserByUsername(String username) throws ServiceException {
		try {
			return this.userDAO.getUserByUsername(username);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public User getUserByEmail(String email) throws ServiceException {
		try {
			return this.userDAO.getUserByEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) throws ServiceException {
		try {
			xxx TODO
			RegistrationResponse response = new RegistrationResponse(HttpStatus.CREATED.value());
			User user = new User();
			user.setCreationDate(new Date());
			user.setEmail(registrationRequest.getEmail());
			user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			user.setEnabled(true);
			user.setDeviceId(cryptographicService.encrypt(registrationRequest.getDeviceId(), "", user.getSalt()));
			user.setPhoneNumber(cryptographicService.encrypt(registrationRequest.getPhoneNumber(), "", user.getSalt()));
			user.setEmailValidated(false);
			user.setEmailHash(RandomStringUtils.randomAlphanumeric(4));
			this.userDAO.save(user);
			
			// TODO ENVIAR EMAIL DE VALIDACION
			
			String body = "Para terminar la registracion use el siguiente codigo en la app o cliquea el siguiente link " + user.getEmailHash();
			emailService.sendEmail(registrationRequest.getEmail(), EmailServiceImpl.defaultFrom, "Registracion", body);
			
			return response;
		} catch (IllegalBlockSizeException | BadPaddingException | DAOException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean updateAndroidRegId(AndroidRegIdRequest androidRegIdRequest) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setAndroidRegId(androidRegIdRequest.getAndroidRegId());
			this.userDAO.save(user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean updateIOsPushId(IOsPushIdRequest iOsPushIdRequest) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setIosPushId(iOsPushIdRequest.getIosPushId());
			this.userDAO.save(user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
		
	@Override
	public void updateLastLoginDate() throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setLastLoginDate(new Date());
			this.userDAO.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean validateEmail(String email, String hash) throws ServiceException {
		try {
			User user = this.userDAO.getUserByEmail(email);
			if (user == null) {
				return false;
			}
			if (user.getEmailHash().equals(hash)) {
				user.setEmailValidated(true);
				this.userDAO.save(user);
				return true;
			} else {
				return false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
