package com.tdil.d2d.esapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.owasp.esapi.ESAPI;

public class EmailValidator implements ConstraintValidator<EMail, String> {
	
	public EmailValidator() {}
	
	@Override
	public void initialize(EMail email) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value != null) {
			//La Long de los valores se deben validar con la anotacion size
			return ESAPI.validator().isValidInput("toAddress", value, "Email", value.length() + 1, true);
		}
		return true;
	}

}
