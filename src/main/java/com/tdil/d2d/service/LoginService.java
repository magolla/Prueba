package com.tdil.d2d.service;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Login;

public interface LoginService {

	public Login getLogin(String authToken) throws ServiceException;

	public String setLogin(String userName, String password) throws ServiceException;
	
}
