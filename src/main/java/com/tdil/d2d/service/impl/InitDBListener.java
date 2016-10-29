package com.tdil.d2d.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.tdil.d2d.dbinit.DBInit;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;
import com.tdil.d2d.utils.ServiceLocator;

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
//				userService.initDbWithTestData();
//				geoService.initDbWithTestData();
	    		initIOSPushCertificate();
				
			} catch (Exception e) {
				LoggerManager.error(this, e);
			}
    	}
    }

	private void initIOSPushCertificate() throws IOException {
		File certificado = new File(ServiceLocator.getTempPath() + "/" + IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME);
		if(certificado.exists()) {
			certificado.delete();
			InputStream io = DBInit.class.getResourceAsStream(IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME);
			String certificadojContent = IOUtils.toString(io);
			IOUtils.write(certificadojContent, new FileOutputStream(ServiceLocator.getTempPath() + "/" + IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME));
			io.close();
		} else {
			InputStream io = DBInit.class.getResourceAsStream(IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME);
			String certificadojContent = IOUtils.toString(io);
			IOUtils.write(certificadojContent, new FileOutputStream(ServiceLocator.getTempPath() + "/" + IOSNotificationServiceImpl.CERTIFICATE_FILE_NAME));
			io.close();
		}
	}

}
