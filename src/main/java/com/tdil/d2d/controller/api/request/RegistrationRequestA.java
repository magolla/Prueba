package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class RegistrationRequestA extends ApiRequest {

	@NotEmpty
    @Length(min= 2, max = 50)
	@ValidInput
	private String firstname;
	
	@NotEmpty
    @Length(min= 2, max = 50)
	@ValidInput
	private String lastname;
	
	@NotEmpty
    @Length(min= 4, max = 50)
	@Email
	@ValidInput
	private String email;
	
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String mobilePhone;
	
    @Length(min= 0, max=50)
	@ValidInput
	private String companyScreenName;
	
	@NotEmpty
    @Length(min= 6, max = 50)
	@ValidInput
	private String deviceId;
	
	private boolean tacAccepted;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public boolean isTacAccepted() {
		return tacAccepted;
	}

	public void setTacAccepted(boolean tacAccepted) {
		this.tacAccepted = tacAccepted;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}

}
