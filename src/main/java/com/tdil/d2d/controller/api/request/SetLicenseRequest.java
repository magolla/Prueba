package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class SetLicenseRequest extends ApiRequest {
	
	@NotEmpty
    @Length(min= 2, max = 50)
	@ValidInput
	private String license;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	
}
