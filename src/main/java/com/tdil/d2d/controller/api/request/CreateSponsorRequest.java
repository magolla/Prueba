package com.tdil.d2d.controller.api.request;

public class CreateSponsorRequest {

	private String name;

	private String base64img;
	
	public CreateSponsorRequest() {
	}

	public CreateSponsorRequest(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase64img() {
		return base64img;
	}

	public void setBase64img(String base64img) {
		this.base64img = base64img;
	}
	
}
