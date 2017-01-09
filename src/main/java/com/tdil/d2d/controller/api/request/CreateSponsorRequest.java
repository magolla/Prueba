package com.tdil.d2d.controller.api.request;

public class CreateSponsorRequest {

	private String name;

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
}
