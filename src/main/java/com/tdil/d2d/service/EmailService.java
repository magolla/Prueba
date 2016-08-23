package com.tdil.d2d.service;

import com.tdil.d2d.exceptions.ServiceException;

public interface EmailService {

	public void sendEmail(String to, String from, String subject, String body) throws ServiceException;

}
