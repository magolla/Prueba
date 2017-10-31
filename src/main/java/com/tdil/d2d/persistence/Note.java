package com.tdil.d2d.persistence;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "D2D_NOTE_SPECIALTY", joinColumns = {
			@JoinColumn(name = "NOTE_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "SPECIALTY_ID",
					nullable = false, updatable = false)})
	private Set<Specialty> specialties = new HashSet<Specialty>(0);
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "D2D_NOTE_OCCUPATION", joinColumns = {
			@JoinColumn(name = "NOTE_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "OCCUPATION_ID",
					nullable = false, updatable = false)})
	private Set<Occupation> occupations;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "D2D_NOTE_SPONSOR", joinColumns = {
			@JoinColumn(name = "NOTE_ID", nullable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name = "SPONSOR_ID",
					nullable = false, updatable = false)})
	private Set<Sponsor> sponsors;

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
	
	@Column(name = "sendUserA", columnDefinition = "boolean default true", nullable = false)
	private boolean sendUserA;
	
	@Column(name = "sendUserBNoSponsor", columnDefinition = "boolean default true", nullable = false)
	private boolean sendUserBNoSponsor;
	
	@Column(name = "sendAllSponsor", columnDefinition = "boolean default true", nullable = false)
	private boolean sendUserBAllSponsor;

	public boolean isSendUserBAllSponsor() {
		return sendUserBAllSponsor;
	}

	public void setSendUserBAllSponsor(boolean sendUserBAllSponsor) {
		this.sendUserBAllSponsor = sendUserBAllSponsor;
	}

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

	public Set<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(Set<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public boolean isSendUserA() {
		return sendUserA;
	}

	public void setSendUserA(boolean sendUserA) {
		this.sendUserA = sendUserA;
	}

	public boolean isSendUserBNoSponsor() {
		return sendUserBNoSponsor;
	}

	public void setSendUserBNoSponsor(boolean sendUserBNoSponsor) {
		this.sendUserBNoSponsor = sendUserBNoSponsor;
	}
}
