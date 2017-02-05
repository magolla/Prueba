package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_NOTIFICATION")
public class Notification implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private JobOffer offer;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "seen")
	private boolean seen;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public JobOffer getOffer() {
		return offer;
	}
	
	public void setOffer(JobOffer offer) {
		this.offer = offer;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public boolean isSeen() {
		return seen;
	}
	
	public void setSeen(boolean read) {
		this.seen = read;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
