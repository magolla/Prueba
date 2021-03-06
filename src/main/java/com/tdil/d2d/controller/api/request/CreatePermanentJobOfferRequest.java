package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class CreatePermanentJobOfferRequest extends ApiRequest {
	
	@Min(value = 1)
	private long occupationId;
	
	@Min(value = 1)
	private long specialtyId;
	
	@Min(value = 1)
	private long taskId;
	
	@NotEmpty
    @Length(max = 50)
	@ValidInput
	private String title;
	
	@NotEmpty
    @Length(max = 50)
	@ValidInput
	private String subtitle;
	
	// Va a la oferta, se toma inicialmente dlel perfil
	private String companyScreenName;
	
	// Si es publica o privada (no ambas)
	private InstitutionType institutionType;
	
	// Imagen del aviso
	private String base64Image;
	
	@Min(value = 2)
	@Max(value = 4)
	private int geoLevelLevel;
	
	@Min(value = 1)
	private long geoLevelId;
	
	private String offerDate;
	private String offerHour;
	
	@NotEmpty
    @Length(max = 4000)
	private String comment;
	
	@NotEmpty
    @Length(max = 50)
	@ValidInput
	private String tasks;
	
	@Min(value = 1)
	private int vacants;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTasks() {
		return tasks;
	}

	public void setTasks(String tasks) {
		this.tasks = tasks;
	}

	public int getVacants() {
		return vacants;
	}

	public void setVacants(int vacants) {
		this.vacants = vacants;
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

	public int getGeoLevelLevel() {
		return geoLevelLevel;
	}

	public void setGeoLevelLevel(int geoLevelLevel) {
		this.geoLevelLevel = geoLevelLevel;
	}

	public long getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(long geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

}
