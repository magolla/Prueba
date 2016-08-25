package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_JOBAPPLICATION")
public class JobApplication implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;
	
	@OneToMany
	private JobOffer offer;
	
	@OneToMany
	private User user;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "cvPlain")
	private String cvPlain;
	
	@Column( name = "cvAttach" )
	@Lob()
	private byte[] cvAttach;
	
	@Column(name = "linkedInCv")
	private String linkedInCv;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
