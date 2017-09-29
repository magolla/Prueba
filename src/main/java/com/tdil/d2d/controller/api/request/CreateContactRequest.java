package com.tdil.d2d.controller.api.request;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public class CreateContactRequest extends ApiRequest {
	
	@Min(value = 1)
	private long contactMotiveId;
	
	@Length(max = 256)
	private String comment;

	public long getContactMotiveId() {
		return contactMotiveId;
	}

	public void setContactMotiveId(long contactMotiveId) {
		this.contactMotiveId = contactMotiveId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
