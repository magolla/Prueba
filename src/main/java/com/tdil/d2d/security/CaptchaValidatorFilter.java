package com.tdil.d2d.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdil.d2d.controller.api.dto.GoogleCaptchaResultDTO;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CaptchaValidatorFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
    private ServletContext servletContext;
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        String recaptchaResponse = httpRequest.getParameter("g-recaptcha-response");
        
        if(username != null && password != null && recaptchaResponse != null) {
        	if(!validateGoogleCaptcha(recaptchaResponse)) {
        		HttpServletResponse httpResponse = (HttpServletResponse) response;
        		httpResponse.sendRedirect(servletContext.getContextPath() + "/admin/login?invalidCaptcha");
        		return;
        	}
        }
        
        chain.doFilter(request, response);
    }
    
	public Boolean validateGoogleCaptcha(String recaptchaResponse) {
		String url = "https://www.google.com/recaptcha/api/siteverify";
		
        try {
        	
        	FormBody.Builder formBuilder = new FormBody.Builder();
        	formBuilder.add("secret", "6Ld0MicUAAAAAECKrIzv8glRw5-pFUmjw2KMCaSX");
        	formBuilder.add("response", recaptchaResponse);
			
			OkHttpClient client = new OkHttpClient();
			
			Request request = new Request.Builder()
		            .addHeader( "Content-Type", "text/plain; charset=windows-1251")
		            .url(url)
		            .cacheControl(CacheControl.FORCE_NETWORK)
		            .post(formBuilder.build())
		            .build();
			
			Response response = client.newCall(request).execute();
        	
            String responseBody = response.body().string();
            
            Gson gson = new GsonBuilder().create();
            GoogleCaptchaResultDTO receiptResult = gson.fromJson(responseBody, GoogleCaptchaResultDTO.class);
            
            if(receiptResult != null && receiptResult.getSuccess() != null) {
            	return receiptResult.getSuccess();
            }
            
        } catch (Exception ex) {
        	return false;
        }
        
        return false;
	}
}