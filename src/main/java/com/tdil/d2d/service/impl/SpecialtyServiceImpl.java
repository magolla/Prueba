package com.tdil.d2d.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.CategoryDto;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
import com.tdil.d2d.controller.api.dto.TaskDTO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.dbinit.DBInit;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Task;
import com.tdil.d2d.service.SpecialtyService;

@Transactional
@Service
public class SpecialtyServiceImpl implements SpecialtyService { 

	@Autowired
	private SpecialtyDAO specialtyDAO;

	@Override
	public Collection<OccupationDTO> listOccupations() throws ServiceException {
		try {
			return toDtoOccupation(specialtyDAO.listOccupation());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<OccupationDTO> listOccupationsNoFilter() throws ServiceException {
		try {
			return toDtoOccupation(specialtyDAO.listOccupationNoFilter());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SpecialtyDTO> listSpecialties(long occupationId) throws ServiceException {
		try {
			return toDtoSpecialty(specialtyDAO.listSpecialties(occupationId));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<TaskDTO> listTasks(long specialtyId) throws ServiceException {
		try {
			return toDtoTask(specialtyDAO.listTasks(specialtyId));
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int getTaskCount(String search) throws ServiceException {
		try {
			return specialtyDAO.taskCount(search);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private static Collection<OccupationDTO> toDtoOccupation(Collection<Occupation> list) {
		return list.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}

	protected static Collection<SpecialtyDTO> toDtoSpecialty(Collection<Specialty> list) {
		return list.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}

	protected static Collection<TaskDTO> toDtoTask(Collection<Task> list) {
		return list.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}

	private static OccupationDTO toDto(Occupation s) {
		OccupationDTO result = new OccupationDTO();
		if(s != null) {
			result.setId(s.getId());
			result.setName(s.getName());
		}
		return result;
	}

	private static SpecialtyDTO toDto(Specialty s) {
		SpecialtyDTO result = new SpecialtyDTO();
		if(s != null) {
			result.setId(s.getId());
			result.setName(s.getName());
		}
		return result;
	}

	private static TaskDTO toDto(Task s) {
		TaskDTO result = new TaskDTO();
		if(s != null) {
			result.setId(s.getId());
			result.setName(s.getName());
		}
		return result;
	}

	@Override
	public void initDB() throws ServiceException {
		try {
			DBInit.initCategories(this);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void add(String occupation, String specialty, String task) throws ServiceException {
		try {
			// TODO Auto-generated method stub
			String oc = StringUtils.capitalize(occupation.trim());
			Occupation o = firstOccupation(specialtyDAO.listOccupation(oc));
			if (o == null) {
				o = new Occupation();
				o.setName(oc);
				specialtyDAO.save(o);
			}
			String st = specialty.trim();
			Specialty s = firstSpecialty(specialtyDAO.listSpecialty(o.getId(), st));
			if (s == null) {
				s = new Specialty();
				s.setOccupation(o);
				s.setName(st);
				specialtyDAO.save(s);
			}
			String ta = normalize(task);
			Task t = firstTask(specialtyDAO.listTask(s.getId(), ta));
			if (t == null) {
				t = new Task();
				t.setSpecialty(s);
				t.setName(ta);
				specialtyDAO.save(t);
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


	private String normalize(String task) {
		String result = task;
		result = result.trim();
		if (result.startsWith("\"")) {
			result = result.substring(1);
		}
		if(result.endsWith("\"")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	private Occupation firstOccupation(List<Occupation> list) {
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	private Specialty firstSpecialty(List<Specialty> list) {
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	private Task firstTask(List<Task> list) {
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public OccupationDTO getOccupationDTOById(Long id) {
		try {
			return toDto(this.specialtyDAO.getOccupationById(id));
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SpecialtyDTO getSpecialtyDTOById(Long id) {
		try {
			return toDto(this.specialtyDAO.getSpecialtyById(id));
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryDto> getTaskByIndex(String length, String start, String search) {

		List<Task> taskList = this.specialtyDAO.getTaskByIndex(length, start, search);

		return taskList.stream().map(task -> toDtoCategory(task)).collect(Collectors.toList());

	}

	private CategoryDto toDtoCategory(Task task) {
		CategoryDto categoryDto = new CategoryDto();

		categoryDto.setTaskId(task.getId());
		categoryDto.setTaskName(task.getName());

		categoryDto.setSpecialtyId(task.getSpecialty().getId());
		categoryDto.setSpecialtyName(task.getSpecialty().getName());

		categoryDto.setOccupationId(task.getSpecialty().getOccupation().getId());
		categoryDto.setOccupationName(task.getSpecialty().getOccupation().getName());

		return categoryDto;
	}

	@Override
	public boolean addSpecialtyToOccupation(String occupationId, String specialtyName) throws ServiceException {
		try {

			Occupation o = specialtyDAO.getOccupationById(Long.valueOf(occupationId));
			if (o == null) {
				return false;
			}

			List<Specialty> specialtyList = specialtyDAO.getSpecialtyByOccupationId(occupationId);

			if(specialtyList.size() == 1 && specialtyList.get(0).getName().equals("")) {
				List<Task> tasksList = specialtyDAO.getTasksBySpecialtyId(specialtyList.get(0).getId());

				if(tasksList.size() == 1 && tasksList.get(0).getName().equals("")) {
					Specialty specialty = specialtyList.get(0);
					specialty.setName(specialtyName);
					specialtyDAO.save(specialty);	
				}

			} else {
				Specialty newSpecialty = new Specialty();
				newSpecialty.setName(specialtyName);
				newSpecialty.setOccupation(o);
				specialtyDAO.save(newSpecialty);

				Task t = new Task();
				t.setSpecialty(newSpecialty);
				t.setName("");
				specialtyDAO.save(t);
			}

			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<SpecialtyDTO> listAllSpecialties() throws ServiceException {
		try {
			return toDtoSpecialty(specialtyDAO.listAllSpecialties());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void addTaskToOccupationAndSpecialty(String taskName, String specialtyId) throws ServiceException  {
		try {
			List<Task> taskList = specialtyDAO.getTasksBySpecialtyId(Long.valueOf(specialtyId));
			
			
			if(taskList.size() == 1 && taskList.get(0).getName().equals("")) {
				Task task = taskList.get(0);
				task.setName(taskName);
				specialtyDAO.save(task);
			} else {
				Specialty spe = specialtyDAO.getSpecialtyById(Long.valueOf(specialtyId));
				Task task = new Task();
				task.setName(taskName);
				task.setSpecialty(spe);
				specialtyDAO.save(task);
			}

		} catch (NumberFormatException | DAOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void editOccupation(long occupationId, String occupationName) throws DAOException {
			Occupation oc = specialtyDAO.getOccupationById(occupationId);
			oc.setName(occupationName);
			specialtyDAO.save(oc);
	}
	
	@Override
	public void editSpecialty(long specialtyId, String specialtyName) throws DAOException {
			Specialty sp = specialtyDAO.getSpecialtyById(specialtyId);
			sp.setName(specialtyName);
			specialtyDAO.save(sp);
	}
	
	@Override
	public void editTask(long taskId, String taskName) throws DAOException {
			Task ta = specialtyDAO.getTaskById(taskId);
			ta.setName(taskName);
			specialtyDAO.save(ta);
	}



}
