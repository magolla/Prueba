package com.tdil.d2d.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "D2D_SPONSOR")
public class Sponsor implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "creationDate")
	private Date creationDate;

	@Column(name = "name")
	private String name;
	
	@Size(max = 262144)
	@Column(name = "base64img")
	@Lob()
	private byte[] base64img;

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

	public byte[] getBase64img() {
		return base64img;
	}

	public void setBase64img(byte[] base64img) {
		this.base64img = base64img;
	}
	
	
}
