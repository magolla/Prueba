package com.tdil.d2d.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tdil.d2d.service.UserService;
import com.tdil.d2d.service.impl.UserServiceImpl;

/**
 *
 */
@Configuration
public class ServiceConfig {

	@Bean(name = "userService")
	public UserService userService() {
		return new UserServiceImpl();
	}
	
}
