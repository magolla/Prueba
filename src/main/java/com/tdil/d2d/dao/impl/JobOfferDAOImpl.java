package com.tdil.d2d.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobOffer;

@Repository
public class JobOfferDAOImpl  extends GenericDAO<JobOffer> implements JobOfferDAO {

	@Override
	public List<JobOffer> getOffers(Long userId) throws DAOException {
		try {
			// TODO NO FINALIZADAS
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(JobOffer.class);
			criteria.add(Restrictions.eq("offerent.id", userId));
			List<JobOffer> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
