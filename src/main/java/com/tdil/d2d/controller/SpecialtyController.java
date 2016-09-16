package com.tdil.d2d.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class SpecialtyController {

    @Autowired
    private SpecialtyService service;

    @RequestMapping(value = "/api/specialties/occupations", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Collection<OccupationDTO>>> listOccupations() {
    	try {
			Collection<OccupationDTO> levels = this.service.listOccupations();
			return new ResponseEntity<GenericResponse<Collection<OccupationDTO>>>(new GenericResponse<Collection<OccupationDTO>>(levels,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Collection<OccupationDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/specialties/occupation/{occupationId}/specialties", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Collection<SpecialtyDTO>>> listSpecialties(@PathVariable long occupationId) {
    	try {
			Collection<SpecialtyDTO> levels = this.service.listSpecialties(occupationId);
			return new ResponseEntity<GenericResponse<Collection<SpecialtyDTO>>>(new GenericResponse<Collection<SpecialtyDTO>>(levels,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Collection<SpecialtyDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/specialties/specialty/{specialtyId}/tasks", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Collection<TaskDTO>>> listTasks(@PathVariable long specialtyId) {
    	try {
			Collection<TaskDTO> levels = this.service.listTasks(specialtyId);
			return new ResponseEntity<GenericResponse<Collection<TaskDTO>>>(new GenericResponse<Collection<TaskDTO>>(levels,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Collection<TaskDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
        
}
