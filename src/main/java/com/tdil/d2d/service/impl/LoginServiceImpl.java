package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.LoginDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Login;
import com.tdil.d2d.service.LoginService;

@Transactional
@Service
public class LoginServiceImpl implements LoginService {
    
    @Autowired
    private LoginDAO loginDAO;

	@Override
	public Login getLogin(String authToken) throws ServiceException {
		try {
			return loginDAO.getLogin(authToken);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String setLogin(String userName, String password) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
