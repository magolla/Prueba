package com.tdil.d2d.esapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.owasp.esapi.ESAPI;

import com.tdil.d2d.utils.LoggerManager;

public class ValidInputValidator implements ConstraintValidator<ValidInput, String> {

	@Override
	public void initialize(ValidInput isValidPrintable) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value != null) {
			try {
				//La Long de los valores se deben validar con la anotacion size
				return ESAPI.validator().isValidInput("IsValidPrintableValidator", value, "Input" ,value.length()+1,true);
			} catch (Exception ex) {
				LoggerManager.error(this,  value);
				LoggerManager.error(this,  ex);
				return false;
			}
		}
		return true;
	}

}
