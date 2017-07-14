package com.tdil.d2d.bo.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.service.NoteService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;



@Controller
public class AdminNoteController {
    
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/notes"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/notes");

		return model;

	}
	
	@RequestMapping(value = "/list/bo-notes", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<NoteDTO>>> getUsers() {
		try{ 
			
			List<NoteDTO> notes = this.noteService.getAll();
	
			return ResponseEntity.ok(new GenericResponse<>(200, notes));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<NoteDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = {"/notes/{noteId}"} , method = RequestMethod.GET)
	public ModelAndView userEdit(@PathVariable long noteId) {
		try{ 
			
			ModelAndView model = new ModelAndView();
			
			NoteDTO note = noteService.getNoteDTOById(noteId);
			model.addObject("noteForm", note);
			
			Map<String,String> categories = new LinkedHashMap<String,String>();
			categories.put(NoteCategory.CAT_1.name(), NoteCategory.CAT_1.getDescription());
			categories.put(NoteCategory.CAT_2.name(), NoteCategory.CAT_2.getDescription());
			categories.put(NoteCategory.CAT_3.name(), NoteCategory.CAT_3.getDescription());
			categories.put(NoteCategory.CAT_4.name(), NoteCategory.CAT_4.getDescription());
			categories.put(NoteCategory.CAT_5.name(), NoteCategory.CAT_5.getDescription());
			
			model.addObject("categoryList", categories);
			
			model.setViewName("admin/note-editor");
			
			return model;
		
		} catch(Exception e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
}
    
