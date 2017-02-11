package com.tdil.d2d.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;

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
		criteria.setFirstResult(page * size);
		criteria.setMaxResults(size);
		return criteria.list();
	}

	@Override
	public List<Note> getNotesForUser(List<Long> ocuppations, List<Long> specialities) {
		
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct note ");
		queryString.append("FROM Note note ");
		queryString.append("left join note.specialties as specialty ");
		queryString.append("WHERE specialty.occupation.id IN (:ocuppations) ");
		queryString.append("AND specialty.id IN (:specialities) ");
		queryString.append("order by note.creationDate desc ");

		Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameterList("ocuppations", ocuppations);
		query.setParameterList("specialities", specialities);


		List<Note> list = query.list();
		
		return list;
	}

	
	@Override
	public Note getNoteById(Long id) {
		return this.getHibernateTemplate().get(Note.class, id);
	}

}
