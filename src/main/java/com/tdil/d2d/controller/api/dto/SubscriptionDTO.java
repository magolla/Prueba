package com.tdil.d2d.controller.api.dto;

import com.tdil.d2d.persistence.Subscription;

import java.util.Date;

public class SubscriptionDTO {

	private long id;
	private long sponsorId;
	private Date expirationDate;


	public SubscriptionDTO() {

	}

	public SubscriptionDTO(Subscription subscription) {
		this.sponsorId = subscription.getSponsorCode().getSponsor().getId();
		this.expirationDate = subscription.getExpirationDate();
		this.id = subscription.getId();
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
