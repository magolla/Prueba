package com.tdil.d2d.controller.api.dto;

public class BOJobOfferDTO{
	
	private long id;
	private Integer applications;
	private String comment;
	private String companyScreenName;
	private String creationDate;
	private String geoLevelName;
	private String offerHour;
	private String institutionType;
	//Concatenar Nombre, Apellido, mail y numero de teléfono
	private String jobApplication_detail;
	private String offerDate;
	private boolean permanent;
	private String status;
	private String subTitle;
	private String title;
	private Integer vacants;
	private String occupationName;
	//Concatenar Nombre, Apellido, mail y numero de teléfono, CompanyScreenName
	private String offerent_detail;
	private String specialtyName;
	private String taskName;

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
	public Integer getApplications() {
		return applications;
	}
	public void setApplications(Integer applications) {
		this.applications = applications;
	}
	public String getJobApplication_detail() {
		return jobApplication_detail;
	}
	public void setJobApplication_detail(String jobApplication_detail) {
		this.jobApplication_detail = jobApplication_detail;
	}
	public String getOfferent_detail() {
		return offerent_detail;
	}
	public void setOfferent_detail(String offerent_detail) {
		this.offerent_detail = offerent_detail;
	}
	public String getGeoLevelName() {
		return geoLevelName;
	}
	public void setGeoLevelName(String string) {
		this.geoLevelName = string;
	}
}
