package com.tdil.d2d.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.security.JwtTokenUtil;
import com.tdil.d2d.security.JwtUser;
import com.tdil.d2d.service.ContactService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(HttpServletResponse response) throws IOException {
        System.out.println("TESTTT");
        response.setContentType("text/plain");
        response.getOutputStream().write("OK".getBytes());

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void home(HttpServletResponse response) throws IOException {
        System.out.println("HOME");
        response.setContentType("text/plain");
        response.getOutputStream().write("OK".getBytes());
    }
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ContactService contactService;
    
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JwtUser> getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return new ResponseEntity<JwtUser>(user, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/user1", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JwtUser> getAuthenticatedUser1(HttpServletRequest request) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<JwtUser>((JwtUser)null, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/sendTestNotificationIOS", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> sendTestNotificationIOS(HttpServletRequest request) {
    	try {
    		boolean response = this.userService.sendTestNotificationIOS();
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/sendTestNotificationAndroid", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> sendTestNotificationAndroid(HttpServletRequest request) {
    	try {
    		boolean response = this.userService.sendTestNotificationAndroid();
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<String> test() {
//		return new ResponseEntity<String>("A", HttpStatus.CREATED);
//    }
    
    @RequestMapping(value = "/initDB", method = RequestMethod.GET)
    public ResponseEntity<String> initDB(HttpServletRequest request) throws ServiceException {
    	specialtyService.initDB();
    	contactService.initDB();
        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }

}
