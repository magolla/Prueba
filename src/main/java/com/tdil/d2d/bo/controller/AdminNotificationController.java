package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
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
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.service.NotificationBackofficeService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.service.SponsorService;

@Controller
public class AdminNotificationController {

	@Autowired
	private SpecialtyService specialtyService;

	@Autowired
	private NotificationBackofficeService notificationBackofficeService;
	
	@Autowired
	private SponsorService sponsorService;


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
		
		List<Sponsor> sponsorList = this.sponsorService.getAllSponsors();
		
		model.addObject("sponsorList", sponsorList);
		
		
		model.setViewName("admin/notification-form");

		return model;
	}


	@RequestMapping(value = {"/BoNotification/send"} , method = RequestMethod.POST)
	public ModelAndView sendNotification(@Valid @ModelAttribute("notificationForm") BoNotificationDTO boNotificationDTO,BindingResult result,HttpServletRequest request) {

		if(Boolean.parseBoolean(request.getParameter("sendTest"))) {
			boNotificationDTO.setOccupations(null);
			boNotificationDTO.setSpecialties(null);
			boNotificationDTO.setUserIds(new String());
		} else {
			boNotificationDTO.setUserTestIds(new String());
		}

		Map<String, String> errors = validateRequest(boNotificationDTO, Boolean.parseBoolean(request.getParameter("sendTest")));

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

		boolean sendSuccess = notificationBackofficeService.sendBackOfficeNotification(boNotificationDTO);

		ModelAndView model = new ModelAndView();
		model.addObject("notificationForm",boNotificationDTO);
		try {
			model.addObject("occupationList", this.specialtyService.listOccupations());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if(sendSuccess) {
			errors.put("pushSuccess", "Las notificaciones se enviaron existosamente");	
		} else {
			errors.put("pushFailed", "Error al enviar las notificaciones");
		}
		model.addObject("errors",errors);
//		model.setViewName("admin/notification-form");
		model.setViewName("redirect:/admin/BoNotification");

		return model;
	}

	private Map<String, String> validateRequest(BoNotificationDTO boNotificationDTO, boolean isTest) {

		Map<String, String> errors = new HashMap<String,String>(); 

		if(!isTest) {
			if(!boNotificationDTO.isAllUser() && (boNotificationDTO.getUserIds() == null|| boNotificationDTO.getUserIds().trim().isEmpty()) && (boNotificationDTO.getOccupations() == null || boNotificationDTO.getOccupations().isEmpty())) {
				errors.put("idsError","Se debe agregar Intereses o Id's de usuarios");
			} else if (!boNotificationDTO.isAllUser()) {
				if(!boNotificationDTO.getUserIds().trim().isEmpty()) {
					try {
						Integer[] userList = Stream.of(boNotificationDTO.getUserIds().split("\\s*,\\s*")).map(Integer::valueOf).toArray(Integer[]::new);
						if(userList.length == 0) {
							errors.put("idsError","El formato de los id's es incorrecto");
						}
					} catch (Exception e) {
						errors.put("idsError","El formato de los id's es incorrecto");
					}	
				}
			}	
		} else {
			if((boNotificationDTO.getUserTestIds() == null || boNotificationDTO.getUserTestIds().trim().isEmpty())) {
				errors.put("idsTestError","Se debe agregar Id's de usuarios");
			} else if(!boNotificationDTO.getUserTestIds().trim().isEmpty()) {
				try {
					Integer[] userList = Stream.of(boNotificationDTO.getUserTestIds().split("\\s*,\\s*")).map(Integer::valueOf).toArray(Integer[]::new);
					if(userList.length == 0) {
						errors.put("idsTestError","El formato de los id's es incorrecto");
					}
				} catch (Exception e) {
					errors.put("idsTestError","El formato de los id's es incorrecto");
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
