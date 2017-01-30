package com.tdil.d2d.controller.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.tdil.d2d.controller.api.request.InstitutionType;

public class SearchOfferDTO {

	private List<Long> geos;
	private List<Long> specialities;
	private List<Long> occupations;
	private List<Long> tasks;
	private Boolean permanent;
	private InstitutionType institutionType;
	
	public List<Long> getGeos() {
		if(this.geos == null) {
			this.geos = new ArrayList<Long>();
		}
		return geos;
	}
	
	public void setGeos(List<Long> geos) {
		this.geos = geos;
	}
	
	public List<Long> getSpecialities() {
		if(this.specialities == null) {
			this.specialities = new ArrayList<Long>();
		}
		return specialities;
	}
	
	public void setSpecialities(List<Long> specialities) {
		this.specialities = specialities;
	}
	
	public List<Long> getOccupations() {
		if(this.occupations == null) {
			this.occupations = new ArrayList<Long>();
		}
		return occupations;
	}
	
	public void setOccupations(List<Long> occupations) {
		this.occupations = occupations;
	}
	
	public List<Long> getTasks() {
		if(this.tasks == null) {
			this.tasks = new ArrayList<Long>();
		}
		return tasks;
	}
	
	public void setTasks(List<Long> tasks) {
		this.tasks = tasks;
	}
	
	public Boolean getPermanent() {
		return permanent;
	}
	
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}
	
	public InstitutionType getInstitutionType() {
		return institutionType;
	}
	
	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}
}
