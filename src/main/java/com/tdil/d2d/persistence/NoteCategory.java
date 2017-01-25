package com.tdil.d2d.persistence;

import javax.persistence.Enumerated;

public enum NoteCategory {

	CAT_1("Cursos, congresos y jornadas"),
	CAT_2("Becas"),
	CAT_3("Notas periodisticas"),
	CAT_4("Promociones"),
	CAT_5(" Productos y servcios de Doc to Doc y terceros asociados");


	private String description;

	private NoteCategory(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
