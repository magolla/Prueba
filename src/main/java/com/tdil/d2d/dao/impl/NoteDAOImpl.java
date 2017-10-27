package com.tdil.d2d.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.User;

@Repository
@Transactional
public class NoteDAOImpl extends HibernateDaoSupport implements NoteDAO {

	private final SessionFactory sessionFactory;

	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@PostConstruct
	public void initHibernate() {
		this.setSessionFactory(this.sessionFactory);
	}

	@Override
	public Note save(Note note) {
		this.getHibernateTemplate().saveOrUpdate(note);
		this.getHibernateTemplate().flush();
		return note;
	}

	@Override
	public List<Note> getNotes(int page, int size, Map<String, Object> params) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Note.class);
		if (!params.containsKey("active")) {
			params.put("active", true);
		}
		params.forEach((key, value) -> {
			if (key.equals("category")) {
				criteria.add(Restrictions.eq(key, NoteCategory.valueOf((String) value)));
			} else {
				criteria.add(Restrictions.eq(key, value));
			}
		});

		criteria.setFirstResult((page - 1) * size);
		criteria.setMaxResults(((page - 1) * size) + size);
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	@Override
	public List<Note> getNotesForUser(int page, int size,List<Long> ocuppations, List<Long> specialities,User user,Subscription userSubscription) {

		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct note ");
		queryString.append("FROM Note note ");
		queryString.append("left join note.specialties as specialty ");
		queryString.append("left join note.occupations as occupation ");
		if(!user.isUserb()) {
			queryString.append("WHERE note.active = 1");
			if(!user.isUserb()) {
				queryString.append("AND (note.sendUserA is true )");
			}
		} else {
			queryString.append("WHERE ((occupation.id IN (:ocuppations) ");
			queryString.append("AND specialty.id IN (:specialities) AND note.active = 1) ");
			queryString.append("OR (occupation.id IN (:ocuppations) AND specialty.id is null AND note.active = 1) ");
			queryString.append("OR (occupation.id is null AND specialty.id is null AND note.active = 1)) ");
			
			if(userSubscription.getSponsorCode() != null) {
				queryString.append("AND ((:sponsorId in sponsor.id)");
				queryString.append("OR (note.sendUserBAllSponsor is true ))");
			} else {
				queryString.append(" AND (note.sendUserBNoSponsor is true )");
			}
		}
		queryString.append("AND (note.expirationDate >= now() OR note.expirationDate is null) ");
		queryString.append("AND (note.publishingDate <= now() OR note.publishingDate is null) ");
		queryString.append("order by note.creationDate desc ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		if(user.isUserb()) {
			query.setParameterList("ocuppations", ocuppations);
			query.setParameterList("specialities", specialities);
//			if(userSubscription.getSponsorCode() != null) {
//				query.setParameter("sponsorId", userSubscription.getSponsorCode());
//			}
		}
		query.setFirstResult((page - 1) * size);
		query.setMaxResults(((page - 1) * size) + size);


		List<Note> list = query.list();

		return list;
	}


	@Override
	public Note getNoteById(Long id) {
		return this.getHibernateTemplate().get(Note.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> getAll() throws DAOException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Note.class);
		return criteria.list();
	}

	@Override
	public Note getLastNote() {
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct note ");
		queryString.append("FROM Note note ");
		queryString.append("left join note.specialties as specialty ");
		queryString.append("left join note.occupations as occupation ");
		queryString.append("order by note.id desc ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());

		List<Note> list = query.list();

		return list.get(0);
	}
}
