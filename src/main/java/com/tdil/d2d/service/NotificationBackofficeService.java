package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.NotificationBackofficeDTO;
import com.tdil.d2d.persistence.Notification;

public interface NotificationBackofficeService {
	boolean sendNotification(NotificationBackofficeDTO notificationBackofficeDTO);

	public List<Notification> getAllNotifications();
	
}
//notificationService.sendNotification(NotificationType.MATCH, null, “titulo”, “mesnaje”, “12312312332312312312312312”);