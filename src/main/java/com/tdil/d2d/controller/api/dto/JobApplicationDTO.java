package com.tdil.d2d.controller.api.dto;

public class JobApplicationDTO {

	private long id;
	private long userId;
	private String creationDate;
	//falta imagen base 64
	private String linkedinInCv;
	//falta cvAttach
	private String cvPlain;
	private String firstname;
	private String lastname;
	private String mobilePhone;
	private String occupationName;
	private String specialtyName;
//	private String geolocationName;
	private String comment;

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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLinkedinInCv() {
		return linkedinInCv;
	}
	public void setLinkedinInCv(String linkedinInCv) {
		this.linkedinInCv = linkedinInCv;
	}
	public String getCvPlain() {
		return cvPlain;
	}
	public void setCvPlain(String cvPlain) {
		this.cvPlain = cvPlain;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getOccupationName() {
		return occupationName;
	}
	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	
}
