package com.tdil.d2d.persistence;

public interface ActivityAction {
    String REGISTER = "Bienvenido a DOC to DOC";
    String LOGIN = "Has ingresado a la aplicación";
    String POST_TEMPORARY_OFFER = "Publicaste una Oferta temporal";
    String POST_PERMANENT_OFFER = "Publicaste una Oferta permanente";
    String APPLY_TO_OFFER = "Aplicaste a una Oferta";
    String ACCEPT_OFFER = "Has sido aceptado para la oferta";
    String REJECT_OFFER = "Has sido rechazado para la oferta";
    String ADD_GEO_LEVEL = "Agregaste una zona en tu perfil";
    String ADD_SPECIALTY = "Agregaste una especialidad a tu perfil";
    String SET_AVATAR = "Agregaste o modificaste tu imagen de perfil";
    String SET_LICENSE = "Agregaste o modificaste tu Matrícula";
    String CHANGE_INSTITUTION_TYPE = "Has definido o modificado el tipo de instituciones";
    String ADD_TASK_TO_PROFILE = "Agrgaste un trabajo de interes";
    String REMOVE_TASK_FROM_PROFILE = "Quitaste un trabajo de interes";
    String ADD_SUBSCRIPTION = "Has contratado una suscripción";
    String PROFILE_COMPLETION = "Has completado tu perfil";
}