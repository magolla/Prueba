package com.tdil.d2d.bo.dto;

import java.util.ArrayList;
import java.util.List;

public class ResultDTO {

	private boolean success;

	private List<String> errors;

	public ResultDTO(boolean success) {
		super();
		this.success = success;
	}
	

	public ResultDTO(boolean success, List<String> errors) {
		super();
		this.success = success;
		this.errors = errors;
	}



	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public static ResultDTO ok(){
		return new ResultDTO(true);
	}
	
	public static ResultDTO error(String error){
		List<String> errors = new ArrayList<>();
		errors.add(error);
	    return new ResultDTO(false, errors);
	}
}
