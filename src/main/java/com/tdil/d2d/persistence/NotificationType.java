package com.tdil.d2d.persistence;

public enum NotificationType {
	/*Este tipo es para notificar a un postulante que se publico una oferta que matchea el perfil*/
	// Tiene que llevarme al detalle de la oferta.
	//¿Que pasa si la oferta esta cerrada?
	NEW_OFFER_MATCH {
		@Override
		public int getIntValue() {
			return 1;
		}

		@Override
		public String getTitle() {
			return "Nueva oferta encontrada";
		}

		@Override
		public String getMessage() {
			return "Se encontraron nuevas ofertas que matchean con tu perfil";
		}
	},
	/*Este tipo es para notificar a un oferente que alguien se postulo a una oferta*/
	// Tiene que llevarme al detalle de la oferta
	NEW_APPLICATION {
		@Override
		public int getIntValue() {
			return 2;
		}

		@Override
		public String getTitle() {

			return "Nueva postulación";
		}

		@Override
		public String getMessage() {
			return "Alguien se postuló a una de tus ofertas";
		}
	},
	/*Esta oferta es para avisar al postulante que se acepto su postulacion*/
	// Tiene que llevarme al detalle de la oferta
	APPLICATION_ACCEPTED {
		@Override
		public int getIntValue() {
			return 3;
		}

		@Override
		public String getTitle() {
			return "Postulación Aceptada";
		}

		@Override
		public String getMessage() {
			return "Se aceptó tu postulación a una oferta";
		}
	},
	/*Esta notificacion es para avisar a los postulantes que una oferta se cerro, se eligio a otro candidato*/
	JOB_OFFER_CLOSE {
		@Override
		public int getIntValue() {
			return 4;
		}

		@Override
		public String getTitle() {
			return "Oferta cerrada";
		}

		@Override
		public String getMessage() {
			return "La oferta en la cual te postulaste fue cerrada";
		}
	},
	/*Esta notificacion es para avisar a los usuarios que hay una nueva beca*/
	// Tiene que llevarme a las notas y centrarme en la clickeada(siempre y cuando no haya sido eliminada)
	NEW_GRANT {
		@Override
		public int getIntValue() {
			return 5;
		}

		@Override
		public String getTitle() {
			return "Se ha publicado una Beca en DOC TO DOC";
		}

		@Override
		public String getMessage() {
			return "Ingresá a DOC TO DOC ahora para leer la nota becas";
		}
	},
	/*Esta notificacion es para avisar a los usuarios que hay una nueva divulgacion cientifica*/
	NEW_NOTE {
		@Override
		public int getIntValue() {
			return 6;
		}

		@Override
		public String getTitle() {
			return "Se ha publicado una Divulgacion Cientifica en DOC TO DOC";
		}

		@Override
		public String getMessage() {
			return "Ingresá a DOC TO DOC ahora para leer la Divulgacion Cientifica";
		}
	},
	/*Esta notificacion es para avisar a los usuarios que hay una nueva promocion*/
	NEW_PROMOTION {
		@Override
		public int getIntValue() {
			return 7;
		}

		@Override
		public String getTitle() {
			return "Se ha publicado una Promoción en DOC TO DOC";
		}

		@Override
		public String getMessage() {
			return "Ingresá a DOC TO DOC ahora para leer sobre esta Promoción";
		}
	},
	/*Esta notificacion es para avisar a los usuarios que hay un nuevo Curso, Congreso y/o Jornada*/
	NEW_CONGRESS {
		@Override
		public int getIntValue() {
			return 8;
		}

		@Override
		public String getTitle() {
			return "Se ha publicado un Curso, Congreso y/o Jornada en DOC TO DOC";
		}

		@Override
		public String getMessage() {
			return "Ingresá a DOC TO DOC ahora para leer la nota completa sobre el Curso, Congreso y/o Jornada publicado";
		}
	},
	/*Esta notificacion es para avisar a los usuarios que hay un nuevo producto o servicio*/
	NEW_PRODUCTANDSERVICES {
		@Override
		public int getIntValue() {
			return 9;
		}

		@Override
		public String getTitle() {
			return "Se ha publicado un nuevo Producto o Servicio en DOC TO DOC";
		}

		@Override
		public String getMessage() {
			return "Ingresá a DOC TO DOC ahora para leer sobre el nuevo Producto o Servicio";
		}
	},
	NEW_OFFER_SEMI_MATCH {
		@Override
		public int getIntValue() {
			return 10;
		}

		@Override
		public String getTitle() {
			return "Nueva oferta en DOC TO DOC";
		}

		@Override
		public String getMessage() {
			return "Hay ofertas de trabajo que podrían interesarte";
		}
	};

	public abstract int getIntValue();

	public abstract String getTitle();

	public abstract String getMessage();

}
