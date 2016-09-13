package com.tdil.d2d.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;

@Repository
public class GeoDAOImpl extends HibernateDaoSupport implements GeoDAO  {

	@Autowired
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void initHibernate() {
		this.setSessionFactory(this.sessionFactory);
	}
	
	@Override
	public List<Geo2> listGeo2(String text) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo2.class);
			criteria.add(Restrictions.like("name", "%" + text + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<Geo3> listGeo3(String text) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo3.class);
			criteria.add(Restrictions.like("name", "%" + text + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<Geo4> listGeo4(String text) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo4.class);
			criteria.add(Restrictions.like("name", "%" + text + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public void save(Geo2 entity) throws DAOException {
		String invocationDetails= "save("+entity.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(entity);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	public void save(Geo3 entity) throws DAOException {
		String invocationDetails= "save("+entity.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(entity);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	public void save(Geo4 entity) throws DAOException {
		String invocationDetails= "save("+entity.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(entity);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
	public Geo2 get2ById(Class<Geo2> aClass, long id) throws DAOException {
		return (Geo2)this.sessionFactory.getCurrentSession().get(aClass, id);
	}
	public Geo3 get3ById(Class<Geo3> aClass, long id) throws DAOException {
		return (Geo3)this.sessionFactory.getCurrentSession().get(aClass, id);
	}
	public Geo4 get4ById(Class<Geo4> aClass, long id) throws DAOException {
		return (Geo4)this.sessionFactory.getCurrentSession().get(aClass, id);
	}
	
	protected void handleException(String invocationDetails, Exception e) throws DAOException {
//		LoggerManager.error(this, e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	}

}
