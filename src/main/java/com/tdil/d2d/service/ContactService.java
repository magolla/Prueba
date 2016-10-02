package com.tdil.d2d.service;

import java.util.Collection;

import com.tdil.d2d.controller.api.dto.ContactMotiveDTO;
import com.tdil.d2d.controller.api.request.CreateContactRequest;
import com.tdil.d2d.exceptions.ServiceException;

public interface ContactService {

	public Collection<ContactMotiveDTO> listContactMotives() throws ServiceException;

	public boolean createContact(CreateContactRequest createOfferRequest) throws ServiceException;

	public void initDB() throws ServiceException;

	public void addContactMotive(String contactMotive) throws ServiceException;
}
