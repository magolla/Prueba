package com.tdil.d2d.bo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.BOUserDTO;
import com.tdil.d2d.bo.dto.DatatablePaginateOutDto;
import com.tdil.d2d.bo.dto.DatatablePaginationInDTO;
import com.tdil.d2d.bo.dto.ResultDTO;
import com.tdil.d2d.bo.dto.RoleDTO;
import com.tdil.d2d.bo.dto.UserDTO;
import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.Base64DTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;
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
	public ResponseEntity<DatatablePaginateOutDto<UserDTO>> getPublicUsers(HttpServletRequest request) {

		DatatablePaginationInDTO datatablePaginationInDTO = new DatatablePaginationInDTO(request); 

		int taskCount = 0; 
		int taskCountFilter = 0; 

		taskCount = userService.getUserCount(""); 
		taskCountFilter = userService.getUserCount(datatablePaginationInDTO.getSearch()); 


		List<UserDTO> users = this.userService.getUserByIndex(datatablePaginationInDTO.getLength(), datatablePaginationInDTO.getStart(), datatablePaginationInDTO.getSearch());

		DatatablePaginateOutDto<UserDTO> datatablePaginateOutDto = new DatatablePaginateOutDto<>(); 


		datatablePaginateOutDto.setData(users); 
		datatablePaginateOutDto.setDraw(Integer.valueOf(datatablePaginationInDTO.getDraw())); 
		datatablePaginateOutDto.setRecordsTotal(taskCount); 
		datatablePaginateOutDto.setRecordsFiltered(taskCountFilter); 

		return ResponseEntity.ok(datatablePaginateOutDto); 

	}


	@RequestMapping(value = {"/users/{userId}"} , method = RequestMethod.GET)
	public ModelAndView userEdit(@PathVariable long userId) {
		try{ 

			ModelAndView model = new ModelAndView();

			List<RoleDTO> roles = this.boUserService.getAllRoles();
			model.addObject("roles", roles);

			BOUserDTO user = boUserService.find(userId);
			model.addObject("userForm", user);

			model.setViewName("admin/user-editor");

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}

	@RequestMapping(value = {"/new-user"} , method = RequestMethod.GET)
	public ModelAndView userNew() {
		try{ 

			ModelAndView model = new ModelAndView();
			model.setViewName("admin/user-editor");

			List<RoleDTO> roles = this.boUserService.getAllRoles();
			model.addObject("roles", roles);

			BOUserDTO user = new BOUserDTO();
			model.addObject("userForm", user);

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}

	@RequestMapping(value = {"/users/save"} , method = RequestMethod.POST)
	public ModelAndView userEdit(@ModelAttribute("userForm") BOUserDTO user) {
		try{ 

			ResultDTO result = boUserService.save(user);

			if(!result.isSuccess()){
				ModelAndView model = new ModelAndView();
				model.setViewName("admin/user-editor");
				model.addObject("errors", result.getErrors());

				List<RoleDTO> roles = this.boUserService.getAllRoles();
				model.addObject("roles", roles);

				model.addObject("userForm", user);

				return model;
			}

			ModelAndView model = new ModelAndView();
			model.setViewName("redirect:/admin/users");

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}


	@RequestMapping(value = {"/users/pdfByUser/{userId}"} , method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPdfFromUser(@PathVariable long userId) {

		
		
		Base64DTO base64dto = null;
		User user = null;
		try {
			user = userService.getUserById(userId);
			base64dto = userService.getPdfCVBase64ById(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(base64dto.getBase64().getBytes());

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData("inline", "CV_" + user.getFirstname() + "_" + user.getLastname() + ".pdf");
		return new ResponseEntity<byte[]>(decoded, headers, HttpStatus.OK);

	}

	@RequestMapping(value = {"/public-users/{userId}"} , method = RequestMethod.GET)
	public ModelAndView showUserData(@PathVariable long userId) {
		try{ 

			ModelAndView model = new ModelAndView();

			UserDTO user = this.userService.getUserWebDetails(userId);
			model.addObject("user", user);

			model.setViewName("admin/show-user");

			return model;

		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}

	@RequestMapping(value = "/public-users/{userId}/activity", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<ActivityLogDTO>>> getLogs(@PathVariable long userId) {
		try{ 

			List<ActivityLogDTO> logs = this.userService.getWebActivityLog(userId);

			return ResponseEntity.ok(new GenericResponse<>(200, logs));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<ActivityLogDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@RequestMapping(value = "/public/lastOffers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>> getLastOffers() {
		List<JobOfferStatusDTO> myOffers = this.userService.getLastOffers();
		return new ResponseEntity<GenericResponse<List<JobOfferStatusDTO>>>(new GenericResponse<List<JobOfferStatusDTO>>(myOffers,HttpStatus.OK.value()), HttpStatus.OK);
	}

}

