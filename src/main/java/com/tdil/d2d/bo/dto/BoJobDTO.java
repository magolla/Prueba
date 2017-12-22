package com.tdil.d2d.bo.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;

public class BoJobDTO {

	
	private long userId;
	
	private boolean permanent;
	
	private long occupationId;

	private long specialtyId; 
	
	private long taskId; 
	
	private GeoLevelDTO geoDto;
	
	private boolean privateInstitution;
	
	private String title;
	
	private String subtitle;
	
	private String companyScreenName;

	private String offerText;

	private Date offerDate;

	private String offerHour;
	
	private String name;
	
	private String lastName;



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


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getOfferText() {
		return offerText;
	}

	public void setOfferText(String offerText) {
		this.offerText = offerText;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
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

	public GeoLevelDTO getGeoDto() {
		return geoDto;
	}

	public void setGeoDto(GeoLevelDTO geoDto) {
		this.geoDto = geoDto;
	}

	public boolean isPrivateInstitution() {
		return privateInstitution;
	}

	public void setPrivateInstitution(boolean privateInstitution) {
		this.privateInstitution = privateInstitution;
	}

	public String getOfferHour() {
		return offerHour;
	}

	public void setOfferHour(String offerHour) {
		this.offerHour = offerHour;
	}


	public String getOfferDateForView() {
		if(offerDate == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(offerDate);
		return format;
	}

	public void setOfferDateForView(String string) {
		
		if(string == null || string.trim().equals("")) {
			setOfferDate(null);
			return;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(string);
			setOfferDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
