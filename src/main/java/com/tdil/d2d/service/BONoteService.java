package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.BONoteDTO;
import com.tdil.d2d.bo.dto.ResultDTO;
import com.tdil.d2d.exceptions.ServiceException;

public interface BONoteService {

	ResultDTO save(BONoteDTO note) throws ServiceException;
	
	List<BONoteDTO> getAll() throws ServiceException;

	BONoteDTO getBONoteDTOById(Long id);
	
}
