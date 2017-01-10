package com.tdil.d2d.exceptions;

public enum ExceptionDefinition {


	// DTD_1xxx: Persistence Exceptions
	DTD_1000("DTD_1000: Error trying to consume <<%s>> sponsor code"),

	// DTD_2xxx: Service Exceptions
	DTD_2001("DTD_2001: Error trying to retrieve logged in user"),
	DTD_2002("DTD_2002: Error trying to get active subscriptions for user <<%s>>"),
	DTD_2003("DTD_2003: Not subscription available for user <<%s>>");

	// DTD_3xxx: Controller Exceptions


	//
	private final String message;

	ExceptionDefinition(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}
}