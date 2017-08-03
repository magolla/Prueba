package com.tdil.d2d.bo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.FilterSubscriptionReportDTO;
import com.tdil.d2d.bo.dto.SubscriptionReportDTO;
import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.BOReportsService;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.utils.LoggerManager;




@Controller
public class AdminReportsController {
    
	@Autowired
	private BOReportsService reportsService;
	
	@Autowired
	private GeoService geoService;
	
	@RequestMapping(value = {"/reports/details"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/reports-detail");

		return model;

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	@RequestMapping(value = {"/reports/subscription"} , method = RequestMethod.GET)
	public ModelAndView subscriptionReport() {
		try{ 
			
			ModelAndView model = new ModelAndView();
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("filterForm", new FilterSubscriptionReportDTO());
			
			model.setViewName("admin/subscription-report");
	
			return model;
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}
	}
	
	@RequestMapping(value = "/reports/subscription", method = RequestMethod.POST)
	public ModelAndView saveNote(@Valid FilterSubscriptionReportDTO filterDTO, BindingResult bindingResult) {
		try { 
			
			SubscriptionReportDTO result = this.reportsService.getSubscriptionReportDTO();
			
			ModelAndView model = new ModelAndView();
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("filterForm", filterDTO);
			model.addObject("subscriptionList", result.getList());
			model.addObject("registeredUsers", result.getCountUsers());
			model.addObject("activeSubscriptions", result.getCountSubscriptions());
			
			model.setViewName("admin/subscription-report");
	
			return model;

		} catch(Exception e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
}
    
