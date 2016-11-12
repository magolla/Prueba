package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class ValidationRequest extends ApiRequest {

	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String mobilePhone;
	
	@NotEmpty
    @Length(min= 6, max = 50)
	@ValidInput
	private String deviceId;
	
	@NotEmpty
    @Length(min= 4, max = 20)
	@ValidInput
	private String smsCode;

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String linePhone) {
		this.mobilePhone = linePhone;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}


}
