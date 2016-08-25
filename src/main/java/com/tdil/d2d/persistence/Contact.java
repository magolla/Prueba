package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_JOBOFFER")
public class Contact implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "creationDate")
	private Date creationDate;

	@OneToMany
	private ContactMotive motive;
	
	@Column(name="comment")
	private String comment;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
