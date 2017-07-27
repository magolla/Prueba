package com.tdil.d2d.bo.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdil.d2d.controller.api.dto.GoogleCaptchaResultDTO;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class LoginController {
    
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
    private ServletContext servletContext;
	
	@RequestMapping(value = {"/", "/admin"} , method = RequestMethod.GET)
	public ModelAndView redirect() {
		return new ModelAndView("redirect:/admin/dashboard");
	}
	
	@RequestMapping(value = {"/dashboard"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/dashboard");

		return model;

	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			@RequestParam(value = "invalidCaptcha", required = false) String invalidCaptcha, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "La sesión ha finalizado correctamente.");
		}
		
		if (invalidCaptcha != null) {
			model.addObject("msg", "El captcha es inválido.");
		}
		
		model.setViewName("admin/login");

		return model;

	}
	
	@RequestMapping(value = "/login", method = { RequestMethod.POST})
	public void postLogin(HttpServletRequest request, ServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String recaptchaResponse = request.getParameter("g-recaptcha-response");
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        
		try {
			//CHECK CAPTCHA
	        if(username != null && password != null && recaptchaResponse != null) {
	        	if(!validateGoogleCaptcha(recaptchaResponse)) {
	        		httpResponse.sendRedirect(servletContext.getContextPath() + "/admin/login?invalidCaptcha");
	        		return;
	        	}
	        }
			
			//CHECK CREDENTIALS
	        try {
				final Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								username,
								password
						));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				httpResponse.sendRedirect(servletContext.getContextPath() + "/admin/login");
				return;
			}
	        
			// Return the token
			httpResponse.sendRedirect(servletContext.getContextPath() + "/admin/dashboard");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {

			Exception exception = (Exception) request.getSession().getAttribute(key);

			String error = "Se ha producido un error. Por favor intente nuevamente mas tarde.";
			if (exception instanceof BadCredentialsException) {
				error = "Usuario o contraseña incorrectos";
			} 
			return error;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

			ModelAndView model = new ModelAndView();

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				System.out.println(userDetail);

				model.addObject("username", userDetail.getUsername());

			}

			model.setViewName("403");
			return model;

		}
    
	private Boolean validateGoogleCaptcha(String recaptchaResponse) {
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
    
