package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ApiResponse defaultErrorHandler(Exception e) throws Exception {


		logger.error("Error", e);

		// Cualquier excepcion no conocida se pone el codigo de error 9999
		ApiResponse response = new ApiResponse(9999);
		response.addError(e.getLocalizedMessage());
		return response;
	}
}
