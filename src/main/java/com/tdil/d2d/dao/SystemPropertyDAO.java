package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.SystemProperty;

public interface SystemPropertyDAO {

	public SystemProperty getSystemPropertyByKey(String key) throws DAOException;

	public List<SystemProperty> getSystemPropertiesByKeys(List<String> keys) throws DAOException;
}
