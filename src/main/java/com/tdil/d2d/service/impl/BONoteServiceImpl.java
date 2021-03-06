package com.tdil.d2d.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tdil.d2d.bo.dto.BONoteDTO;
import com.tdil.d2d.bo.dto.ResultDTO;
import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.dao.SponsorDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.service.BONoteService;

@Transactional
@Service
public class BONoteServiceImpl implements BONoteService {
	
	private final NoteDAO noteDAO;
	private final SpecialtyDAO specialtyDAO;
	private final UserDAO userDAO;
	
	@Autowired
	private SponsorDAO sponsorDao;
	
	@Autowired
	public BONoteServiceImpl(NoteDAO noteDAO, SpecialtyDAO specialtyDAO, UserDAO userDAO) {
		this.noteDAO = noteDAO;
		this.specialtyDAO = specialtyDAO;
		this.userDAO = userDAO;
	}
	
	@Override
	public List<BONoteDTO> getAll() throws ServiceException {
		try {
			return toBoDtoList(this.noteDAO.getAll());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ResultDTO save(BONoteDTO noteDTO) throws ServiceException {
		if(StringUtils.isEmpty(noteDTO.getTitle())){
			return ResultDTO.error("El título es obligatorio");
		}
		
		if(StringUtils.isEmpty(noteDTO.getSubtitle())){
			return ResultDTO.error("El subtítulo es obligatorio");
		}
		
		if(StringUtils.isEmpty(noteDTO.getContent())){
			return ResultDTO.error("El contenido es obligatorio");
		}
		
		if(StringUtils.isEmpty(noteDTO.getCategory())){
			return ResultDTO.error("La categoría es obligatoria");
		}
		
		if(noteDTO.getPublishingDate().after(new Date())){
			return ResultDTO.error("La fecha de publicacion no puede ser mayor a la actual");
		}
		
		Note note = toPersistent(noteDTO);
		this.noteDAO.save(note);
		
		return ResultDTO.ok();
	}
	
	private List<BONoteDTO> toBoDtoList(Collection<Note> list) {
		return list.stream().map(note -> toBoDto(note)).collect(Collectors.toList());
	}
	
	private  BONoteDTO toBoDto(Note note) {
		BONoteDTO result = new BONoteDTO();

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

		for (Occupation occupation : note.getOccupations()) {
			result.addOccupation(occupation.getId());
		}

		for (Specialty specialty : note.getSpecialties()) {
			result.addSpecialty(specialty.getId());
		}

		result.setSendUserA(note.isSendUserA());
		result.setSendUserB(note.isSendUserBNoSponsor());
		result.setSendUserBAllSponsor(note.isSendUserBAllSponsor());
		result.setSponsors(note.getSponsors().stream().map(Sponsor::getId).collect(Collectors.toList()));
		
		return result;
	}
	
	private  Note toPersistent(BONoteDTO noteDTO) {
		
		Note note = this.noteDAO.getNoteById(noteDTO.getId());
		
		
		
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		Date out = Date.from(todayMidnight.atZone(ZoneId.systemDefault()).toInstant());
		
		try {
			if(note == null) {
				note = new Note();
				note.setCreationDate(out);
			} 
			


			// Se pone por default la fecha de HOY a la madrugada
			note.setPublishingDate(noteDTO.getPublishingDate());
			
			note.setActive(noteDTO.isActive());
			note.setContent(noteDTO.getContent());
			note.setExpirationDate(noteDTO.getExpirationDate());
			note.setCategory(NoteCategory.getCategoryEnum(noteDTO.getCategory()));
			
//			note.setPublishingDate(noteDTO.getPublishingDate());
			note.setTitle(noteDTO.getTitle());
			note.setSubtitle(noteDTO.getSubtitle());
			if(noteDTO.getImage() != null) {
				note.setBase64img(noteDTO.getImage().getBytes());
			}
			
			Set<Occupation> occupations = new HashSet<Occupation>(0);
			for (Occupation occupation : specialtyDAO.listOccupationsByIds(noteDTO.getOccupations())) {
				occupations.add(occupation);
			}
			
			Set<Specialty> specialties = new HashSet<Specialty>(0);
			for (Specialty specialty : specialtyDAO.listSpecialtiesByIds(noteDTO.getSpecialties())) {
				specialties.add(specialty);
			}
			
			Set<Sponsor> sponsors = new HashSet<Sponsor>(0);
			for (Sponsor sponsor : sponsorDao.listSponsorsByIds(noteDTO.getSponsors())) {
				sponsors.add(sponsor);
			}
			
			note.setOccupations(occupations);
			note.setSpecialties(specialties);
			note.setSponsors(sponsors);
			note.setSendUserA(noteDTO.isSendUserA());
			note.setSendUserBNoSponsor(noteDTO.isSendUserB());
			note.setSendUserBAllSponsor(noteDTO.isSendUserBAllSponsor());
			
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}

		return note;
	}
	
	@Override
	public BONoteDTO getBONoteDTOById(Long id) {
		return toBoDto(this.noteDAO.getNoteById(id));
	}
}
