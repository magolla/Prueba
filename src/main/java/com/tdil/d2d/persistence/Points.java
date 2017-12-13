package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "D2D_POINTS")
public class Points implements PersistentEntity{

	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name="motive")
	private String motive;
	
	@ManyToOne
	private User user;
	
	@Column(name = "points")
	private long points;
	
	
	public Points(ActivityActionEnum actionEnum, User user) {
		this.creationDate = new Date();
		this.motive = actionEnum.getMessage();
		this.points = actionEnum.getValue();
		this.user = user;
	}
	
	
	@Override
	public long getId() {
		return 0;
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

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

}
