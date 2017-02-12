package com.tdil.d2d.persistence;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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

	@Size(max = 262144)
	@Column(name = "base64img")
	@Lob()
	private byte[] base64img;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Occupation> occupations;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Specialty> specialties;

	@Enumerated(EnumType.STRING)
	@Column(name = "category")
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

	public Set<Occupation> getOccupations() {
		return occupations;
	}

	public void setOccupations(Set<Occupation> occupations) {
		this.occupations = occupations;
	}

	public Set<Specialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(Set<Specialty> specialties) {
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

	public void addOccupation(Occupation occupation) {
		this.getOccupations().add(occupation);
	}

	public void addSpeciality(Specialty specialty) {
		this.getSpecialties().add(specialty);
	}

	public byte[] getBase64img() {
		return base64img;
	}

	public void setBase64img(byte[] base64img) {
		this.base64img = base64img;
	}
	
	
}
