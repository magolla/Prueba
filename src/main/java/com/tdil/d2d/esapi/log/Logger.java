package com.tdil.d2d.esapi.log;

import com.tdil.d2d.utils.LoggerManager;

public class Logger implements org.owasp.esapi.Logger {

	@Override
	public void setLevel(int level) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getESAPILevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fatal(EventType type, String message) {
		LoggerManager.error(Logger.class, message);
	}

	@Override
	public void fatal(EventType type, String message, Throwable throwable) {
		LoggerManager.error(Logger.class, throwable);
		
	}

	@Override
	public boolean isFatalEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void error(EventType type, String message) {
		LoggerManager.error(Logger.class, message);
		
	}

	@Override
	public void error(EventType type, String message, Throwable throwable) {
		LoggerManager.error(Logger.class, throwable);
	}

	@Override
	public boolean isErrorEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void warning(EventType type, String message) {
		LoggerManager.warn(Logger.class, message);
		
	}

	@Override
	public void warning(EventType type, String message, Throwable throwable) {
		LoggerManager.warn(Logger.class, throwable);
	}

	@Override
	public boolean isWarningEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void info(EventType type, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(EventType type, String message, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInfoEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void debug(EventType type, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(EventType type, String message, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(EventType type, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(EventType type, String message, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTraceEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void always(EventType type, String message) {
		LoggerManager.error(Logger.class, message);
		
	}

	@Override
	public void always(EventType type, String message, Throwable throwable) {
		// TODO Auto-generated method stub
		LoggerManager.error(Logger.class, throwable);
	}

}
