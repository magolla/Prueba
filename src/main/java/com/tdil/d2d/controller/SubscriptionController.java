package com.tdil.d2d.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.request.UseSponsorCodeRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class SubscriptionController {
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    
    @RequestMapping(value = "/api/subscription/sponsor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> useSponsorCode(@Valid @RequestBody UseSponsorCodeRequest useSponsorCodeRequest) {
    	try {
			boolean response = this.subscriptionService.useSponsorCode(useSponsorCodeRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    
}