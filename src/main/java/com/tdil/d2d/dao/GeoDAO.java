package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;

public interface GeoDAO {
	
	public void save(Geo2 geo2) throws DAOException;
	public void save(Geo3 geo3) throws DAOException;
	public void save(Geo4 geo4) throws DAOException;
	
	public Geo2 get2ById(Class<Geo2> aClass, long id) throws DAOException;
	public Geo3 get3ById(Class<Geo3> aClass, long id) throws DAOException;
	public Geo4 get4ById(Class<Geo4> aClass, long id) throws DAOException;
	
	

}
