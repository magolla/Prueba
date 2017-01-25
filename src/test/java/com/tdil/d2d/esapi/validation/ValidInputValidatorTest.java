package com.tdil.d2d.esapi.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidInputValidatorTest {

	@Test
	public void testIsValidInput() {
		assertTrue(new ValidInputValidator().isValid("hola", null));
		assertTrue(new ValidInputValidator().isValid("hola chau", null));
		assertTrue(new ValidInputValidator().isValid("hola \n chau", null));
	}

}