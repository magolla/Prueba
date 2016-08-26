package com.tdil.d2d.controller.api.response;

public class GenericResponse<T> extends ApiResponse {

	private T data;
	
	public GenericResponse(T data, int status) {
		super(status);
		setData(data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
