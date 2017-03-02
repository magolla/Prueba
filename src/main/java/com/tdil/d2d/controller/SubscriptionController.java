package com.tdil.d2d.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.dto.SubscriptionDTO;
import com.tdil.d2d.controller.api.request.GenerateSuscriptionRequest;
import com.tdil.d2d.controller.api.request.RedeemSponsorCodeRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.SessionService;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class SubscriptionController extends AbstractController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private SessionService sessionService;

	/**
	 * Deprecado utilizar el que se encuentra en SponsorCodeController
	 *
	 * @param useSponsorCodeRequest
	 * @param bidingResult
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/subscription/sponsor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> useSponsorCode(
			@Valid @RequestBody RedeemSponsorCodeRequest useSponsorCodeRequest, BindingResult bidingResult) {
		if (bidingResult.hasErrors()) {
			return new ResponseEntity<ApiResponse>(
					getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())),
					HttpStatus.BAD_REQUEST);
		}
		try {
			boolean response = this.subscriptionService.useSponsorCode(useSponsorCodeRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse) null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/subscription/status", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> getSubscriptionInformation(BindingResult bidingResult) {

		User user = this.sessionService.getUserLoggedIn();
		Subscription subscription = this.subscriptionService.getActiveSubscription(user.getId());

		SubscriptionDTO dto = new SubscriptionDTO(subscription);

		return ResponseEntity.ok(new GenericResponse<>(200, dto));
	}

	@RequestMapping(value = "/subscription/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> create(
			@Valid @RequestBody GenerateSuscriptionRequest createSuscriptionRequest, BindingResult bidingResult) {
		if (bidingResult.hasErrors()) {
			return new ResponseEntity<ApiResponse>(
					getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())),
					HttpStatus.BAD_REQUEST);
		}
		Subscription response;
		try {
			
			User user = this.sessionService.getUserLoggedIn();
			response = this.subscriptionService.register(user, createSuscriptionRequest.getDuration());

			if (response != null) {
				return ResponseEntity.ok(new GenericResponse<>(200, response.getId()));
			} else {
				return ResponseEntity.ok(new GenericResponse<>(400, "create_subscription_error"));
			}
		} catch (ServiceException e) {
			return ResponseEntity.ok(new GenericResponse<>(500));
		}
	}

	@RequestMapping(value = "/subscription/free", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> createFreeSuscription(BindingResult bidingResult) {
		if (bidingResult.hasErrors()) {
			return new ResponseEntity<ApiResponse>(
					getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())),
					HttpStatus.BAD_REQUEST);
		}
		Subscription response;
		try {
			
			User user = this.sessionService.getUserLoggedIn();
			response = this.subscriptionService.createFreeSubscription(user);

			if (response != null) {
				return ResponseEntity.ok(new GenericResponse<>(200, response.getId()));
			} else {
				return ResponseEntity.ok(new GenericResponse<>(400, "create_subscription_error"));
			}
		} catch (ServiceException e) {
			return ResponseEntity.ok(new GenericResponse<>(500));
		}
	}

}
