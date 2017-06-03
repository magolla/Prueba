package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.BOUserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.User;

@Repository
public class BOUserDAOImpl extends GenericDAO<BOUser> implements BOUserDAO {

	@Override
	public BOUser getUserByEmail(String email) throws DAOException {
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

}
