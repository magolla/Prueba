package com.tdil.d2d.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.dto.InAppPurchaseDTO;
import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Receipt;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;

@Transactional
@Repository
public class SubscriptionDAOImpl extends HibernateDaoSupport implements SubscriptionDAO {

	private SessionFactory sessionFactory;

	private Logger logger = LoggerFactory.getLogger(SubscriptionDAOImpl.class);

	@Autowired
	public SubscriptionDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

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
	public Sponsor getSponsorById(long id) {
		return (Sponsor) this.getSessionFactory().getCurrentSession().get(Sponsor.class, id);
	}

	@Override
	public List<SponsorCode> saveSponsorCode(List<SponsorCode> codes) {
		codes.forEach(elem -> this.getHibernateTemplate().saveOrUpdate(elem));
		this.getHibernateTemplate().flush();
		return codes;
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
		String invocationDetails = "save(" + sponsor.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().saveOrUpdate(sponsor);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@Override
	public void saveSponsorCode(SponsorCode sponsorCode) throws DAOException {
		String invocationDetails = "save(" + sponsorCode.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().saveOrUpdate(sponsorCode);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@Override
	public void saveSubscription(Subscription subscription) throws DAOException {
		String invocationDetails = "save(" + subscription.getClass().getName() + ") ";
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
			criteria.add(Restrictions.gt("expirationDate", Calendar.getInstance().getTime()));
			
			List<Subscription> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<SponsorCode> listSponsorCodes(Long sponsorId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SponsorCode.class);
		criteria.add(Restrictions.eq("sponsor.id", sponsorId));
		List<SponsorCode> codes = criteria.list();
		logger.info("Sponsor codes found: {}", codes.size());
		return codes;
	}

	protected void handleException(String invocationDetails, Exception e) throws DAOException {
//		LoggerManager.error(this, e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	}

	@Override
	public boolean checkIfSponsorCodeExist(Long sponsorId, String sponsorCode) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SponsorCode.class);
		criteria.add(Restrictions.eq("sponsor.id", sponsorId));
		criteria.add(Restrictions.eq("code", sponsorCode));
		List<SponsorCode> codes = criteria.list();
		logger.info("Sponsor codes found: {}", codes.size());

		if(codes.size() == 0) {
			System.out.println("No esta repetido, se guardara en la base el code: " + sponsorCode);
			return false;
		} else {
			System.out.println("El codigo: " + sponsorCode + " para el usuario con ID: " + sponsorId + " ya existe, se generara otro codigo");
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getStoredTransationIds(List<InAppPurchaseDTO> latestPurchases) {
		
		List<String> transactionIds = new ArrayList<String>();
		for (InAppPurchaseDTO inAppPurchaseDTO : latestPurchases) {
			transactionIds.add(inAppPurchaseDTO.getTransactionID());
		}
		
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct receipt.transactionId ");
		queryString.append("FROM Receipt receipt ");
		queryString.append("WHERE receipt.transactionId in (:transactionIds) ");
		queryString.append("order by receipt.creationDate desc");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameterList("transactionIds", transactionIds);

		List<String> list = query.list();
		return list;
	}
	
	@Override
	public void saveReceipt(Receipt receipt) throws DAOException {
		String invocationDetails = "save(" + receipt.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().save(receipt);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
	@Override
	public Receipt getLastReceipt(Long userId) {
		
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct receipt ");
		queryString.append("FROM Receipt receipt ");
		queryString.append("WHERE receipt.user.id = :userId ");
		queryString.append("order by receipt.expiresDate desc");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setMaxResults(1);
		query.setParameter("userId", userId);

		return (Receipt) query.uniqueResult();
	}
}
