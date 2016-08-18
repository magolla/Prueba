package com.tdil.d2d.utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;


@Component
public class Downloader {
	private HttpClient httpClient;

	@PostConstruct
	public void init() {
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(10 * 60 * 1000)
				.setConnectTimeout(10 * 60 * 1000)
				.build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setMaxConnPerRoute(20).setMaxConnTotal(200).build();
	}


	public String get(String url) throws Exception {
		try {
			url = url.trim();

			//DefaultHttpClient client = new CustomHttpClient("180000");
			HttpGet method = new HttpGet(url);

			HttpResponse response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = EntityUtils.toString(entity);
				return content;
			}

		} catch (IOException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
		return url;
	}   


	public String postInsecure(String url, Map<String,String> parameters) throws Exception {
		try {

			//Codigo de prueba para que se pueda pegar a https sin Certificado

			SSLContext sslContext = SSLContext.getInstance("SSL");

			// set up a TrustManager that trusts everything
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {        	         
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0,
						String arg1) throws CertificateException {
					System.out.println("checkClientTrusted =============");

				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0,
						String arg1) throws CertificateException {
					System.out.println("checkServerTrusted =============");

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					System.out.println("getAcceptedIssuers =============");
					return null;
				}
			} }, new SecureRandom());



			org.apache.http.conn.ssl.SSLSocketFactory sf = new org.apache.http.conn.ssl.SSLSocketFactory(sslContext);
			Scheme httpsScheme = new Scheme("https", 443, sf);
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(httpsScheme);

			// apache HttpClient version >4.2 should use BasicClientConnectionManager
			ClientConnectionManager cm = new SingleClientConnManager(schemeRegistry);     

			url = url.trim();

			DefaultHttpClient client = new DefaultHttpClient(cm);
			

			HttpPost httpPost = new HttpPost(url);            

			if(parameters!=null){
				List<NameValuePair> params = new ArrayList<NameValuePair>(2);
				for (Map.Entry<String, String> entry : parameters.entrySet()){

					params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));            	  
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));   
			}  

			HttpResponse response = client.execute(httpPost);
			HttpEntity result = response.getEntity();
			if (result != null) {
				String content = EntityUtils.toString(result);
				content = content.replace("\"", "");
				return content;
			}

		} catch (IOException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
		return url;
	}

	
	public String post(String url, Map<String,String> parameters) throws Exception {
		
		if(ServiceLocator.isLocalhost() || ServiceLocator.isRC())
			return postInsecure(url, parameters);
		try {
			HttpPost httpPost = new HttpPost(url);            

			if(parameters!=null){
				List<NameValuePair> params = new ArrayList<NameValuePair>(2);
				for (Map.Entry<String, String> entry : parameters.entrySet()){

					params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));            	  
				}
				httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));   
			}  

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity result = response.getEntity();
			if (result != null) {
				String content = EntityUtils.toString(result);
				content = content.replace("\"", "");
				return content;
			}

		} catch (IOException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
		return url;
	}
	
	

	public String post(String url, String request) throws Exception {
		try {
			String newUrl = url.trim();

			//DefaultHttpClient client = new CustomHttpClient("180000");
			HttpPost httpPost = new HttpPost(newUrl);            

			StringEntity entity = new StringEntity(request);
			entity.setContentType(ContentType.TEXT_PLAIN.toString());
			httpPost.setEntity(entity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity result = response.getEntity();
			String content = EntityUtils.toString(result);
			return content;

		} catch (IOException e) {
			throw e;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception ex) {
			throw ex;
		}
	}

}
