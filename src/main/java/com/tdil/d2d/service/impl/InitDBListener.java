package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tdil.d2d.service.UserService;

@Component
public class InitDBListener implements ApplicationListener<ContextRefreshedEvent> {
  
	@Autowired
	private UserService userService;
	
    public void onApplicationEvent(ContextRefreshedEvent event) {
//    	userService.initPaymentDestinations();
    }

}
