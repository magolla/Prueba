package com.tdil.d2d.dao.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.controller.api.dto.SearchOfferDTO;
import com.tdil.d2d.controller.api.request.InstitutionType;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobOffer;

@Repository
public class JobOfferDAOImpl extends GenericDAO<JobOffer> implements JobOfferDAO {

	@Override
	public List<JobOffer> getOpenOffers(Long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.add(Restrictions.eq("offerent.id", userId));
			criteria.add(Restrictions.ge("offerDate", new Date()));
			criteria.addOrder(Order.asc("id"));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<JobOffer> getClosedOffers(Long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.add(Restrictions.eq("offerent.id", userId));
			criteria.add(Restrictions.eq("status", JobOffer.CLOSED));
			criteria.add(Restrictions.ge("offerDate", new Date()));
			criteria.addOrder(Order.asc("id"));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Collection<JobOffer> getOffers(long specialtyId, long geoLevelId, boolean permanent) throws DAOException {
		try {
			// TODO NO cerradas, no finalizadas
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.add(Restrictions.eq("specialty.id", specialtyId));
			criteria.add(Restrictions.eq("geoLevelId", geoLevelId));
			criteria.add(Restrictions.eq("permanent", permanent));
			criteria.addOrder(Order.asc("id"));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<JobOffer> getOffersBy(SearchOfferDTO searchOfferDTO) throws DAOException {
		
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct offer ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.permanent = :permanent ");
		queryString.append("AND (:institutionType is null OR offer.institutionType = :institutionType) ");
		queryString.append("AND offer.status = :status ");
		queryString.append("AND offer.vacants > 0 ");
		queryString.append("AND offer.offerDate > (:nowDate) ");
		
		if(!searchOfferDTO.getOccupations().isEmpty()) {
			queryString.append("AND offer.occupation.id in (:ocuppations) ");
		}
		if(!searchOfferDTO.getSpecialities().isEmpty()) {
			queryString.append("AND offer.specialty.id in (:specialities) ");
		}
		if(!searchOfferDTO.getTasks().isEmpty()) {
			queryString.append("AND offer.task.id in (:tasks) ");
		}
		if(!searchOfferDTO.getGeos().isEmpty()) {
			queryString.append("AND offer.geoLevelId in (:geoIds) ");
		}
		queryString.append("order by offer.id asc");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("permanent", searchOfferDTO.getPermanent());
		if(searchOfferDTO.getInstitutionType().equals(InstitutionType.BOTH)) {
			query.setParameter("institutionType", null);
		} else {
			query.setParameter("institutionType", searchOfferDTO.getInstitutionType().toString());
		}
		query.setParameter("status", JobOffer.VACANT);
		query.setCalendarDate("nowDate", Calendar.getInstance());
		
		if(!searchOfferDTO.getOccupations().isEmpty()) {
			query.setParameterList("ocuppations", searchOfferDTO.getOccupations());
		}
		if(!searchOfferDTO.getSpecialities().isEmpty()) {
			query.setParameterList("specialities", searchOfferDTO.getSpecialities());
		}
		if(!searchOfferDTO.getTasks().isEmpty()) {
			query.setParameterList("tasks", searchOfferDTO.getTasks());
		}
		if(!searchOfferDTO.getGeos().isEmpty()) {
			query.setParameterList("geoIds", searchOfferDTO.getGeos());
		}
		
		return query.list();
	}
	
	@Override
	public List<JobOffer> getAllPermanentOffersOpen() throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.add(Restrictions.eq("permanent", true));
			criteria.add(Restrictions.eq("status", JobOffer.VACANT));
			criteria.add(Restrictions.ge("offerDate", new Date()));
			
			criteria.addOrder(Order.asc("id"));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
