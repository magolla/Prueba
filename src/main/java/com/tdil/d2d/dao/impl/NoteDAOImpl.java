package com.tdil.d2d.dao.impl;

import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.NoteCategory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

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
		this.getHibernateTemplate().save(note);
		this.getHibernateTemplate().flush();
		return note;
	}

	@Override
	public List<Note> getNotes(Map<String, Object> params) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Note.class);
		params.forEach((key, value) -> {
			if (key.equals("category")) {
				criteria.add(Restrictions.eq(key, NoteCategory.valueOf((String) value)));
			} else {
				criteria.add(Restrictions.eq(key, value));
			}
		});
		return criteria.list();
	}

	@Override
	public Note getNoteById(Long id) {
		return this.getHibernateTemplate().get(Note.class, id);
	}

}
