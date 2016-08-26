package com.tdil.d2d.controller.api.response;

public class ApiResponse {

	private int status;
	
	public ApiResponse(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
