package com.tdil.d2d.service;

import com.tdil.d2d.persistence.NotificationType;

public interface NotificationService {

	public void sendNotification(NotificationType notificationType, String title, String message, String regId);
}
//notificationService.sendNotification(NotificationType.MATCH, null, “titulo”, “mesnaje”, “12312312332312312312312312”);