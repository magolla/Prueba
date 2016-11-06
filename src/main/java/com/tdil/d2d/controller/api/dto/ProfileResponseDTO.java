package com.tdil.d2d.controller.api.dto;

import java.util.Collection;

import com.tdil.d2d.controller.api.request.InstitutionType;

public class ProfileResponseDTO {

	private String license;
	private InstitutionType institutionType;
	private Collection<TaskDTO> tasks;
	
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public InstitutionType getInstitutionType() {
		return institutionType;
	}
	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}
	public Collection<TaskDTO> getTasks() {
		return tasks;
	}
	public void setTasks(Collection<TaskDTO> tasks) {
		this.tasks = tasks;
	}
	
}
