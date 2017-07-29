package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.bo.dto.ResultDTO;
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
		result.setPassword("xxxxxxxx");//Show just 8 dots in the form
		result.setActive(user.isActive());
		
		
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
	public List<String> getAllRolesLabels() throws ServiceException {
		try {
			return toDtoLabelList(this.userDAO.getAllRoles());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	
	private List<String> toDtoLabelList(Collection<Role> list) {
		return list.stream().map(role -> toRoleLabel(role)).collect(Collectors.toList());
	}
	
	private  String toRoleLabel(Role role) {
		return role.getDescription();
	}
	

	@Override
	public BOUserDTO find(long userId) throws ServiceException {
		try {
			return toDto(this.userDAO.find(userId));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ResultDTO save(BOUserDTO dto)  throws ServiceException {
		try {
			
			if(StringUtils.isEmpty(dto.getEmail())){
				return ResultDTO.error("El email es obligatorio");
			}
			
			if(StringUtils.isEmpty(dto.getName())){
				return ResultDTO.error("El nombre es obligatorio");
			}
			
			if(dto.getRolesIds()==null || dto.getRolesIds().size()==0){
				return ResultDTO.error("Seleccionar por lo menos un rol");
			}
			
			BOUser userByEmail = userDAO.findByEmail(dto.getEmail());
			
			BOUser user;
			if(dto.getId()!=0){
				
				//Edit user
				user = userDAO.find(dto.getId());
				//Validate unique email
				if(userByEmail!=null && !userByEmail.getEmail().equals(user.getEmail())){
					return ResultDTO.error("El email ingresado ya pertenece a otro usuario.");
				}
				
				if(dto.isPasswordChanged()){
					if(StringUtils.isEmpty(dto.getPassword()) || dto.getPassword().length()<6){
						return ResultDTO.error("Password: La longitud mínima es de 6 caracteres");
					}
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
					user.setPassword(encoder.encode(dto.getPassword()));
				}
				
			} else {
				
				//Validate unique email
				if(userByEmail!=null){
					return ResultDTO.error("El email ingresado ya pertenece a otro usuario.");
				}
				
				if(StringUtils.isEmpty(dto.getPassword()) || dto.getPassword().length()<6){
					return ResultDTO.error("Password: La longitud mínima es de 6 caracteres");
				}
				
				//New User
				user = new BOUser();
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(dto.getPassword()));
			}
			
			user.setEmail(dto.getEmail());
			user.setName(dto.getName());
			user.setActive(dto.getActive());
			
			Set<Role> roles = new HashSet<Role>();
			if(dto.getRolesIds()!=null){
				for(Long roleId : dto.getRolesIds()){
					roles.add(userDAO.findRole(roleId));
				}
			}
			user.setRoles(roles);
			
			this.userDAO.save(user);
			return ResultDTO.ok(); 
		
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	
}
