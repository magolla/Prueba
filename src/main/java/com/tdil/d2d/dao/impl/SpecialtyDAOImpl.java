package com.tdil.d2d.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.PersistentEntity;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Task;

@Repository
@Transactional
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
		return (Occupation) this.sessionFactory.getCurrentSession().get(Occupation.class, id);
	}

	@Override
	public Specialty getSpecialtyById(long id) throws DAOException {
		return (Specialty) this.sessionFactory.getCurrentSession().get(Specialty.class, id);
	}

	@Override
	public Task getTaskById(long id) throws DAOException {
		return (Task) this.sessionFactory.getCurrentSession().get(Task.class, id);
	}

	@Override
	public List<Occupation> listOccupation() throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Occupation.class);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
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
	public List<Specialty> listSpecialties(long occupationId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Specialty.class);
			criteria.add(Restrictions.eq("occupation.id", occupationId));
			criteria.addOrder(Order.asc("name"));
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
	public List<Specialty> listSpecialty(long occupationId, String specialty) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Specialty.class);
			criteria.add(Restrictions.eq("occupation.id", occupationId));
			criteria.add(Restrictions.like("name", "%" + specialty + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<Task> listTasks(long specialtyId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
			criteria.add(Restrictions.eq("specialty.id", specialtyId));
			criteria.addOrder(Order.asc("name"));
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

	@Override
	public List<Task> listTask(long specialtyId, String task) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
			criteria.add(Restrictions.eq("specialty.id", specialtyId));
			criteria.add(Restrictions.like("name", "%" + task + "%"));
			criteria.addOrder(Order.asc("name"));
			criteria.setMaxResults(5);
			return criteria.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	

	private void basicSave(PersistentEntity entity) throws DAOException {
		String invocationDetails = "save(" + entity.getClass().getName() + ") ";
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
	
	@SuppressWarnings("unchecked")
	public List<Occupation> listOccupationsByIds(List<Long> ids) throws DAOException {
		if(ids.isEmpty()) {
			return new ArrayList<Occupation>();
		}
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct o ");
			queryString.append("FROM Occupation o ");
			queryString.append("WHERE o.id in(:ids) ");
			
			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameterList("ids", ids);

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Specialty> listSpecialtiesByIds(List<Long> ids) throws DAOException {
		if(ids.isEmpty()) {
			return new ArrayList<Specialty>();
		}
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct s ");
			queryString.append("FROM Specialty s ");
			queryString.append("WHERE s.id in(:ids) ");
			
			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameterList("ids", ids);

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public int taskCount(String search) throws DAOException {
		try {
			
			
			
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
			criteria.createAlias("specialty", "sn");
			criteria.createAlias("sn.occupation", "so");
			criteria.add(Restrictions.disjunction()
			        .add(Restrictions.like("name", search, MatchMode.ANYWHERE))
			        .add(Restrictions.like("sn.name", search, MatchMode.ANYWHERE))
			        .add(Restrictions.like("so.name", search, MatchMode.ANYWHERE))
			    );
			
			int count = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
			return count;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	
	@Override
	public List<Task> getTaskByIndex(String length, String start, String search) {
		
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Task.class);
			criteria.createAlias("specialty", "sn");
			criteria.createAlias("sn.occupation", "so");
			criteria.add(Restrictions.disjunction()
			        .add(Restrictions.like("name", search, MatchMode.ANYWHERE))
			        .add(Restrictions.like("sn.name", search, MatchMode.ANYWHERE))
			        .add(Restrictions.like("so.name", search, MatchMode.ANYWHERE))
			    );
			criteria.setFirstResult(Integer.valueOf(start));
			criteria.setMaxResults(Integer.valueOf(length));
			return criteria.list();
	}
}
