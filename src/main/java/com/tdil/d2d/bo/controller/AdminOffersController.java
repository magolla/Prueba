package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tdil.d2d.bo.dto.BoJobDTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
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
	
	
	@RequestMapping(value = {"/BoOffers/save"} , method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> save(@Valid BoJobDTO boJob) {
		
		
//		return new ResponseEntity<String>(boJob, HttpStatus.OK);
		return null;
	}
	
	
	
	@RequestMapping(value = {"/new-offer"} , method = RequestMethod.GET)
	public ModelAndView userNew() {
		try{ 
			
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/offer-editor");
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("occupationList", this.specialtyService.listOccupations());
			BoJobDTO boJobDTO = new BoJobDTO();
			model.addObject("jobForm", boJobDTO);
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	@RequestMapping(value = {"/BoOffers/specialties/{occupationId}"} , method = RequestMethod.GET)
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
	
	
	@RequestMapping(value = {"/BoOffers/tasks/{specialtyId}"} , method = RequestMethod.GET)
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
	
	
	@RequestMapping(value = "/BoOffers/countries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
