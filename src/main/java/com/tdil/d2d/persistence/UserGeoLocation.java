package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_USERGEOLOC")
public class UserGeoLocation implements PersistentEntity {
	

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@Column(name = "geoLevelLevel")
	private int geoLevelLevel;
	
	@Column(name = "geoLevelId")
	private long geoLevelId;
	
	@Column(name = "geoLevelName")
	private String geoLevelName;
	
	@ManyToOne
	private User user;

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

	public int getGeoLevelLevel() {
		return geoLevelLevel;
	}

	public void setGeoLevelLevel(int geoLevelLevel) {
		this.geoLevelLevel = geoLevelLevel;
	}

	public long getGeoLevelId() {
		return geoLevelId;
	}

	public void setGeoLevelId(long geoLevelId) {
		this.geoLevelId = geoLevelId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getGeoLevelName() {
		return geoLevelName;
	}

	public void setGeoLevelName(String geoLevelName) {
		this.geoLevelName = geoLevelName;
	}

}
