package com.tdil.d2d.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.dao.impl.UserDAOImpl;

@Configuration
public class DaoConfig {

	
	@Bean(name = "userDAO")
	public UserDAO userDAO() {
		return new UserDAOImpl();
	}
}
