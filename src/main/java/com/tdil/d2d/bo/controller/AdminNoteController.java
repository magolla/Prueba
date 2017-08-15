package com.tdil.d2d.bo.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.bo.dto.BONoteDTO;
import com.tdil.d2d.bo.dto.ResultDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.service.BONoteService;
import com.tdil.d2d.service.SpecialtyService;
import com.tdil.d2d.utils.ImageResizer;
import com.tdil.d2d.utils.LoggerManager;



@Controller
public class AdminNoteController {
    
	@Autowired
	private BONoteService noteService;
	
	@Autowired
	private SpecialtyService specialtyService;
	
	@RequestMapping(value = {"/BoNotes"} , method = RequestMethod.GET)
	public ModelAndView homePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("admin/notes");

		return model;

	}
	
	@RequestMapping(value = "/list/bo-notes", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<BONoteDTO>>> getNotes() {
		try{ 
			
			List<BONoteDTO> notes = this.noteService.getAll();
	
			return ResponseEntity.ok(new GenericResponse<>(200, notes));
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<BONoteDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = {"/notes/{noteId}"} , method = RequestMethod.GET)
	public ModelAndView noteEdit(@PathVariable long noteId) {
		try{ 
			
			ModelAndView model = new ModelAndView();
			
			BONoteDTO note = noteService.getBONoteDTOById(noteId);
			model.addObject("noteForm", note);
			model.addObject("categoryList", this.getCategoryList());
			model.addObject("occupationList", this.specialtyService.listOccupations());
			
			Map<OccupationDTO, Collection<SpecialtyDTO>> specialtiesLists = new HashMap<OccupationDTO, Collection<SpecialtyDTO>>();
			for (Long occupationId : note.getOccupations()) {
				specialtiesLists.put(this.specialtyService.getOccupationDTOById(occupationId), this.specialtyService.listSpecialties(occupationId));
			}
			model.addObject("specialtiesLists", specialtiesLists);
			
			model.setViewName("admin/note-editor");
			
			return model;
		
		} catch(Exception e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	@RequestMapping(value = {"/notes/new-note"} , method = RequestMethod.GET)
	public ModelAndView userNew() {
		try{ 
			
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/note-editor");
			
			BONoteDTO note = new BONoteDTO();
			note.setActive(true);
			model.addObject("noteForm", note);
			model.addObject("categoryList", this.getCategoryList());
			model.addObject("occupationList", this.specialtyService.listOccupations());
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
	
	@RequestMapping(value = "/notes/save", method = RequestMethod.POST)
	public ModelAndView saveNote(@Valid BONoteDTO note,
	BindingResult bindingResult,
	@RequestParam(value = "file", required = false) MultipartFile image) {
		try { 
			
			if(image.getSize() > 0) {
				byte[] resizedBytes = ImageResizer.resize(image);
				
				String base64String = Base64.encodeBase64String(resizedBytes);
				note.setImage(base64String);
			}
			
			ResultDTO result = noteService.save(note);
			
			if(!result.isSuccess()){
				ModelAndView model = new ModelAndView();
				model.setViewName("admin/note-editor");
				model.addObject("errors", result.getErrors());
				model.addObject("noteForm", note);
				
				return model;
			}
			
			ModelAndView model = new ModelAndView();
			model.setViewName("redirect:/admin/BoNotes");

			return model;
			
		} catch(Exception e) {
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}
	}
	
	private Map<String,String> getCategoryList() {
		Map<String,String> categories = new LinkedHashMap<String,String>();
		categories.put(NoteCategory.CAT_1.name(), NoteCategory.CAT_1.getDescription());
		categories.put(NoteCategory.CAT_2.name(), NoteCategory.CAT_2.getDescription());
		categories.put(NoteCategory.CAT_3.name(), NoteCategory.CAT_3.getDescription());
		categories.put(NoteCategory.CAT_4.name(), NoteCategory.CAT_4.getDescription());
		categories.put(NoteCategory.CAT_5.name(), NoteCategory.CAT_5.getDescription());
		
		return categories;
	}
	
	@RequestMapping(value = {"/notes/specialties/{occupationId}"} , method = RequestMethod.GET)
	public ModelAndView getSpecialties(@PathVariable long occupationId) {
		try{ 
			
			ModelAndView model = new ModelAndView();
			
			OccupationDTO occupation = specialtyService.getOccupationDTOById(occupationId);
			
			Collection<SpecialtyDTO> specialties = this.specialtyService.listSpecialties(occupationId);
			model.addObject("occupation", occupation);
			model.addObject("specialtyList", specialties);
			model.setViewName("admin/specialty-note-editor");
			
			return model;
		
		}catch(Exception e){
			e.printStackTrace();
			ModelAndView model = new ModelAndView();
			model.setViewName("admin/generic-error");
			return model;	
		}

	}
}
    
