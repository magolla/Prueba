package com.tdil.d2d.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_USER")
public class User implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "username")
	private String username;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "email")
	private String email;
	
	@Column(name = "emailHash")
	private String emailHash;
	
	@Column(name = "emailValidated")
	private boolean emailValidated;
	
	/* Formayo yyyyMMdd*/
	@Column(name = "birthdate")
	private String birthdate;

	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "androidRegId", length=256)
	private String androidRegId;
	
	@Column(name = "iosPushId", length=256)
	private String iosPushId;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "lastLoginDate")
	private Date lastLoginDate;

	@Column(name = "lastPasswordResetDate")
	private Date lastPasswordResetDate;

	@Column(name = "pass")
	private String password;
	
	@Column(name = "deviceId")
	private String deviceId;
	
	@Column(name = "phoneNumber")
	private String phoneNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
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

	public String isAndroidRegId() {
		return androidRegId;
	}

	public void setAndroidRegId(String androidRegId) {
		this.androidRegId = androidRegId;
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

	public String getAndroidRegId() {
		return androidRegId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSalt() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(this.getCreationDate());
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	public boolean isEmailValidated() {
		return emailValidated;
	}

	public void setEmailValidated(boolean emailValidated) {
		this.emailValidated = emailValidated;
	}

	public String getIosPushId() {
		return iosPushId;
	}

	public void setIosPushId(String iosPushId) {
		this.iosPushId = iosPushId;
	}

}
