package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_SPONSOR_CODE")
public class SponsorCode implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "consumeDate")
	private Date consumeDate;

	@Column(name = "code", unique = true)
	private String code;


	@Column(name = "units")
	private int units;

	@Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
	private boolean enabled;

	@Column(name = "timeUnit")
	@Enumerated(javax.persistence.EnumType.STRING)
	private SubscriptionTimeUnit timeUnit;

	@ManyToOne
	private Sponsor sponsor;

	@ManyToOne
	private User consumer;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public SubscriptionTimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(SubscriptionTimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}

	public User getConsumer() {
		return this.consumer;
	}

	public Date getConsumeDate() {
		return consumeDate;
	}

	public void setConsumeDate(Date consumeDate) {
		this.consumeDate = consumeDate;
	}
}
