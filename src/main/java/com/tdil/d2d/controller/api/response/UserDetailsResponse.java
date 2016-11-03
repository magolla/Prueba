package com.tdil.d2d.controller.api.response;

public class UserDetailsResponse extends ApiResponse {
	
	private String sponsorName;
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
	
	

}
