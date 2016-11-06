package com.tdil.d2d.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "D2D_NOTE")
public class Note implements PersistentEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
//	titulo
//	subtitulo
//	contenido
//	
//	n imagenes
//	
//	profesiones, especialidades
//	url para redirigir

}
