package com.tdil.d2d.controller.api.request;

public class GenerateSponsorCodesRequest extends ApiRequest {

	private long sponsorId;
	private int codesCount;
	private int units;
	private String timeUnits;

	public long getSponsorId() {
		return sponsorId;
	}

	public int getCodesCount() {
		return codesCount;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public void setCodesCount(int codesCount) {
		this.codesCount = codesCount;
	}

	public int getUnits() {
		return units;
	}

	public String getTimeUnits() {

		return timeUnits;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public void setTimeUnits(String timeUnits) {
		this.timeUnits = timeUnits;
	}
}
