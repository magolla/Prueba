package com.tdil.d2d.controller.api.dto;

public class  JobOfferStatusDTO{

	private long id;
	private String comment;
	private String companyScreenName;
	private String creationDate;
	private long geoLevelId;
	private int geoLevelLevel;
	private String geoLevelName;
	private String institutionType;
	private String offerDate;
	private String offerHour;
	private boolean permanent;
	private String status;
	private String title;
	private String subTitle;
	private Integer vacants;
	private long occupation_id;
	private String occupationName;
	private long specialty_Id;
	private String specialtyName;
	private long task_id;
	private String taskName;
	private long offerent_id;
	private Integer applications;
	private byte[] base64img;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCompanyScreenName() {
		return companyScreenName;
	}
	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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
	public String getInstitutionType() {
		return institutionType;
	}
	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
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
	public boolean isPermanent() {
		return permanent;
	}
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public Integer getVacants() {
		return vacants;
	}
	public void setVacants(Integer vacants) {
		this.vacants = vacants;
	}
	public long getOccupation_id() {
		return occupation_id;
	}
	public void setOccupation_id(long occupation_id) {
		this.occupation_id = occupation_id;
	}
	public String getOccupationName() {
		return occupationName;
	}
	public void setOccupationName(String occupationName) {
		this.occupationName = occupationName;
	}
	public long getSpecialty_Id() {
		return specialty_Id;
	}
	public void setSpecialty_Id(long specialty_Id) {
		this.specialty_Id = specialty_Id;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long task_id) {
		this.task_id = task_id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public long getOfferent_id() {
		return offerent_id;
	}
	public void setOfferent_id(long offerent_id) {
		this.offerent_id = offerent_id;
	}
	public Integer getApplications() {
		return applications;
	}
	public void setApplications(Integer applications) {
		this.applications = applications;
	}
	public void setBase64img(byte[] base64img) {
		this.base64img = base64img;
	}

	public byte[] getBase64img() {
		return base64img;
	}
}
