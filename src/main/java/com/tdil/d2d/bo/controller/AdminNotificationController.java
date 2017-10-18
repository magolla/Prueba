package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.BoNotificationDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.NotificationBackofficeService;
import com.tdil.d2d.service.SpecialtyService;

@Controller
public class AdminNotificationController {

	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private NotificationBackofficeService notificationBackofficeService;
	

	@RequestMapping(value = {"/BoNotification"} , method = RequestMethod.GET)
	public ModelAndView notificationHome() {

		ModelAndView model = new ModelAndView();
		BoNotificationDTO boNotificationDTO = new BoNotificationDTO();
		model.addObject("notificationForm", boNotificationDTO); 
		try {
			model.addObject("occupationList", this.specialtyService.listOccupations());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.setViewName("admin/notification-form");

		return model;
	}


	@RequestMapping(value = {"/BoNotification/send"} , method = RequestMethod.POST)
	public ModelAndView sendNotification(@Valid @ModelAttribute("notificationForm") BoNotificationDTO boNotificationDTO,BindingResult result) {

		Map<String, String> errors = validateRequest(boNotificationDTO);
		
		

		if(!errors.isEmpty()) {
			ModelAndView model = new ModelAndView();
			model.addObject("notificationForm",boNotificationDTO);
			try {
				model.addObject("occupationList", this.specialtyService.listOccupations());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			model.addObject("errors",errors);
			model.setViewName("admin/notification-form");

			return model;
		}

		boolean userList = notificationBackofficeService.sendBackOfficeNotification(boNotificationDTO);

		ModelAndView model = new ModelAndView();
		model.addObject("notificationForm",boNotificationDTO);
		try {
			model.addObject("occupationList", this.specialtyService.listOccupations());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.setViewName("admin/notification-form");

		return model;
	}

	private Map<String, String> validateRequest(BoNotificationDTO boNotificationDTO) {

		Map<String, String> errors = new HashMap<String,String>(); 

		if(!boNotificationDTO.isAllUser() && (boNotificationDTO.getUserIds() == null|| boNotificationDTO.getUserIds().trim().isEmpty()) && (boNotificationDTO.getOccupations() == null || boNotificationDTO.getOccupations().isEmpty())) {
			errors.put("idsError","Se debe agregar Ocupaciones o Id's de usuarios");
		} else if (!boNotificationDTO.isAllUser()) {
			if(!boNotificationDTO.getUserIds().trim().isEmpty()) {
				try {
					Stream.of(boNotificationDTO.getUserIds().split("\\s*,\\s*")).map(Integer::valueOf).toArray(Integer[]::new);
				} catch (Exception e) {
					errors.put("idsError","El formato de los id's es incorrecto");
				}	
			}
		}

		if(boNotificationDTO.getTitulo().trim().isEmpty()) {
			errors.put("titleError","El titulo no puede estar vacio.");
		}

		if(boNotificationDTO.getMessage().trim().isEmpty()) {
			errors.put("messageError","El mensaje no puede estar vacio.");
		}

		return errors;
	}


	@RequestMapping(value = {"/specialties/{occupationId}","/BoNotification/specialties/{occupationId}"} , method = RequestMethod.GET)
	public ModelAndView getSpecialties(@PathVariable long occupationId) {
		try{ 

			ModelAndView model = new ModelAndView();

			OccupationDTO occupation = specialtyService.getOccupationDTOById(occupationId);

			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(occupationId);
			model.addObject("occupation", occupation);
			model.addObject("specialtyList", specialties);
			model.setViewName("admin/specialty-note-editor");

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}

}
