package com.tdil.d2d.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.dto.ContactMotiveDTO;
import com.tdil.d2d.controller.api.request.CreateContactRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.ContactService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class ContactController {
    
    @Autowired
    private ContactService service;
    
    @RequestMapping(value = "/api/contact/motives", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Collection<ContactMotiveDTO>>> listContactMotives() {
    	try {
			Collection<ContactMotiveDTO> levels = this.service.listContactMotives();
			return new ResponseEntity<GenericResponse<Collection<ContactMotiveDTO>>>(new GenericResponse<Collection<ContactMotiveDTO>>(levels,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Collection<ContactMotiveDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/contact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createContact(@Valid @RequestBody CreateContactRequest createOfferRequest) {
    	try {
			boolean response = this.service.createContact(createOfferRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.CREATED.value()), HttpStatus.CREATED);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
}
