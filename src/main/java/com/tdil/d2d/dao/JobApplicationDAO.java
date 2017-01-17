package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobApplication;

public interface JobApplicationDAO {
	
	public JobApplication getById(Class<JobApplication> aClass, long id) throws DAOException;

	public void save(JobApplication jobApplication) throws DAOException;

	public List<JobApplication> getJobApplications(long offerId) throws DAOException;

	List<JobApplication> getJobApplicationsByUserAndOffer(long offerId, long userId) throws DAOException;

}
