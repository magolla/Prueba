package com.tdil.d2d.controller.api.response;

import java.util.Collection;
import java.util.HashSet;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.controller.api.request.NotificationConfigurationResponse;

public class UserDetailsResponse extends ApiResponse {
	
	private String userId;
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
	private String licence;
	private OccupationDTO occupation;
	private Collection<SpecialtyDTO> specialities = new HashSet<>();
	private Collection<TaskDTO> tasks = new HashSet<>();
	private String institutionType;
	private Collection<GeoLevelDTO> geoLevels;
	private NotificationConfigurationResponse notificationConfigurationResponse;
	private boolean hasLinkedinProfile;
    private String cv;

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

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public Collection<SpecialtyDTO> getSpecialities() {
		return specialities;
	}

	public void setSpecialities(Collection<SpecialtyDTO> specialties) {
		this.specialities = specialties;
	}

	public OccupationDTO getOccupation() {
		return occupation;
	}

	public void setOccupation(OccupationDTO occupation) {
		this.occupation = occupation;
	}

	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public Collection<TaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(Collection<TaskDTO> tasks) {
		this.tasks = tasks;
	}

	public Collection<GeoLevelDTO> getGeoLevels() {
		return geoLevels;
	}

	public void setGeoLevels(Collection<GeoLevelDTO> geoLevels) {
		this.geoLevels = geoLevels;
	}

	public NotificationConfigurationResponse getNotificationConfigurationResponse() {
		return notificationConfigurationResponse;
	}

	public void setNotificationConfigurationResponse(NotificationConfigurationResponse notificationConfigurationResponse) {
		this.notificationConfigurationResponse = notificationConfigurationResponse;
	}
	
	public boolean isHasLinkedinProfile() {
		return hasLinkedinProfile;
	}

	public void setHasLinkedinProfile(boolean hasLinkedinProfile) {
		this.hasLinkedinProfile = hasLinkedinProfile;
	}

	public String getCV() {
		return cv;
	}

	public void setCV(String cv) {
        this.cv = cv;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
