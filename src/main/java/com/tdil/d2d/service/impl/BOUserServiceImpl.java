package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.bo.dto.RoleDTO;
import com.tdil.d2d.dao.BOUserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.Role;
import com.tdil.d2d.service.BOUserService;

@Transactional
@Service
public class BOUserServiceImpl implements BOUserService {

	private Logger logger = LoggerFactory.getLogger(BOUserServiceImpl.class);

	private static String STATE_ACTIVE_LABEL = "Activo";
	private static String STATE_INACTIVE_LABEL = "Inactivo";
	
	@Autowired
	private BOUserDAO userDAO;
	
	@Override
	public List<BOUserDTO> getAll() throws ServiceException {
		try {
			return toDtoList(this.userDAO.getAll());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<BOUserDTO> toDtoList(Collection<BOUser> list) {
		return list.stream().map(user -> toDto(user)).collect(Collectors.toList());
	}
	
	private  BOUserDTO toDto(BOUser user) {
		BOUserDTO result = new BOUserDTO();

		result.setId(user.getId());
		result.setEmail(user.getEmail());
		result.setName(user.getName());
		
		if(user.isActive()){
			result.setState(STATE_ACTIVE_LABEL);
		} else {
			result.setState(STATE_INACTIVE_LABEL);
		}
		
		List<RoleDTO> roles = new ArrayList<RoleDTO>();
		for(Role role : user.getRoles()){
			roles.add(new RoleDTO(role.getId(), role.getName(), role.getDescription()));
		}
		result.setRoles(roles);
		
		return result;
	}

	@Override
	public List<RoleDTO> getAllRoles() throws ServiceException {
		try {
			return toDtoRoleList(this.userDAO.getAllRoles());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<RoleDTO> toDtoRoleList(Collection<Role> list) {
		return list.stream().map(role -> toRoleDto(role)).collect(Collectors.toList());
	}
	
	private  RoleDTO toRoleDto(Role role) {
		return new RoleDTO(role.getId(), role.getName(), role.getDescription());
	}

	@Override
	public BOUserDTO find(long userId) throws ServiceException {
		try {
			return toDto(this.userDAO.find(userId));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	
}
