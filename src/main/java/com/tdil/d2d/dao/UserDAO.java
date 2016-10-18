package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.User;

public interface UserDAO {
	
	public User getById(Class<User> aClass, long id) throws DAOException;
	
	public User getUserByUsername(String username) throws DAOException;

	public void save(User user) throws DAOException;

	public User getLastLoginUser() throws DAOException;

	public User getUserByEmail(String email) throws DAOException;

	public User getUserByMobilePhone(String mobilePhone) throws DAOException;

}
