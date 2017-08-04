package com.tdil.d2d.bo.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterJobOfferReportDTO {

	private Date startDate;
	private Date endDate;
	private List<Long> geoLevels2;
	private boolean activeOffers = false;
	private boolean temporalOffers = false;
	private boolean permanentOffers = false;
	private Long occupationId;
	private Long specialtyId;
	private Long taskId;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Long> getGeoLevels2() {
		if(this.geoLevels2 == null) {
			this.geoLevels2 = new ArrayList<Long>();
		}
		return geoLevels2;
	}
	
	public void setGeoLevels2(List<Long> geoLevels2) {
		this.geoLevels2 = geoLevels2;
	}
	
	public boolean isActiveOffers() {
		return activeOffers;
	}

	public void setActiveOffers(boolean activeOffers) {
		this.activeOffers = activeOffers;
	}

	public boolean isTemporalOffers() {
		return temporalOffers;
	}

	public void setTemporalOffers(boolean temporalOffers) {
		this.temporalOffers = temporalOffers;
	}

	public boolean isPermanentOffers() {
		return permanentOffers;
	}

	public void setPermanentOffers(boolean permanentOffers) {
		this.permanentOffers = permanentOffers;
	}

	public Long getOccupationId() {
		return occupationId;
	}

	public void setOccupationId(Long occupationId) {
		this.occupationId = occupationId;
	}

	public Long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getStartDateForView() {
		if(startDate == null) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(startDate);
		return format;
	}
	
	public void setStartDateForView(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(string);
			setStartDate(date);
		} catch (ParseException e) {
		}
	}
	
	public String getEndDateForView() {
		if(endDate == null) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(endDate);
		return format;
	}
	
	public void setEndDateForView(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(string);
			setEndDate(date);
		} catch (ParseException e) {
		}
	}
}
