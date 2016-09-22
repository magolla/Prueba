package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.request.AddLocationRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtyRequest;
import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.CreateJobOfferRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequest;
import com.tdil.d2d.controller.api.response.RegistrationResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;

public interface UserService {

	public User getUserByUsername(String username) throws ServiceException;

	public RegistrationResponse register(RegistrationRequest registrationRequest) throws ServiceException;

	public boolean updateAndroidRegId(AndroidRegIdRequest registrationRequest) throws ServiceException;

	public void updateLastLoginDate() throws ServiceException;

	public User getUserByEmail(String username) throws ServiceException;

	public boolean updateIOsPushId(IOsPushIdRequest iOsPushIdRequest) throws ServiceException;

	public boolean validateEmail(String email, String hash) throws ServiceException;

	public boolean createJobOffer(CreateJobOfferRequest createOfferRequest) throws ServiceException;

	public List<JobOfferStatusDTO> getMyOffers() throws ServiceException;

	public List<ActivityLogDTO> getActivityLog() throws ServiceException;

	public void initDbWithTestData() throws ServiceException;

	public boolean addSpecialty(AddSpecialtyRequest addSpecialtyRequest) throws ServiceException;

	public boolean addLocation(AddLocationRequest addLocationRequest) throws ServiceException;

}
