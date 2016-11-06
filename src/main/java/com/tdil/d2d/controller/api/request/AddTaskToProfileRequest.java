package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Min;

public class AddTaskToProfileRequest extends ApiRequest {
	
	@Min(value = 1)
	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}
