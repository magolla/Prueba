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

import com.tdil.d2d.dao.LoginDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Login;
import com.tdil.d2d.persistence.User;

@Repository
public class LoginDAOImpl  extends HibernateDaoSupport implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void initHibernate() {
		this.setSessionFactory(this.sessionFactory);
	}
	
	public void save(Object entity) throws DAOException {
		String invocationDetails= "save("+entity.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(entity);	
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
	
	@Override
	public Login getLogin(String authToken) throws DAOException {
		try {
			Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("authToken", authToken));
			List<Login> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
}
