package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.exceptions.ServiceException;

public interface GeoService {

	public void initDbWithTestData() throws ServiceException;

	public List<GeoLevelDTO> search(String text) throws ServiceException;

	public List<GeoLevelDTO> listGeoLevel2() throws ServiceException;
}
