package com.tdil.d2d.bo.dto;

import java.util.Date;

public class UserDTO {

	private long id;
	
	private String name;

	private String email;
	
	private String state;
	
	private String subscriptionType;
	
	private String subscriptionState;
	
	private String expirationDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getSubscriptionState() {
		return subscriptionState;
	}

	public void setSubscriptionState(String subscriptionState) {
		this.subscriptionState = subscriptionState;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	
}
