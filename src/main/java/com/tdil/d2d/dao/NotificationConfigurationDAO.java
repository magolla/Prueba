package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.NotificationConfiguration;

public interface NotificationConfigurationDAO {
	
	public NotificationConfiguration getById(Class<NotificationConfiguration> aClass, long id) throws DAOException;

	public void save(NotificationConfiguration notificationConfiguration) throws DAOException;

	public NotificationConfiguration getByUser(long userId) throws DAOException;


}
