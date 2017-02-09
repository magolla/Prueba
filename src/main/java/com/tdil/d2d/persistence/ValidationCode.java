package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_VALIDATIONCODE")
public class ValidationCode implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "mobilePhone")
	private String mobilePhone;

	@Column(name = "code")
	private String code;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	public ValidationCode() {
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
}
