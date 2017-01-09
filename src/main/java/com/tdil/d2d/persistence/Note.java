package com.tdil.d2d.persistence;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "D2D_NOTE")
public class Note implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "subtitle", length = 512)
	private String subtitle;

	@Column(name = "content", length = 5120)
	private String content;


	@OneToMany
	private List<Occupation> occupations;

	@OneToMany
	private List<Specialty> specialties;

	@Column(name="category")
	private NoteCategory category;

	@Column(name = "is_active")
	private boolean active;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "publishing_date")
	private Date publishingDate;

	@Column(name = "expiration_date")
	private Date expirationDate;

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

	public List<Occupation> getOccupations() {
		return occupations;
	}

	public void setOccupations(List<Occupation> occupations) {
		this.occupations = occupations;
	}

	public List<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<Specialty> specialties) {
		this.specialties = specialties;
	}

	public NoteCategory getCategory() {
		return category;
	}

	public void setCategory(NoteCategory category) {
		this.category = category;
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
}
