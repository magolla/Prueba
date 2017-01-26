package com.tdil.d2d.controller.api.request;

import org.hibernate.validator.constraints.Length;

public class ApplyToOfferRequest extends ApiRequest {
	
    @Length(max = 4000)
	private String comment;
    
    @Length(max = 4000)
	private String cvPlain;
    
    // Codificado en base64
    private String cvPdf;
    
    private String linkedInCV;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCvPdf() {
		return cvPdf;
	}

	public void setCvPdf(String cvPdf) {
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
