package com.tdil.d2d.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.JobApplicationDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobApplication;

@Transactional
@Repository
public class JobApplicationDAOImpl  extends GenericDAO<JobApplication> implements JobApplicationDAO {

	@Override
	public List<JobApplication> getJobApplications(long offerId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobApplication.class);
			criteria.add(Restrictions.eq("offer.id", offerId));
			criteria.addOrder(Order.asc("id"));
			List<JobApplication> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<JobApplication> getJobApplicationsByUserAndOffer(long offerId,long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobApplication.class);
			criteria.add(Restrictions.eq("offer.id", offerId));
			criteria.add(Restrictions.eq("user.id", userId));
			criteria.addOrder(Order.asc("id"));
			List<JobApplication> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
