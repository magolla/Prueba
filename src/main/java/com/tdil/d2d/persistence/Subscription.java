package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_SUBSCRIPTION")
public class Subscription implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "expirationDate")
	private Date expirationDate;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private SponsorCode sponsorCode;
	

	@Column(name = "freeSuscription")
	private boolean freeSuscription;
	
	public Subscription() {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public SponsorCode getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(SponsorCode sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public boolean isFreeSuscription() {
		return freeSuscription;
	}
	
	public void setFreeSuscription(boolean freeSuscription) {
		this.freeSuscription = freeSuscription;
	}
}
