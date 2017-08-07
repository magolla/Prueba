package com.tdil.d2d.bo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.FilterJobOfferReportDTO;
import com.tdil.d2d.bo.dto.FilterSubscriptionReportDTO;
import com.tdil.d2d.bo.dto.JobOfferReportDTO;
import com.tdil.d2d.bo.dto.SubscriptionReportDTO;
import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.BOReportsService;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.utils.LoggerManager;




@Controller
public class AdminReportsController {
    
	@Autowired
	private BOReportsService reportsService;
	
	@Autowired
	private GeoService geoService;

	@Autowired
	private SpecialtyService specialtyService;
	
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
			FilterSubscriptionReportDTO form = new FilterSubscriptionReportDTO();
			List<Long> geoLevels2 = new ArrayList<Long>();
			geoLevels2.add(-1L);
			form.setGeoLevels2(geoLevels2);
			form.setFree(true);
			form.setAndroid(true);
			form.setIos(true);
			form.setSponsor(true);
			model.addObject("filterForm", form);
			
			SubscriptionReportDTO result = this.reportsService.getSubscriptionReportDTO(form);
			model.addObject("subscriptionList", result.getList());
			model.addObject("registeredUsers", result.getCountAllUsers());
			model.addObject("registeredUsersB", result.getCountUsersB());
			model.addObject("activeSubscriptions", result.getCountSubscriptions());
			
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
	public ModelAndView subscriptionReportPost(@Valid FilterSubscriptionReportDTO filterDTO, BindingResult bindingResult) {
		try { 
			
			SubscriptionReportDTO result = this.reportsService.getSubscriptionReportDTO(filterDTO);
			
			ModelAndView model = new ModelAndView();
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("filterForm", filterDTO);
			model.addObject("subscriptionList", result.getList());
			model.addObject("registeredUsers", result.getCountAllUsers());
			model.addObject("registeredUsersB", result.getCountUsersB());
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

	@RequestMapping(value = {"/reports/jobofferstats"} , method = RequestMethod.GET)
	public ModelAndView jobOfferReport() {
		try{ 
			
			FilterJobOfferReportDTO defaultFilterDTO = new FilterJobOfferReportDTO();
			List<Long> geoLevels2 = new ArrayList<Long>();
			geoLevels2.add(-1L);
			defaultFilterDTO.setGeoLevels2(geoLevels2);
			
			Calendar now = Calendar.getInstance();
			defaultFilterDTO.setEndMonth(now.get(Calendar.MONTH) + 1);
			defaultFilterDTO.setEndYear(now.get(Calendar.YEAR));
			
			now.add(Calendar.MONTH, -11);
			defaultFilterDTO.setStartMonth(now.get(Calendar.MONTH) + 1);
			defaultFilterDTO.setStartYear(now.get(Calendar.YEAR));

			defaultFilterDTO.setActiveOffers(true);
			defaultFilterDTO.setTotalOffers(true);
			defaultFilterDTO.setPermanentOffers(true);
			defaultFilterDTO.setTemporalOffers(true);
			defaultFilterDTO.setContracted(true);
			
			ModelAndView model = new ModelAndView();
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("occupationList", this.specialtyService.listOccupations());
			model.addObject("filterForm", defaultFilterDTO);
			
			JobOfferReportDTO report = this.reportsService.getJobOfferReportDTO(defaultFilterDTO);
			model.addObject("report", report);
			
			model.setViewName("admin/job-offer-report");
	
			return model;
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}
	}
	
	@RequestMapping(value = "/reports/jobofferstats", method = RequestMethod.POST)
	public ModelAndView jobOfferReportPost(@Valid FilterJobOfferReportDTO filterDTO, BindingResult bindingResult) {
		try { 
			
			JobOfferReportDTO report = this.reportsService.getJobOfferReportDTO(filterDTO);
			
			ModelAndView model = new ModelAndView();
			model.addObject("geoList", this.geoService.listGeoLevel2());
			model.addObject("occupationList", this.specialtyService.listOccupations());
			model.addObject("filterForm", filterDTO);
			
			if(filterDTO.getOccupationId() != null && filterDTO.getOccupationId() != -1) {
				model.addObject("specialtyList", this.specialtyService.listSpecialties(filterDTO.getOccupationId()));
			}
			if(filterDTO.getSpecialtyId() != null && filterDTO.getSpecialtyId() != -1) {
				model.addObject("taskList", this.specialtyService.listTasks(filterDTO.getSpecialtyId()));
			}
			
			model.addObject("report", report);
			
			model.setViewName("admin/job-offer-report");
	
			return model;

		} catch(Exception e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	@RequestMapping(value = {"/reports/specialties/{occupationId}"} , method = RequestMethod.GET)
	public ModelAndView getSpecialties(@PathVariable long occupationId) {
		try{ 
			
			ModelAndView model = new ModelAndView();
			
			OccupationDTO occupation = specialtyService.getOccupationDTOById(occupationId);
			
			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(occupationId);
			model.addObject("occupation", occupation);
			model.addObject("specialtyList", specialties);
			
			model.setViewName("admin/specialty-job-offer-report-editor");
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	@RequestMapping(value = {"/reports/tasks/{specialtyId}"} , method = RequestMethod.GET)
	public ModelAndView getTasks(@PathVariable long specialtyId) {
		try{ 
			
			ModelAndView model = new ModelAndView();
			
			SpecialtyDTO specialty = specialtyService.getSpecialtyDTOById(specialtyId);
			
			Collection<TaskDTO> tasks = this.specialtyService.listTasks(specialtyId);
			model.addObject("specialty", specialty);
			model.addObject("taskList", tasks);
			model.setViewName("admin/task-job-offer-report-editor");
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
}
    
