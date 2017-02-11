package com.tdil.d2d.dao;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Notification;

public interface NotificationDAO {
	
	public Notification getById(Class<Notification> aClass, long id) throws DAOException;

	public void save(Notification notification) throws DAOException;

	public Notification getByUser(long userId) throws DAOException;

	public Notification getByUserOffer(Long userId, Long offerId, String type);
}
