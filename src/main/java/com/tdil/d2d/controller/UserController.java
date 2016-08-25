package com.tdil.d2d.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.ApiResponse;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequest;
import com.tdil.d2d.controller.api.request.RegistrationResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.security.JwtTokenUtil;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class UserController {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    private static final String UNKNOWN_HOST = "unknown";

    public static String HOSTNAME;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    static {
        HOSTNAME = UNKNOWN_HOST;
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            HOSTNAME = UNKNOWN_HOST;
        }
    }

    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
    	try {
			RegistrationResponse response = this.userService.register(registrationRequest);
			return new ResponseEntity<RegistrationResponse>(response, HttpStatus.CREATED);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<RegistrationResponse>((RegistrationResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/androidRegId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> androidRegId(@Valid @RequestBody AndroidRegIdRequest androidRegIdRequest) {
    	try {
			boolean response = this.userService.updateAndroidRegId(androidRegIdRequest);
			return new ResponseEntity<ApiResponse>(new ApiResponse(response == true ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/iosPushId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> iosPushId(@Valid @RequestBody IOsPushIdRequest iOsPushIdRequest) {
    	try {
			boolean response = this.userService.updateIOsPushId(iOsPushIdRequest);
			return new ResponseEntity<ApiResponse>(new ApiResponse(response == true ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/user/validateEmail", method = RequestMethod.GET)
    public ModelAndView validateEmail(@RequestParam("email") String email, @RequestParam("hash") String hash) {
    	try {
			boolean validated = this.userService.validateEmail(email, hash);
			if (validated) {
				return new ModelAndView("emailValidated");
			} else {
				return new ModelAndView("emailNotValidated");
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ModelAndView("emailNotValidated");
		}
    }
    
    @RequestMapping(value = "/test111", method = RequestMethod.GET)
    public ModelAndView test() {
		return new ModelAndView("index");

    }
    
}
