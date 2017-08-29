package com.tdil.d2d.service.impl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.communication.ProxyConfiguration;
import com.tdil.d2d.dao.NotificationDAO;
import com.tdil.d2d.persistence.Notification;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.service.NotificationService;
import com.tdil.d2d.utils.LoggerManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Transactional
@Service("androidNotificationServiceImpl")
public class AndroidNotificationServiceImpl implements NotificationService {

	@Value("${proxy.server}")
	private String proxyServer;

	@Value("${proxy.port}")
	private String proxyPort;

	@Value("${android.push.apiKey}")
	private String apikey;

	private ProxyConfiguration proxyConfiguration;

	private ExecutorService executor = Executors.newFixedThreadPool(5);

	public static String HTTPS = "https://fcm.googleapis.com/fcm/send";
	
	@Autowired
	private NotificationDAO notificationDAO;

	@Override
	public void sendNotification(NotificationType notificationType, String regId) {
		try {

			JSONObject request = new JSONObject();
			request.put("to",regId); 

			JSONObject data = new JSONObject();
			data.put("title", notificationType.getTitle());
			data.put("message", notificationType.getMessage());
			data.put("notification_type", notificationType.getIntValue());
			request.put("data",data);

			executor.submit(new SendAndroidPushNotification(request.toString(), regId, this));
		} catch (JSONException e) {
			LoggerManager.error(this, e);
		}
	}

	@Override
	public void sendNotification(Notification notification, NotificationType type) {

		try {

			Integer count = this.notificationDAO.getCoutNotificationByUserId(notification.getUser().getId());

			JSONObject request = new JSONObject();
			request.put("to",notification.getUser().getAndroidRegId()); 

			JSONObject data = new JSONObject();
			data.put("title", notification.getTitle());
			data.put("message", notification.getMessage());
			data.put("action", notification.getAction());
			data.put("action_id", notification.getActionId());
			data.put("unreadNotifications", count.toString());

			JSONObject notificationValues = new JSONObject();
			notificationValues.put("title", notification.getTitle());
			notificationValues.put("body", notification.getMessage());

			request.put("data",data);
			//			request.put("notification",notificationValues);

			executor.submit(new SendAndroidPushNotification(request.toString(), notification.getUser().getAndroidRegId(), this));
		} catch (JSONException e) {
			LoggerManager.error(this, e);
		}

	}

	private void send(SendAndroidPushNotification sendAndroidPushNotification) {
		try {

			MediaType jsonType = MediaType.parse("application/json; charset=utf-8");

			OkHttpClient client = new OkHttpClient();

			RequestBody body = RequestBody.create(jsonType, sendAndroidPushNotification.getJson());
			Request request = new Request.Builder()
					.url(HTTPS)
					.header("Authorization", "key="+apikey)
					.post(body)
					.build();
			Response response = client.newCall(request).execute();
			LoggerManager.info(this, response.body().string());

		} catch (IOException e) {
			LoggerManager.error(this,  e);
		} 
	}

	public ProxyConfiguration getProxyConfiguration() {
		return proxyConfiguration;
	}

	public void setProxyConfiguration(ProxyConfiguration proxyConfiguration) {
		this.proxyConfiguration = proxyConfiguration;
	}

	static class SendAndroidPushNotification implements Runnable {

		private String json;
		private String regId;
		private AndroidNotificationServiceImpl androidNotificationServiceImpl;

		public SendAndroidPushNotification(String json, String regId, AndroidNotificationServiceImpl androidNotificationServiceImpl) {
			super();
			this.json = json;
			this.regId = regId;
			this.androidNotificationServiceImpl = androidNotificationServiceImpl;
		}

		@Override
		public void run() {
			this.androidNotificationServiceImpl.send(this);
		}

		public String getJson() {
			return json;
		}

		public String getRegId() {
			return regId;
		}

		public AndroidNotificationServiceImpl getAndroidNotificationServiceImpl() {
			return androidNotificationServiceImpl;
		}

	}

}
