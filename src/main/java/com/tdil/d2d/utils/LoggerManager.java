package com.tdil.d2d.utils;

import org.apache.logging.log4j.LogManager;

public abstract class LoggerManager {
	
	public static void info(Object o, String message){
		info(o.getClass(), message);
	}
	
	public static void info(Class<?> c, String message){
		LogManager.getLogger(c).info(message);
	}
	
	public static void warn(Object o, String message){
		warn(o.getClass(), message);
	}
	
	public static void warn(Object o, Throwable exception) {
		warn(o.getClass(), exception);
	}

	public static void warn(Class<?> c, String message){
		LogManager.getLogger(c).warn(message);
	}
	
	public static void warn(Class<?> c, Throwable exception) {
		LogManager.getLogger(c).warn(exception.getMessage(), exception);
	}
	
	public static void error(Object o, String message){
		error(o.getClass(), message);
	}
	
	public static void error(Object o, Throwable exception) {
		error(o.getClass(), exception);
	}

	public static void error(Class<?> c, String message){
		LogManager.getLogger(c).error(message);
	}
	
	public static void error(Class<?> c, Throwable exception) {
		LogManager.getLogger(c).error(exception.getMessage(), exception);
	}
}
