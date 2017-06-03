package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.BOUser;

public interface BOUserDAO {
	
	public BOUser getUserByEmail(String email) throws DAOException;
	
}
