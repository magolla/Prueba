package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_LOGIN")
public class Login {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    
    @Column(name = "modificationTime")
    private Date modificationTime;
    
    @Column(name = "authToken")
    private String authToken;
    
    @Column(name = "userId")
    private long userId;
    
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	
    
}
