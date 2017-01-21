package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.SessionService;

@Transactional
@Service
public class SessionServiceImpl implements SessionService {

	private final UserDAO userDAO;

	@Autowired
	public SessionServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User getUserLoggedIn() {
		try {
			return userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
		} catch (Exception e) {
			System.out.println(e);
			throw new DTDException(ExceptionDefinition.DTD_2001, e);
		}
	}
}
