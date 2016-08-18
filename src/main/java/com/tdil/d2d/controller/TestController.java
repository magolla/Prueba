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

import com.tdil.d2d.security.JwtTokenUtil;
import com.tdil.d2d.security.JwtUser;
import com.tdil.d2d.service.UserService;

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
    
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JwtUser> getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return new ResponseEntity<JwtUser>(user, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/api/user1", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JwtUser> getAuthenticatedUser1(HttpServletRequest request) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<JwtUser>((JwtUser)null, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> test() {
		return new ResponseEntity<String>("A", HttpStatus.CREATED);
    }

}
