package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.tdil.d2d.bo.dto.DatatablePaginateOutDTO;
import com.tdil.d2d.bo.dto.DatatablePaginationInDTO;
import com.tdil.d2d.bo.dto.GeosDto;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.request.CategoryEditRequest;
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
			geoService.addGeo3(geo3ProvinceId, newRegionName);
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha cargado exitosamente."));
		} catch (NumberFormatException | DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/BoGeolevel/saveCity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> saveTask(@RequestParam("cityName") String cityName, @RequestParam("cityGeo3Id") String cityGeo3Id) {
		
		try {
			geoService.addGeo4(cityGeo3Id, cityName);
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha cargado exitosamente."));
		} catch (NumberFormatException | DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/BoCategory/editProvince", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse<String>> editProvince(@RequestBody CategoryEditRequest body) {
		
		try {
			geoService.editProvince(body.getId(), body.getName());
			return ResponseEntity.ok(new GenericResponse<>(200, "La ocupacion se ha editado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/BoCategory/editRegion", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse<String>> editRegion(@RequestBody CategoryEditRequest body) {
		
		try {
			geoService.editRegion(body.getId(), body.getName());
			return ResponseEntity.ok(new GenericResponse<>(200, "La especialidad se ha editado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/BoCategory/editCity", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse<String>> editCity(@RequestBody CategoryEditRequest body) {
		
		try {
			geoService.editCity(body.getId(), body.getName());
			return ResponseEntity.ok(new GenericResponse<>(200, "La tarea se ha editado exitosamente."));
		} catch (DAOException e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<String>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
	
	
	@RequestMapping(value = {"BoGeolevel/city/{provinceId}",}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
	public ResponseEntity<String> getSpecialties(@PathVariable("provinceId") long provinceId) {
		try {
			Collection<GeoLevelDTO> geoList = geoService.listGeoLevel3ByProvince(provinceId);

			Gson gson = new Gson();
			String jsonString = gson.toJson(geoList);

			return new ResponseEntity<String>(jsonString, HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<String>("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
