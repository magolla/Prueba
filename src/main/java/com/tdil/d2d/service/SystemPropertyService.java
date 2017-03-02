package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.controller.api.dto.SystemPropertyDTO;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.persistence.SystemProperty;

public interface SystemPropertyService {

	SystemProperty getSystemPropertyByKey(String key);

	List<SystemPropertyDTO> getSystemPropertiesByKeys(List<String> keys) throws DTDException;

}
