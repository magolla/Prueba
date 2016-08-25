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
	
	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "mobilePhone")
	private String mobilePhone;
	
	@Column(name = "linePhone")
	private String linePhone;
	
	@Column(name = "tacAccepted")
	private boolean tacAccepted;
	
	@Column(name = "tacAcceptDate")
	private Date tacAcceptDate;

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
	
	@Column(name = "deviceId")
	private String deviceId;

	@Column(name = "androidRegId", length=256)
	private String androidRegId;
	
	@Column(name = "iosPushId", length=256)
	private String iosPushId;
	
	@Column(name = "lastPasswordResetDate")
	private Date lastPasswordResetDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getAndroidRegId() {
		return androidRegId;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public boolean isTacAccepted() {
		return tacAccepted;
	}

	public void setTacAccepted(boolean tacAccepted) {
		this.tacAccepted = tacAccepted;
	}

	public Date getTacAcceptDate() {
		return tacAcceptDate;
	}

	public void setTacAcceptDate(Date tacAcceptDate) {
		this.tacAcceptDate = tacAcceptDate;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

}
