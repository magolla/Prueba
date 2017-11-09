package com.tdil.d2d.bo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.UserService;

@Controller
public class AdminOffersController {

	@Autowired
	private UserService userService;

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
	
	
	
	@RequestMapping(value = {"/new-offer"} , method = RequestMethod.GET)
	public ModelAndView userNew() {
		try{ 
			
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/offer-editor");
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
}
