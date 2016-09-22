package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Min;

public class AddSpecialtyRequest extends ApiRequest {
	
	@Min(value = 1)
	private long specialtyId;

	public long getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(long specialtyId) {
		this.specialtyId = specialtyId;
	}
	
}
