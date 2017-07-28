package com.tdil.d2d.bo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.BOReportsService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.utils.LoggerManager;




@Controller
public class AdminReportsController {
    
	@Autowired
	private BOReportsService reportsService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@RequestMapping(value = {"/reports/details"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/reports-detail");

		return model;

	}
	
	@RequestMapping(value = "/reports/all-jobsoffer", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<BOJobOfferDTO>>> getAllJobOffers() {
		try{ 
			
			List<BOJobOfferDTO> reports = this.reportsService.getAllJobOffers();
	
			return ResponseEntity.ok(new GenericResponse<>(200, reports));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<BOJobOfferDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
    
