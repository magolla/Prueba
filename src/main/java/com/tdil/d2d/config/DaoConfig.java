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
	
//	@Bean(name = "userService")
//	public UserService userService() {
//		return new UserServiceImpl();
//	}
//	
//	@Bean(name = "jobOfferDAO")
//	public JobOfferDAO jobOfferDAO() {
//		return new JobOfferDAOImpl();
//	}
//	
//	
//	@Bean(name = "jobApplicationDAO")
//	public JobApplicationDAO jobApplicationDAO() {
//		return new JobApplicationDAOImpl();
//	}
//	
//	@Bean(name = "geoDAO")
//	public GeoDAO geoDAO() {
//		return new GeoDAOImpl();
//	}

}
