package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.NotEmpty;

public class IOsPushIdRequest extends ApiRequest {

	@NotEmpty
	private String iosPushId;

	public String getIosPushId() {
		return iosPushId;
	}

	public void setIosPushId(String iosPushId) {
		this.iosPushId = iosPushId;
	}



}
