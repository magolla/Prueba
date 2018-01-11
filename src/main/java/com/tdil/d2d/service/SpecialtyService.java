package com.tdil.d2d.service;

import java.util.Collection;
import java.util.List;

import com.tdil.d2d.bo.dto.CategoryDto;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.exceptions.ServiceException;

public interface SpecialtyService {

	public void add(String occupation, String specialty, String task) throws ServiceException;

	public Collection<OccupationDTO> listOccupations() throws ServiceException;
	public Collection<SpecialtyDTO> listSpecialties(long occuppationId) throws ServiceException;
	public Collection<TaskDTO> listTasks(long specialtyId) throws ServiceException;

	public void initDB() throws ServiceException;

	public OccupationDTO getOccupationDTOById(Long id);
	public SpecialtyDTO getSpecialtyDTOById(Long id);
	
	public int getTaskCount(String search) throws ServiceException;
	public List<CategoryDto> getTaskByIndex(String length, String start, String search);


}
