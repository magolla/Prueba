package com.tdil.d2d.bo.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterJobOfferDailyReportDTO {

	//DEFAULT
	private Integer quantityOfDays = 30;
	private Date toDate;
	
	private List<Long> geoLevels2;
	private boolean activeOffers = false;
	private boolean temporalOffers = false;
	private boolean permanentOffers = false;
	private boolean totalOffers = false;
	private boolean contracted = false;
	private Long occupationId;
	private Long specialtyId;
	private Long taskId;
	
	public Date getToDate() {
		return toDate;
	}
	
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	public String getToDateForView() {
		if(toDate == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(toDate);
		return format;
	}

	public void setToDateForView(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(string);
			setToDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	
	public boolean isTotalOffers() {
		return totalOffers;
	}

	public void setTotalOffers(boolean totalOffers) {
		this.totalOffers = totalOffers;
	}

	public boolean isContracted() {
		return contracted;
	}

	public void setContracted(boolean contracted) {
		this.contracted = contracted;
	}

	public Long getOccupationId() {
		if(occupationId == null) {
			return -1L;
		}
		return occupationId;
	}

	public void setOccupationId(Long occupationId) {
		this.occupationId = occupationId;
	}

	public Long getSpecialtyId() {
		if(specialtyId == null) {
			return -1L;
		}
		return specialtyId;
	}

	public void setSpecialtyId(Long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public Long getTaskId() {
		if(taskId == null) {
			return -1L;
		}
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	public List<String> validate() {
		List<String> validations = new ArrayList<String>();
		
		return validations;
	}
	
	public Integer getQuantityOfDays() {
		return quantityOfDays;
	}
	
	public void setQuantityOfDays(Integer quantityOfDays) {
		this.quantityOfDays = quantityOfDays;
	}
}
