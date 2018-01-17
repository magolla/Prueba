package com.tdil.d2d.dao.impl;

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

import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.persistence.GeoLevel;

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

	private Geo2 get2ById(long id) throws DAOException {
		return (Geo2)this.sessionFactory.getCurrentSession().get(Geo2.class, id);
	}
	private Geo3 get3ById(long id) throws DAOException {
		return (Geo3)this.sessionFactory.getCurrentSession().get(Geo3.class, id);
	}
	private Geo4 get4ById(long id) throws DAOException {
		return (Geo4)this.sessionFactory.getCurrentSession().get(Geo4.class, id);
	}

	protected void handleException(String invocationDetails, Exception e) throws DAOException {
		//		LoggerManager.error(this, e.getMessage(), e);
		throw new DAOException(e.getMessage(), e);
	}

	@Override
	public GeoLevel getGeoByIdAndLevel(long geoLevelId, int geoLevelLevel) throws DAOException {
		if (geoLevelLevel == 2) {
			return this.get2ById(geoLevelId);
		} else if (geoLevelLevel == 3) {
			return this.get3ById(geoLevelId);
		} if (geoLevelLevel == 4) {
			return this.get4ById(geoLevelId);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Geo4> getListGeo4ByGeo2(Long geo2Id) throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct geo4 ");
			queryString.append("FROM Geo4 geo4 ");
			queryString.append("JOIN geo4.geo3 geo3 ");
			queryString.append("JOIN geo3.geo2 geo2 ");
			queryString.append("WHERE geo2.id = :geo2 ");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameter("geo2", geo2Id);

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Geo4> getListGeo4ByGeo3(Long geo3Id) throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct geo4 ");
			queryString.append("FROM Geo4 geo4 ");
			queryString.append("JOIN geo4.geo3 geo3 ");
			queryString.append("WHERE geo3.id = :geo3 ");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameter("geo3", geo3Id);

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Geo3> getListGeo3ByGeo2(Long geo2Id) throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct geo3 ");
			queryString.append("FROM Geo3 geo3 ");
			queryString.append("JOIN geo3.geo2 geo2 ");
			queryString.append("WHERE geo2.id = :geo2 ");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameter("geo2", geo2Id);

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Geo2> getListGeo2() throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct geo2 ");
			queryString.append("FROM Geo2 geo2 ");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<Geo4> getGeoByIndex(String length, String start, String search) throws DAOException{

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo4.class);
		criteria.createAlias("geo3", "g3");
		criteria.createAlias("g3.geo2", "g2");
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.like("name", search, MatchMode.ANYWHERE))
				.add(Restrictions.like("g3.name", search, MatchMode.ANYWHERE))
				.add(Restrictions.like("g2.name", search, MatchMode.ANYWHERE))
				);
		criteria.setFirstResult(Integer.valueOf(start));
		criteria.setMaxResults(Integer.valueOf(length));
		return criteria.list();
	}

	@Override
	public int geoCount(String search) {

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo4.class);
		criteria.createAlias("geo3", "g3");
		criteria.createAlias("g3.geo2", "g2");
		criteria.add(Restrictions.disjunction()
				.add(Restrictions.like("name", search, MatchMode.ANYWHERE))
				.add(Restrictions.like("g3.name", search, MatchMode.ANYWHERE))
				.add(Restrictions.like("g2.name", search, MatchMode.ANYWHERE))
				);
		int count = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return count;

	}

	@Override
	public Geo2 searchGeo2(String geo2) throws DAOException{

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo2.class);
		criteria.add(Restrictions.eq("name", geo2));

		List<Geo2> geo2List = criteria.list();

		if(geo2List.isEmpty()) {
			return null;
		} else {
			return geo2List.get(0);
		}
	}

	@Override
	public Geo3 searchGeo3(String geo3) throws DAOException{
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo3.class);
		criteria.add(Restrictions.eq("name", geo3));

		List<Geo3> geo3List = criteria.list();

		if(geo3List.isEmpty()) {
			return null;
		} else {
			return geo3List.get(0);
		}
	}

	@Override
	public Geo4 searchGeo4(String geo4) throws DAOException{
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Geo4.class);
		criteria.add(Restrictions.eq("name", geo4));

		List<Geo4> geo4List = criteria.list();

		if(geo4List.isEmpty()) {
			return null;
		} else {
			return geo4List.get(0);
		}
	}
}
