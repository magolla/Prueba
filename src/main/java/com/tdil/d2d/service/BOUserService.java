package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.exceptions.ServiceException;

public interface BOUserService {

	public List<BOUserDTO> getAll() throws ServiceException;
}
