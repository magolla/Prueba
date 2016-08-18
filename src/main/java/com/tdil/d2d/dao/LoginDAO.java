package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Login;

public interface LoginDAO {
	
	public Login getLogin(String authToken) throws DAOException;

	public void save(Object user) throws DAOException;

}
