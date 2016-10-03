package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.NotificationConfigurationDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.NotificationConfiguration;

@Repository
public class NotificationConfigurationDAOImpl  extends GenericDAO<NotificationConfiguration> implements NotificationConfigurationDAO {

	@Override
	public NotificationConfiguration getByUser(long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(NotificationConfiguration.class);
			criteria.add(Restrictions.eq("user.id", userId));
			List<NotificationConfiguration> list = criteria.list();
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
