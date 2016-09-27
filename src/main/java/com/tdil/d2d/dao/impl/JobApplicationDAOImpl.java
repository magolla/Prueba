package com.tdil.d2d.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.JobApplicationDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobApplication;

@Repository
public class JobApplicationDAOImpl  extends GenericDAO<JobApplication> implements JobApplicationDAO {

	@Override
	public List<JobApplication> getJobApplications(long offerId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobApplication.class);
			criteria.add(Restrictions.eq("offer.id", offerId));
			List<JobApplication> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
