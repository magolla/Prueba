package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.request.CreateNoteRequest;
import com.tdil.d2d.controller.api.request.IdRequest;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class NoteController {

	public static final int DEFAULT_PAGE_SIZE = 10;
	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/notes", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<Note>> save(@RequestBody CreateNoteRequest request) {

		Note note = fromRequest(request);
		note.setCreationDate(new Date());
		note.setActive(true);
		Note result = this.noteService.save(note);
		return ResponseEntity.ok(new GenericResponse<>(200, result));
	}

	@RequestMapping(value = "/notes", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<List<NoteDTO>>> getNotes(@RequestParam Map<String, Object> params) {

		int size = (int) params.getOrDefault("size", DEFAULT_PAGE_SIZE);
		int page = (int) params.getOrDefault("page", 0);

		List<Note> notes = this.noteService.getNotes(page, size, params);

		List<NoteDTO> response = notes.stream().map((elem) -> toDTO(elem)).collect(Collectors.toList());

		return ResponseEntity.ok(new GenericResponse<>(200, response));
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

		return dto;
	}

	protected Note fromRequest(CreateNoteRequest request) {
		Note note = new Note();
		note.setTitle(request.getTitle());
		note.setSubtitle(request.getSubtitle());
		note.setContent(request.getContent());
		note.setExpirationDate(request.getExpirationDate());
		note.setCategory(NoteCategory.valueOf(request.getCategory()));
		return note;
	}

}