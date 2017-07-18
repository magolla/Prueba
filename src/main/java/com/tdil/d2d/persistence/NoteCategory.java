package com.tdil.d2d.persistence;

public enum NoteCategory {

	CAT_1("Cursos, congresos y jornadas"),
	CAT_2("Becas"),
	CAT_3("Notas periodisticas"),
	CAT_4("Promociones"),
	CAT_5("Productos y servcios de Doc to Doc y terceros asociados");


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

	public static NoteCategory getCategoryEnum(String string) {
		switch(string) {
			case "CAT_1":
				return CAT_1;
			case "CAT_2":
				return CAT_2;
			case "CAT_3":
				return CAT_3;
			case "CAT_4":
				return CAT_4;
			case "CAT_5":
				return CAT_5;
		}
		return null;
	}
}
