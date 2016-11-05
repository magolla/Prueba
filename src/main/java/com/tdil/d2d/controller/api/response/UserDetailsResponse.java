package com.tdil.d2d.controller.api.response;

public class UserDetailsResponse extends ApiResponse {
	
	private String firstname;
	private String lastname;
	private String sponsorName;
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
	
	

}
