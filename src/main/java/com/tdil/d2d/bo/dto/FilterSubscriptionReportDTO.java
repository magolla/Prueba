package com.tdil.d2d.bo.dto;

import java.util.ArrayList;
import java.util.List;

public class FilterSubscriptionReportDTO {

	private boolean android = false;
	private boolean sponsor = false;
	private boolean ios = false;
	private boolean free = false;
	
	private List<Long> geoLevels2;
	
	public boolean getAndroid() {
		return android;
	}

	public void setAndroid(boolean android) {
		this.android = android;
	}

	public boolean isSponsor() {
		return sponsor;
	}

	public void setSponsor(boolean sponsor) {
		this.sponsor = sponsor;
	}

	public boolean isIos() {
		return ios;
	}

	public void setIos(boolean ios) {
		this.ios = ios;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
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
	
}
