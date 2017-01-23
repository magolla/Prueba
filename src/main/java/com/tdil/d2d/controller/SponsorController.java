package com.tdil.d2d.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.dto.SponsorDTO;
import com.tdil.d2d.controller.api.request.CreateSponsorRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.service.SponsorService;

@Controller
public class SponsorController {

	private Logger logger = LoggerFactory.getLogger(SponsorController.class);

	@Autowired
	private SponsorService sponsorService;

	@RequestMapping(value = "/sponsors", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> createSponsor(@Valid @RequestBody CreateSponsorRequest request) {
		logger.info("Executing createSponsor");
		Sponsor sponsor = this.sponsorService.createSponsor(request.getName(), request.getBase64img());
		GenericResponse<Sponsor> response = new GenericResponse<>(200, sponsor);
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/sponsors", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> listSponsorCodes() {
		logger.info("Executing listSponsorCodes");
		List<Sponsor> sponsors = this.sponsorService.getAllSponsors();
		List<SponsorDTO> sponsorsDTO = sponsors.stream().map(elem -> new SponsorDTO(elem)).collect(Collectors.toList());
		GenericResponse<List<SponsorDTO>> response = new GenericResponse<>(200, sponsorsDTO);
		return ResponseEntity.ok(response);
	}

}
