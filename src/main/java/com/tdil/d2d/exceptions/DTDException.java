package com.tdil.d2d.exceptions;

public class DTDException extends RuntimeException {

	private ExceptionDefinition definition;

	public DTDException(ExceptionDefinition definition, String... info) {
		super(String.format(definition.getMessage(), info));
		this.definition = definition;
	}

	public DTDException(ExceptionDefinition definition, Throwable t, String... info) {
		super(String.format(definition.getMessage(), info), t);
		this.definition = definition;
	}

	public String getStatusCode() {
		return definition.name();
	}
}
