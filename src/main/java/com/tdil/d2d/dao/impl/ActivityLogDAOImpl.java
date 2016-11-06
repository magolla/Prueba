package com.tdil.d2d.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.ActivityLog;

@Repository
public class ActivityLogDAOImpl  extends GenericDAO<ActivityLog> implements ActivityLogDAO {

	@Override
	public List<ActivityLog> getLastLog(Long userId) throws DAOException {
		try {
			// TODO no mas de 10 ordenadas de las mas nuevas a las mas viejas
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ActivityLog.class);
			criteria.add(Restrictions.eq("user.id", userId));
			criteria.addOrder(Order.desc("id"));
			List<ActivityLog> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
