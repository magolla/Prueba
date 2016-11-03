package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class UseSponsorCodeRequest extends ApiRequest {
	
	@NotEmpty
    @Length(min= 2, max = 50)
	@ValidInput
	private String sponsorCode;

	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}
	
}
