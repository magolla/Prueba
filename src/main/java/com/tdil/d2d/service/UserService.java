package com.tdil.d2d.service;

import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequest;
import com.tdil.d2d.controller.api.request.RegistrationResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;

public interface UserService {

	public User getUserByUsername(String username) throws ServiceException;

	public RegistrationResponse register(RegistrationRequest registrationRequest) throws ServiceException;

	public boolean updateAndroidRegId(AndroidRegIdRequest registrationRequest) throws ServiceException;

	public void updateLastLoginDate() throws ServiceException;

}
