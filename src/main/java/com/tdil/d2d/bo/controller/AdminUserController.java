package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.controller.api.dto.ContactMotiveDTO;
import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.service.BOUserService;
import com.tdil.d2d.utils.LoggerManager;



@Controller
public class AdminUserController {
    
	@Autowired
	private BOUserService userService;
	
	@RequestMapping(value = {"/users"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/users");

		return model;

	}
	
	@RequestMapping(value = "/list/bo-users", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<BOUserDTO>>> getNotes(@RequestParam Map<String, Object> params) {
		try{ 
			
			List<BOUserDTO> users = this.userService.getAll();
	
			return ResponseEntity.ok(new GenericResponse<>(200, users));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<BOUserDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
    
