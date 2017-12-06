package com.tdil.d2d.bo.formValidators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tdil.d2d.bo.dto.BoJobDTO;

@Component
public class BoJobOfferValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return BoJobDTO.class.equals(arg0);
	}

	@Override
	public void validate(Object obj, Errors errors) {

		BoJobDTO boJob = (BoJobDTO) obj;


		//Se valida el usuario seleccionado
		if (boJob.getUserId() == 0) {
			errors.rejectValue("userId", "userId.required");
		}

		//Se validan las especialidades
		if(boJob.getOccupationId() == 0 || boJob.getSpecialtyId() == 0 || boJob.getTaskId() == 0) {
			errors.rejectValue("occupationId", "especialties.required");
		}
		
		
		//Se Valida la Geolocalizacion
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "geoDto.name", "geoDto.name.required");

		if(boJob.isPermanent()) {
			//Se valida que el titulo y subtitulo no esten vacios
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subtitle", "subtitle.required");
		} else {
			//Se valida que la fecha y hora sean correctas
			if(boJob.getOfferDate() == null) {
				errors.rejectValue("offerDate", "offerDate.required");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "offerHour", "offerHour.required");
			
		}

	}

}
