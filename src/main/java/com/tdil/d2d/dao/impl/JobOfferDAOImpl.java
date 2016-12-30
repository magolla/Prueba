package com.tdil.d2d.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobApplication;
import com.tdil.d2d.persistence.JobOffer;

@Repository
public class JobOfferDAOImpl  extends GenericDAO<JobOffer> implements JobOfferDAO {

	@Override
	public List<JobOffer> getOpenOffers(Long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			Criteria criteria2 = this.getSessionFactory().getCurrentSession().createCriteria(JobApplication.class);
			criteria.add(Restrictions.eq("offerent.id", userId));
			criteria.add(Restrictions.ge("offerDate", new Date()));
			criteria.addOrder(Order.asc("id"));
			List<JobOffer> list = criteria.list();
			List<JobApplication> jobAppList = new ArrayList<JobApplication>();
			
			for (int i = 0; i < list.size(); i++) {
				criteria2.add(Restrictions.eq("offer.id", list.get(i).getId()));
//				criteria2.setProjection(Projections.rowCount());
				jobAppList = criteria2.list();
				
				list.get(i).setApplications(jobAppList.size());
				jobAppList.clear();
				criteria2 = this.getSessionFactory().getCurrentSession().createCriteria(JobApplication.class);
			}
			
			System.out.println();
			
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
	
}
