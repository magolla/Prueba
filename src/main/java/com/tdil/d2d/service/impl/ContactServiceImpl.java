package com.tdil.d2d.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.dto.ContactMotiveDTO;
import com.tdil.d2d.controller.api.request.CreateContactRequest;
import com.tdil.d2d.dao.ContactDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.dbinit.DBInit;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Contact;
import com.tdil.d2d.persistence.ContactMotive;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.ContactService;

@Transactional
@Service
public class ContactServiceImpl implements ContactService { 
	
	@Autowired
	private ContactDAO contactDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Collection<ContactMotiveDTO> listContactMotives() throws ServiceException {
		try {
			return toDto(contactDAO.getContactMotives());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean createContact(CreateContactRequest createOfferRequest) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
			Contact contact = new Contact();
			contact.setMotive(contactDAO.getById(ContactMotive.class, createOfferRequest.getContactMotiveId()));
			contact.setComment(createOfferRequest.getComment());
			contact.setUser(user);
			contact.setCreationDate(new Date());
			contactDAO.save(contact);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private Collection<ContactMotiveDTO> toDto(List<ContactMotive> list) {
		return list.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}
	

	private ContactMotiveDTO toDto(ContactMotive s) {
		ContactMotiveDTO result = new ContactMotiveDTO();
		result.setId(s.getId());
		result.setMotive(s.getMotive());
		return result;
	}
	
	@Override
	public void initDB() throws ServiceException {
		try {
			DBInit.initContactMotives(this);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public void addContactMotive(String contactMotiveDesc) throws ServiceException {
		try {
			ContactMotive contactMotive = new ContactMotive();
			contactMotive.setMotive(contactMotiveDesc);
			this.contactDAO.save(contactMotive);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
}
