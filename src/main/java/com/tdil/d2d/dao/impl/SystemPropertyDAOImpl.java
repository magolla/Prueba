package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.SystemPropertyDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.SystemProperty;

@Repository
public class SystemPropertyDAOImpl  extends GenericDAO<SystemProperty> implements SystemPropertyDAO {

	@Override
	public SystemProperty getSystemPropertyByKey(String key) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SystemProperty.class);
			criteria.add(Restrictions.eq("key", key));
			List<SystemProperty> list = criteria.list();
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
	public List<SystemProperty> getSystemPropertiesByKeys(List<String> keys) throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct systemProperty ");
			queryString.append("FROM SystemProperty systemProperty ");
			queryString.append("WHERE systemProperty.key in (:keyProperties) ");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameterList("keyProperties", keys);
			

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}
