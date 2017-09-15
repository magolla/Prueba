package com.tdil.d2d.service.impl;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.communication.ProxyConfiguration;
import com.tdil.d2d.dao.NotificationDAO;
import com.tdil.d2d.persistence.Notification;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.service.NotificationService;

import javapns.notification.PushNotificationBigPayload;

@Transactional
@Service("iosNotificationServiceImpl")
public class IOSNotificationServiceImpl implements NotificationService {

	public static final String CERTIFICATE_FILE_NAME = "Certificates.p12";

	private static ProxyConfiguration proxyConfiguration;

	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	public static ProxyConfiguration getProxyConfiguration() {
		return proxyConfiguration;
	}

	public static void setProxyConfiguration(ProxyConfiguration proxyConfiguration) {
		IOSNotificationServiceImpl.proxyConfiguration = proxyConfiguration;
	}
	
	@Autowired
	private NotificationDAO notificationDAO;

	@Override
	public void sendNotification(NotificationType notificationType, String regId) {
		try {

			PushNotificationBigPayload payload = PushNotificationBigPayload.complex();
			payload.addCustomAlertTitle(notificationType.getTitle());
			payload.addCustomAlertBody(notificationType.getMessage());
			payload.addCustomDictionary("acme", notificationType.getIntValue());

			executor.submit(new SendIOSPushNotification(payload, 0, regId));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	public static void send(int userId, int type, int level, String title, String message, String deviceId) {

	}

	public static String getIosPushNoticationKeystoreLocation() throws URISyntaxException {

		URL resource = IOSNotificationServiceImpl.class.getResource(IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME);

		return resource.getPath();
	}

	//-TODO agregar payload
	@Override
	public void sendNotification(Notification notification, NotificationType type) {
		try {

			Integer count = this.notificationDAO.getCountNotificationByUserId(notification.getUser().getId());
			
			PushNotificationBigPayload payload = PushNotificationBigPayload.complex();
			payload.addCustomAlertTitle(notification.getTitle());
			payload.addCustomAlertBody(notification.getMessage());
			payload.addBadge(count);
			

			if(type != null) {
				payload.addCustomDictionary("action", notification.getAction());
				payload.addCustomDictionary("action_id", notification.getActionId());
				
			}
			executor.submit(new SendIOSPushNotification(payload, 0, notification.getUser().getIosPushId()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
