package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.dto.SystemPropertyDTO;
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

	@Override
	public List<SystemPropertyDTO> getSystemPropertiesByKeys(List<String> keys) throws DTDException{
		try {
			List<SystemPropertyDTO> propertiesDTO = new ArrayList<>();
			
			List<SystemProperty> properties = systemPropertyDAO.getSystemPropertiesByKeys(keys);
			for (SystemProperty systemProperty : properties) {
				propertiesDTO.add(toDTO(systemProperty));
			}
			
			return propertiesDTO;
		} catch (DAOException e) {
			throw new DTDException(ExceptionDefinition.DTD_2007, e);
		}
	}

	private SystemPropertyDTO toDTO(SystemProperty s) {
		SystemPropertyDTO r = new SystemPropertyDTO();
		r.setId(s.getId());
		r.setKey(s.getKey());
		r.setValue(s.getValue());
		return r;
	}
}
