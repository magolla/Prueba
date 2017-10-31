package com.tdil.d2d.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.SponsorDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Sponsor;

@Transactional
@Repository
public class SponsorDAOImpl extends HibernateDaoSupport implements SponsorDAO {


	private SessionFactory sessionFactory;

	private Logger logger = LoggerFactory.getLogger(SponsorDAOImpl.class);

	@Autowired
	public SponsorDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@PostConstruct
	public void initHibernate() {
		this.setSessionFactory(this.sessionFactory);
	}

	@Override
	public Sponsor getSponsorById(long id) {
		return this.getHibernateTemplate().get(Sponsor.class, id);
	}

	@Override
	public Sponsor save(Sponsor sponsor) {
		this.getHibernateTemplate().saveOrUpdate(sponsor);
		this.getHibernateTemplate().flush();
		return sponsor;
	}

	@Override
	public void delete(Sponsor sponsor) {
		this.getHibernateTemplate().delete(sponsor);
	}

	@Override
	public void delete(long id) {
		Sponsor sponsor = this.getSponsorById(id);
		this.getHibernateTemplate().delete(sponsor);
	}

	@Override
	public List<Sponsor> getAllSponsors() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Sponsor.class);
		List sponsors = criteria.list();
		logger.info("Sponsors found: {}", sponsors.size());
		return sponsors;
	}

	@Override
	public List<Sponsor> listSponsorsByIds(List<Long> ids) throws DAOException {
		if(ids == null || ids.isEmpty()) {
			return new ArrayList<Sponsor>();
		}
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct s ");
			queryString.append("FROM Sponsor s ");
			queryString.append("WHERE s.id in(:ids) ");
			
			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameterList("ids", ids);

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
