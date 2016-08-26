package com.tdil.d2d.controller.api.dto;

public class JobOfferStatusDTO {

	private long id;
	private String creationDate;
	private String specialtyName;
	private String subspecialtyName;
	private String address;
	private String offerDate;
	private String offerHour;
	
	private Integer vacants;
	private Integer applications;
	private String status;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getSubspecialtyName() {
		return subspecialtyName;
	}
	public void setSubspecialtyName(String subspecialtyName) {
		this.subspecialtyName = subspecialtyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}
	public String getOfferHour() {
		return offerHour;
	}
	public void setOfferHour(String offerHour) {
		this.offerHour = offerHour;
	}
	public Integer getVacants() {
		return vacants;
	}
	public void setVacants(Integer vacants) {
		this.vacants = vacants;
	}
	public Integer getApplications() {
		return applications;
	}
	public void setApplications(Integer applications) {
		this.applications = applications;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
