package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AndroidRegIdRequest extends ApiRequest {

	@NotEmpty
    @Length(min= 8, max = 250)
	private String androidRegId;

	public String getAndroidRegId() {
		return androidRegId;
	}

	public void setAndroidRegId(String androidRegId) {
		this.androidRegId = androidRegId;
	}


}
