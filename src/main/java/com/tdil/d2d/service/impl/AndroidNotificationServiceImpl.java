package com.tdil.d2d.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.communication.ProxyConfiguration;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.service.NotificationService;
import com.tdil.d2d.utils.LoggerManager;

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
	
	public static String HTTPS = "https://android.googleapis.com/gcm/send";
	
	/*
	 * 
	 * ApiKey android
AIzaSyD5EwRNmbA7keuvTyJNah3_MTsKRvftAwY
Server Key
AIzaSyBYrCBCm1KnrAuntw93AaDL8A9M_J11OiI
	 */
	
	@PostConstruct
	public void init() {
//		if (!StringUtils.isEmpty(proxyServer) && !StringUtils.isEmpty(proxyPort)) {
//			setProxyConfiguration(new ProxyConfiguration(proxyServer, Integer.parseInt(proxyPort)));
//		}
	}
	
	@Override
	public void sendNotification(NotificationType type, String title, String message, String regId) {
		try {
			JSONObject jsonObject = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("title",title);
			data.put("message",message);
			JSONArray registration_ids = new JSONArray();
			registration_ids.put(regId);
			jsonObject.put("data",data);
			jsonObject.put("registration_ids",registration_ids);
			executor.submit(new SendAndroidPushNotification(jsonObject.toString(), regId, this));
		} catch (JSONException e) {
			 LoggerManager.error(this, e);
		}
	}
		
	private void send(SendAndroidPushNotification sendAndroidPushNotification) {
		try {
			URL obj = new URL(HTTPS);
			HttpURLConnection conn;
			if (getProxyConfiguration() != null) {
				conn = (HttpURLConnection)obj.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(getProxyConfiguration().getServer(), getProxyConfiguration().getPort())));
			} else {
				conn = (HttpURLConnection)obj.openConnection();
			}
		      conn.setRequestMethod("POST");
		      conn.setRequestProperty("Content-Type", "application/json");
		      conn.setRequestProperty("Accept-Encoding", "application/json");
		      //Se pasa el Api key como parametro de la cabecera de la petici�n http
		      conn.setRequestProperty("Authorization","key=" +this.apikey);
		      if(sendAndroidPushNotification.getJson()!=null){
		        conn.setDoOutput(true);
		        OutputStream os = conn.getOutputStream();
		        os.write(sendAndroidPushNotification.getJson().getBytes("UTF-8"));
		        os.flush();
		      }
		      if (conn.getResponseCode() != 200) {
		    	  LoggerManager.error(this, "Failed : HTTP error code : " + conn.getResponseCode());
		    	  //NotificationsService.unregisterAndroidId(userId);
		    	  return;
		      }
		      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      String outputLine;
		      StringBuilder totalSalida = new StringBuilder();
		      while ((outputLine = br.readLine()) != null) {
		        totalSalida.append(outputLine/*new String(outputLine.getBytes("ISO-8859-1"), "UTF-8")*/);
		      }
		      conn.disconnect();
		      LoggerManager.info(this, totalSalida.toString());
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
