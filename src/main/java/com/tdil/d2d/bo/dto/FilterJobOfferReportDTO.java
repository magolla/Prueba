package com.tdil.d2d.bo.dto;

import java.util.ArrayList;
import java.util.List;

public class FilterJobOfferReportDTO {

	private Integer startMonth;
	private Integer startYear;
	private Integer endMonth;
	private Integer endYear;
	
	private List<Long> geoLevels2;
	private boolean activeOffers = false;
	private boolean temporalOffers = false;
	private boolean permanentOffers = false;
	private boolean totalOffers = false;
	private boolean contracted = false;
	private Long occupationId;
	private Long specialtyId;
	private Long taskId;
	
	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
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
	
	public Integer getQuantityOfMonths() {
		int m1 = startYear * 12 + startMonth;
	    int m2 = endYear * 12 + endMonth;
	    int quantity = m2 - m1 + 1;
		return quantity;
	}
}
