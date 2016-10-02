package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_NOTIFCONF")
public class NotificationConfiguration implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private User user;
	
	@Column(name = "push")
	private boolean push;
	
	@Column(name = "notes")
	private boolean notes;
	
	@Column(name = "congress")
	private boolean congress;
	
	@Column(name = "promotionsOffers")
	private boolean promotionsOffers;
	
	@Column(name = "grantOffers")
	private boolean grantOffers;
	
	@Column(name = "courses")
	private boolean courses;
	
	@Column(name = "productAndServices")
	private boolean productAndServices;
	
	@Column(name = "notifAllDay")
	private boolean notifAllDay;
	
	@Column(name = "notif9to20")
	private boolean notif9to20;

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

	public boolean isPush() {
		return push;
	}

	public void setPush(boolean push) {
		this.push = push;
	}

	public boolean isNotes() {
		return notes;
	}

	public void setNotes(boolean notes) {
		this.notes = notes;
	}

	public boolean isCongress() {
		return congress;
	}

	public void setCongress(boolean congress) {
		this.congress = congress;
	}


	public boolean isCourses() {
		return courses;
	}

	public void setCourses(boolean courses) {
		this.courses = courses;
	}

	public boolean isProductAndServices() {
		return productAndServices;
	}

	public void setProductAndServices(boolean productAndServices) {
		this.productAndServices = productAndServices;
	}

	public boolean isNotifAllDay() {
		return notifAllDay;
	}

	public void setNotifAllDay(boolean notifAllDay) {
		this.notifAllDay = notifAllDay;
	}

	public boolean isNotif9to20() {
		return notif9to20;
	}

	public void setNotif9to20(boolean notif9to20) {
		this.notif9to20 = notif9to20;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isPromotionsOffers() {
		return promotionsOffers;
	}

	public void setPromotionsOffers(boolean promotionsOffers) {
		this.promotionsOffers = promotionsOffers;
	}

	public boolean isGrantOffers() {
		return grantOffers;
	}

	public void setGrantOffers(boolean grantOffers) {
		this.grantOffers = grantOffers;
	}


}
