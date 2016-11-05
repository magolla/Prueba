package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.JobApplicationDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.request.AddLocationRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtyRequest;
import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.ApplyToOfferRequest;
import com.tdil.d2d.controller.api.request.ConfigureNotificationsRequest;
import com.tdil.d2d.controller.api.request.CreateJobOfferRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.NotificationConfigurationResponse;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.controller.api.request.RegistrationRequestB;
import com.tdil.d2d.controller.api.request.ValidationRequest;
import com.tdil.d2d.controller.api.response.RegistrationResponse;
import com.tdil.d2d.controller.api.response.UserDetailsResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;

public interface UserService {

	public User getUserByUsername(String username) throws ServiceException;

	public RegistrationResponse register(RegistrationRequestA registrationRequest) throws ServiceException;
	public RegistrationResponse register(RegistrationRequestB registrationRequest) throws ServiceException;

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

	public List<JobOfferStatusDTO> getMatchedOffers() throws ServiceException;

	public boolean apply(long offerId, ApplyToOfferRequest applyToOffer) throws ServiceException;

	public List<JobApplicationDTO> offerApplications(long offerId) throws ServiceException;

	public JobApplicationDTO offerApplication(long applicationId) throws ServiceException;

	public boolean accept(long offerId, long applicationId) throws ServiceException;

	public boolean reject(long offerId, long applicationId) throws ServiceException;

	public NotificationConfigurationResponse getNotificationConfiguration() throws ServiceException;

	public boolean setNotificationConfiguration(ConfigureNotificationsRequest notificationConfiguration) throws ServiceException;

	public boolean validate(ValidationRequest validationRequest) throws ServiceException;

	public User getUserByMobilePhone(String mobilePhone) throws ServiceException;

	public boolean sendTestNotificationAndroid() throws ServiceException;
	public boolean sendTestNotificationIOS() throws ServiceException;

	public User getLoggedUser() throws ServiceException;

	public UserDetailsResponse me() throws ServiceException;

}
