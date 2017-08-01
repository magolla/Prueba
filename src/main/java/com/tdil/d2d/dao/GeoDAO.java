package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.persistence.GeoLevel;

public interface GeoDAO {
	
	public void save(Geo2 geo2) throws DAOException;
	public void save(Geo3 geo3) throws DAOException;
	public void save(Geo4 geo4) throws DAOException;
	
	public List<Geo2> listGeo2(String text) throws DAOException;
	public List<Geo3> listGeo3(String text) throws DAOException;
	public List<Geo4> listGeo4(String text) throws DAOException;
	
	public GeoLevel getGeoByIdAndLevel(long geoLevelId, int geoLevelLevel) throws DAOException;
	
	public List<Geo4> getListGeo4ByGeo2(Long geo2Id) throws DAOException;
	public List<Geo4> getListGeo4ByGeo3(Long geo3Id) throws DAOException;
	
	public List<Geo2> getListGeo2() throws DAOException;
}
