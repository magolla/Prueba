package com.tdil.d2d.controller.api.dto;

import com.tdil.d2d.persistence.Sponsor;

import java.util.Date;

public class SponsorDTO {

	private long id;
	private String name;
	private Date creationDate;

	public SponsorDTO() {

	}

	public SponsorDTO(Sponsor elem) {
		this.setId(elem.getId());
		this.setName(elem.getName());
		this.setCreationDate(elem.getCreationDate());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
