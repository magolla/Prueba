package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class CreateJobOfferRequest extends ApiRequest {

	@NotEmpty
	private long specialtyId;
	
	@NotEmpty
	private long subspecialtyId;
	
	@NotEmpty
    @Length(max = 100)
	@ValidInput
	private String address;
	
	@NotEmpty
    @Length(min= 8, max = 8)
	@Pattern(regexp="[0-9]{8}")
	private String offerDate;
	
	@NotEmpty
    @Length(min= 4, max = 4)
	@Pattern(regexp="[0-9]{4}")
	private String offerHour;
	
	@NotEmpty
	private boolean permanent;
	
	@NotEmpty
    @Length(max = 100)
	private String comment;
	
	@NotEmpty
    @Length(max = 50)
	@ValidInput
	private String tasks;
	
	private int vacants;

	public long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public long getSubspecialtyId() {
		return subspecialtyId;
	}

	public void setSubspecialtyId(long subspecialtyId) {
		this.subspecialtyId = subspecialtyId;
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

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
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

	
}
