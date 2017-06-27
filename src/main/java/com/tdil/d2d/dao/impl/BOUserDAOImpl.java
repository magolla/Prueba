package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.utils.FindbugsSuppressWarnings;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.BOUserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.Role;

@Repository
public class BOUserDAOImpl extends GenericDAO<BOUser> implements BOUserDAO {

	@Override
	public BOUser findByEmail(String email) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(BOUser.class);
			criteria.add(Restrictions.eq("email", email));
			List<BOUser> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BOUser> getAll() throws DAOException {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(BOUser.class);
			return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() throws DAOException {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Role.class);
			return criteria.list();
	}

	@Override
	public BOUser find(long userId)  throws DAOException{
		try{
			return getById(BOUser.class, userId);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Role findRole(Long roleId) throws DAOException {
		try{
			return (Role)getSessionFactory().getCurrentSession().get(Role.class, roleId);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void save(BOUser user)  throws DAOException {
		String invocationDetails= "save("+user.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(user);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

}
