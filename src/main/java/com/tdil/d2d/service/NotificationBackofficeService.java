package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.NotificationBackofficeDTO;
import com.tdil.d2d.controller.api.dto.NotificationDTO;

public interface NotificationBackofficeService {
	boolean sendNotification(NotificationBackofficeDTO notificationBackofficeDTO);

	public List<NotificationDTO> getAllNotifications();
	
}
//notificationService.sendNotification(NotificationType.MATCH, null, “titulo”, “mesnaje”, “12312312332312312312312312”);