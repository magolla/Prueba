package com.tdil.d2d.esapi.log;

import org.owasp.esapi.Logger;

public class LogFactory implements org.owasp.esapi.LogFactory{
	
	public LogFactory() {
	}

	@Override
	public Logger getLogger(String moduleName) {
		return new com.tdil.d2d.esapi.log.Logger();
	}

	@Override
	public Logger getLogger(Class clazz) {
		return new com.tdil.d2d.esapi.log.Logger();
	}

}
