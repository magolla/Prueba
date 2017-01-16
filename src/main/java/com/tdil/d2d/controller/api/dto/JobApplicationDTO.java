package com.tdil.d2d.controller.api.dto;

public class JobApplicationDTO {

	private long id;
	private long userId;
	private String creationDate;
	private String base64Image;
	private String linkedinInCv;
	private String cvAttach;
	private String cvPlain;
	private String firstname;
	private String lastname;
	private String mobilePhone;
	private String occupationName;
	private String specialtyName;
	private long geoLevelId;
	private int geoLevelLevel;
	private String geoLevelName;
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
	public long getGeoLevelId() {
		return geoLevelId;
	}
	public void setGeoLevelId(long geoLevelId) {
		this.geoLevelId = geoLevelId;
	}
	public int getGeoLevelLevel() {
		return geoLevelLevel;
	}
	public void setGeoLevelLevel(int geoLevelLevel) {
		this.geoLevelLevel = geoLevelLevel;
	}
	public String getGeoLevelName() {
		return geoLevelName;
	}
	public void setGeoLevelName(String geoLevelName) {
		this.geoLevelName = geoLevelName;
	}
	public String getBase64Image() {
		return base64Image;
	}
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	public String getCvAttach() {
		return cvAttach;
	}
	public void setCvAttach(String cvAttach) {
		this.cvAttach = cvAttach;
	}
	
}
