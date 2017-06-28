package com.tdil.d2d.bo.dto;

import java.util.Date;

public class UserDTO {

	private long id;
	
	private String name;

	private String email;
	
	private boolean active;
	
	private boolean userB;
	
	private boolean hasActiveSuscription;
	
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isUserB() {
		return userB;
	}

	public void setUserB(boolean userB) {
		this.userB = userB;
	}

	public boolean isHasActiveSuscription() {
		return hasActiveSuscription;
	}

	public void setHasActiveSuscription(boolean hasActiveSuscription) {
		this.hasActiveSuscription = hasActiveSuscription;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	
}
