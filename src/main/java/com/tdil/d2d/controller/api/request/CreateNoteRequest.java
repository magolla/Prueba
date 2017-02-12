package com.tdil.d2d.controller.api.request;

import java.util.Date;
import java.util.List;

public class CreateNoteRequest {

	private String title;

	private String subtitle;

	private String content;

	private List<Long> occupations;

	private List<Long> specialties;

	private String category;

	private Date expirationDate;
	
    private String base64img;
    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Long> getOccupations() {
		return occupations;
	}

	public void setOccupations(List<Long> occupations) {
		this.occupations = occupations;
	}

	public List<Long> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<Long> specialties) {
		this.specialties = specialties;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getBase64img() {
		return base64img;
	}

	public void setBase64img(String base64img) {
		this.base64img = base64img;
	}
	
}
