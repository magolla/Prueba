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

import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.PersistentEntity;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Task;

@Repository
public class SpecialtyDAOImpl extends HibernateDaoSupport implements SpecialtyDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void initHibernate() {
		this.setSessionFactory(this.sessionFactory);
	}
	
	@Override
	public void save(Occupation occupation) throws DAOException {
		basicSave(occupation);
	}
	
	@Override
	public void save(Specialty specialty) throws DAOException {
		basicSave(specialty);
	}
	
	@Override
	public void save(Task task) throws DAOException {
		basicSave(task);
	}
	
	@Override
	public Occupation getOccupationById(long id) throws DAOException {
		return (Occupation)this.sessionFactory.getCurrentSession().get(Occupation.class, id);
	}
	
	@Override
	public Specialty getSpecialtyById(long id) throws DAOException {
		return (Specialty)this.sessionFactory.getCurrentSession().get(Specialty.class, id);
	}
	
	@Override
	public Task getTaskById(long id) throws DAOException {
		return (Task)this.sessionFactory.getCurrentSession().get(Task.class, id);
	}
	
	@Override
	public List<Occupation> listOccupation(String text) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Occupation.class);
			criteria.add(Restrictions.like("name", "%" + text + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<Specialty> listSpecialty(String text) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Specialty.class);
			criteria.add(Restrictions.like("name", "%" + text + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<Task> listTask(String text) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
			criteria.add(Restrictions.like("name", "%" + text + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	private void basicSave(PersistentEntity entity) throws DAOException {
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
	
	protected void handleException(String invocationDetails, Exception e) throws DAOException {
//		LoggerManager.error(this, e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	}
}