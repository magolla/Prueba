package com.tdil.d2d.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.dto.JobApplicationDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.request.ApplyToOfferRequest;
import com.tdil.d2d.controller.api.request.CreateJobOfferRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.security.JwtTokenUtil;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class OfferController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    
    @RequestMapping(value = "/api/offer/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> offerCreate(@Valid @RequestBody CreateJobOfferRequest createOfferRequest) {
    	try {
			boolean response = this.userService.createJobOffer(createOfferRequest);
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
    
    @RequestMapping(value = "/api/user/offers", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>> offers() {
    	try {
			List<JobOfferStatusDTO> myOffers = this.userService.getMyOffers();
			return new ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>>(new GenericResponse<List<JobOfferStatusDTO>>(myOffers,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/offers/matches", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>> offersMatches() {
    	try {
			List<JobOfferStatusDTO> myOffers = this.userService.getMatchedOffers();
			return new ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>>(new GenericResponse<List<JobOfferStatusDTO>>(myOffers,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/offer/{offerId}/apply", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> apply(@PathVariable long offerId, @Valid @RequestBody ApplyToOfferRequest applyToOffer) {
    	try {
			boolean result = this.userService.apply(offerId, applyToOffer);
			if (result) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.CREATED.value()), HttpStatus.CREATED);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/offer/{offerId}/applications", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<List<JobApplicationDTO>>> offerApplications(@PathVariable long offerId) {
    	try {
    		return new ResponseEntity<GenericResponse<List<JobApplicationDTO>>>(new GenericResponse<List<JobApplicationDTO>>(this.userService.offerApplications(offerId), HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<JobApplicationDTO>>>(new GenericResponse<List<JobApplicationDTO>>(null, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/offer/{offerId}/application/{applicationId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<JobApplicationDTO>> offerApplication(@PathVariable long offerId, @PathVariable long applicationId) {
    	try {
    		return new ResponseEntity<GenericResponse<JobApplicationDTO>>(new GenericResponse<JobApplicationDTO>(this.userService.offerApplication(applicationId), HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<JobApplicationDTO>>((GenericResponse<JobApplicationDTO>)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/offer/{offerId}/application/{applicationId}/accept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> acceptOfferApplication(@PathVariable long offerId, @PathVariable long applicationId) {
    	try {
    		boolean result = this.userService.accept(offerId, applicationId);
			if (result) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/offer/{offerId}/application/{applicationId}/reject", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> rejectOfferApplication(@PathVariable long offerId, @PathVariable long applicationId) {
    	try {
    		boolean result = this.userService.reject(offerId, applicationId);
			if (result) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
}
