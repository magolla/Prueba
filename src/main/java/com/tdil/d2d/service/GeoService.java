package com.tdil.d2d.service;

import java.util.Collection;
import java.util.List;

import com.tdil.d2d.bo.dto.GeosDto;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;

public interface GeoService {

	public void initDbWithTestData() throws ServiceException;

	public List<GeoLevelDTO> search(String text) throws ServiceException;

	public List<GeoLevelDTO> listGeoLevel2() throws ServiceException;

	public List<GeosDto> getGeoByIndex(String length, String start, String search) throws DAOException;

	public int getGeoCount(String string) throws DAOException;

	void add(String geo2, String geo3, String geo4) throws DAOException;

	public void addGeo3(String geo3ProvinceId, String newRegionName) throws NumberFormatException, DAOException;


	public Collection<GeoLevelDTO> listGeoLevel3ByProvince(long provinceId) throws ServiceException;

	public void addGeo4(String cityGeo3Id, String cityName) throws NumberFormatException, DAOException;

	public void editProvince(long id, String name) throws DAOException;

	public void editRegion(long id, String name) throws DAOException;

	public void editCity(long id, String name) throws DAOException;

	void addBackend(String geo2, String geo3, String geo4) throws DAOException;
}
