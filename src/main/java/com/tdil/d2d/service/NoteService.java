package com.tdil.d2d.service;

import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NoteService {

	Note save(Note note);

	List<Note> getNotes(int page, int size, Map<String, Object> params);

	Note getNoteById(Long id);

	Set<Occupation> getOccupations(Long id);

	Set<Specialty> getSpecialities(Long id);

	Occupation addOccupation(Long id, Long occupationId);

	Specialty addSpeciality(Long id, Long specialityId);

	void disableNote(Long id);
}
