package com.tdil.d2d.bo.dto;

import java.util.ArrayList;
import java.util.List;

public class FilterJobOfferReportDTO {

	private List<Long> geoLevels2;
	
	public List<Long> getGeoLevels2() {
		if(this.geoLevels2 == null) {
			this.geoLevels2 = new ArrayList<Long>();
		}
		return geoLevels2;
	}
	
	public void setGeoLevels2(List<Long> geoLevels2) {
		this.geoLevels2 = geoLevels2;
	}
	
}
