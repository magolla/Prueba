package com.tdil.d2d.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class GeoController {

    @Autowired
    private GeoService geoService;

    @RequestMapping(value = "/api/geo/autocomplete", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<List<GeoLevelDTO>>> autocomplete(@RequestParam("searchString") String searchString) {
    	try {
			List<GeoLevelDTO> levels = this.geoService.search(searchString);
			return new ResponseEntity<GenericResponse<List<GeoLevelDTO>>>(new GenericResponse<List<GeoLevelDTO>>(levels,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<GeoLevelDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
        
}
