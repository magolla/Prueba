package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SearchOfferRequest extends ApiRequest {
	
	@Min(value = 0)
	private long occupationId;
	
	@Min(value = 0)
	private long specialtyId;
	
	@Min(value = 0)
	private long taskId;
	
	@Min(value = 0)
	@Max(value = 4)
	private int geoLevelLevel;
	
	@Min(value = 0)
	private long geoLevelId;

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
	
}
