package com.tdil.d2d.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class ServiceLocator {   



    public static final String CORPORATE_ENVIRONMENT = "corporate.environment";
    public static final String PROD = "prod";
    public static final String IC = "ic";
    public static final String RC = "rc";
    public static final String LH = "localhost";
    
    private static ApplicationContext context;
    
    private static String UNKNOWN_HOST = "unknown";
    private static String HOSTNAME = UNKNOWN_HOST;
    
    static {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostName().toString();
        } catch (UnknownHostException e) {
            hostname = UNKNOWN_HOST;
        }
        HOSTNAME = hostname;
    }
    
    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ServiceLocator.context = context;
    }
    
    public static <T> T getBean(Class<T> aBeanClass) {
		return getContext().getBean(aBeanClass);
	}
    
    public static boolean isProd(){
    	return context.getEnvironment().acceptsProfiles(PROD);
    }
    public static boolean isBeta(){
    	return context.getEnvironment().acceptsProfiles(PROD);
    }
    public static boolean isLocalhost(){
    	return context.getEnvironment().acceptsProfiles(LH);
    }
    public static boolean isIC(){
    	return context.getEnvironment().acceptsProfiles(IC);
    }
    
    public static boolean isRC(){
    	return context.getEnvironment().acceptsProfiles(RC);
    }

    /**
     * Retorna el nombre del host. Si hay un error retorna unknown.
     * 
     * @return
     */
    public static String getHostName() {
        return HOSTNAME;
    }

}
