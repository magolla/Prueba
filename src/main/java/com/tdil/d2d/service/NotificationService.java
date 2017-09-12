package com.tdil.d2d.service;

import com.tdil.d2d.persistence.Notification;
import com.tdil.d2d.persistence.NotificationType;

public interface NotificationService {

	public void sendNotification(NotificationType notificationType, String regId);

	public void sendNotification(Notification notification,NotificationType notificationType);
}
//notificationService.sendNotification(NotificationType.MATCH, null, “titulo”, “mesnaje”, “12312312332312312312312312”);