package com.tdil.d2d.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_MEDIA")
public class Media implements PersistentEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Column(name = "type")
	@Enumerated(javax.persistence.EnumType.STRING)
	private MediaType type;
	
	@Column(name = "data")
	@Lob()
	private byte[] data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
