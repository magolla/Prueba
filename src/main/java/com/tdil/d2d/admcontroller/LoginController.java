package com.tdil.d2d.admcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class LoginController {
    
	@RequestMapping(value = {"/", "/admin"} , method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Doc to Doc");
		model.addObject("message", "Dashboard");
		model.setViewName("admin/dashboard");

		return model;

	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "La sesión ha finalizado correctamente.");
		}
		model.setViewName("admin/login");

		return model;

	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {

			Exception exception = (Exception) request.getSession().getAttribute(key);

			String error = "Se ha producido un error. Por favor intente nuevamente mas tarde.";
			if (exception instanceof BadCredentialsException) {
				error = "Usuario o contraseña incorrectos";
			} else {
				error = "Generic Error";
			}
			return error;
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

			ModelAndView model = new ModelAndView();

			// check if user is login
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				System.out.println(userDetail);

				model.addObject("username", userDetail.getUsername());

			}

			model.setViewName("403");
			return model;

		}
    
}
    
