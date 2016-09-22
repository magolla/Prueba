package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class AddLocationRequest extends ApiRequest {
	
	@Min(value = 2)
	@Max(value = 4)
	private int geoLevelLevel;
	
	@Min(value = 1)
	private long geoLevelId;
	
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
