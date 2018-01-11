package com.tdil.d2d.bo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.CategoryDto;
import com.tdil.d2d.bo.dto.DatatablePaginateOutDto;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.SpecialtyService;

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
    public ResponseEntity<DatatablePaginateOutDto<CategoryDto>> getUsers(HttpServletRequest request, HttpServletResponse res) {
    	
    	String length = request.getParameter("length");
    	String start = request.getParameter("start");
    	String draw = request.getParameter("draw");
    	String search = request.getParameter("search[value]");
    	System.out.println(length);
    	System.out.println(start);
    	System.out.println(draw);
    	System.out.println(search);
    	
    	
		int taskCount = 0;
		int taskCountFilter = 0;
		try {
			taskCount = specialtyService.getTaskCount("");
			taskCountFilter = specialtyService.getTaskCount(search);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		System.out.println("Para la busqueda: " + search + " - Cantidad de resultados: " + taskCount );
    	
    	DatatablePaginateOutDto<CategoryDto> datatablePaginateOutDto = new DatatablePaginateOutDto<>();
    	
    	List<CategoryDto> categoryList = specialtyService.getTaskByIndex(length, start, search);
    	
    	datatablePaginateOutDto.setData(categoryList);
    	datatablePaginateOutDto.setDraw(Integer.valueOf(draw));
    	datatablePaginateOutDto.setRecordsTotal(taskCount);
    	datatablePaginateOutDto.setRecordsFiltered(taskCountFilter);
//    	Gson gson = new Gson();
    	
//    	String datatableOut = gson.toJson(datatablePaginateOutDto);
    	
//        return datatableOut;
        
		return ResponseEntity.ok(datatablePaginateOutDto);
    }

}
