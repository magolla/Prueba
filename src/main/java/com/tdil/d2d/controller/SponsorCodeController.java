package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.request.GenerateSponsorCodesRequest;
import com.tdil.d2d.controller.api.request.RedeemSponsorCodeRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.service.SessionService;
import com.tdil.d2d.service.SponsorCodeService;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
public class SponsorCodeController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(SponsorCodeController.class);

	@Autowired
	SponsorCodeService sponsorCodeService;

	@Autowired
	private SessionService sessionService;

	@RequestMapping(value = "/codes/{sponsorId}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> listSponsorCodes(@Valid @PathVariable long sponsorId) {
		List<SponsorCode> codes = this.sponsorCodeService.listSponsorCodesBySponsorId(sponsorId);
		ApiResponse<List<SponsorCode>> apiResponse = new ApiResponse(200, codes);
		return ResponseEntity.ok(apiResponse);
	}

	@RequestMapping(value = "/codes/generate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> generateSponsorCodes(@Valid @RequestBody GenerateSponsorCodesRequest generateSponsorCodesRequest, BindingResult bidingResult) {

		List<SponsorCode> codes = this.sponsorCodeService.generateSponsorCodes(
				generateSponsorCodesRequest.getSponsorId(),
				generateSponsorCodesRequest.getCodesCount(),
				generateSponsorCodesRequest.getUnits(),
				SubscriptionTimeUnit.valueOf(generateSponsorCodesRequest.getTimeUnits()));
		ApiResponse<List<SponsorCode>> apiResponse = new ApiResponse(200, codes);
		return ResponseEntity.ok(apiResponse);

	}

	@RequestMapping(value = "/codes/redeem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> redeemSponsorCode(@Valid @RequestBody RedeemSponsorCodeRequest redeemSponsorCodeRequest, BindingResult bidingResult) {

		this.sponsorCodeService.consumeSponsorCode(this.sessionService.getUserLoggedIn(), redeemSponsorCodeRequest.getSponsorCode());
		ApiResponse<List<SponsorCode>> apiResponse = new ApiResponse(200, "ok");
		return ResponseEntity.ok(apiResponse);

	}
}
