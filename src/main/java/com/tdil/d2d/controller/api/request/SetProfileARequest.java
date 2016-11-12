package com.tdil.d2d.controller.api.request;

public class SetProfileARequest extends ApiRequest {
	
	private String firstname;
	private String lastname;
	private String email;
	private String companyScreenName;
	private String companyScreenDescription;
	
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
	public String getCompanyScreenName() {
		return companyScreenName;
	}
	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}
	public String getCompanyScreenDescription() {
		return companyScreenDescription;
	}
	public void setCompanyScreenDescription(String companyScreenDescription) {
		this.companyScreenDescription = companyScreenDescription;
	}
}
