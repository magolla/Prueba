package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.SystemPropertyDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.persistence.SystemProperty;
import com.tdil.d2d.service.SystemPropertyService;

@Transactional
@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {

	private final SystemPropertyDAO systemPropertyDAO;
	
	@Autowired
	public SystemPropertyServiceImpl(SystemPropertyDAO systemPropertyDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
	}

	@Override
	public SystemProperty getSystemPropertyByKey(String key) {
		try {
			return systemPropertyDAO.getSystemPropertyByKey(key);
		} catch (DAOException e) {
			throw new DTDException(ExceptionDefinition.DTD_2004, e);
		}
	}


}
