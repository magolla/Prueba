package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tdil.d2d.esapi.validation.ValidInput;

public class RegistrationRequest extends ApiRequest {

	@NotEmpty
	@Length(min= 6, max = 20)
	@ValidInput
	private String username;

	@NotEmpty
    @Length(min= 1, max = 50)
	@ValidInput
	private String firstname;
	
	@NotEmpty
    @Length(min= 1, max = 50)
	@ValidInput
	private String lastname;
	
	@NotEmpty
    @Length(min= 4, max = 50)
	@Email
	@ValidInput
	private String email;
	
	@Min(value = 1)
	private long identityCardTypeId;
	
	@NotEmpty
    @Length(min= 6, max = 50)
	@ValidInput
	private String identityCard;
	
	@Min(value = 1)
	private long countryId;
	
	@NotEmpty
    @Length(min= 1, max = 50)
	@ValidInput
	private String city;
	
	@NotEmpty
    @Length(min= 8, max = 8)
	@Pattern(regexp = "[0-9]{8}")
	@ValidInput
	private String birthdate;
	
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String password;
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String deviceId;
	@NotEmpty
    @Length(min= 6, max = 20)
	@ValidInput
	private String phoneNumber;


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public long getIdentityCardTypeId() {
		return identityCardTypeId;
	}
	public void setIdentityCardTypeId(long identityCardTypeId) {
		this.identityCardTypeId = identityCardTypeId;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistrationRequest [");
		if (username != null) {
			builder.append("username=");
			builder.append(username);
			builder.append(", ");
		}
		if (firstname != null) {
			builder.append("firstname=");
			builder.append(firstname);
			builder.append(", ");
		}
		if (lastname != null) {
			builder.append("lastname=");
			builder.append(lastname);
			builder.append(", ");
		}
		if (email != null) {
			builder.append("email=");
			builder.append(email);
			builder.append(", ");
		}
		builder.append("identityCardTypeId=");
		builder.append(identityCardTypeId);
		builder.append(", ");
		if (identityCard != null) {
			builder.append("identityCard=");
			builder.append(identityCard);
			builder.append(", ");
		}
		builder.append("countryId=");
		builder.append(countryId);
		builder.append(", ");
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (birthdate != null) {
			builder.append("birthdate=");
			builder.append(birthdate);
			builder.append(", ");
		}
		if (password != null) {
			builder.append("password=");
			builder.append(password);
			builder.append(", ");
		}
		if (deviceId != null) {
			builder.append("deviceId=");
			builder.append(deviceId);
			builder.append(", ");
		}
		if (phoneNumber != null) {
			builder.append("phoneNumber=");
			builder.append(phoneNumber);
		}
		builder.append("]");
		return builder.toString();
	}
	
	
}
