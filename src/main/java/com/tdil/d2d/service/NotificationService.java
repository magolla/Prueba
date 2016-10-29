package com.tdil.d2d.service;

import com.tdil.d2d.persistence.NotificationType;

public interface NotificationService {

	public void sendNotification(NotificationType type, String title, String message, String regId);
}
