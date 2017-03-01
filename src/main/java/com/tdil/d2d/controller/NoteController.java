package com.tdil.d2d.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.request.CreateNoteRequest;
import com.tdil.d2d.controller.api.request.IdRequest;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.controller.api.response.RegistrationResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.service.NoteService;
import com.tdil.d2d.service.SessionService;
import com.tdil.d2d.utils.LoggerManager;

@Controller
public class NoteController {

	public static final String DEFAULT_PAGE_SIZE = "10";
	@Autowired
	private NoteService noteService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value = "/notes", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<Note>> save(@RequestBody CreateNoteRequest request) {

		Note note = fromRequest(request);
		note.setCreationDate(new Date());
		note.setActive(true);
		Note result = this.noteService.save(note);
		return ResponseEntity.ok(new GenericResponse<>(200, result));
	}

	@RequestMapping(value = "/notes", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<NoteDTO>>> getNotes(@RequestParam Map<String, Object> params) {

		String size = (String) (String)params.getOrDefault("size", DEFAULT_PAGE_SIZE);
	    String page = (String) (String)params.getOrDefault("page", "0");

	    params.remove("size");
	    params.remove("page");
	    
		List<Note> notes = this.noteService.getNotes(Integer.valueOf(page), Integer.valueOf(size), params);

		List<NoteDTO> response = notes.stream().map((elem) -> toDTO(elem)).collect(Collectors.toList());

		return ResponseEntity.ok(new GenericResponse<>(200, response));
	}

	@RequestMapping(value = "/notesforuser", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<NoteDTO>>> getNotesForUser() {
        try{
        	
			List<Note> notes = this.noteService.getNotesForUser();
	
			List<NoteDTO> response = notes.stream().map((elem) -> toDTO(elem)).collect(Collectors.toList());
	
			return ResponseEntity.ok(new GenericResponse<>(200, response));
			
        } catch (ServiceException e) {
            LoggerManager.error(this, e);
            RegistrationResponse response = new RegistrationResponse(0);
            response.addError(e.getLocalizedMessage());
            return new ResponseEntity<GenericResponse<List<NoteDTO>>> ((GenericResponse<List<NoteDTO>>) null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/homenote", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<NoteDTO>> getHomeNote() {
        try{
        	
			Note note = this.noteService.getHomeNote();
	
			if(note != null){
                NoteDTO dto = toDTO(note);
			    return ResponseEntity.ok(new GenericResponse<>(200, dto));
			} else{
				return ResponseEntity.ok(new GenericResponse<>(400, null));
			}
        } catch (ServiceException e) {
            LoggerManager.error(this, e);
            RegistrationResponse response = new RegistrationResponse(0);
            response.addError(e.getLocalizedMessage());
            return new ResponseEntity<GenericResponse<NoteDTO>> ((GenericResponse<NoteDTO>) null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/notes/{id}", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<NoteDTO>> getNoteById(@PathVariable("id") Long id) {
		Note note = this.noteService.getNoteById(id);
		return ResponseEntity.ok(new GenericResponse<>(200, toDTO(note)));
	}

	@RequestMapping(value = "/notes/{id}/occupations", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<OccupationDTO>> addOccupationToNote(@PathVariable("id") Long id, @RequestBody IdRequest request) {
		Occupation addedOccupation = this.noteService.addOccupation(id, request.getId());
		return ResponseEntity.ok(new GenericResponse<>(200, toOccupationDTO(addedOccupation)));
	}

	@RequestMapping(value = "/notes/{id}/specialities", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<SpecialtyDTO>> addSpecialityToNote(@PathVariable("id") Long id, @Valid @RequestBody IdRequest request) {
		Specialty addedSpeciality = this.noteService.addSpeciality(id, request.getId());
		return ResponseEntity.ok(new GenericResponse<>(200, toSpecialityDTO(addedSpeciality)));
	}

	@RequestMapping(value = "/notes/{id}/occupations", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<OccupationDTO>>> getNoteOccupations(@PathVariable("id") Long id) {
		Set<Occupation> occupations = this.noteService.getOccupations(id);
		List<OccupationDTO> response = occupations.stream().map(elem -> toOccupationDTO(elem)).collect(Collectors.toList());
		return ResponseEntity.ok(new GenericResponse<>(200, response));
	}

	@RequestMapping(value = "/notes/{id}/specialities", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<SpecialtyDTO>>> getNoteSpecialities(@PathVariable("id") Long id) {
		Set<Specialty> specialities = this.noteService.getSpecialities(id);
		List<SpecialtyDTO> response = specialities.stream().map(elem -> toSpecialityDTO(elem)).collect(Collectors.toList());
		return ResponseEntity.ok(new GenericResponse<>(200, response));
	}

	@RequestMapping(value = "/notes/{id}", method = {RequestMethod.DELETE}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<String>> disableNote(@PathVariable("id") Long id) {
		this.noteService.disableNote(id);
		return ResponseEntity.ok(new GenericResponse<>(200, id.toString()));
	}

	private SpecialtyDTO toSpecialityDTO(Specialty elem) {
		if (elem == null) return new SpecialtyDTO();
		SpecialtyDTO specialtyDTO = new SpecialtyDTO();
		specialtyDTO.setId(elem.getId());
		specialtyDTO.setName(elem.getName());
		return specialtyDTO;
	}

	private OccupationDTO toOccupationDTO(Occupation elem) {
		if (elem == null) return new OccupationDTO();
		OccupationDTO occupationDTO = new OccupationDTO();
		occupationDTO.setId(elem.getId());
		occupationDTO.setName(elem.getName());
		return occupationDTO;
	}

	private NoteDTO toDTO(Note elem) {
		NoteDTO dto = new NoteDTO();

		dto.setId(elem.getId());
		dto.setActive(elem.isActive());
		dto.setContent(elem.getContent());
		dto.setExpirationDate(elem.getExpirationDate());
		dto.setCategory(elem.getCategory().toString());
		dto.setCreationDate(elem.getCreationDate());
		dto.setPublishingDate(elem.getPublishingDate());
		dto.setTitle(elem.getTitle());
		dto.setSubtitle(elem.getSubtitle());
		if(elem.getBase64img()!=null)
			dto.setImage(new String(elem.getBase64img()));
		return dto;
	}

	protected Note fromRequest(CreateNoteRequest request) {
		Note note = new Note();
		note.setTitle(request.getTitle());
		note.setSubtitle(request.getSubtitle());
		note.setContent(request.getContent());
		note.setExpirationDate(request.getExpirationDate());
		note.setCategory(NoteCategory.valueOf(request.getCategory()));
		note.setBase64img(request.getBase64img().getBytes());
		return note;
	}

}
