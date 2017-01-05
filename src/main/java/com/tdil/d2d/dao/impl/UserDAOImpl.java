package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserLinkedinProfile;
import com.tdil.d2d.persistence.UserProfile;

@Repository
public class UserDAOImpl  extends GenericDAO<User> implements UserDAO {

	@Override
	public User getUserByUsername(String username) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("username", username));
			List<User> list = criteria.list();
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
	public User getUserByEmail(String email) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			List<User> list = criteria.list();
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
	public User getUserByMobilePhone(String mobilePhone) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("mobilePhone", mobilePhone));
			List<User> list = criteria.list();
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
	public User getLastLoginUser() throws DAOException {
		DetachedCriteria maxDateQuery = DetachedCriteria.forClass(User.class);
		ProjectionList proj = Projections.projectionList();
		proj.add(Projections.max("lastLoginDate"));
		maxDateQuery.setProjection(proj);

		Criteria crit = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
		crit.add(Subqueries.propertiesEq(new String[] {"lastLoginDate"}, maxDateQuery));

		List<User> dtlList = crit.list();
		if (dtlList.size() > 0) {
			return dtlList.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public UserProfile getUserProfile(User user) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserProfile.class);
			criteria.add(Restrictions.eq("user.id", user.getId()));
			List<UserProfile> list = criteria.list();
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
	public void save(UserProfile userProfile) throws DAOException {
		String invocationDetails= "save("+userProfile.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(userProfile);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
	@Override
    public UserLinkedinProfile getUserLinkedinProfile(User user) throws DAOException {
        try {
            Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserLinkedinProfile.class);
            criteria.add(Restrictions.eq("user.id", user.getId()));
            List<UserLinkedinProfile> list = criteria.list();
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
    public void save(UserLinkedinProfile linkedinProfile) throws DAOException {
        String invocationDetails= "save(" + linkedinProfile.getClass().getName() + ") ";
        try {
            this.getHibernateTemplate().save(linkedinProfile);
            this.getHibernateTemplate().flush();
        } catch (DataIntegrityViolationException e) {
            this.handleException(invocationDetails, e);
        } catch (Exception e) {
            this.handleException(invocationDetails, e);
        }
    }
}
