package com.tdil.d2d.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.controller.api.dto.ContactMotiveDTO;
import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.dao.BOUserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.ContactMotive;
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
		
		return result;
	}

	
}
