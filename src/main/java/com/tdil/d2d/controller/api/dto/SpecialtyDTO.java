package com.tdil.d2d.controller.api.dto;

import java.util.List;

import com.tdil.d2d.persistence.Task;

public class SpecialtyDTO {

	private long id;
	private String name;
	private List<Task> taskList;
	
	public SpecialtyDTO(long id, String name, List<Task> taskList) {
		this.id = id;
		this.name = name;
		this.taskList = taskList;
	}
	
	public SpecialtyDTO() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	

}
