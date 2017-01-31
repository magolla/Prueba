package com.tdil.d2d.controller.api.dto;

import com.tdil.d2d.controller.api.request.NotificationConfigurationResponse;

public class MatchedUserDTO {

	private Long userId;
	private String firstname;
	private String lastname;
	private String email;
	private String mobilePhone;
	private NotificationConfigurationResponse notificationConfigurationResponse;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
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
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	public void setMobilePhone(String phone) {
		this.mobilePhone = phone;
	}
	
	public NotificationConfigurationResponse getNotificationConfigurationResponse() {
		return notificationConfigurationResponse;
	}
	
	public void setNotificationConfigurationResponse(
			NotificationConfigurationResponse notificationConfigurationResponse) {
		this.notificationConfigurationResponse = notificationConfigurationResponse;
	}
}
