package com.tdil.d2d.controller;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Notification;

public interface NotifService {
	
	public void sendNotification(Notification notification) throws ServiceException;

}
