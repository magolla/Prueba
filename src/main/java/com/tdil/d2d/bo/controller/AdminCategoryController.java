package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tdil.d2d.bo.dto.CategoryDto;
import com.tdil.d2d.bo.dto.DatatablePaginateOutDTO;
import com.tdil.d2d.bo.dto.DatatablePaginationInDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.request.CategoryEditRequest;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.utils.LoggerManager;

@Controller
public class AdminCategoryController {


	@Autowired
	private SpecialtyService specialtyService;
	
	

	@RequestMapping(value = {"/BoCategory"} , method = RequestMethod.GET)
	public ModelAndView boOfferResume() {

		ModelAndView model = new ModelAndView();

		model.setViewName("admin/categories-table");

		return model;

	}


	@RequestMapping(value = "/BoCategory/getCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DatatablePaginateOutDTO<CategoryDto>> getUsers(HttpServletRequest request, HttpServletResponse res) {


		DatatablePaginationInDTO datatablePaginationInDTO = new DatatablePaginationInDTO(request);

		int taskCount = 0;
		int taskCountFilter = 0;
		try {
			taskCount = specialtyService.getTaskCount("");
			taskCountFilter = specialtyService.getTaskCount(datatablePaginationInDTO.getSearch());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		DatatablePaginateOutDTO<CategoryDto> datatablePaginateOutDto = new DatatablePaginateOutDTO<>();

		List<CategoryDto> categoryList = specialtyService.getTaskByIndex(datatablePaginationInDTO.getLength(), datatablePaginationInDTO.getStart(), datatablePaginationInDTO.getSearch());

		datatablePaginateOutDto.setData(categoryList);
		datatablePaginateOutDto.setDraw(Integer.valueOf(datatablePaginationInDTO.getDraw()));
		datatablePaginateOutDto.setRecordsTotal(taskCount);
		datatablePaginateOutDto.setRecordsFiltered(taskCountFilter);
		return ResponseEntity.ok(datatablePaginateOutDto);
	}

	@RequestMapping(value = "/BoCategory/saveOccupation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> saveOccupation(@RequestParam("occupationName") String occupationName) {

		if(occupationName.trim().isEmpty()) {
			return ResponseEntity.ok(new GenericResponse<>(201, "El campo no puede estar vacio."));
		}


		try {
			specialtyService.add(occupationName, "", "");
			return ResponseEntity.ok(new GenericResponse<>(200, "La ocupacion se ha cargado exitosamente."));
		} catch (ServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/BoCategory/saveSpecialty", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> saveSpecialty(@RequestParam("espOccupationId") String espOccupationId, @RequestParam("newSpecialtyName") String newSpecialtyName) {
		
		try {
			specialtyService.addSpecialtyToOccupation(espOccupationId, newSpecialtyName);
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha cargado exitosamente."));
		} catch (ServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/BoCategory/saveTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> saveTask(@RequestParam("taskName") String taskName, @RequestParam("specialtyId") String specialtyId) {
		
		try {
			specialtyService.addTaskToOccupationAndSpecialty(taskName,specialtyId);
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha cargado exitosamente."));
		} catch (ServiceException | DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/BoCategory/editOccupation", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse<String>> editOccupation(@RequestBody CategoryEditRequest body) {
		
		try {
			specialtyService.editOccupation(body.getId(), body.getName());
			return ResponseEntity.ok(new GenericResponse<>(200, "La ocupacion se ha editado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/BoCategory/editSpecialty", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse<String>> editSpecialty(@RequestBody CategoryEditRequest body) {
		
		try {
			specialtyService.editSpecialty(body.getId(), body.getName());
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha editado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/BoCategory/editTask", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse<String>> editTask(@RequestBody CategoryEditRequest body) {
		
		try {
			specialtyService.editTask(body.getId(), body.getName());
			return ResponseEntity.ok(new GenericResponse<>(200, "La tarea se ha editado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = {"/BoCategory/occupations",}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	public ResponseEntity<String> getOccupations() {
		try {
			Collection<OccupationDTO> occupationList = specialtyService.listOccupationsNoFilter();

			Gson gson = new Gson();
			String jsonString = gson.toJson(occupationList);

			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = {"/BoCategory/specialties/{specialtyId}",}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	public ResponseEntity<String> getSpecialties(@PathVariable("specialtyId") int specialtyId) {
		try {
			Collection<SpecialtyDTO> specialtyList = specialtyService.listSpecialties(specialtyId);

			Gson gson = new Gson();
			String jsonString = gson.toJson(specialtyList);

			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
