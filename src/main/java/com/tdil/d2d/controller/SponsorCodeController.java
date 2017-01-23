package com.tdil.d2d.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.dto.ReedemCodeDTO;
import com.tdil.d2d.controller.api.request.GenerateSponsorCodesRequest;
import com.tdil.d2d.controller.api.request.RedeemSponsorCodeRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.service.SessionService;
import com.tdil.d2d.service.SponsorCodeService;

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
		GenericResponse<List<SponsorCode>> apiResponse = new GenericResponse<>(200, codes);
		return ResponseEntity.ok(apiResponse);
	}

	@RequestMapping(value = "/codes/generate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> generateSponsorCodes(@Valid @RequestBody GenerateSponsorCodesRequest generateSponsorCodesRequest, BindingResult bidingResult) {

		List<SponsorCode> codes = this.sponsorCodeService.generateSponsorCodes(
				generateSponsorCodesRequest.getSponsorId(),
				generateSponsorCodesRequest.getCodesCount(),
				generateSponsorCodesRequest.getUnits(),
				SubscriptionTimeUnit.valueOf(generateSponsorCodesRequest.getTimeUnits()));
		GenericResponse<List<SponsorCode>> apiResponse = new GenericResponse<>(200, codes);
		return ResponseEntity.ok(apiResponse);

	}

	@RequestMapping(value = "/codes/redeem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> redeemSponsorCode(@Valid @RequestBody RedeemSponsorCodeRequest redeemSponsorCodeRequest, BindingResult bidingResult) {

		Subscription suscription = this.sponsorCodeService.consumeSponsorCode(this.sessionService.getUserLoggedIn(), redeemSponsorCodeRequest.getSponsorCode());
		if(suscription==null){
			GenericResponse<String> apiResponse = new GenericResponse<>(400, "invalid_code");
			return ResponseEntity.ok(apiResponse);

		}
		GenericResponse<ReedemCodeDTO> apiResponse = new GenericResponse<>(200, new ReedemCodeDTO(suscription));
		return ResponseEntity.ok(apiResponse);

	}
}
