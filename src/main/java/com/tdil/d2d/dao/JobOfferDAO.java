package com.tdil.d2d.dao;

import java.util.Collection;
import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobOffer;

public interface JobOfferDAO {
	
	public JobOffer getById(Class<JobOffer> aClass, long id) throws DAOException;

	public void save(JobOffer jobOffer) throws DAOException;

	public List<JobOffer> getOpenOffers(Long id) throws DAOException;
	public List<JobOffer> getClosedOffers(Long id) throws DAOException;

	public Collection<JobOffer> getOffers(long specialtyId, long geoLevelId, boolean permanent) throws DAOException;


}
