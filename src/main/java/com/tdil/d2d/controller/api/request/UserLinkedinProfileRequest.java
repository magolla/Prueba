package com.tdil.d2d.controller.api.request;

    public class UserLinkedinProfileRequest extends ApiRequest {

    	private String accessToken;
    	private int expiresIn;
    	private String firstName;
    	private String lastName;
    	private String email;
    	private String headLine;
    	private String industry;
    	private String publicProfileURL;
    	private String positionsJson;

    	public String getAccessToken() {
    		return accessToken;
    	}

    	public void setAccessToken(String accessToken) {
    		this.accessToken = accessToken;
    	}

    	public int getExpiresIn() {
    		return expiresIn;
    	}

    	public void setExpiresIn(int expiresIn) {
    		this.expiresIn = expiresIn;
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

    }