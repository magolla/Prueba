package com.tdil.d2d.exceptions;

public class DTDException extends RuntimeException {

	public DTDException(ExceptionDefinition definition, String... info) {
		super(String.format(definition.getMessage(), info));
	}

	public DTDException(ExceptionDefinition definition, Throwable t, String... info) {
		super(String.format(definition.getMessage(), info), t);
	}
}
