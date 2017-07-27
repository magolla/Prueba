package com.tdil.d2d.controller.api.dto;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class GoogleCaptchaResultDTO  {

	@SerializedName("success")
    private Boolean success;
	

	@SerializedName("challenge_ts")
    private Date challengeDate;
	
	@SerializedName("hostname")
    private String hostname;
	
    @SerializedName("error-codes")
    private List<String> errorCodes;
    
	public Boolean getSuccess() {
		return success;
	}
    
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	
	public Date getChallengeDate() {
		return challengeDate;
	}
	
	public String getHostname() {
		return hostname;
	}
}