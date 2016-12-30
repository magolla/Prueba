package com.tdil.d2d.controller.api.request;

public class AddLocationsRequest extends ApiRequest {
	
	private int[] geoLevelLevel;
	private long[] geoLevelId;
	private String[] geoLevelNames;
	
	public int[] getGeoLevelLevel() {
		return geoLevelLevel;
	}

	public void setGeoLevelLevel(int[] geoLevelLevel) {
		this.geoLevelLevel = geoLevelLevel;
	}

	public long[] getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(long[] geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public String[] getGeoLevelNames() {
		return geoLevelNames;
	}

	public void setGeoLevelNames(String[] geoLevelNames) {
		this.geoLevelNames = geoLevelNames;
	}
}
