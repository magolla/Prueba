package com.tdil.d2d.controller.api.request;

public class AddSpecialtiesRequest extends ApiRequest {
	
	private long[] specialtyIds;

	public long[] getSpecialtyId() {
		return specialtyIds;
	}

	public void setSpecialtyId(long[] specialtyIds) {
		this.specialtyIds = specialtyIds;
	}
	
}
