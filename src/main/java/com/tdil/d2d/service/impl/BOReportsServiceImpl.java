package com.tdil.d2d.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.SubscriptionReportDTO;
import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.BOReportsService;
import com.tdil.d2d.service.SessionService;

@Transactional
@Service
public class BOReportsServiceImpl implements BOReportsService {
	
	
	private final JobOfferDAO jobOfferDAO;
	private final UserDAO userDAO;
	private final GeoDAO geoDAO;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	public BOReportsServiceImpl(JobOfferDAO jobOfferDAO, UserDAO userDAO,GeoDAO geoDAO) {
		this.jobOfferDAO = jobOfferDAO;
		this.userDAO = userDAO;
		this.geoDAO = geoDAO;
	}
	
	
	
	
	@Override
	public List<BOJobOfferDTO> getAllJobOffers() throws ServiceException {
		try {
			
			 List<JobOffer> jobOffers = this.jobOfferDAO.getAllOffers();
			
			
			return toBoDtoList(jobOffers);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	
	private List<BOJobOfferDTO> toBoDtoList(Collection<JobOffer> list) {
		return list.stream().map(jobOffer -> {
			try {
				return toBoDto(jobOffer);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}
	
	
	
	private  BOJobOfferDTO toBoDto(JobOffer jobOffer) throws DAOException {
		BOJobOfferDTO result = new BOJobOfferDTO();
		
		result.setId(jobOffer.getId());
		result.setApplications(jobOffer.getApplications());
		result.setComment(jobOffer.getComment());
		result.setCompanyScreenName(jobOffer.getCompanyScreenName());
		result.setCreationDate(jobOffer.getCreationDate().toString());
		result.setGeoLevelName(this.geoDAO.getGeoByIdAndLevel(jobOffer.getGeoLevelId(),	 jobOffer.getGeoLevelLevel()).getName());
		//Preguntar si quiere geoname
		DateFormat df = new SimpleDateFormat("hh:'00' a");
		String hour = df.format(jobOffer.getOfferDate());
		result.setOfferHour(hour);
		result.setInstitutionType(jobOffer.getInstitutionType().name());
		if(jobOffer.getJobApplication_id() != null) {
			User usuarioApplicant = userDAO.getById(User.class, jobOffer.getJobApplication_id());	
			result.setJobApplication_detail(usuarioApplicant.getFirstname() + " " + usuarioApplicant.getLastname() + "<br>" 
					+ usuarioApplicant.getEmail() + "<br>" + usuarioApplicant.getMobilePhone() + "<br>" + usuarioApplicant.getCompanyScreenName());
		} else {
			result.setJobApplication_detail("N/A");
		}
		
		df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(jobOffer.getOfferDate());
		result.setOfferDate(date);
		result.setPermanent(jobOffer.isPermanent());
		result.setStatus(jobOffer.getStatus());
		result.setSubTitle(jobOffer.getSubtitle());
		result.setTitle(jobOffer.getTitle());
		result.setVacants(jobOffer.getVacants());
		result.setOccupationName(jobOffer.getOccupation().getName());
		result.setOfferent_detail(jobOffer.getOfferent().getFirstname() + " " + jobOffer.getOfferent().getLastname() + "<br>" 
				+ jobOffer.getOfferent().getEmail() + "<br>" + jobOffer.getOfferent().getMobilePhone() + "<br>" + jobOffer.getOfferent().getCompanyScreenName());
		result.setSpecialtyName(jobOffer.getSpecialty().getName());
		result.setTaskName(jobOffer.getTask().getName());

		return result;
	}
	
	public List<SubscriptionReportDTO> getSubscriptionList() {
		List<SubscriptionReportDTO> subscriptions = new ArrayList<SubscriptionReportDTO>();
		subscriptions.add(new SubscriptionReportDTO("Suscripciones pagas android mercado pago", 100, "rgba(255, 165, 0, 1)"));
		subscriptions.add(new SubscriptionReportDTO("Suscripciones por sponsor", 200, "rgba(0, 128, 128, 1)"));
		subscriptions.add(new SubscriptionReportDTO("Suscripciones inapp iOS", 300, "rgba(3, 72, 123, 1)"));
		subscriptions.add(new SubscriptionReportDTO("Suscripciones gratuitas dtd", 400, "rgba(238,67,100, 1)"));
		
		return subscriptions;
	}
}
