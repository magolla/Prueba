package com.tdil.d2d.persistence;

public enum NotificationType {

	/*Este tipo es para notificar a un postulante que se publico una oferta que matchea el perfil*/
	NEW_OFFER_MATCH,
	/*Este tipo es para notificar a un oferente que alguien se postulo a una oferta*/
	NEW_APPLICATION,
	/*Esta oferta es para avisar al postulante que se acepto su postulacion*/
	APPLICATION_ACCEPTED,
	/*Esta notificacion es para avisar a los postulantes que una oferta se cerro, se eligio a otro candidato*/
	JOB_OFFER_CLOSE
	
}
