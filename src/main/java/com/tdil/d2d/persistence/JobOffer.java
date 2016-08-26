package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_JOBOFFER")
public class JobOffer implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@OneToOne
	private Specialty specialty;

	@OneToOne
	private SubSpecialty subSpecialty;
	
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
	
	@Column(name="tasks")
	private String tasks;
	
	@Column(name="vacants")
	private Integer vacants;
	
	@Column(name="status")
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
