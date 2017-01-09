package com.tdil.d2d.persistence;

    import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

    @Entity
    @Table(name = "D2D_USER_LINKEDIN_PROF")
    public class UserLinkedinProfile implements PersistentEntity {

    	@Id
    	@GeneratedValue
    	@Column(name = "id")
    	private long id;

    	@OneToOne
    	private User user;

    	@Column(name = "creationDate")
    	private Date creationDate;

    	@Column(name = "accessToken")
    	private String accessToken;

    	@Column(name = "expiresOn")
    	private Date expiresOn;

    	@Column(name = "firstName")
    	private String firstName;

    	@Column(name = "lastName")
    	private String lastName;

    	@Column(name = "email")
    	private String email;

    	@Column(name = "headLine")
    	private String headLine;

    	@Column(name = "industry")
    	private String industry;

    	@Column(name = "publicProfileURL")
    	private String publicProfileURL;

    	@Column(name = "positionsJson")
    	@Size(max = 4000)
    	private String positionsJson;

    	@Override
    	public long getId() {
    		return this.id;
    	}

    	public User getUser() {
    		return user;
    	}

    	public void setUser(User user) {
    		this.user = user;
    	}

    	public Date getCreationDate() {
    		return creationDate;
    	}

    	public void setCreationDate(Date creationDate) {
    		this.creationDate = creationDate;
    	}

    	public String getAccessToken() {
    		return accessToken;
    	}

    	public void setAccessToken(String accessToken) {
    		this.accessToken = accessToken;
    	}

    	public Date getExpiresOn() {
    		return expiresOn;
    	}

    	public void setExpiresOn(Date expiresOn) {
    		this.expiresOn = expiresOn;
    	}

    	public String getFirstName() {
    		return firstName;
    	}

    	public void setFirstName(String firstName) {
    		this.firstName = firstName;
    	}

    	public String getLastName() {
    		return lastName;
    	}

    	public void setLastName(String lastName) {
    		this.lastName = lastName;
    	}

    	public String getEmail() {
    		return email;
    	}

    	public void setEmail(String email) {
    		this.email = email;
    	}

    	public String getHeadLine() {
    		return headLine;
    	}

    	public void setHeadLine(String headLine) {
    		this.headLine = headLine;
    	}

    	public String getIndustry() {
    		return industry;
    	}

    	public void setIndustry(String industry) {
    		this.industry = industry;
    	}

    	public String getPublicProfileURL() {
    		return publicProfileURL;
    	}

    	public void setPublicProfileURL(String publicProfileURL) {
    		this.publicProfileURL = publicProfileURL;
    	}

    	public String getPositionsJson() {
    		return positionsJson;
    	}

    	public void setPositionsJson(String positionsJson) {
    		this.positionsJson = positionsJson;
    	}

    	public void setId(long id) {
    		this.id = id;
    	}

    }