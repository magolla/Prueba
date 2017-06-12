package com.tdil.d2d.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.tdil.d2d.bo.dto.UserDTO;
import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.Base64DTO;
import com.tdil.d2d.controller.api.dto.JobApplicationDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.dto.MatchedUserDTO;
import com.tdil.d2d.controller.api.dto.MatchesSummaryDTO;
import com.tdil.d2d.controller.api.dto.ProfileResponseDTO;
import com.tdil.d2d.controller.api.request.AddLocationRequest;
import com.tdil.d2d.controller.api.request.AddLocationsRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtiesRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtyRequest;
import com.tdil.d2d.controller.api.request.AddTaskToProfileRequest;
import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.ApplyToOfferRequest;
import com.tdil.d2d.controller.api.request.Base64Request;
import com.tdil.d2d.controller.api.request.ConfigureNotificationsRequest;
import com.tdil.d2d.controller.api.request.CreatePaymentRequest;
import com.tdil.d2d.controller.api.request.CreatePermanentJobOfferRequest;
import com.tdil.d2d.controller.api.request.CreatePreferenceMPRequest;
import com.tdil.d2d.controller.api.request.CreateTemporaryJobOfferRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.NotificationConfigurationResponse;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.controller.api.request.RegistrationRequestB;
import com.tdil.d2d.controller.api.request.SearchOfferRequest;
import com.tdil.d2d.controller.api.request.SendSMSRequest;
import com.tdil.d2d.controller.api.request.SetAvatarRequest;
import com.tdil.d2d.controller.api.request.SetInstitutionTypeRequest;
import com.tdil.d2d.controller.api.request.SetLicenseRequest;
import com.tdil.d2d.controller.api.request.SetProfileARequest;
import com.tdil.d2d.controller.api.request.SetProfileBRequest;
import com.tdil.d2d.controller.api.request.SetTasksToProfileRequest;
import com.tdil.d2d.controller.api.request.UserLinkedinProfileRequest;
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

	public boolean createJobOffer(CreateTemporaryJobOfferRequest createOfferRequest) throws ServiceException;
	public boolean editJobOffer(CreateTemporaryJobOfferRequest createOfferRequest, long offerId) throws ServiceException;
	public boolean createJobOffer(CreatePermanentJobOfferRequest createOfferRequest) throws ServiceException;
	public boolean editJobOffer(CreatePermanentJobOfferRequest createOfferRequest, long offerId) throws ServiceException;

	public List<JobOfferStatusDTO> getMyOffers() throws ServiceException;
	
	public List<JobOfferStatusDTO> getPermanentOffersOpen() throws ServiceException;

	List<JobOfferStatusDTO> getMyOffers(long userID) throws ServiceException;

	public List<JobOfferStatusDTO> getMyOffersClosed() throws ServiceException;

	public List<ActivityLogDTO> getActivityLog() throws ServiceException;

	public void initDbWithTestData() throws ServiceException;

	public boolean addSpecialty(AddSpecialtyRequest addSpecialtyRequest) throws ServiceException;
	public boolean addSpecialties(AddSpecialtiesRequest addSpecialtiesRequest) throws ServiceException;

	public boolean addLocation(AddLocationRequest addLocationRequest) throws ServiceException;
	public boolean addLocations(AddLocationsRequest addLocationsRequest) throws ServiceException;
	
	public MatchesSummaryDTO getMatchedOffersSummary() throws ServiceException;
	public List<JobOfferStatusDTO> getMatchedTemporalOffers() throws ServiceException;
	public List<JobOfferStatusDTO> getMatchedPermamentOffers() throws ServiceException;
	public List<JobOfferStatusDTO> getPermamentOffers(SearchOfferRequest searchOfferRequest) throws ServiceException;
	public void offerApplicationCV(long offerId, long applicationId, ServletOutputStream outputStream) throws ServiceException;

	public boolean apply(long offerId, ApplyToOfferRequest applyToOffer) throws ServiceException;

	public List<JobApplicationDTO> offerApplications(long offerId) throws ServiceException;

	public JobApplicationDTO offerApplication(long applicationId) throws ServiceException;

	public boolean accept(long offerId, long applicationId) throws ServiceException;

	public boolean reject(long offerId, long applicationId) throws ServiceException;
	
	public boolean close(long offerId) throws ServiceException;

	public NotificationConfigurationResponse getNotificationConfiguration() throws ServiceException;

	public NotificationConfigurationResponse getNotificationConfiguration(Long userId) throws ServiceException;

	public boolean setNotificationConfiguration(ConfigureNotificationsRequest notificationConfiguration) throws ServiceException;

	public boolean validate(ValidationRequest validationRequest) throws ServiceException;

	public User getUserByMobilePhone(String mobilePhone) throws ServiceException;

	public boolean sendTestNotificationAndroid() throws ServiceException;
	public boolean sendTestNotificationIOS() throws ServiceException;

	public User getLoggedUser() throws ServiceException;

	public UserDetailsResponse me() throws ServiceException;
	
	public UserDetailsResponse getUser(long id) throws ServiceException;

	// Profile
	public ProfileResponseDTO profile() throws ServiceException;
	
	public boolean setLicense(SetLicenseRequest setLicenseRequest) throws ServiceException;	
	public boolean setInstitutionType(SetInstitutionTypeRequest institutionTypeRequest) throws ServiceException;
	public boolean addTask(AddTaskToProfileRequest taskToProfileRequest) throws ServiceException;
	public boolean setTasks(SetTasksToProfileRequest tasksToProfileRequest) throws ServiceException;
	public boolean removeTask(AddTaskToProfileRequest taskToProfileRequest) throws ServiceException;
	public boolean setAvatar(SetAvatarRequest setAvatarRequest) throws ServiceException;
	boolean setAvatar(User user,SetAvatarRequest setAvatarRequest) throws ServiceException;
	public void getAvatar(OutputStream outputStream) throws ServiceException;
	public void getAvatar(long userId, ServletOutputStream outputStream) throws ServiceException;
	public Base64DTO getAvatarBase64() throws ServiceException;
	public Base64DTO getAvatarBase64(long userId) throws ServiceException;

	public void setProfileA(SetProfileARequest setProfileARequest) throws ServiceException;

	public void setProfileB(SetProfileBRequest setProfileBRequest) throws ServiceException;
	
	public void updateUserLinkedinProfile(UserLinkedinProfileRequest userLinkedinProfileRequest) throws ServiceException;
	
    public void setCV(String cv) throws ServiceException;
    
	public String createMercadoPagoPreference(CreatePreferenceMPRequest createPreferenceMPRequest) throws ServiceException;

	public boolean createPayment(CreatePaymentRequest createPaymentRequest) throws ServiceException;
	
	public void setPdfCV(Base64Request base64Request) throws ServiceException;

	public Base64DTO getPdfCVBase64() throws ServiceException;

	List<JobOfferStatusDTO> getAllPermanentOffersOpen() throws ServiceException;

	List<JobOfferStatusDTO> getAllTemporalOffersOpen() throws ServiceException;
	
	public UserDetailsResponse getApprovedCandidateForOffer(long id) throws ServiceException;

	public boolean searchIfApplied(long offerId, long userId);

	public boolean switchToB();

	public List<MatchedUserDTO> getMatchedUsers(Long offerId) throws ServiceException;

	public boolean notifyToMatchedUsers(Long offerId) throws ServiceException;
	
	public List<Long> getOfferIdsByDate(Date date) throws ServiceException;

	public void sendSMS(SendSMSRequest request) throws ServiceException;
	
	public List<UserDTO> getAll() throws ServiceException;
}
