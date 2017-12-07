package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tdil.d2d.bo.dto.BoJobDTO;
import com.tdil.d2d.bo.dto.UserDTO;
import com.tdil.d2d.bo.formValidators.BoJobOfferValidator;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.controller.api.request.InstitutionType;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.GeoLevel;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;

@Controller
public class AdminOffersController {

	@Autowired
	private UserService userService;

	@Autowired
	private GeoService geoService;

	@Autowired
	private SpecialtyService specialtyService;

	@Autowired
	private BoJobOfferValidator boJobOfferValidator;
	
	@Autowired
	private GeoDAO geoDAO;


	@InitBinder("boJobDTO")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(boJobOfferValidator);
	}


	@RequestMapping(value = {"/BoOffers"} , method = RequestMethod.GET)
	public ModelAndView boOfferResume() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/offers-table");

		return model;

	}

	@RequestMapping(value = {"/deleteOffer"} , method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> boDeleteOffer(@RequestParam("offerId") int offerId) {
		try {
			userService.deleteOfferById(offerId);
		} catch (ServiceException | DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	


	@RequestMapping(value = {"/getOfferById"} , method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getOfferById(@RequestParam("offerId") int offerId) {
		User user;
		try {
			user = userService.getUserById(offerId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Gson gson = new Gson();

		String jsonString = gson.toJson(user);

		return new ResponseEntity<String>(jsonString, HttpStatus.OK);
	}
	
	
	

	@RequestMapping(value = {"/new-offer"} , method = RequestMethod.GET)
	public ModelAndView userNew() {
		try{ 

			ModelAndView model = new ModelAndView();
			model.setViewName("admin/offer-editor");
			model.addObject("occupationList", this.specialtyService.listOccupations());
			BoJobDTO boJobDTO = new BoJobDTO();
			model.addObject("boJobDTO", boJobDTO);
			
			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	@Transactional
	@RequestMapping(value = {"/editOffer/{offerId}"} , method = RequestMethod.GET, produces = "application/json")
	public ModelAndView boOpenOfferForEdit(@PathVariable("offerId") int offerId) {
		
		JobOfferStatusDTO offerToEdit = userService.getOfferById(offerId);
		BoJobDTO boJobDTO = offerStatusToBoJob(offerToEdit);	
	
		
		try{ 
			UserDTO user = userService.getUserWebDetails(boJobDTO.getUserId());
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/offer-editor");
			model.addObject("occupationList", this.specialtyService.listOccupations());
			model.addObject("offerId", offerId);
			model.addObject("offerEdit", offerToEdit);
			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(boJobDTO.getOccupationId());

			Collection<TaskDTO> tasks = this.specialtyService.listTasks(boJobDTO.getSpecialtyId());
			model.addObject("specialtyList", specialties);
			model.addObject("taskList", tasks);
			model.addObject("boJobDTO", boJobDTO);
			model.addObject("user", user);
			
			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}
	}
	

	private BoJobDTO offerStatusToBoJob(JobOfferStatusDTO offerToEdit) {
		BoJobDTO boJobDTO = new BoJobDTO();
		
		GeoLevel geo = null;
		try {
			geo = geoDAO.getGeoByIdAndLevel(offerToEdit.getGeoLevelId(),offerToEdit.getGeoLevelLevel());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		boJobDTO.setCompanyScreenName(offerToEdit.getCompanyScreenName());
		boJobDTO.setGeoDto(new GeoLevelDTO(offerToEdit.getGeoLevelId(),offerToEdit.getGeoLevelLevel(),geo.getName()));
		boJobDTO.setOccupationId(offerToEdit.getOccupation_id());
		boJobDTO.setSpecialtyId(offerToEdit.getSpecialty_Id());
		boJobDTO.setTaskId(offerToEdit.getTask_id());
		
		boJobDTO.setOfferText(offerToEdit.getComment());
		boJobDTO.setName(offerToEdit.getOfferentFirstname());
		boJobDTO.setLastName(offerToEdit.getOfferentLastname());
		
		if(offerToEdit.isPermanent()) {
			boJobDTO.setPermanent(true);	
			boJobDTO.setTitle(offerToEdit.getTitle());
			boJobDTO.setSubtitle(offerToEdit.getSubTitle());
		} else {
			boJobDTO.setPermanent(false);
			boJobDTO.setOfferHour(offerToEdit.getOfferHour());
			boJobDTO.setOfferDateForView(offerToEdit.getOfferDate());
		}
		
		if(offerToEdit.getInstitutionType().equals(InstitutionType.PRIVATE.name())) {
			boJobDTO.setPrivateInstitution(true);			
		} else {
			boJobDTO.setPrivateInstitution(false);
		}

		boJobDTO.setUserId(offerToEdit.getOfferent_id());
		return boJobDTO;
	}


	@RequestMapping(value = {"/BoOffers/save"} , method = RequestMethod.POST, produces = "application/json")
	public ModelAndView save(@ModelAttribute("boJobDTO") @Validated BoJobDTO boJob,BindingResult bindingResult) {

		try {
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/offer-editor");

			model.addObject("occupationList", this.specialtyService.listOccupations());
			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(boJob.getOccupationId());

			Collection<TaskDTO> tasks = this.specialtyService.listTasks(boJob.getSpecialtyId());
			model.addObject("specialtyList", specialties);
			model.addObject("taskList", tasks);


			if (bindingResult.hasErrors()) {
				if(boJob.getUserId() != 0) {
					UserDTO user = userService.getUserWebDetails(boJob.getUserId());
					model.addObject("user", user);
				}
				model.addObject("boJobDTO", boJob);
				return model;
			}
			
			this.userService.addOffer(boJob,-1);
			model.setViewName("redirect:/admin/new-offer");
			
			BoJobDTO boJobDTO = new BoJobDTO();
			model.addObject("boJobDTO", boJobDTO);
			model.addObject("confirmed", "La oferta se a cargado exitosamente.");
			return model;
		} catch (ServiceException e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	
	@RequestMapping(value = {"/BoOffers/edit/{offerId}"} , method = RequestMethod.POST, produces = "application/json")
	public ModelAndView editOffer(@ModelAttribute("boJobDTO") @Validated BoJobDTO boJob,BindingResult bindingResult, @PathVariable("offerId") int offerId) {

		try {
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/offer-editor");

			model.addObject("occupationList", this.specialtyService.listOccupations());

			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(boJob.getOccupationId());

			Collection<TaskDTO> tasks = this.specialtyService.listTasks(boJob.getSpecialtyId());
			model.addObject("specialtyList", specialties);
			model.addObject("taskList", tasks);
			
			
			if (bindingResult.hasErrors()) {
				if(boJob.getUserId() != 0) {
					UserDTO user = userService.getUserWebDetails(boJob.getUserId());
					model.addObject("user", user);
				}
				model.addObject("boJobDTO", boJob);
				return model;
			}
			
			this.userService.addOffer(boJob, offerId);

			model.setViewName("redirect:/admin/new-offer");
			BoJobDTO boJobDTO = new BoJobDTO();
			model.addObject("boJobDTO", boJobDTO);
			model.addObject("confirmed", "La oferta se a editado exitosamente.");
			return model;
		} catch (ServiceException e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}

	@RequestMapping(value = {"/BoOffers/specialties/{occupationId}","/BoOffers/edit/BoOffers/specialties/{occupationId}","/BoOffers/BoOffers/specialties/{occupationId}", "/editOffer/BoOffers/specialties/{occupationId}"} , method = RequestMethod.GET)
	public ModelAndView getSpecialties(@PathVariable long occupationId) {
		try{ 

			ModelAndView model = new ModelAndView();

			OccupationDTO occupation = specialtyService.getOccupationDTOById(occupationId);
			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(occupationId);
			model.addObject("occupation", occupation);
			model.addObject("specialtyList", specialties);
			model.setViewName("admin/specialty-job-offer-editor");

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}


	@RequestMapping(value = {"/BoOffers/tasks/{specialtyId}","/BoOffers/edit/BoOffers/tasks/{specialtyId}","/BoOffers/BoOffers/tasks/{specialtyId}","/editOffer/BoOffers/tasks/{specialtyId}"} , method = RequestMethod.GET)
	public ModelAndView getTasks(@PathVariable long specialtyId) {
		try{ 

			ModelAndView model = new ModelAndView();

			SpecialtyDTO specialty = specialtyService.getSpecialtyDTOById(specialtyId);

			Collection<TaskDTO> tasks = this.specialtyService.listTasks(specialtyId);
			model.addObject("specialty", specialty);
			model.addObject("taskList", tasks);
			model.setViewName("admin/task-job-offer-editor");

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}


	@RequestMapping(value = {"/BoOffers/countries","/countries", "BoOffers/edit/countries", "/editOffer/countries"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> autocomplete(@RequestParam("query") String searchString) {
		try {
			List<GeoLevelDTO> levels = this.geoService.search(searchString);

			Gson gson = new Gson();

			String jsonString = gson.toJson(levels);

			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
			//			return new ResponseEntity<GenericResponse<List<GeoLevelDTO>>>(new GenericResponse<List<GeoLevelDTO>>(levels,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
			//			return new ResponseEntity<String>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



}
