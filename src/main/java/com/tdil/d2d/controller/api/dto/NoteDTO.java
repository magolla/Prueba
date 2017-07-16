package com.tdil.d2d.controller.api.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adriel on 1/10/17.
 */
public class NoteDTO {

	private long id;

	private String title;

	private String subtitle;

	private String content;

	private String category;

	private boolean active;

	private Date creationDate;

	private Date publishingDate;

	private Date expirationDate;

	private String image;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getPublishingDateForView() {
		if(publishingDate == null) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(publishingDate);
		return format;
	}
	
	public void setPublishingDateForView(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(string);
			setPublishingDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public String getExpirationDateForView() {
		if(expirationDate == null) {
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String format = formatter.format(expirationDate);
		return format;
	}
	
	public void setExpirationDateForView(String string) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Date date = formatter.parse(string);
			setExpirationDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
