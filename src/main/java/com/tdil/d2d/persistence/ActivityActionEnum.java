package com.tdil.d2d.persistence;

public enum ActivityActionEnum {

	REGISTER ( "Bienvenido a DOC to DOC",1),
	LOGIN ( "Has ingresado a la aplicación",1),
	POST_TEMPORARY_OFFER ( "Publicaste una Oferta temporal",1),
	POST_PERMANENT_OFFER ( "Publicaste una Oferta permanente",1),
	APPLY_TO_OFFER ( "Aplicaste a una Oferta",1),
	ACCEPT_OFFER ( "Has sido aceptado para la oferta",1),
	REJECT_OFFER ( "Has sido rechazado para la oferta",1),
	CLOSED_OFFER ( "La oferta ha sido cerrada",1),
	ADD_GEO_LEVEL ( "Agregaste una zona en tu perfil",1),
	ADD_SPECIALTY ( "Agregaste una especialidad a tu perfil",1),
	SET_AVATAR ( "Agregaste o modificaste tu imagen de perfil",1),
	SET_LICENSE ( "Agregaste o modificaste tu Matrícula",1),
	CHANGE_INSTITUTION_TYPE ( "Has definido o modificado el tipo de instituciones",1),
	ADD_TASK_TO_PROFILE ( "Agrgaste un trabajo de interes",1),
	REMOVE_TASK_FROM_PROFILE ( "Quitaste un trabajo de interes",1),
	ADD_SUBSCRIPTION ( "Has contratado una suscripción",1),
	PROFILE_COMPLETION ( "Has completado tu perfil",1);
	
	private final String message;
	private final long value;
	
	ActivityActionEnum(String message,long value){
		this.message = message;
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public long getValue() {
		return value;
	}
	
}
