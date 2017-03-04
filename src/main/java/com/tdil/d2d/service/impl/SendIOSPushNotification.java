package com.tdil.d2d.service.impl;

import java.util.List;

import com.tdil.d2d.utils.LoggerManager;

import javapns.devices.Device;
import javapns.devices.Devices;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.Payload;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;

public class SendIOSPushNotification implements Runnable {

	private Payload payload;
	private int userId;
	private Object devices;

	public SendIOSPushNotification(Payload payload, int userId, Object devices) {
		super();
		this.setDevices(devices);
		this.setUserId(userId);
		this.setPayload(payload);
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Object getDevices() {
		return devices;
	}

	public void setDevices(Object devices) {
		this.devices = devices;
	}

	@Override
	public void run() {
		if (payload != null) {
			PushedNotifications notifications = new PushedNotifications();
			PushNotificationManager pushManager = new PushNotificationManager();
			try {
				// TODO pasar la clave del keystore a property
				AppleNotificationServer server = new AppleNotificationServerBasicImpl(
						IOSNotificationServiceImpl.getIosPushNoticationKeystoreLocation(), "doctodoc", false);

				if (IOSNotificationServiceImpl.getProxyConfiguration() != null) {
					// server.setProxy("1.234.45.50", 3128);
					server.setProxy(IOSNotificationServiceImpl.getProxyConfiguration().getServer(),
							IOSNotificationServiceImpl.getProxyConfiguration().getPort());
				}

				pushManager.initializeConnection(server);
				List<Device> deviceList = Devices.asDevices(devices);
				notifications.setMaxRetained(deviceList.size());
				for (Device device : deviceList) {
					try {
						BasicDevice.validateTokenFormat(device.getToken());
						PushedNotification notification = pushManager.sendNotification(device, payload, false);
						notifications.add(notification);
					} catch (InvalidDeviceTokenFormatException e) {
						//notifications.add(new PushedNotification(device, payload, e));
						//NotificationsService.unregisterIOSId(userId);
						LoggerManager.error(this, e);
					}
				}
			} catch (Exception e) {
				LoggerManager.error(this, e);
			} finally {
				try {
					pushManager.stopConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
