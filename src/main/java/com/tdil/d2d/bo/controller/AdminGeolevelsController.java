package com.tdil.d2d.bo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.tdil.d2d.bo.dto.DatatablePaginateOutDTO;
import com.tdil.d2d.bo.dto.DatatablePaginationInDTO;
import com.tdil.d2d.bo.dto.GeosDto;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.GeoService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.utils.LoggerManager;

@Controller
public class AdminGeolevelsController {


	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private GeoService geoService;
	
	

	@RequestMapping(value = {"/BoGeolevel"} , method = RequestMethod.GET)
	public ModelAndView boGeoLevel() {

		ModelAndView model = new ModelAndView();

		model.setViewName("admin/geolevels-table");

		return model;

	}


	@RequestMapping(value = "/BoGeolevel/getGeolevels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DatatablePaginateOutDTO<GeosDto>> getUsers(HttpServletRequest request) {


		DatatablePaginationInDTO datatablePaginationInDTO = new DatatablePaginationInDTO(request);

		int geoCount = 0;
		int geoCountFilter = 0;
		try {
			geoCount = geoService.getGeoCount("");
			geoCountFilter = geoService.getGeoCount(datatablePaginationInDTO.getSearch());
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		

		DatatablePaginateOutDTO<GeosDto> datatablePaginateOutDto = new DatatablePaginateOutDTO<>();

		List<GeosDto> geoList = null;
		try {
			geoList = geoService.getGeoByIndex(datatablePaginationInDTO.getLength(), datatablePaginationInDTO.getStart(), datatablePaginationInDTO.getSearch());
		} catch (DAOException e) {
			e.printStackTrace();
		}

		datatablePaginateOutDto.setData(geoList);
		datatablePaginateOutDto.setDraw(Integer.valueOf(datatablePaginationInDTO.getDraw()));
		datatablePaginateOutDto.setRecordsTotal(geoCount);
		datatablePaginateOutDto.setRecordsFiltered(geoCountFilter);
		return ResponseEntity.ok(datatablePaginateOutDto);
	}

	@RequestMapping(value = "/BoGeolevel/saveProvince", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> saveProvince(@RequestParam("provinceName") String provinceName) {

		if(provinceName.trim().isEmpty()) {
			return ResponseEntity.ok(new GenericResponse<>(201, "El campo no puede estar vacio."));
		}

		try {
			geoService.add(provinceName, "", "");
			return ResponseEntity.ok(new GenericResponse<>(200, "La provincia se ha cargado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@RequestMapping(value = "/BoGeolevel/saveRegion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> saveRegion(@RequestParam("geo3ProvinceId") String geo3ProvinceId, @RequestParam("newRegionName") String newRegionName) {
		
		try {
//			specialtyService.addSpecialtyToOccupation(espOccupationId, newSpecialtyName);
			geoService.addGeo3(geo3ProvinceId, newRegionName);
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha cargado exitosamente."));
		} catch (NumberFormatException | DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
//	
//	@RequestMapping(value = "/BoCategory/saveTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<GenericResponse<String>> saveTask(@RequestParam("taskName") String taskName, @RequestParam("specialtyId") String specialtyId) {
//		
//		try {
//			specialtyService.addTaskToOccupationAndSpecialty(taskName,specialtyId);
//			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha cargado exitosamente."));
//		} catch (ServiceException | DAOException e) {
//			e.printStackTrace();
//			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	
//	@RequestMapping(value = "/BoCategory/editOccupation", method = RequestMethod.POST)
//	public ResponseEntity<GenericResponse<String>> editOccupation(@RequestBody CategoryEditRequest body) {
//		
//		try {
//			specialtyService.editOccupation(body.getId(), body.getName());
//			return ResponseEntity.ok(new GenericResponse<>(200, "La ocupacion se ha editado exitosamente."));
//		} catch (DAOException e) {
//			e.printStackTrace();
//			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	@RequestMapping(value = "/BoCategory/editSpecialty", method = RequestMethod.POST)
//	public ResponseEntity<GenericResponse<String>> editSpecialty(@RequestBody CategoryEditRequest body) {
//		
//		try {
//			specialtyService.editSpecialty(body.getId(), body.getName());
//			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha editado exitosamente."));
//		} catch (DAOException e) {
//			e.printStackTrace();
//			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	@RequestMapping(value = "/BoCategory/editTask", method = RequestMethod.POST)
//	public ResponseEntity<GenericResponse<String>> editTask(@RequestBody CategoryEditRequest body) {
//		
//		try {
//			specialtyService.editTask(body.getId(), body.getName());
//			return ResponseEntity.ok(new GenericResponse<>(200, "La tarea se ha editado exitosamente."));
//		} catch (DAOException e) {
//			e.printStackTrace();
//			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
	@RequestMapping(value = {"/BoGeolevel/provinces",}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	public ResponseEntity<String> getGeo2() {
		try {
//			Collection<OccupationDTO> occupationList = specialtyService.listOccupationsNoFilter();

			List<GeoLevelDTO> geo2List = geoService.listGeoLevel2();
			Gson gson = new Gson();
			String jsonString = gson.toJson(geo2List);

			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
//	
//	
//	@RequestMapping(value = {"/BoCategory/specialties/{specialtyId}",}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
//	public ResponseEntity<String> getSpecialties(@PathVariable("specialtyId") int specialtyId) {
//		try {
//			Collection<SpecialtyDTO> specialtyList = specialtyService.listSpecialties(specialtyId);
//
//			Gson gson = new Gson();
//			String jsonString = gson.toJson(specialtyList);
//
//			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
//		} catch (ServiceException e) {
//			LoggerManager.error(this, e);
//			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

}
