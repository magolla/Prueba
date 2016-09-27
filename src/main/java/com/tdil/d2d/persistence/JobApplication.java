package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_JOBAPPLICATION")
public class JobApplication implements PersistentEntity {

	public static String PENDING = "PENDING";
	public static String ACEPTED = "ACEPTED";
	public static String REJECTED = "REJECTED";
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@ManyToOne
	private JobOffer offer;
	
	@ManyToOne
	private User user;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "cvPlain")
	@Lob()
	private String cvPlain;
	
	@Column( name = "cvAttach" )
	@Lob()
	private byte[] cvAttach;
	
	@Column(name = "linkedInCv")
	private String linkedInCv;
	
	@Column(name="status")
	private String status;

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

	public JobOffer getOffer() {
		return offer;
	}

	public void setOffer(JobOffer offer) {
		this.offer = offer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCvPlain() {
		return cvPlain;
	}

	public void setCvPlain(String cvPlain) {
		this.cvPlain = cvPlain;
	}

	public byte[] getCvAttach() {
		return cvAttach;
	}

	public void setCvAttach(byte[] cvAttach) {
		this.cvAttach = cvAttach;
	}

	public String getLinkedInCv() {
		return linkedInCv;
	}

	public void setLinkedInCv(String linkedInCv) {
		this.linkedInCv = linkedInCv;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
