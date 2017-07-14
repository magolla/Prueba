package com.tdil.d2d.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;

public interface NoteService {

	Note save(Note note);

	List<Note> getNotes(int page, int size, Map<String, Object> params);

	List<Note> getNotesForUser(int page, int size) throws ServiceException ;

	Note getNoteById(Long id);

	Set<Occupation> getOccupations(Long id);

	Set<Specialty> getSpecialities(Long id);

	Occupation addOccupation(Long id, Long occupationId);

	Specialty addSpeciality(Long id, Long specialityId);

	void disableNote(Long id);

	Note getHomeNote()  throws ServiceException;
	
	List<NoteDTO> getAll() throws ServiceException;

	NoteDTO getNoteDTOById(Long id);
}
