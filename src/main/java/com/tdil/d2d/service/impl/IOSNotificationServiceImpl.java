package com.tdil.d2d.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.communication.ProxyConfiguration;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.service.NotificationService;
import com.tdil.d2d.utils.ServiceLocator;

import javapns.notification.PushNotificationPayload;

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
	
	@Override
	public void sendNotification(NotificationType type, String title, String message, String regId) {
		PushNotificationPayload payload = PushNotificationPayload.combined(message, 0, null);
		// TODO ver como agregar a la notificación los datos custom del tipo
		// (type), nivel (level) y titulo (title).

		// limpio el deviceId de los posibles espacios que pueda tener
		regId = regId.replaceAll(" ", "");
		executor.submit(new SendIOSPushNotification(payload, 0, regId));
	}

	public static void send(int userId, int type, int level, String title, String message, String deviceId) {
		
	}

	public static String getIosPushNoticationKeystoreLocation() {
		return ServiceLocator.getTempPath() + "/" + IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME;
	}

}