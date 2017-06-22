package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.bo.dto.RoleDTO;
import com.tdil.d2d.exceptions.ServiceException;

public interface BOUserService {

	public List<BOUserDTO> getAll() throws ServiceException;
	
	public List<RoleDTO> getAllRoles() throws ServiceException;

	public BOUserDTO find(long userId) throws ServiceException;;
}
