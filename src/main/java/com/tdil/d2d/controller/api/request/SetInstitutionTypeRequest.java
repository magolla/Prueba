package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.NotNull;

public class SetInstitutionTypeRequest extends ApiRequest {
	
	@NotNull
	private InstitutionType institutionType;

	public InstitutionType getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(InstitutionType institutionType) {
		this.institutionType = institutionType;
	}

	
}
