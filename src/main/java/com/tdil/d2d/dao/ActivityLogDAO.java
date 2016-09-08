package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.ActivityLog;

public interface ActivityLogDAO {
	
	public ActivityLog getById(Class<ActivityLog> aClass, long id) throws DAOException;

	public void save(ActivityLog ActivityLog) throws DAOException;

	public List<ActivityLog> getLastLog(Long userId) throws DAOException;


}
