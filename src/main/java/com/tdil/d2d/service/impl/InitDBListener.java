package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.service.UserService;

@Component
public class InitDBListener implements ApplicationListener<ContextRefreshedEvent> {
  
	@Autowired
	private UserService userService;
	@Autowired
	private GeoService geoService;
	
	private boolean initialized = false;
	
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	if (!initialized) {
    		initialized = true;
	    	try {
				userService.initDbWithTestData();
				geoService.initDbWithTestData();
				
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

}
