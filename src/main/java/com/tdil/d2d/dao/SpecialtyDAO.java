package com.tdil.d2d.dao;

import java.util.Collection;
import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Task;

public interface SpecialtyDAO {
	
	public void save(Occupation occupation) throws DAOException;
	public void save(Specialty specialty) throws DAOException;
	public void save(Task task) throws DAOException;
	
	public Occupation getOccupationById(long id) throws DAOException;
	public Specialty getSpecialtyById(long id) throws DAOException;
	public Task getTaskById(long id) throws DAOException;

	public List<Occupation> listOccupation() throws DAOException;
	public List<Specialty> listSpecialties(long occupationId) throws DAOException;
	public List<Task> listTasks(long specialtyId) throws DAOException;
	
	public List<Occupation> listOccupation(String text) throws DAOException;
	public List<Specialty> listSpecialty(String text) throws DAOException;
	public List<Task> listTask(String text) throws DAOException;
	public List<Specialty> listSpecialty(long occupationId, String specialty) throws DAOException;
	public List<Task> listTask(long specialtyId, String task) throws DAOException;
	
	public List<Occupation> listOccupationsByIds(List<Long> ids) throws DAOException;
	public List<Specialty> listSpecialtiesByIds(List<Long> ids) throws DAOException;
	int taskCount(String search) throws DAOException;
	public List<Task> getTaskByIndex(String length, String start, String search);
	public List<Specialty> getSpecialtyByOccupationId(String occupationId);
	public Collection<Occupation> listOccupationNoFilter() throws DAOException;
	public List<Task> getTasksBySpecialtyId(long id);
	List<Specialty> listAllSpecialties() throws DAOException;
}
