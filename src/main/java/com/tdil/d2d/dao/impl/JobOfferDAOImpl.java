package com.tdil.d2d.dao.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.bo.dto.FilterJobOfferDailyReportDTO;
import com.tdil.d2d.bo.dto.FilterJobOfferReportDTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
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
			criteria.addOrder(Order.desc("id"));
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
			criteria.addOrder(Order.desc("id"));
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
			criteria.addOrder(Order.desc("id"));
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
		queryString.append("AND (:offerentId is null OR offer.offerent.id != :offerentId) ");

		if(!searchOfferDTO.getOccupations().isEmpty()) {
			queryString.append("AND offer.occupation.id in (:ocuppations) ");
		}
		if(!searchOfferDTO.getSpecialities().isEmpty()) {
			queryString.append("AND offer.specialty.id in (:specialities) ");
		}
		if(!searchOfferDTO.getTasks().isEmpty()) {
			queryString.append("AND offer.task.id in (:tasks) ");
		}
//		if(!searchOfferDTO.getGeos().isEmpty()) {
//			queryString.append("AND (");
//			String OR = "";
//			for (GeoLevelDTO location : searchOfferDTO.getGeos()) {
//				//queryString.append(OR + location + " in elements(userProfile.user.userGeoLocations.id) ");
//				queryString.append(OR + "(offer.geoLevelId = " + location.getId() + " AND offer.geoLevelLevel = " + location.getLevel() + ") ");
//				OR = "OR ";
//			}	
//			queryString.append(") ");
//		}
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
		query.setParameter("offerentId", searchOfferDTO.getOfferentIdToIgnore());

		Collection<JobOffer> jobOffer = query.list();


		if(!jobOffer.isEmpty()) {
			if(searchOfferDTO.getGeos().isEmpty()) {
				return query.list();	
			} else {
				return filtrarGeolevels(searchOfferDTO,jobOffer);	
			}
				
		} else {
			return query.list();	
		}
	}

	private Collection<JobOffer> filtrarGeolevels(SearchOfferDTO searchOfferDTO, Collection<JobOffer> jobOffer) {

		List<JobOffer> jobOffers = new ArrayList<JobOffer>();
		for (Iterator iterator = jobOffer.iterator(); iterator.hasNext();) {
			JobOffer jobOffer2 = (JobOffer) iterator.next();

			for (GeoLevelDTO geoLevelDTO: searchOfferDTO.getGeos()) {
				if(geoLevelDTO.getId() == jobOffer2.getGeoLevelId() && geoLevelDTO.getLevel() == jobOffer2.getGeoLevelLevel()) {

					if(!jobOffers.contains(jobOffer2)) {
						jobOffers.add(jobOffer2);
					}
				}
			}
		}
		Collection<JobOffer> jCollection = jobOffers;
		return jCollection;
	}

	@Override
	public List<JobOffer> getAllPermanentOffersOpen() throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.add(Restrictions.eq("permanent", true));
			criteria.add(Restrictions.eq("status", JobOffer.VACANT));
			criteria.add(Restrictions.ge("offerDate", new Date()));
			criteria.addOrder(Order.desc("id"));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<JobOffer> getAllOffers() throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.addOrder(Order.desc("id"));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getOfferIdsByDate(Date date) throws DAOException {
		Calendar calendarFrom = Calendar.getInstance();
		calendarFrom.setTime(date);
		calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
		calendarFrom.set(Calendar.MINUTE, 0);
		calendarFrom.set(Calendar.SECOND, 0);
		calendarFrom.set(Calendar.MILLISECOND, 0);

		Calendar calendarTo = Calendar.getInstance();
		calendarTo.setTime(date);
		calendarTo.set(Calendar.HOUR_OF_DAY, 23);
		calendarTo.set(Calendar.MINUTE, 59);
		calendarTo.set(Calendar.SECOND, 59);
		calendarTo.set(Calendar.MILLISECOND, 999);

		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct offer.id ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.status = :status ");
		queryString.append("AND offer.vacants > 0 ");
		queryString.append("AND offer.creationDate > :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.offerDate > (:nowDate) ");
		queryString.append("order by offer.id asc");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("status", JobOffer.VACANT);
		query.setString("fromDate", formatter.format(calendarFrom.getTime()));
		query.setString("toDate", formatter.format(calendarTo.getTime()));
		query.setCalendarDate("nowDate", Calendar.getInstance());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, Set<Long> offersIdByGeo) throws DAOException {
		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
		calendar.set(Calendar.YEAR, filterDTO.getStartYear());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date from = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getEndMonth());
		calendar.set(Calendar.YEAR, filterDTO.getEndYear());
		Date to = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(*) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, boolean permanent, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
		calendar.set(Calendar.YEAR, filterDTO.getStartYear());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date from = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getEndMonth());
		calendar.set(Calendar.YEAR, filterDTO.getEndYear());
		Date to = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(*) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.permanent = :isPermanent ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameter("isPermanent", permanent);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getActiveJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
		calendar.set(Calendar.YEAR, filterDTO.getStartYear());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date from = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getEndMonth());
		calendar.set(Calendar.YEAR, filterDTO.getEndYear());
		Date to = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(*) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.offerDate >= :nowDate ");
		queryString.append("AND offer.status != :closeStatus ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameter("nowDate", now);
		query.setParameter("closeStatus", JobOffer.CLOSED);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getJobOfferContractedMonthly(FilterJobOfferReportDTO filterDTO, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
		calendar.set(Calendar.YEAR, filterDTO.getStartYear());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date from = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, filterDTO.getEndMonth());
		calendar.set(Calendar.YEAR, filterDTO.getEndYear());
		Date to = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(jobApplication_id) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getJobOfferQuantitiesDaily(FilterJobOfferDailyReportDTO filterDTO, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(filterDTO.getToDate());
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date to = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
		Date from = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(*) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth, DAY(creationDate) as reportDay ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getJobOfferQuantitiesDaily(FilterJobOfferDailyReportDTO filterDTO, boolean permanent, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(filterDTO.getToDate());
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date to = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
		Date from = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(*) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth, DAY(creationDate) as reportDay ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.permanent = :isPermanent ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameter("isPermanent", permanent);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getActiveJobOfferQuantitiesDaily(FilterJobOfferDailyReportDTO filterDTO, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();

		calendar.setTime(filterDTO.getToDate());
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date to = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
		Date from = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(*) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth, DAY(creationDate) as reportDay ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.offerDate >= :nowDate ");
		queryString.append("AND offer.status != :closeStatus ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameter("nowDate", now);
		query.setParameter("closeStatus", JobOffer.CLOSED);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getJobOfferContractedDaily(FilterJobOfferDailyReportDTO filterDTO, Set<Long> offersIdByGeo) throws DAOException {

		if(offersIdByGeo.isEmpty()) {
			return new ArrayList<Object>();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(filterDTO.getToDate());
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date to = calendar.getTime();

		calendar.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
		Date from = calendar.getTime();

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT count(jobApplication_id) as quantity, YEAR(creationDate) as reportYear, MONTH(creationDate) as reportMonth, DAY(creationDate) as reportDay ");
		queryString.append("FROM JobOffer offer ");
		queryString.append("WHERE offer.creationDate >= :fromDate ");
		queryString.append("AND offer.creationDate < :toDate ");
		queryString.append("AND offer.id IN :ids ");
		queryString.append("AND (:occupationId = -1L or offer.occupation.id = :occupationId) ");
		queryString.append("AND (:specialtyId = -1L or offer.specialty.id = :specialtyId) ");
		queryString.append("AND (:taskId = -1L or offer.task.id = :taskId) ");
		queryString.append("GROUP BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");
		queryString.append("ORDER BY YEAR(offer.creationDate), MONTH(offer.creationDate), DAY(offer.creationDate) ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		query.setParameterList("ids", offersIdByGeo);
		query.setParameter("occupationId", filterDTO.getOccupationId());
		query.setParameter("specialtyId", filterDTO.getSpecialtyId());
		query.setParameter("taskId", filterDTO.getTaskId());

		return query.list();
	}

	@Override
	public Set<Long> getByGeo(List<GeoLevelDTO> locations) throws DAOException {
		try {

			Set<Long> result = new HashSet<Long>();

			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct offer.id ");
			queryString.append("FROM JobOffer offer ");


			if(!locations.isEmpty()) {

				String whereIn = "";
				int count = 1;
				for (GeoLevelDTO location : locations) {
					whereIn = whereIn + "(" + location.getId() + ", " + location.getLevel() + "),";

					if(count > 1000){

						whereIn = whereIn.substring(0, whereIn.length() - 1);
						queryString.append("WHERE (geoLevelId, geoLevelLevel) IN (" + whereIn + ")");

						Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());

						result.addAll(query.list());

						queryString = new StringBuilder("");
						queryString.append("SELECT distinct offer.id ");
						queryString.append("FROM JobOffer offer ");

						whereIn = "";

						count = 0;
					}

					count = count + 1;

				}

				whereIn = whereIn.substring(0, whereIn.length() - 1);

				queryString.append("WHERE (geoLevelId, geoLevelLevel) IN (" + whereIn + ")");

				Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
				result.addAll(query.list());

				return result;

			} else {

				Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
				result.addAll(query.list());
				return result;
			}


		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}