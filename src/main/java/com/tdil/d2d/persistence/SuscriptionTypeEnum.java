package com.tdil.d2d.persistence;

public enum SuscriptionTypeEnum {
	/*Este tipo es para notificar a un postulante que se publico una oferta que matchea el perfil*/
	// Tiene que llevarme al detalle de la oferta.
	//¿Que pasa si la oferta esta cerrada?
	FREE_SUSCRIPTION {
		@Override
		public int getIntValue() {
			return 1;
		}

		@Override
		public String getMessage() {
			return "Promo nueva registracion";
		}
	},
	/*Este tipo es para notificar a un oferente que alguien se postulo a una oferta*/
	// Tiene que llevarme al detalle de la oferta
	PAY_ANDROID_SUSCRIPTION {
		@Override
		public int getIntValue() {
			return 2;
		}

		@Override
		public String getMessage() {
			return "Mercadopago";
		}
	},
	/*Esta oferta es para avisar al postulante que se acepto su postulacion*/
	// Tiene que llevarme al detalle de la oferta
	PAY_IOS_SUSCRIPTION {
		@Override
		public int getIntValue() {
			return 3;
		}

		@Override
		public String getMessage() {
			return "iTunes";
		}
	},
	/*Esta notificacion es para avisar a los postulantes que una oferta se cerro, se eligio a otro candidato*/
	SPONSOR_SUSCRIPTION {
		@Override
		public int getIntValue() {
			return 4;
		}

		@Override
		public String getMessage() {
			return "Sponsor";
		}
	},
	/*Esta notificacion es para avisar a los usuarios que hay una nueva beca*/
	// Tiene que llevarme a las notas y centrarme en la clickeada(siempre y cuando no haya sido eliminada)
	NEW_FREE_SUSCRIPTION {
		@Override
		public int getIntValue() {
			return 5;
		}

		@Override
		public String getMessage() {
			return "Aplicada la promo por extension";
		}

	},
	EXTENDED_SUSCRIPTION {
		@Override
		public int getIntValue() {
			return 6;
		}

		@Override
		public String getMessage() {
			return "Extendida";
		}

	};

	public abstract int getIntValue();
	public abstract String getMessage();

}
