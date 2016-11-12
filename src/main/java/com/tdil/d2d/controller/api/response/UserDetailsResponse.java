package com.tdil.d2d.controller.api.response;

public class UserDetailsResponse extends ApiResponse {
	
	private String firstname;
	private String lastname;
	private String sponsorName;
	private String email;
	private String mobileNumber;
	private String companyScreenName;
	private String base64img;
	private boolean userb;
	private boolean hasSubscription;
	private String subscriptionExpirationDate;

	public UserDetailsResponse(int status) {
		super(status);
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public boolean isHasSubscription() {
		return hasSubscription;
	}

	public void setHasSubscription(boolean hasSubscription) {
		this.hasSubscription = hasSubscription;
	}

	public String getSubscriptionExpirationDate() {
		return subscriptionExpirationDate;
	}

	public void setSubscriptionExpirationDate(String subscriptionExpirationDate) {
		this.subscriptionExpirationDate = subscriptionExpirationDate;
	}

	public boolean isUserb() {
		return userb;
	}

	public void setUserb(boolean userb) {
		this.userb = userb;
	}

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}

	public String getBase64img() {
		return base64img;
	}

	public void setBase64img(String base64img) {
		this.base64img = base64img;
	}
	
	

}
