package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class SendSMSRequest extends ApiRequest {

	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String mobilePhone;
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String linePhone) {
		this.mobilePhone = linePhone;
	}

}
