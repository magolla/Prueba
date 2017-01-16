package com.tdil.d2d.service.impl;

import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class NoteServiceImpl implements NoteService {

	private final NoteDAO noteDAO;
	private final SpecialtyDAO specialtyDAO;

	@Autowired
	public NoteServiceImpl(NoteDAO noteDAO, SpecialtyDAO specialtyDAO) {
		this.noteDAO = noteDAO;
		this.specialtyDAO = specialtyDAO;
	}

	@Override
	public Note save(Note note) {
		this.noteDAO.save(note);
		return note;
	}

	@Override
	public List<Note> getNotes(Map<String, Object> params) {
		return this.noteDAO.getNotes(params);
	}

	@Override
	public Note getNoteById(Long id) {
		return this.noteDAO.getNoteById(id);
	}

	@Override
	public Set<Occupation> getOccupations(Long id) {
		Note note = this.getNoteById(id);
		return note.getOccupations();
	}

	@Override
	public Set<Specialty> getSpecialities(Long id) {
		Note note = this.getNoteById(id);
		return note.getSpecialties();
	}

	@Override
	public Occupation addOccupation(Long id, Long occupationId) {
		try {
			Note note = this.getNoteById(id);
			Occupation occupation = specialtyDAO.getOccupationById(occupationId);
			note.addOccupation(occupation);
			this.noteDAO.save(note);
			return occupation;
		} catch (DAOException e) {
			throw new DTDException(ExceptionDefinition.DTD_2004, e);
		}
	}

	@Override
	public Specialty addSpeciality(Long id, Long specialityId) {
		try {
			Note note = this.getNoteById(id);
			Specialty specialty = specialtyDAO.getSpecialtyById(specialityId);
			note.addSpeciality(specialty);
			this.noteDAO.save(note);
			return specialty;
		} catch (DAOException e) {
			throw new DTDException(ExceptionDefinition.DTD_2004, e);
		}
	}

	@Override
	public void disableNote(Long id) {
		Note note = this.getNoteById(id);
		note.setActive(false);
		this.save(note);
	}
}
