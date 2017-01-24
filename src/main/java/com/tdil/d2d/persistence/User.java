package com.tdil.d2d.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "D2D_USER")
public class User implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "lastLoginDate")
	private Date lastLoginDate;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "mobilePhone", unique = true)
	private String mobilePhone;

	@Column(name = "mobileHash")
	private String mobileHash;

	@Column(name = "tacAccepted")
	private boolean tacAccepted;

	@Column(name = "tacAcceptDate")
	private Date tacAcceptDate;

	@Column(name = "email")
	private String email;

	@Column(name = "emailHash")
	private String emailHash;

	@Column(name = "phoneValidated")
	private boolean phoneValidated;

	@Column(name = "emailValidated")
	private boolean emailValidated;

	@Column(name = "pass")
	private String password;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "userb")
	private boolean userb;

	@Column(name = "deviceId")
	private String deviceId;

	@Column(name = "androidRegId", length = 256)
	private String androidRegId;

	@Column(name = "iosPushId", length = 256)
	private String iosPushId;

	@Column(name = "lastPasswordResetDate")
	private Date lastPasswordResetDate;

	@Column(name = "companyScreenName", length = 256)
	private String companyScreenName;

	@Column(name = "companyScreenDescription", length = 2000)
	private String companyScreenDescription;

	@Column(name = "license", length = 256)
	private String license;

	//Ver tamaño
	@Size(max = 262144)
	@Column(name = "base64img")
	@Lob()
	private byte[] base64img;
	
	@Column(name = "CV", length = 2000)
    private String cv;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "D2D_USER_SPECIALTY", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "SPECIALTY_ID",
					nullable = false, updatable = false)})
	private Set<Specialty> specialties = new HashSet<Specialty>(0);

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "D2D_USER_GEOLOC", joinColumns = {
			@JoinColumn(name = "USER_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "USERGEOLOC_ID",
					nullable = false, updatable = false)})
	private Set<UserGeoLocation> userGeoLocations = new HashSet<UserGeoLocation>(0);
	
	//@OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="pdfcv_id")
	private Media pdfCV;

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

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	public Set<UserGeoLocation> getUserGeoLocations() {
		return userGeoLocations;
	}

	public void setUserGeoLocations(Set<UserGeoLocation> userGeoLocations) {
		this.userGeoLocations = userGeoLocations;
	}

	public boolean isPhoneValidated() {
		return phoneValidated;
	}

	public void setPhoneValidated(boolean phoneValidated) {
		this.phoneValidated = phoneValidated;
	}

	public String getMobileHash() {
		return mobileHash;
	}

	public void setMobileHash(String mobileHash) {
		this.mobileHash = mobileHash;
	}

	public boolean isUserb() {
		return userb;
	}

	public void setUserb(boolean userb) {
		this.userb = userb;
	}

	public String getCompanyScreenName() {
		return companyScreenName;
	}

	public void setCompanyScreenName(String companyScreenName) {
		this.companyScreenName = companyScreenName;
	}

	public byte[] getBase64img() {
		return base64img;
	}

	public void setBase64img(byte[] base64img) {
		this.base64img = base64img;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getCompanyScreenDescription() {
		return companyScreenDescription;
	}

	public void setCompanyScreenDescription(String companyScreenDescription) {
		this.companyScreenDescription = companyScreenDescription;
	}
	
	public String getCV() {
        return cv;
    }

    public void setCV(String cv) {
        this.cv = cv;
    }

	public Media getPdfCV() {
		return pdfCV;
	}

	public void setPdfCV(Media pdfCV) {
		this.pdfCV = pdfCV;
	}

}
