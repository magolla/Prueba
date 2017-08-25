package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.NotificationDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Notification;

@Repository
public class NotificationDAOImpl  extends GenericDAO<Notification> implements NotificationDAO {

	@Override
	public Notification getByUser(long userId) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Notification.class);
			criteria.add(Restrictions.eq("user.id", userId));
			List<Notification> list = criteria.list();
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
	public Notification getByUserOffer(Long userId, Long offerId, String type) {
		
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct notification ");
		queryString.append("FROM Notification notification ");
		queryString.append("WHERE notification.offer.id = :offer_id ");
		queryString.append("AND notification.user.id = :user_id ");
		queryString.append("AND notification.action = :action ");
		queryString.append("order by notification.creationDate desc");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("offer_id", offerId);
		query.setParameter("user_id", userId);
		query.setParameter("action", type);

		List<Notification> list = query.list();
		
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<Notification> getAllNotificationByUserId(long id) {
		
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct notification ");
		queryString.append("FROM Notification notification ");
		queryString.append("WHERE notification.user.id = :user_id ");
		queryString.append("AND notification.status = :status ");
		queryString.append("order by notification.creationDate desc");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("user_id", id);
		query.setParameter("status", "Enviado");

		List<Notification> list = query.list();
		
		updateNotification(id);
		
		
		return list.isEmpty() ? null : list;
		
	}
	
	
	public void updateNotification(long id){
		StringBuilder queryString = new StringBuilder("");
		queryString.append("update Notification notification ");
		queryString.append("set notification.status = 'Leído' ");
		queryString.append("WHERE notification.user.id = :user_id ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("user_id", id);

		int result = query.executeUpdate();
	}

}
