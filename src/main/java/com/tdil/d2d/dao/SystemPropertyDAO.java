package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.SystemProperty;

public interface SystemPropertyDAO {

	public SystemProperty getSystemPropertyByKey(String key) throws DAOException;

}
