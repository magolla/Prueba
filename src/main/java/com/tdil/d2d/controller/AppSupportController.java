package com.tdil.d2d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.service.AppSupportService;
import com.tdil.d2d.service.SessionService;

//Aca van los servicios encargados del mantenimiento de la app

@Controller
public class AppSupportController {

	@Autowired
	private AppSupportService appSupportService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value = "/validateVersion/{version}/{platform}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<Boolean>> validateAppVersion(@PathVariable("version") int version, @PathVariable("platform") int platform) {

		boolean needUpdate = this.appSupportService.validateVersion(version, platform);
		
		return new ResponseEntity<GenericResponse<Boolean>>(new GenericResponse<Boolean>(needUpdate, HttpStatus.OK.value()), HttpStatus.OK);
	}

}
