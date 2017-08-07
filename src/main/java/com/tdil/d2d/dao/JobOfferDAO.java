package com.tdil.d2d.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.tdil.d2d.bo.dto.FilterJobOfferReportDTO;
import com.tdil.d2d.controller.api.dto.SearchOfferDTO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobOffer;

public interface JobOfferDAO {
	
	public JobOffer getById(Class<JobOffer> aClass, long id) throws DAOException;

	public void save(JobOffer jobOffer) throws DAOException;

	public List<JobOffer> getOpenOffers(Long id) throws DAOException;
	public List<JobOffer> getClosedOffers(Long id) throws DAOException;
	public List<JobOffer> getAllPermanentOffersOpen() throws DAOException;

	public Collection<JobOffer> getOffers(long specialtyId, long geoLevelId, boolean permanent) throws DAOException;

	public Collection<JobOffer> getOffersBy(SearchOfferDTO searchOfferDTO) throws DAOException;
	
	public List<Long> getOfferIdsByDate(Date date) throws DAOException;

	List<JobOffer> getAllOffers() throws DAOException;

	List<Object> getJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO) throws DAOException;
	List<Object> getJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, boolean permanent) throws DAOException;
	List<Object> getActiveJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO) throws DAOException;
	List<Object> getJobOfferContractedMonthly(FilterJobOfferReportDTO filterDTO) throws DAOException;
}
