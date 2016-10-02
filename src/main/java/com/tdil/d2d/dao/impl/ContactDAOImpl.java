package com.tdil.d2d.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.dao.ContactDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Contact;
import com.tdil.d2d.persistence.ContactMotive;

@Repository
public class ContactDAOImpl  extends GenericDAO<ContactMotive> implements ContactDAO {

	@Override
	public List<ContactMotive> getContactMotives() throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ContactMotive.class);
			criteria.addOrder(Order.asc("motive"));
			List<ContactMotive> list = criteria.list();
			return list;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public void save(Contact entity) throws DAOException {
		String invocationDetails= "save("+entity.getClass().getName()+") ";
		try {
			this.getHibernateTemplate().save(entity);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}
	
}
