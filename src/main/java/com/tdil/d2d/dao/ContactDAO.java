package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Contact;
import com.tdil.d2d.persistence.ContactMotive;

public interface ContactDAO {
	
	public ContactMotive getById(Class<ContactMotive> aClass, long id) throws DAOException;

	public void save(ContactMotive contactMotive) throws DAOException;
	
	public void save(Contact contact) throws DAOException;

	public List<ContactMotive> getContactMotives() throws DAOException;

}
