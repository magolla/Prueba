package com.tdil.d2d.controller.api.request;

public class SetTasksToProfileRequest extends ApiRequest {
	
	private int[] taskId;

	public int[] getTaskId() {
		return taskId;
	}

	public void setTaskId(int[] taskId) {
		this.taskId = taskId;
	}

}
