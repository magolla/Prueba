package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class RegistrationRequest extends ApiRequest {

	@NotEmpty
    @Length(min= 4, max = 50)
	@Email
	@ValidInput
	private String email;
	
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String password;
	
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String deviceId;
	
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String phoneNumber;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	
}
