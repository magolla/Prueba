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
	
	@ManyToOne
	private Note note;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "actionId")
	private long actionId;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getActionId() {
		return actionId;
	}

	public void setActionId(long actionId) {
		this.actionId = actionId;
	}

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
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
