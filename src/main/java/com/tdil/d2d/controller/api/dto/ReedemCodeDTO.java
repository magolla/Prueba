package com.tdil.d2d.controller.api.dto;

import java.text.SimpleDateFormat;

import com.tdil.d2d.persistence.Subscription;

public class ReedemCodeDTO {

	private String image;
	private String expirationDate;

	public ReedemCodeDTO() {

	}

	public ReedemCodeDTO(Subscription elem) {
		if(elem.getSponsorCode().getSponsor().getBase64img()!=null)
		    this.setImage(new String(elem.getSponsorCode().getSponsor().getBase64img()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.setExpirationDate(sdf.format(elem.getExpirationDate()));
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	
}
