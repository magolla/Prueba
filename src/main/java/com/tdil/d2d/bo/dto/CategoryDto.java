package com.tdil.d2d.bo.dto;

public class CategoryDto {
	
	private String occupationName;
	private String specialtyName;
	private String taskName;
	private long occupationId;
	private long specialtyId;
	private long taskId;
	
	
	public String getOccupationName() {
		return occupationName;
	}
	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public long getOccupationId() {
		return occupationId;
	}
	public void setOccupationId(long occupationId) {
		this.occupationId = occupationId;
	}
	public long getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(long specialtyId) {
		this.specialtyId = specialtyId;
	}
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

}
