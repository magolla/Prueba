package com.tdil.d2d.controller.api.response;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {


	protected T data;
	private int status;
	private List<String> errors = new ArrayList<>();

	public ApiResponse() {
	}

	public ApiResponse(int status) {
		this.status = status;
	}

	public ApiResponse(int status, T data) {
		this.status = status;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}

}
