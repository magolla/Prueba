package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.Role;

public interface BOUserDAO {
	
	public BOUser findByEmail(String email) throws DAOException;
	
	public List<BOUser> getAll() throws DAOException;
	
	public List<Role> getAllRoles() throws DAOException;

	public BOUser find(long userId)  throws DAOException;

	public Role findRole(Long roleId)  throws DAOException;

	public void save(BOUser user)  throws DAOException;

}
