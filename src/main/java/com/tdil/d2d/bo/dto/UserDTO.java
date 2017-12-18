package com.tdil.d2d.bo.dto;

import java.util.List;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;

public class UserDTO {

	private long id;
	
	private String name;
	
	private String lastname;

	private String email;
	
	private boolean active;
	
	private boolean userB;
	
	private boolean hasActiveSuscription;
	
	private String expirationDate;
	
	private String operativeSystem;
	
	private String creationDate;
	
	private String lastLoginDate;
	
	private String license;
	
	private String mobilePhone;
	
	private String avatar;
	
	private String institutionType;
	
	private String userOccupation;
	
	private String companyScreenName;
	
	private List<SpecialtyDTO> userSpecialty;
	
	private List<GeoLevelDTO> geoLevels;
	
	
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

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

	public String getOperativeSystem() {
		return operativeSystem;
	}

	public void setOperativeSystem(String operativeSystem) {
		this.operativeSystem = operativeSystem;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getInstitutionType() {
		return institutionType;
	}

	public void setInstitutionType(String institutionType) {
		this.institutionType = institutionType;
	}

	public String getUserOccupation() {
		return userOccupation;
	}

	public void setUserOccupation(String userOccupation) {
		this.userOccupation = userOccupation;
	}

	public List<SpecialtyDTO> getUserSpecialty() {
		return userSpecialty;
	}

	public void setUserSpecialty(List<SpecialtyDTO> userSpecialty) {
		this.userSpecialty = userSpecialty;
	}

	public List<GeoLevelDTO> getGeoLevels() {
		return geoLevels;
	}

	public void setGeoLevels(List<GeoLevelDTO> geoLevels) {
		this.geoLevels = geoLevels;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}
}
