package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;

import com.tdil.d2d.esapi.validation.ValidInput;

public class ApplyToOfferRequest extends ApiRequest {
	
    @Length(max = 256)
	@ValidInput
	private String comment;
    
    @Length(max = 2048)
	@ValidInput
	private String cvPlain;
    
    private byte[] cvPdf;
    
    private String linkedInCV;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public byte[] getCvPdf() {
		return cvPdf;
	}

	public void setCvPdf(byte[] cvPdf) {
		this.cvPdf = cvPdf;
	}

	public String getLinkedInCV() {
		return linkedInCV;
	}

	public void setLinkedInCV(String linkedInCV) {
		this.linkedInCV = linkedInCV;
	}

	public String getCvPlain() {
		return cvPlain;
	}

	public void setCvPlain(String cvPlain) {
		this.cvPlain = cvPlain;
	}

}
