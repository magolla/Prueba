package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.DTDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = DTDException.class)
	@ResponseBody
	public GenericResponse<String> handleDTDException(DTDException e) {


		logger.error("Error", e);

		// Cualquier excepcion no conocida se pone el codigo de error 9999
		GenericResponse<String> response = new GenericResponse<String>(500, e.getStatusCode());
		response.addError(e.getLocalizedMessage());

		return response;
	}


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public GenericResponse<String> handleDefaultError(Exception e) {


		logger.error("Error", e);

		// Cualquier excepcion no conocida se pone el codigo de error 9999
		GenericResponse<String> response = new GenericResponse<String>(9999);
		response.addError(e.getLocalizedMessage());

		return response;
	}
}
