package com.tdil.d2d.bo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.bo.dto.RoleDTO;
import com.tdil.d2d.bo.dto.UserDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.BOUserService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;



@Controller
public class AdminUserController {
    
	@Autowired
	private BOUserService boUserService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/users"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/system-users");

		return model;

	}
	
	@RequestMapping(value = "/list/bo-users", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<BOUserDTO>>> getUsers() {
		try{ 
			
			List<BOUserDTO> users = this.boUserService.getAll();
	
			return ResponseEntity.ok(new GenericResponse<>(200, users));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<BOUserDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = {"/public-users"} , method = RequestMethod.GET)
	public ModelAndView publicUsers() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/public-users");

		return model;

	}
	
	@RequestMapping(value = "/list/public-users", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<UserDTO>>> getPublicUsers() {
		try{ 
			
			List<UserDTO> users = this.userService.getAll();
	
			return ResponseEntity.ok(new GenericResponse<>(200, users));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<UserDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = {"/users/{userId}"} , method = RequestMethod.GET)
	public ModelAndView userEdit(@PathVariable long userId) {
		try{ 
			
			ModelAndView model = new ModelAndView();
			model.addObject("user_id", userId);
			model.setViewName("admin/user-editor");
			
			List<RoleDTO> roles = this.boUserService.getAllRoles();
			model.addObject("roles", roles);
			
			BOUserDTO user = boUserService.find(userId);
			model.addObject("user", user);
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
}
    
