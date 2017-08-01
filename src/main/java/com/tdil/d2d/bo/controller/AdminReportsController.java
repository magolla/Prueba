package com.tdil.d2d.bo.controller;

import java.util.ArrayList;
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
			
			ModelAndView model = new ModelAndView();
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("filterForm", filterDTO);
			model.addObject("subscriptionList", this.getSubscriptionList());
			model.addObject("registeredUsers", 1000);
			model.addObject("activeSubscriptions", 900);
			
			model.setViewName("admin/subscription-report");
	
			return model;

		} catch(Exception e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	private List<SubscriptionReportDTO> getSubscriptionList() {
		List<SubscriptionReportDTO> subscriptions = new ArrayList<SubscriptionReportDTO>();
		subscriptions.add(new SubscriptionReportDTO("Suscripciones pagas android mercado pago", 100, "rgba(255, 165, 0, 1)"));
		subscriptions.add(new SubscriptionReportDTO("Suscripciones por sponsor", 200, "rgba(0, 128, 128, 1)"));
		subscriptions.add(new SubscriptionReportDTO("Suscripciones inapp iOS", 300, "rgba(3, 72, 123, 1)"));
		subscriptions.add(new SubscriptionReportDTO("Suscripciones gratuitas dtd", 400, "rgba(238,67,100, 1)"));
		
		return subscriptions;
	}
}
    
