package com.tdil.d2d.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;

@Repository
public class SubscriptionDAOImpl extends HibernateDaoSupport implements SubscriptionDAO  {

	@Autowired
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void initHibernate() {
		this.setSessionFactory(this.sessionFactory);
	}
	
	@Override
	public Sponsor getSponsorByName(Class<Sponsor> class1, String name) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Sponsor.class);
			criteria.add(Restrictions.eq("name", name));
			List<Sponsor> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public SponsorCode getSponsorCode(Class<SponsorCode> aClass, String code) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SponsorCode.class);
			criteria.add(Restrictions.eq("code", code));
			List<SponsorCode> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void saveSponsor(Sponsor sponsor) throws DAOException {
		String invocationDetails= "save("+sponsor.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(sponsor);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
	@Override
	public void saveSponsorCode(SponsorCode sponsorCode) throws DAOException {
		String invocationDetails= "save("+sponsorCode.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(sponsorCode);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
	@Override
	public void saveSubscription(Subscription subscription) throws DAOException {
		String invocationDetails= "save("+subscription.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(subscription);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
	@Override
	public List<Subscription> listSubscriptions(Long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Subscription.class);
			criteria.add(Restrictions.eq("user.id", userId));
			List<Subscription> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	protected void handleException(String invocationDetails, Exception e) throws DAOException {
//		LoggerManager.error(this, e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	}

}
