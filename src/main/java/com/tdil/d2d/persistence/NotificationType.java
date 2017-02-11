package com.tdil.d2d.persistence;

public enum NotificationType {
	/*Este tipo es para notificar a un postulante que se publico una oferta que matchea el perfil*/
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
	};

	public abstract int getIntValue();
	
	public abstract String getTitle();
	
	public abstract String getMessage();
	
}
