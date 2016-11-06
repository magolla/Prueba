package com.tdil.d2d.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.utils.LoggerManager;

public class AbstractController {

	protected void printErrors(BindingResult bidingResult) {
		for (ObjectError oe : bidingResult.getAllErrors()) {
			LoggerManager.error(this, oe.toString());
		}
	}

	protected <T extends ApiResponse> T getErrorResponse(BindingResult bidingResult, T response) {
		for (ObjectError oe : bidingResult.getAllErrors()) {
			LoggerManager.error(this, oe.toString());
			response.addError(oe.toString());
		}
		return response;
	}

}
