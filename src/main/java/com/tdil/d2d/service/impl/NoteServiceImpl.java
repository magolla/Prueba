package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tdil.d2d.bo.dto.ResultDTO;
import com.tdil.d2d.controller.api.dto.NoteDTO;
import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.NoteService;

@Transactional
@Service
public class NoteServiceImpl implements NoteService {

	private final NoteDAO noteDAO;
	private final SpecialtyDAO specialtyDAO;
	private final UserDAO userDAO;
	
	@Autowired
	public NoteServiceImpl(NoteDAO noteDAO, SpecialtyDAO specialtyDAO, UserDAO userDAO) {
		this.noteDAO = noteDAO;
		this.specialtyDAO = specialtyDAO;
		this.userDAO = userDAO;
	}

	@Override
	public Note save(Note note) {
		this.noteDAO.save(note);
		return note;
	}

	@Override
	public ResultDTO save(NoteDTO noteDTO) throws ServiceException {
		if(StringUtils.isEmpty(noteDTO.getTitle())){
			return ResultDTO.error("El título es obligatorio");
		}
		
		if(StringUtils.isEmpty(noteDTO.getSubtitle())){
			return ResultDTO.error("El subtítulo es obligatorio");
		}
		
		if(StringUtils.isEmpty(noteDTO.getContent())){
			return ResultDTO.error("El contenido es obligatorio");
		}
		
		Note note = toPersistent(noteDTO);
		this.noteDAO.save(note);
		
		return ResultDTO.ok();
	}

	@Override
	public List<Note> getNotes(int page, int size, Map<String, Object> params) {
		return this.noteDAO.getNotes(page, size, params);
	}
	
	@Override
	public List<Note> getNotesForUser(int page, int size) throws ServiceException {
		
		User user = getLoggedUser();
		
		List<Long> specialities = new ArrayList<Long>();
		List<Long> ocuppations = new ArrayList<Long>();
		
		for (Specialty speciality : user.getSpecialties()){
			specialities.add(speciality.getId());
			if(speciality.getOccupation()!=null)
		 	  ocuppations.add(speciality.getOccupation().getId());
		}
		
		if(specialities.size()==0){
			return new ArrayList<Note>();
		}
		
		return this.noteDAO.getNotesForUser(page, size, ocuppations, specialities);
	}
	
	@Override
	public Note getHomeNote() throws ServiceException {
		
		User user = getLoggedUser();
		
		List<Long> specialities = new ArrayList<Long>();
		List<Long> ocuppations = new ArrayList<Long>();
		
		for (Specialty speciality : user.getSpecialties()){
			specialities.add(speciality.getId());
			if(speciality.getOccupation()!=null)
		 	  ocuppations.add(speciality.getOccupation().getId());
		}
		
		if(specialities.size()==0){
			return null;
		}
		
		List<Note> notes = this.noteDAO.getNotesForUser(1, 1, ocuppations, specialities);
		
		if(notes.size()>0){
			return notes.get(0);
		}else{
			return null;
		}
	
	}
	
	public User getLoggedUser() throws ServiceException {
		try {
			return userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
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
	
	@Override
	public List<NoteDTO> getAll() throws ServiceException {
		try {
			return toDtoList(this.noteDAO.getAll());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<NoteDTO> toDtoList(Collection<Note> list) {
		return list.stream().map(note -> toDto(note)).collect(Collectors.toList());
	}
	
	private  NoteDTO toDto(Note note) {
		NoteDTO result = new NoteDTO();

		result.setId(note.getId());
		result.setActive(note.isActive());
		result.setContent(note.getContent());
		result.setExpirationDate(note.getExpirationDate());
		if(note.getCategory() != null) {
			result.setCategory(note.getCategory().toString());
		}
		result.setCreationDate(note.getCreationDate());
		result.setPublishingDate(note.getPublishingDate());
		result.setTitle(note.getTitle());
		result.setSubtitle(note.getSubtitle());
		if(note.getBase64img()!=null) {
			result.setImage(new String(note.getBase64img()));
		}

		return result;
	}
	
	private  Note toPersistent(NoteDTO noteDTO) {
		
		Note note = this.getNoteById(noteDTO.getId());
		if(note == null) {
			note = new Note();
		}
		
		note.setActive(noteDTO.isActive());
		note.setContent(noteDTO.getContent());
		note.setExpirationDate(noteDTO.getExpirationDate());
		note.setCategory(NoteCategory.getCategoryEnum(noteDTO.getCategory()));
		note.setCreationDate(noteDTO.getCreationDate());
		note.setPublishingDate(noteDTO.getExpirationDate());
		note.setTitle(noteDTO.getTitle());
		note.setSubtitle(noteDTO.getSubtitle());
		if(noteDTO.getImage() != null) {
			note.setBase64img(noteDTO.getImage().getBytes());
		}

		return note;
	}
	
	@Override
	public NoteDTO getNoteDTOById(Long id) {
		return toDto(this.noteDAO.getNoteById(id));
	}
}
