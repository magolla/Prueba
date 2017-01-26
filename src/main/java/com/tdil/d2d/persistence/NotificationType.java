package com.tdil.d2d.persistence;

public enum NotificationType {
	/*Este tipo es para notificar a un postulante que se publico una oferta que matchea el perfil*/
	NEW_OFFER_MATCH {
		@Override
		public int getIntValue() {
			return 1;
		}
	},
	/*Este tipo es para notificar a un oferente que alguien se postulo a una oferta*/
	NEW_APPLICATION {
		@Override
		public int getIntValue() {
			return 2;
		}
	},
	/*Esta oferta es para avisar al postulante que se acepto su postulacion*/
	APPLICATION_ACCEPTED {
		@Override
		public int getIntValue() {
			return 3;
		}
	},
	/*Esta notificacion es para avisar a los postulantes que una oferta se cerro, se eligio a otro candidato*/
	JOB_OFFER_CLOSE {
		@Override
		public int getIntValue() {
			return 4;
		}
	};

	public abstract int getIntValue();
}
