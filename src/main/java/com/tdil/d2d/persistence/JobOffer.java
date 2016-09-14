package com.tdil.d2d.persistence;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_JOBOFFER")
public class JobOffer implements PersistentEntity {
	
	public static String VACANT = "VACANT";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@ManyToOne
	private Occupation occupation;
	
	@ManyToOne
	private Specialty specialty;

	@OneToMany
    @JoinColumn(name="TASK_ID")
	private Set<Task> tasks;
	
	@Column(name="address")
	private String address;
	
	@Column(name = "offerDate")
	private Date offerDate;
	
	@Column(name="hour")
	private String hour;

	@Column(name="permanent")
	private boolean permanent;
	
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="vacants")
	private Integer vacants;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	private User offerent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(Date offerDate) {
		this.offerDate = offerDate;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getVacants() {
		return vacants;
	}

	public void setVacants(Integer vacants) {
		this.vacants = vacants;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getOfferent() {
		return offerent;
	}

	public void setOfferent(User offerent) {
		this.offerent = offerent;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}


}
