package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Points;

public interface PointsDAO {
	
	public void save(Points points) throws DAOException;

}
