package com.tdil.d2d.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
@Order(value=1)
public class WebInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
        
		String currentProfile = System.getProperty("spring.profiles.active");
		if (!StringUtils.isEmpty(currentProfile)) {
			environment.setActiveProfiles(currentProfile);
		} else {
			System.setProperty("spring.profiles.active", "prod");
			environment.setActiveProfiles("prod");
		}

		String defaultProfile = "prod";
		environment.setDefaultProfiles(defaultProfile);
		if (!StringUtils.isEmpty(currentProfile)) {
			System.setProperty("classifier.environment",currentProfile);
		} else {
			System.setProperty("classifier.environment","prod");
		}
	}
	
    
}
