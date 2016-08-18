package com.tdil.d2d.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequest;
import com.tdil.d2d.controller.api.request.RegistrationResponse;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.security.RuntimeContext;
import com.tdil.d2d.service.AndroidNotificationService;
import com.tdil.d2d.service.CryptographicService;
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
	
	@Override
	public User getUserByUsername(String username) throws ServiceException {
		try {
			return this.userDAO.getUserByUsername(username);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public RegistrationResponse register(RegistrationRequest registrationRequest) throws ServiceException {
		try {
			RegistrationResponse response = new RegistrationResponse(HttpStatus.CREATED.value());
			User user = new User();
			user.setCreationDate(new Date());
			user.setUsername(registrationRequest.getUsername());
			user.setFirstname(registrationRequest.getFirstname());
			user.setLastname(registrationRequest.getLastname());
			user.setEmail(registrationRequest.getEmail());
			user.setBirthdate(registrationRequest.getBirthdate());
			user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			user.setEnabled(true);
			user.setDeviceId(cryptographicService.encrypt(registrationRequest.getDeviceId(), "", user.getSalt()));
			user.setPhoneNumber(cryptographicService.encrypt(registrationRequest.getPhoneNumber(), "", user.getSalt()));
			this.userDAO.save(user);
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
	public void updateLastLoginDate() throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setLastLoginDate(new Date());
			this.userDAO.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
