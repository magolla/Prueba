package com.tdil.d2d.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercadopago.MP;
import com.tdil.d2d.bo.dto.BONoteDTO;
import com.tdil.d2d.bo.dto.BoJobDTO;
import com.tdil.d2d.bo.dto.UserCandidateDTO;
import com.tdil.d2d.bo.dto.UserDTO;
import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.Base64DTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.JobApplicationDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.dto.MatchedUserDTO;
import com.tdil.d2d.controller.api.dto.MatchesSummaryDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
import com.tdil.d2d.controller.api.dto.ProfileResponseDTO;
import com.tdil.d2d.controller.api.dto.SearchOfferDTO;
import com.tdil.d2d.controller.api.dto.SpecialtyDTO;
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
import com.tdil.d2d.controller.api.request.InstitutionType;
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
import com.tdil.d2d.controller.api.response.UserReceiptResponse;
import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.dao.JobApplicationDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.NoteDAO;
import com.tdil.d2d.dao.NotificationConfigurationDAO;
import com.tdil.d2d.dao.NotificationDAO;
import com.tdil.d2d.dao.PaymentDAO;
import com.tdil.d2d.dao.PointsDAO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.dao.SystemPropertyDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.ActivityActionEnum;
import com.tdil.d2d.persistence.ActivityLog;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.persistence.GeoLevel;
import com.tdil.d2d.persistence.JobApplication;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.Notification;
import com.tdil.d2d.persistence.NotificationConfiguration;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Payment;
import com.tdil.d2d.persistence.Points;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SuscriptionTypeEnum;
import com.tdil.d2d.persistence.Task;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserGeoLocation;
import com.tdil.d2d.persistence.UserLinkedinProfile;
import com.tdil.d2d.persistence.UserProfile;
import com.tdil.d2d.persistence.ValidationCode;
import com.tdil.d2d.security.RuntimeContext;
import com.tdil.d2d.service.CryptographicService;
import com.tdil.d2d.service.EmailService;
import com.tdil.d2d.service.NotificationService;
import com.tdil.d2d.service.SponsorCodeService;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.service.UserService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${mercadopago.client_id}")
	private String clientId;

	@Value("${mercadopago.secret_id}")
	private String secretId;

	@Value("${smsservice.user}")
	private String smsUser;

	@Value("${smsservice.password}")
	private String smsPassword;

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JobOfferDAO jobDAO;
	@Autowired
	private JobApplicationDAO jobApplicationDAO;
	@Autowired
	private SpecialtyDAO specialtyDAO;
	@Autowired
	private NotificationConfigurationDAO notificationConfigurationDAO;
	@Autowired
	private NotificationDAO notificationDAO;
	@Autowired
	private ActivityLogDAO activityLogDAO;
	@Autowired
	private GeoDAO geoDAO;
	@Autowired
	private NoteDAO noteDAO;

	@Autowired
	private PointsDAO pointsDAO;

	@Autowired
	private PaymentDAO paymentDAO;

	@Autowired
	private SystemPropertyDAO systemPropertyDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CryptographicService cryptographicService;

	@Autowired
	@Qualifier(value = "androidNotificationServiceImpl")
	private NotificationService androidNotificationService;
	@Autowired
	@Qualifier(value = "iosNotificationServiceImpl")
	private NotificationService iosNotificationService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private SubscriptionDAO subscriptionDAO;

	@Autowired
	private SponsorCodeService sponsorCodeService;

	@Override
	public User getUserByUsername(String username) throws ServiceException {
		try {
			return this.userDAO.getUserByUsername(username);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User getUserByEmail(String email) throws ServiceException {
		try {
			return this.userDAO.getUserByEmail(email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public User getUserByMobilePhone(String mobilePhone) throws ServiceException {
		try {
			return this.userDAO.getUserByMobilePhone(mobilePhone);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public RegistrationResponse register(RegistrationRequestA registrationRequest) throws ServiceException {
		try {
			RegistrationResponse response = new RegistrationResponse(HttpStatus.CREATED.value());
			User user = getOrCreateUser(registrationRequest.getMobilePhone());
			Date registrationDate = new Date();
			user.setCreationDate(registrationDate);
			user.setEmail(registrationRequest.getEmail());
			user.setFirstname(registrationRequest.getFirstname());
			user.setLastname(registrationRequest.getLastname());
			// user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			user.setEnabled(true);
			user.setDeviceId(encriptDeviceId(registrationRequest.getDeviceId(), user));
			// user.setPhoneNumber(cryptographicService.encrypt(registrationRequest.getPhoneNumber(),
			// "", user.getSalt()));
			user.setUserb(false);
			user.setPhoneValidated(true);
			user.setEmailValidated(false);
			user.setEmailHash(RandomStringUtils.randomAlphanumeric(4));
			user.setCompanyScreenName(registrationRequest.getCompanyScreenName());
			user.setTacAccepted(registrationRequest.isTacAccepted());
			user.setTacAcceptDate(registrationDate);

			user.setPassword(passwordEncoder.encode(registrationRequest.getDeviceId()));
			this.userDAO.save(user);
			if(!user.getMobilePhone().equals("94572109469428712369")) {
				subscribeUser(user);	
			}
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());
			if (notificationConfiguration == null) {
				notificationConfiguration = NotificationConfiguration.getDefaultConfiguration();
				notificationConfiguration.setUser(user);
				this.notificationConfigurationDAO.save(notificationConfiguration);
			}

			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.REGISTER.getMessage()));
			savePoints(ActivityActionEnum.REGISTER, user);


			try {
				String body = "Para terminar la registracion use el siguiente codigo en la app o cliquea el siguiente link "
						+ user.getEmailHash();
				emailService.sendEmail(registrationRequest.getEmail(), EmailServiceImpl.defaultFrom, "Registracion",
						body);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		} catch (IllegalBlockSizeException | BadPaddingException | DAOException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ServiceException(e);
		}
	}

	private void savePoints(ActivityActionEnum register, User user) {

		if(register.getValue() != 0) {
			try {
				pointsDAO.save(new Points(register, user));
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendSMS(String mobilePhone, String mobileHash) throws IOException{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("http://servicio.smsmasivos.com.ar/enviar_sms.asp?api=1&"
						+ "usuario=" + smsUser +"&clave=" + smsPassword + "&tos=" + mobilePhone + "&texto=" + mobileHash)
				.build();
		Response smsResponse = client.newCall(request).execute();;
		if("OK".equals(smsResponse.body().string())){
			logger.info("Code sent succesfully to " + mobilePhone + " - Code: " + mobileHash);
		} else {
			logger.info("Can't send code by SMS. Error: " + smsResponse.toString());
		}
	}

	@Override
	public RegistrationResponse register(RegistrationRequestB registrationRequest) throws ServiceException {
		try {
			RegistrationResponse response = new RegistrationResponse(HttpStatus.CREATED.value());
			User user = getOrCreateUser(registrationRequest.getMobilePhone());
			Date registrationDate = new Date();
			user.setCreationDate(registrationDate);
			user.setEmail(registrationRequest.getEmail());
			user.setFirstname(registrationRequest.getFirstname());
			user.setLastname(registrationRequest.getLastname());
			// user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			user.setEnabled(true);
			user.setDeviceId(encriptDeviceId(registrationRequest.getDeviceId(), user));
			// user.setPhoneNumber(cryptographicService.encrypt(registrationRequest.getPhoneNumber(),
			// "", user.getSalt()));
			user.setUserb(true);
			user.setPhoneValidated(true);
			user.setEmailValidated(false);
			user.setEmailHash(RandomStringUtils.randomAlphanumeric(4));
			user.setTacAccepted(registrationRequest.isTacAccepted());
			user.setTacAcceptDate(registrationDate);
			user.setMobileHash(RandomStringUtils.randomAlphanumeric(4));

			user.setPassword(passwordEncoder.encode(registrationRequest.getDeviceId()));
			this.userDAO.save(user);
			if(!user.getMobilePhone().equals("94572109469428712369")) {

				Subscription subscription = this.subscriptionDAO.getSubscriptionByUser(user);
				if(subscription.getId() == 0) {
					if(registrationRequest.getSuscriptionCode() == null) {
						subscribeUser(user);	
					} else {
						Subscription suscription = this.sponsorCodeService.consumeSponsorCode(user, registrationRequest.getSuscriptionCode());
					}
				}

			}
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());
			if (notificationConfiguration == null) {
				notificationConfiguration = NotificationConfiguration.getDefaultConfiguration();
				notificationConfiguration.setUser(user);
				this.notificationConfigurationDAO.save(notificationConfiguration);
			}

			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.REGISTER.getMessage()));
			savePoints(ActivityActionEnum.REGISTER, user);

			try {
				String body = "Para terminar la registracion use el siguiente codigo en la app o cliquea el siguiente link "
						+ user.getEmailHash();
				emailService.sendEmail(registrationRequest.getEmail(), EmailServiceImpl.defaultFrom, "Registracion",
						body);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		} catch (IllegalBlockSizeException | BadPaddingException | DAOException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ServiceException(e);
		}
	}

	private User getOrCreateUser(String mobilePhone) throws ServiceException {
		User user = this.getUserByMobilePhone(mobilePhone);
		if (user == null) {
			user = new User();
			user.setMobilePhone(mobilePhone);
		}
		return user;
	}

	private String encriptDeviceId(String deviceId, User user) throws InvalidKeyException, NoSuchAlgorithmException,
	NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return cryptographicService.encrypt(deviceId, "", user.getSalt());
	}

	@Override
	public boolean validate(ValidationRequest validationRequest) throws ServiceException {
		try {
			ValidationCode validationCode = this.userDAO.getValidationCode(validationRequest.getMobilePhone(), validationRequest.getSmsCode());
			if (validationCode!=null) {
				logger.info("Code found = {}", validationCode.getCode());

				validationCode.setEnabled(false);

				this.userDAO.save(validationCode);
				return true;
			}
			return false;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private void subscribeUser(User user) {
		try {
			Subscription subscription = this.subscriptionService.createFreeSubscription(user,false);
			if(subscription == null) {
				return;
			}
			subscription.setSubscriptionDetail(SuscriptionTypeEnum.FREE_SUSCRIPTION.getMessage());
			subscriptionDAO.saveSubscription(subscription);
		} catch (ServiceException | DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateAndroidRegId(AndroidRegIdRequest androidRegIdRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			user.setAndroidRegId(androidRegIdRequest.getAndroidRegId());
			user.setIosPushId(null);
			this.userDAO.save(user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean updateIOsPushId(IOsPushIdRequest iOsPushIdRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			user.setIosPushId(iOsPushIdRequest.getIosPushId());
			user.setAndroidRegId(null);
			this.userDAO.save(user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addSpecialty(AddSpecialtyRequest addSpecialtyRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			Specialty specialty = this.specialtyDAO.getSpecialtyById(addSpecialtyRequest.getSpecialtyId());
			user.getSpecialties().add(specialty);
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.ADD_SPECIALTY.getMessage()));
			savePoints(ActivityActionEnum.ADD_SPECIALTY, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addSpecialties(AddSpecialtiesRequest addSpecialtiesRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			user.getSpecialties().clear();
			for (long id : addSpecialtiesRequest.getSpecialtyId()) {
				Specialty specialty = this.specialtyDAO.getSpecialtyById(id);
				user.getSpecialties().add(specialty);
			}
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.ADD_SPECIALTY.getMessage()));
			savePoints(ActivityActionEnum.ADD_SPECIALTY, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addLocation(AddLocationRequest addLocationRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			UserGeoLocation loc = new UserGeoLocation();
			loc.setGeoLevelLevel(addLocationRequest.getGeoLevelLevel());
			loc.setGeoLevelId(addLocationRequest.getGeoLevelId());
			user.getUserGeoLocations().add(loc);
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.ADD_GEO_LEVEL.getMessage()));
			savePoints(ActivityActionEnum.ADD_GEO_LEVEL, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addLocations(AddLocationsRequest addLocationsRequest) throws ServiceException {
		try {
			User user = getLoggedUser();

			userDAO.deleteUserGeoLocations(user);

			user.getUserGeoLocations().clear();

			for (int i = 0; i < addLocationsRequest.getGeoLevelId().length; i++) {
				UserGeoLocation loc = new UserGeoLocation();
				loc.setGeoLevelLevel(addLocationsRequest.getGeoLevelLevel()[i]);
				loc.setGeoLevelId(addLocationsRequest.getGeoLevelId()[i]);
				loc.setGeoLevelName(addLocationsRequest.getGeoLevelNames()[i]);
				loc.setUser(user);
				user.getUserGeoLocations().add(loc);
			}
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.ADD_GEO_LEVEL.getMessage()));
			savePoints(ActivityActionEnum.ADD_GEO_LEVEL, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean setLicense(SetLicenseRequest setLicenseRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			user.setLicense(setLicenseRequest.getLicense());
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.SET_LICENSE.getMessage()));
			savePoints(ActivityActionEnum.SET_LICENSE, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean setAvatar(SetAvatarRequest setAvatarRequest) throws ServiceException {
		User user = getLoggedUser();
		return this.setAvatar(user, setAvatarRequest);
	}

	@Override
	public void getAvatar(OutputStream outputStream) throws ServiceException {
		try {
			User user = getLoggedUser();
			if(user.getAvatar()!=null)
				outputStream.write(user.getAvatar().getData());
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Base64DTO getAvatarBase64() throws ServiceException {
		User user = getLoggedUser();
		if (user.getAvatar() != null) {
			return new Base64DTO(new String(user.getAvatar().getData()));
		} else {
			return new Base64DTO("");
		}
	}

	@Override
	public void getAvatar(long userId, ServletOutputStream outputStream) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, userId);
			if(user.getAvatar()!=null)
				outputStream.write(user.getAvatar().getData());
		} catch (DAOException | IOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Base64DTO getAvatarBase64(long userId) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, userId);
			if (user.getAvatar() != null) {
				return new Base64DTO(new String(user.getAvatar().getData()));
			} else {
				return new Base64DTO();
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean setInstitutionType(SetInstitutionTypeRequest institutionTypeRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setUser(user);
			}
			userProfile.setInstitutionType(institutionTypeRequest.getInstitutionType());
			this.userDAO.save(userProfile);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.CHANGE_INSTITUTION_TYPE.getMessage()));
			savePoints(ActivityActionEnum.CHANGE_INSTITUTION_TYPE, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addTask(AddTaskToProfileRequest taskToProfileRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setUser(user);
			}
			Task task = this.specialtyDAO.getTaskById(taskToProfileRequest.getTaskId());
			userProfile.getTasks().add(task);
			this.userDAO.save(userProfile);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.ADD_TASK_TO_PROFILE.getMessage()));
			savePoints(ActivityActionEnum.ADD_TASK_TO_PROFILE, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public boolean setTasks(SetTasksToProfileRequest tasksToProfileRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setUser(user);
			}
			userProfile.getTasks().clear();
			for (int id : tasksToProfileRequest.getTaskId()) {
				Task task = this.specialtyDAO.getTaskById(id);
				userProfile.getTasks().add(task);
			}
			this.userDAO.save(userProfile);
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.ADD_TASK_TO_PROFILE.getMessage()));
			savePoints(ActivityActionEnum.ADD_TASK_TO_PROFILE, user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean removeTask(AddTaskToProfileRequest taskToProfileRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setUser(user);
			}
			Task toRemove = null;
			for (Task task2 : userProfile.getTasks()) {
				if (task2.getId() == taskToProfileRequest.getTaskId()) {
					toRemove = task2;
				}
			}
			if (toRemove != null) {
				userProfile.getTasks().remove(toRemove);
				this.userDAO.save(userProfile);
				activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.REMOVE_TASK_FROM_PROFILE.getMessage()));
				savePoints(ActivityActionEnum.REMOVE_TASK_FROM_PROFILE, user);
			}
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void setProfileA(SetProfileARequest setProfileARequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			user.setFirstname(setProfileARequest.getFirstname());
			user.setLastname(setProfileARequest.getLastname());
			user.setEmail(setProfileARequest.getEmail());
			user.setCompanyScreenName(setProfileARequest.getCompanyScreenName());
			user.setCompanyScreenDescription(setProfileARequest.getCompanyScreenDescription());
			this.userDAO.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void setProfileB(SetProfileBRequest setProfileBRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			user.setFirstname(setProfileBRequest.getFirstname());
			user.setLastname(setProfileBRequest.getLastname());
			user.setEmail(setProfileBRequest.getEmail());
			user.setUserb(true);
			this.userDAO.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ProfileResponseDTO profile() throws ServiceException {
		ProfileResponseDTO result = new ProfileResponseDTO();
		try {
			User user = getLoggedUser();
			result.setLicense(user.getLicense());
			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile != null) {
				result.setInstitutionType(userProfile.getInstitutionType());
				result.setTasks(SpecialtyServiceImpl.toDtoTask(userProfile.getTasks()));
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}

	@Override
	public void updateLastLoginDate() throws ServiceException {
		try {
			User user = getLoggedUser();
			user.setLastLoginDate(new Date());
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.LOGIN.getMessage()));
			savePoints(ActivityActionEnum.LOGIN, user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean validateEmail(String email, String hash) throws ServiceException {
		try {
			User user = this.userDAO.getUserByEmail(email);
			if (user == null) {
				return false;
			}
			if (user.getEmailHash().equals(hash)) {
				user.setEmailValidated(true);
				this.userDAO.save(user);
				return true;
			} else {
				return false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean createJobOffer(CreateTemporaryJobOfferRequest createOfferRequest, User user, int offerId) throws ServiceException {
		try {

			User finalUser = new User();

			if(user != null) {
				finalUser = user;
			} else {
				finalUser = getLoggedUser();
			}


			JobOffer jobOffer = null;

			if(offerId == -1) {
				jobOffer = new JobOffer();
			} else {
				jobOffer = jobDAO.getById(JobOffer.class, offerId);
			}

			jobOffer.setOfferent(finalUser);
			jobOffer.getOfferent().setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setCreationDate(new Date());
			jobOffer.setGeoLevelLevel(createOfferRequest.getGeoLevelLevel());
			jobOffer.setGeoLevelId(createOfferRequest.getGeoLevelId());
			jobOffer.setOccupation(specialtyDAO.getOccupationById(createOfferRequest.getOccupationId()));
			jobOffer.setSpecialty(specialtyDAO.getSpecialtyById(createOfferRequest.getSpecialtyId()));
			jobOffer.setTask(specialtyDAO.getTaskById(createOfferRequest.getTaskId()));
			// TODO
			// xxx nuevos campos
			jobOffer.setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setInstitutionType(createOfferRequest.getInstitutionType());
			jobOffer.setOfferDate(getDate(createOfferRequest.getOfferDate() + " " + createOfferRequest.getOfferHour(),
					"yyyyMMdd HHmm"));
			jobOffer.setHour(createOfferRequest.getOfferHour());
			jobOffer.setPermanent(false);
			jobOffer.setComment(createOfferRequest.getComment());
			// jobOffer.setTasks(createOfferRequest.getTasks());
			jobOffer.setVacants(createOfferRequest.getVacants());
			jobOffer.setStatus(JobOffer.VACANT);
			this.jobDAO.save(jobOffer);

			if(jobOffer.isPermanent()){
				activityLogDAO.save(new ActivityLog(finalUser, ActivityActionEnum.POST_PERMANENT_OFFER.getMessage()));
				savePoints(ActivityActionEnum.POST_PERMANENT_OFFER, finalUser);
			} else {
				activityLogDAO.save(new ActivityLog(finalUser, ActivityActionEnum.POST_TEMPORARY_OFFER.getMessage()));
				savePoints(ActivityActionEnum.POST_TEMPORARY_OFFER, finalUser);
			}


			this.notifyToMatchedUsers(jobOffer.getId());
			this.notifyToSemiMatchedUsers(jobOffer.getId());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}



	@Override
	public boolean editJobOffer(CreateTemporaryJobOfferRequest createOfferRequest, long offerId) throws ServiceException {
		try {

			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);
			jobOffer.setOfferent(getLoggedUser());
			jobOffer.getOfferent().setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setCreationDate(new Date());
			jobOffer.setGeoLevelLevel(createOfferRequest.getGeoLevelLevel());
			jobOffer.setGeoLevelId(createOfferRequest.getGeoLevelId());
			jobOffer.setOccupation(specialtyDAO.getOccupationById(createOfferRequest.getOccupationId()));
			jobOffer.setSpecialty(specialtyDAO.getSpecialtyById(createOfferRequest.getSpecialtyId()));
			jobOffer.setTask(specialtyDAO.getTaskById(createOfferRequest.getTaskId()));
			jobOffer.setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setInstitutionType(createOfferRequest.getInstitutionType());
			jobOffer.setOfferDate(getDate(createOfferRequest.getOfferDate() + " " + createOfferRequest.getOfferHour(),
					"yyyyMMdd HHmm"));
			jobOffer.setHour(createOfferRequest.getOfferHour());
			jobOffer.setPermanent(false);
			jobOffer.setComment(createOfferRequest.getComment());
			// jobOffer.setTasks(createOfferRequest.getTasks());
			jobOffer.setVacants(createOfferRequest.getVacants());
			jobOffer.setStatus(JobOffer.VACANT);
			this.jobDAO.save(jobOffer);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityActionEnum.POST_TEMPORARY_OFFER.getMessage()));
			savePoints(ActivityActionEnum.POST_TEMPORARY_OFFER, getLoggedUser());

			this.notifyToMatchedUsers(jobOffer.getId());
			this.notifyToSemiMatchedUsers(jobOffer.getId());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean createJobOffer(CreatePermanentJobOfferRequest createOfferRequest, User user, int offerId) throws ServiceException {
		try {

			User finalUser = new User();

			if(user != null) {
				finalUser = user;
			} else {
				finalUser = getLoggedUser();
			}


			JobOffer jobOffer = null;

			if(offerId == -1) {
				jobOffer = new JobOffer();
			} else {
				jobOffer = jobDAO.getById(JobOffer.class, offerId);
			}


			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			createOfferRequest.setOfferDate(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
			createOfferRequest.setOfferHour("0000");

			jobOffer.setOfferent(finalUser);
			jobOffer.getOfferent().setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setCreationDate(new Date());
			jobOffer.setGeoLevelLevel(createOfferRequest.getGeoLevelLevel());
			jobOffer.setGeoLevelId(createOfferRequest.getGeoLevelId());
			jobOffer.setOccupation(specialtyDAO.getOccupationById(createOfferRequest.getOccupationId()));
			jobOffer.setSpecialty(specialtyDAO.getSpecialtyById(createOfferRequest.getSpecialtyId()));
			jobOffer.setTask(specialtyDAO.getTaskById(createOfferRequest.getTaskId()));
			// TODO
			// xxx nuevos campos
			jobOffer.setTitle(createOfferRequest.getTitle());
			jobOffer.setSubtitle(createOfferRequest.getSubtitle());
			jobOffer.setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setInstitutionType(createOfferRequest.getInstitutionType());
			jobOffer.setOfferDate(getDate(createOfferRequest.getOfferDate(), "yyyyMMdd"));
			jobOffer.setHour(createOfferRequest.getOfferHour());
			jobOffer.setPermanent(true);
			jobOffer.setComment(createOfferRequest.getComment());
			// jobOffer.setTasks(createOfferRequest.getTasks());
			jobOffer.setVacants(createOfferRequest.getVacants());
			jobOffer.setStatus(JobOffer.VACANT);
			this.jobDAO.save(jobOffer);
			activityLogDAO.save(new ActivityLog(finalUser, ActivityActionEnum.POST_PERMANENT_OFFER.getMessage()));
			savePoints(ActivityActionEnum.POST_PERMANENT_OFFER, finalUser);

			this.notifyToMatchedUsers(jobOffer.getId());
			this.notifyToSemiMatchedUsers(jobOffer.getId());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public boolean editJobOffer(CreatePermanentJobOfferRequest createOfferRequest, long offerId) throws ServiceException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			createOfferRequest.setOfferDate(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
			createOfferRequest.setOfferHour("0000");
			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);
			jobOffer.setOfferent(getLoggedUser());
			jobOffer.getOfferent().setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setCreationDate(new Date());
			jobOffer.setGeoLevelLevel(createOfferRequest.getGeoLevelLevel());
			jobOffer.setGeoLevelId(createOfferRequest.getGeoLevelId());
			jobOffer.setOccupation(specialtyDAO.getOccupationById(createOfferRequest.getOccupationId()));
			jobOffer.setSpecialty(specialtyDAO.getSpecialtyById(createOfferRequest.getSpecialtyId()));
			jobOffer.setTask(specialtyDAO.getTaskById(createOfferRequest.getTaskId()));
			// TODO
			// xxx nuevos campos
			jobOffer.setTitle(createOfferRequest.getTitle());
			jobOffer.setSubtitle(createOfferRequest.getSubtitle());
			jobOffer.setCompanyScreenName(createOfferRequest.getCompanyScreenName());
			jobOffer.setInstitutionType(createOfferRequest.getInstitutionType());
			jobOffer.setOfferDate(getDate(createOfferRequest.getOfferDate(), "yyyyMMdd"));
			jobOffer.setHour(createOfferRequest.getOfferHour());
			jobOffer.setPermanent(true);
			jobOffer.setComment(createOfferRequest.getComment());
			// jobOffer.setTasks(createOfferRequest.getTasks());
			jobOffer.setVacants(createOfferRequest.getVacants());
			jobOffer.setStatus(JobOffer.VACANT);
			this.jobDAO.save(jobOffer);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityActionEnum.POST_PERMANENT_OFFER.getMessage()));
			savePoints(ActivityActionEnum.POST_PERMANENT_OFFER, getLoggedUser());

			this.notifyToMatchedUsers(jobOffer.getId());
			this.notifyToSemiMatchedUsers(jobOffer.getId());
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public User getLoggedUser() throws ServiceException {
		try {
			return userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean apply(long offerId, ApplyToOfferRequest applyToOffer) throws ServiceException {
		try {

			User user = getLoggedUser();

			if(user.getSpecialties().isEmpty()) {
				return false;
			}

			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);
			if (jobOffer.getVacants() == 0) {
				return false;
			}
			if (JobOffer.CLOSED.equals(jobOffer.getStatus())) {
				return false;
			}
			if (jobOffer.isExpired()) {
				return false;
			}

			JobApplication jobApplication = new JobApplication();
			jobApplication.setComment(applyToOffer.getComment());
			jobApplication.setCvAttach(Base64.decodeBase64(applyToOffer.getCvPdf())); //TODO debería salir del profile
			jobApplication.setCvPlain(applyToOffer.getCvPlain()); //TODO debería salir del profile

			UserLinkedinProfile linkedinProfile = this.userDAO.getUserLinkedinProfile(user);
			if(linkedinProfile != null) {
				jobApplication.setLinkedInCv(linkedinProfile.getPublicProfileURL());
			}
			jobApplication.setOffer(jobOffer);
			jobApplication.setUser(user);
			this.jobApplicationDAO.save(jobApplication);
			jobOffer.setApplications(jobOffer.getApplications() + 1);
			this.jobDAO.save(jobOffer);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityActionEnum.APPLY_TO_OFFER.getMessage()));
			savePoints(ActivityActionEnum.APPLY_TO_OFFER, getLoggedUser());

			sendNotification(NotificationType.NEW_APPLICATION, jobOffer.getOfferent(), jobOffer);

			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void offerApplicationCV(long offerId, long applicationId, ServletOutputStream outputStream)
			throws ServiceException {
		try {
			JobApplication application = this.jobApplicationDAO.getById(JobApplication.class, applicationId);
			outputStream.write(application.getCvAttach());
		} catch (DAOException | IOException e) {
			throw new ServiceException(e);
		}
	}

	private long addOccupation(String occupationName) throws ServiceException {
		try {
			Occupation s = new Occupation();
			s.setName(occupationName);
			this.specialtyDAO.save(s);
			return s.getId();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private long addSpecialty(long occupationId, String specialtyName) throws ServiceException {
		try {
			Occupation s = this.specialtyDAO.getOccupationById(occupationId);
			Specialty specialty = new Specialty();
			specialty.setOccupation(s);
			specialty.setName(specialtyName);
			this.specialtyDAO.save(specialty);
			return specialty.getId();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private long addTask(long specialtyId, String taskName) throws ServiceException {
		try {
			Specialty s = this.specialtyDAO.getSpecialtyById(specialtyId);
			Task task = new Task();
			task.setSpecialty(s);
			task.setName(taskName);
			this.specialtyDAO.save(task);
			return task.getId();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private Date getDate(String offerDate, String string) throws ParseException {
		return new SimpleDateFormat(string).parse(offerDate);
	}

	@Override
	public List<JobOfferStatusDTO> getMyOffers() throws ServiceException {
		long id = RuntimeContext.getCurrentUser().getId();
		return this.getMyOffers(id);
	}

	@Override
	public List<JobOfferStatusDTO> getPermanentOffersOpen() throws ServiceException {
		return this.getAllPermanentOffersOpen();
	}

	@Override
	public List<JobOfferStatusDTO> getMyOffersClosed() throws ServiceException {
		try {
			List<JobOffer> offers = this.jobDAO.getClosedOffers(RuntimeContext.getCurrentUser().getId());
			return offers.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public MatchesSummaryDTO getMatchedOffersSummary() throws ServiceException {
		MatchesSummaryDTO result = new MatchesSummaryDTO();
		result.setPermament(getMatchedPermamentOffers().size());
		result.setTemporal(getMatchedTemporalOffers().size());
		return result;
	}

	@Override
	public List<JobOfferStatusDTO> getMatchedTemporalOffers() throws ServiceException {
		return getMatchedOffers(false);
	}

	@Override
	public List<JobOfferStatusDTO> getMatchedPermamentOffers() throws ServiceException {
		return getMatchedOffers(true);
	}

	@Override
	public List<JobOfferStatusDTO> getPermamentOffers(SearchOfferRequest searchOfferRequest) throws ServiceException {
		try {
			List<JobOffer> result = new ArrayList<>();
			SearchOfferDTO searchOfferDTO = new SearchOfferDTO();

			if(!Long.valueOf(0).equals(searchOfferRequest.getGeoLevelId())) {
				UserGeoLocation geoLocation = new UserGeoLocation();
				geoLocation.setGeoLevelId(searchOfferRequest.getGeoLevelId());
				geoLocation.setGeoLevelLevel(searchOfferRequest.getGeoLevelLevel());

				Set<UserGeoLocation> geoLocations = new HashSet<UserGeoLocation>();
				geoLocations.add(geoLocation);

				List<GeoLevelDTO> geos = this.getGeoLevels(geoLocations);

				searchOfferDTO.setGeos(geos);
			}			
			if(!Long.valueOf(0).equals(searchOfferRequest.getSpecialtyId())) {
				searchOfferDTO.getSpecialities().add(searchOfferRequest.getSpecialtyId());
			}
			if(!Long.valueOf(0).equals(searchOfferRequest.getOccupationId())) {
				searchOfferDTO.getOccupations().add(searchOfferRequest.getOccupationId());
			}
			if(!Long.valueOf(0).equals(searchOfferRequest.getTaskId())) {
				searchOfferDTO.getTasks().add(searchOfferRequest.getTaskId());
			}
			searchOfferDTO.setInstitutionType(searchOfferRequest.getInstitutionType());

			searchOfferDTO.setPermanent(true);

			if(com.tdil.d2d.security.RuntimeContext.getCurrentUser() != null) {
				searchOfferDTO.setOfferentIdToIgnore(com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
			}

			result = (List<JobOffer>) this.jobDAO.getOffersBy(searchOfferDTO);

			return result.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private List<JobOfferStatusDTO> getMatchedOffers(boolean permament) throws ServiceException {
		try {
			List<JobOffer> result = new ArrayList<>();
			User user = getLoggedUser();

			List<GeoLevelDTO> geos = this.getGeoLevels(user.getUserGeoLocations());

			SearchOfferDTO searchOfferDTO = new SearchOfferDTO();
			searchOfferDTO.setGeos(geos);

			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile != null) {
				searchOfferDTO.setInstitutionType(userProfile.getInstitutionType());
				searchOfferDTO.setTasks(getTasks(userProfile.getTasks()));
			}

			searchOfferDTO.setPermanent(permament);

			if(com.tdil.d2d.security.RuntimeContext.getCurrentUser() != null) {
				searchOfferDTO.setOfferentIdToIgnore(com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
			}

			result = (List<JobOffer>) this.jobDAO.getOffersBy(searchOfferDTO);

			return result.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private List<GeoLevelDTO> getGeoLevels(Set<UserGeoLocation> userGeoLocations) throws DAOException {
		List<GeoLevelDTO> geos = new ArrayList<GeoLevelDTO>();
		GeoLevelDTO geoDto;

		for (UserGeoLocation location : userGeoLocations) {
			geoDto = new GeoLevelDTO(location.getGeoLevelId(), location.getGeoLevelLevel());
			geos.add(geoDto);
			GeoLevel geoLevel = this.geoDAO.getGeoByIdAndLevel(location.getGeoLevelId(), location.getGeoLevelLevel());
			if (location.getGeoLevelLevel() == 4) {
				Geo4 geo4 = (Geo4) geoLevel;
				geos.add(new GeoLevelDTO(geo4.getGeo3().getId(), 3));
				geos.add(new GeoLevelDTO(geo4.getGeo3().getGeo2().getId(), 2));
			}
			if (location.getGeoLevelLevel() == 3) {
				Geo3 geo3 = (Geo3) geoLevel;
				geos.add(new GeoLevelDTO(geo3.getGeo2().getId(), 2));

				List<Geo4> geos4 = this.geoDAO.getListGeo4ByGeo3(location.getGeoLevelId());
				for (Geo4 geo4 : geos4) {
					geos.add(new GeoLevelDTO(geo4.getId(), 4));
				}
			}
			if (location.getGeoLevelLevel() == 2) {
				List<Geo4> geos4 = this.geoDAO.getListGeo4ByGeo2(location.getGeoLevelId());
				for (Geo4 geo4 : geos4) {
					geos.add(new GeoLevelDTO(geo4.getId(), 4));
					geos.add(new GeoLevelDTO(geo4.getGeo3().getId(), 3));
				}
			}
		}

		return geos;
	}

	private List<Long> getTasks(Set<Task> taskObjects) throws DAOException {
		List<Long> tasks = new ArrayList<Long>();

		for (Task taskObject : taskObjects) {
			tasks.add(taskObject.getId());
		}

		return tasks;
	}

	@Override
	public List<JobApplicationDTO> offerApplications(long offerId) throws ServiceException {
		try {
			JobOffer offer = this.jobDAO.getById(JobOffer.class, offerId);
			if (offer.getOfferent().getId() != RuntimeContext.getCurrentUser().getId()) {
				return null;
			}
			List<JobApplication> applications = this.jobApplicationDAO.getJobApplications(offerId);
			return applications.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public JobApplicationDTO offerApplication(long applicationId) throws ServiceException {
		try {
			JobApplication application = this.jobApplicationDAO.getById(JobApplication.class, applicationId);
			if (application.getOffer().getOfferent().getId() != RuntimeContext.getCurrentUser().getId()) {
				return null;
			}
			return toDTO(application);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean accept(long offerId, long applicationId) throws ServiceException {
		try {
			JobOffer offer = this.jobDAO.getById(JobOffer.class, offerId);
			if (offer.getVacants() == 0) {
				return false;
			}
			if (JobOffer.CLOSED.equals(offer.getStatus())) {
				return false;
			}
			if (offer.isExpired()) {
				return false;
			}
			if (offer.getOfferent().getId() != RuntimeContext.getCurrentUser().getId()) {
				return false;
			}
			JobApplication application = this.jobApplicationDAO.getById(JobApplication.class, applicationId);
			application.setStatus(JobApplication.ACEPTED);
			offer.setVacants(offer.getVacants() - 1);
			if (offer.getVacants() == 0) {
				offer.setStatus(JobOffer.CLOSED);
			}
			offer.setJobApplication_id((int) (long) application.getId());

			this.jobDAO.save(offer);
			this.jobApplicationDAO.save(application);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityActionEnum.ACCEPT_OFFER.getMessage()));
			savePoints(ActivityActionEnum.ACCEPT_OFFER, getLoggedUser());

			sendNotification(NotificationType.APPLICATION_ACCEPTED, application.getUser(), offer);

			if(JobApplication.CLOSED.equals(offer.getStatus())){

				//Notify rejected applications
				List<JobApplication> applications = this.jobApplicationDAO.getJobApplications(offerId);
				for(JobApplication rejectedApplication : applications){
					if(!JobApplication.ACEPTED.equals(rejectedApplication.getStatus()) && rejectedApplication.getId() != applicationId){
						sendNotification(NotificationType.JOB_OFFER_CLOSE, rejectedApplication.getUser(), offer);
					}
				}

			}

			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean reject(long offerId, long applicationId) throws ServiceException {
		try {
			JobOffer offer = this.jobDAO.getById(JobOffer.class, offerId);
			if (JobOffer.CLOSED.equals(offer.getStatus())) {
				return false;
			}
			if (offer.isExpired()) {
				return false;
			}
			if (offer.getOfferent().getId() != RuntimeContext.getCurrentUser().getId()) {
				return false;
			}
			JobApplication application = this.jobApplicationDAO.getById(JobApplication.class, applicationId);
			application.setStatus(JobApplication.REJECTED);
			this.jobApplicationDAO.save(application);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityActionEnum.REJECT_OFFER.getMessage()));
			savePoints(ActivityActionEnum.REJECT_OFFER, getLoggedUser());
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean close(long offerId) throws ServiceException {
		try {
			JobOffer offer = this.jobDAO.getById(JobOffer.class, offerId);
			if (JobOffer.CLOSED.equals(offer.getStatus())) {
				return false;
			}
			if (offer.isExpired()) {
				return false;
			}
			if (offer.getOfferent().getId() != RuntimeContext.getCurrentUser().getId()) {
				return false;
			}
			offer.setStatus(JobOffer.CLOSED);
			this.jobDAO.save(offer);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityActionEnum.CLOSED_OFFER.getMessage()));
			savePoints(ActivityActionEnum.CLOSED_OFFER, getLoggedUser());

			if(JobApplication.CLOSED.equals(offer.getStatus())){

				//Notify rejected applications
				List<JobApplication> applications = this.jobApplicationDAO.getJobApplications(offerId);
				for(JobApplication rejectedApplication : applications){
					if(!JobApplication.ACEPTED.equals(rejectedApplication.getStatus())){
						sendNotification(NotificationType.JOB_OFFER_CLOSE, rejectedApplication.getUser(), offer);
					}
				}

			}

			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public NotificationConfigurationResponse getNotificationConfiguration() throws ServiceException {
		try {
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO
					.getByUser(RuntimeContext.getCurrentUser().getId());
			return toResponse(notificationConfiguration);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public NotificationConfigurationResponse getNotificationConfiguration(Long userId) throws ServiceException {
		try {
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO
					.getByUser(userId);
			return toResponse(notificationConfiguration);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private NotificationConfigurationResponse toResponse(NotificationConfiguration notificationConfiguration) {
		NotificationConfigurationResponse response = new NotificationConfigurationResponse(200);
		if (notificationConfiguration != null) {
			response.setCongress(notificationConfiguration.isCongress());
			response.setCourses(notificationConfiguration.isCourses());
			// response.setCreationDate(notificationConfiguration.getCreationDate());
			response.setGrantOffers(notificationConfiguration.isGrantOffers());
			// response.setId(notificationConfiguration.getId());
			response.setNotes(notificationConfiguration.isNotes());
			response.setNotif9to20(notificationConfiguration.isNotif9to20());
			response.setNotifAllDay(notificationConfiguration.isNotifAllDay());
			response.setProductAndServices(notificationConfiguration.isProductAndServices());
			response.setPromotionsOffers(notificationConfiguration.isPromotionsOffers());
			response.setPush(notificationConfiguration.isPush());
			response.setStatus(200);
		}
		return response;
	}

	@Override
	public boolean setNotificationConfiguration(ConfigureNotificationsRequest configureNotificationsRequest)
			throws ServiceException {
		try {
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO
					.getByUser(RuntimeContext.getCurrentUser().getId());
			if (notificationConfiguration == null) {
				notificationConfiguration = new NotificationConfiguration();
				notificationConfiguration.setUser(getLoggedUser());
			}
			notificationConfiguration.setCongress(configureNotificationsRequest.isCongress());
			notificationConfiguration.setCourses(configureNotificationsRequest.isCourses());
			// response.setCreationDate(configureNotificationsRequest.getCreationDate());
			notificationConfiguration.setGrantOffers(configureNotificationsRequest.isGrantOffers());
			// response.setId(notificationConfiguration.getId());
			notificationConfiguration.setNotes(configureNotificationsRequest.isNotes());
			notificationConfiguration.setNotif9to20(configureNotificationsRequest.isNotif9to20());
			notificationConfiguration.setNotifAllDay(configureNotificationsRequest.isNotifAllDay());
			notificationConfiguration.setProductAndServices(configureNotificationsRequest.isProductAndServices());
			notificationConfiguration.setPromotionsOffers(configureNotificationsRequest.isPromotionsOffers());
			notificationConfiguration.setPush(configureNotificationsRequest.isPush());
			this.notificationConfigurationDAO.save(notificationConfiguration);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean sendTestNotificationAndroid() throws ServiceException {

		try {
			androidNotificationService.sendNotification(NotificationType.NEW_APPLICATION, "PUT DEVICE TOKEN HERE");

			return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean sendTestNotificationIOS() throws ServiceException {

		try {
			iosNotificationService.sendNotification(NotificationType.NEW_APPLICATION, "PUT DEVICE TOKEN HERE");
			return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private JobApplicationDTO toDTO(JobApplication s) {
		JobApplicationDTO result = new JobApplicationDTO();

		// Application ID
		result.setId(s.getId());
		// User ID
		result.setUserId(s.getUser().getId());
		// Creation Date
		result.setCreationDate(s.getCreationDate() != null ? s.getCreationDate().toString() : "");
		// Base64Image
		if(s.getUser().getAvatar() != null) {
			result.setBase64Image(new String(s.getUser().getAvatar().getData()));
		}
		// Linkedin CV
		result.setLinkedinInCv(s.getLinkedInCv());
		// Falta cvAttach
		if(s.getUser().getPdfCV() != null) {
			result.setCvAttach(new String(Base64.encodeBase64(s.getUser().getPdfCV().getData())));
		}
		// cvPlain
		result.setCvPlain(s.getUser().getCV());
		// FirstName
		result.setFirstname(s.getUser().getFirstname());
		// LastName
		result.setLastname(s.getUser().getLastname());
		// numberPhone
		result.setMobilePhone(s.getUser().getMobilePhone());
		// Occupation
		//		result.setOccupationName(s.getOffer().getOccupation().getName());

		Iterator iter = s.getUser().getSpecialties().iterator();
		Specialty first = (Specialty) iter.next();
		result.setOccupationName(first.getOccupation().getName());
		// Specialty
		result.setSpecialties(s.getUser().getSpecialties());

		//geolevel
		//		result.setGeoLevelId(s.getOffer().getGeoLevelId());
		//		result.setGeoLevelLevel(s.getOffer().getGeoLevelLevel());
		//		result.setUserGeoLocations(s.getUser().getUserGeoLocations());
		result.setGeoLevels(toDtoGeoLevel(s.getUser().getUserGeoLocations()));

		//		GeoLevel geoLevel;
		//		try {
		//			geoLevel = this.geoDAO.getGeoByIdAndLevel(s.getOffer().getGeoLevelId(), s.getOffer().getGeoLevelLevel());
		//			result.setGeoLevelName(geoLevel.getName());
		//		} catch (DAOException e) {
		//			throw new RuntimeException(e);
		//		}

		result.setComment(s.getComment());
		return result;
	}

	private JobOfferStatusDTO toDTO(JobOffer s) {
		JobOfferStatusDTO result = new JobOfferStatusDTO();

		result.setId(s.getId());
		result.setComment(s.getComment());
		result.setCompanyScreenName(s.getCompanyScreenName());
		result.setCreationDate(s.getCreationDate().toString());
		result.setGeoLevelId(s.getGeoLevelId());
		result.setGeoLevelLevel(s.getGeoLevelLevel());
		GeoLevel geoLevel;
		try {
			geoLevel = this.geoDAO.getGeoByIdAndLevel(s.getGeoLevelId(), s.getGeoLevelLevel());
			result.setGeoLevelName(geoLevel.getName());
		} catch (DAOException e) {
			throw new RuntimeException(e);
		}
		result.setOfferHour(s.getHour());
		result.setInstitutionType(s.getInstitutionType().toString());
		result.setOfferDate(s.getOfferDate().toString());
		result.setPermanent(s.isPermanent());
		result.setStatus(s.getStatus());
		result.setSubTitle(s.getSubtitle());
		result.setTitle(s.getTitle());
		result.setVacants(s.getVacants());
		result.setOccupation_id(s.getOccupation().getId());
		result.setOccupationName(s.getOccupation().getName());
		result.setOfferent_id(s.getOfferent().getId());
		result.setSpecialty_Id(s.getSpecialty().getId());
		result.setSpecialtyName(s.getSpecialty().getName());
		result.setTask_id(s.getTask().getId());
		result.setTaskName(s.getTask().getName());
		result.setApplications(s.getApplications());
		if(s.getOfferent().getAvatar()!=null)
			//			result.setBase64img(new String(s.getOfferent().getAvatar().getData()));
			result.setBase64img(new String(""));
		result.setJobApplication_id(s.getJobApplication_id());
		result.setOfferentFirstname(s.getOfferent().getFirstname());
		result.setOfferentLastname(s.getOfferent().getLastname());
		return result;
	}

	@Override
	public UserDetailsResponse me() throws ServiceException {
		User user = getLoggedUser();
		return getUserDetailsResponse(user);
	}

	@Override
	public UserDetailsResponse getUser(long id) throws ServiceException {
		User user = null;
		try {
			user = this.userDAO.getById(User.class, id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return getUserDetailsResponse(user);
	}


	@Override
	@Transactional
	public User getUserById(long id) throws ServiceException {
		User user = null;
		try {
			user = this.userDAO.getById(User.class, id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return user;
	}


	@Override
	public UserDTO getUserWebDetails(long id) throws ServiceException {
		User user = null;
		try {
			user = this.userDAO.getById(User.class, id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return toDtoWebDetails(user);
	}


	private UserDetailsResponse getUserDetailsResponse(User user) throws ServiceException {
		UserDetailsResponse resp = new UserDetailsResponse(HttpStatus.OK.value());
		resp.setUserId(String.valueOf(user.getId()));
		resp.setFirstname(user.getFirstname());
		resp.setLastname(user.getLastname());
		resp.setMobileNumber(user.getMobilePhone());
		resp.setEmail(user.getEmail());
		resp.setCompanyScreenName(user.getCompanyScreenName());
		resp.setLicence(user.getLicense());
		try {
			UserProfile userProfile = this.userDAO.getUserProfile(user);
			if (userProfile != null) {
				resp.setInstitutionType(userProfile.getInstitutionType().toString());
				resp.setTasks(SpecialtyServiceImpl.toDtoTask(userProfile.getTasks()));
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		resp.setGeoLevels(toDtoGeoLevel(user.getUserGeoLocations()));

		if (!user.getSpecialties().isEmpty()) {
			user.getSpecialties().stream().findFirst().ifPresent(new Consumer<Specialty>() {
				@Override
				public void accept(Specialty t) {
					OccupationDTO o = new OccupationDTO();
					o.setId(t.getOccupation().getId());
					o.setName(t.getOccupation().getName());
					resp.setOccupation(o);
				}
			});
			resp.setSpecialities(SpecialtyServiceImpl.toDtoSpecialty(user.getSpecialties()));
		}

		if (user.getAvatar() != null) {
			resp.setBase64img(new String(user.getAvatar().getData()));
		}
		resp.setUserb(user.isUserb());
		if (resp != null) {
			Subscription subscription = subscriptionService.getActiveSubscription(user.getId());
			if (subscription != null) {
				if (subscription.getSponsorCode() != null && subscription.getSponsorCode().getSponsor() != null) {
					Sponsor sponsor = subscription.getSponsorCode().getSponsor();
					resp.setSponsorName(sponsor.getName());
					if(sponsor.getBase64img()!=null)
						resp.setSponsorImage(new String(sponsor.getBase64img()));
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				resp.setSubscriptionExpirationDate(sdf.format(subscription.getExpirationDate()));
				resp.setHasSubscription(true);
			}
		}

		NotificationConfigurationResponse notificationConfigurationResponse = this.getNotificationConfiguration();
		resp.setNotificationConfigurationResponse(notificationConfigurationResponse);

		try {
			UserLinkedinProfile userLinkedinProfile = this.userDAO.getUserLinkedinProfile(user);
			resp.setHasLinkedinProfile(userLinkedinProfile != null);
			if(resp.isHasLinkedinProfile()) {
				resp.setLinkedinProfileUrl(userLinkedinProfile.getPublicProfileURL());
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

		resp.setCV(user.getCV());
		resp.setAlreadyUsedFreeSuscription(user.isAlreadyUsedFreeSuscription());

		return resp;
	}

	private static Collection<GeoLevelDTO> toDtoGeoLevel(Collection<UserGeoLocation> list) {
		return list.stream().map(s -> toDto(s)).collect(Collectors.toList());
	}

	private static GeoLevelDTO toDto(UserGeoLocation s) {
		GeoLevelDTO result = new GeoLevelDTO();
		result.setId(s.getGeoLevelId());
		result.setLevel(s.getGeoLevelLevel());
		result.setName(s.getGeoLevelName());
		return result;
	}

	@Override
	public List<ActivityLogDTO> getActivityLog() throws ServiceException {
		try {
			List<ActivityLog> creditCards = this.activityLogDAO.getLastLog(RuntimeContext.getCurrentUser().getId());
			return creditCards.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ActivityLogDTO> getWebActivityLog(Long userId) throws ServiceException {
		try {
			List<ActivityLog> logs = this.activityLogDAO.getLastLog(userId);
			return logs.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private ActivityLogDTO toDTO(ActivityLog s) {
		ActivityLogDTO r = new ActivityLogDTO();
		r.setId(s.getId());
		r.setCreationDate(s.getCreationDate().toString());
		r.setLog(s.getLog());
		return r;
	}

	@Override
	public void initDbWithTestData() throws ServiceException {
		long l = this.addOccupation("Medico");
		l = this.addSpecialty(l, "Alergista");
		l = this.addTask(l, "reemplazo de consultorio");
	}

	@Override
	public List<JobOfferStatusDTO> getMyOffers(long userId) throws ServiceException {
		try {
			List<JobOffer> offers = this.jobDAO.getOpenOffers(userId);
			return offers.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<JobOfferStatusDTO> getAllPermanentOffersOpen() throws ServiceException {
		try {
			SearchOfferDTO searchOfferDTO = new SearchOfferDTO();
			searchOfferDTO.setPermanent(true);
			searchOfferDTO.setInstitutionType(InstitutionType.BOTH);

			if(com.tdil.d2d.security.RuntimeContext.getCurrentUser() != null) {
				searchOfferDTO.setOfferentIdToIgnore(com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
			}

			Collection<JobOffer> offers = this.jobDAO.getOffersBy(searchOfferDTO);
			return offers.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<JobOfferStatusDTO> getAllTemporalOffersOpen() throws ServiceException {
		try {
			SearchOfferDTO searchOfferDTO = new SearchOfferDTO();
			searchOfferDTO.setPermanent(false);
			searchOfferDTO.setInstitutionType(InstitutionType.BOTH);

			if(com.tdil.d2d.security.RuntimeContext.getCurrentUser() != null) {
				searchOfferDTO.setOfferentIdToIgnore(com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
			}
			Collection<JobOffer> offers = this.jobDAO.getOffersBy(searchOfferDTO);
			return offers.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean setAvatar(User user, SetAvatarRequest setAvatarRequest) throws ServiceException {
		try {

			Media media = userDAO.getMediaBy(user.getId(), MediaType.AVATAR);
			if(media == null) {
				media = new Media();
				media.setType(MediaType.AVATAR);
			}

			media.setData(setAvatarRequest.getAvatarBase64().getBytes());
			user.setAvatar(media);
			this.userDAO.save(media);
			this.userDAO.save(user);

			activityLogDAO.save(new ActivityLog(user, ActivityActionEnum.SET_AVATAR.getMessage()));
			//            savePoints(ActivityActionEnum.SET_AVATAR, getLoggedUser());

			return true;			

		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void updateUserLinkedinProfile(UserLinkedinProfileRequest userLinkedinProfileRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			UserLinkedinProfile linkedinProfile = this.userDAO.getUserLinkedinProfile(user);
			if (linkedinProfile == null) {
				linkedinProfile = new UserLinkedinProfile();
				linkedinProfile.setUser(user);
				linkedinProfile.setCreationDate(new Date());
			}
			linkedinProfile.setAccessToken(userLinkedinProfileRequest.getAccessToken());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, userLinkedinProfileRequest.getExpiresIn());
			linkedinProfile.setExpiresOn(c.getTime());
			linkedinProfile.setFirstName(userLinkedinProfileRequest.getFirstName());
			linkedinProfile.setLastName(userLinkedinProfileRequest.getLastName());
			linkedinProfile.setEmail(userLinkedinProfileRequest.getEmail());
			linkedinProfile.setHeadLine(userLinkedinProfileRequest.getHeadLine());
			linkedinProfile.setIndustry(userLinkedinProfileRequest.getIndustry());
			linkedinProfile.setPublicProfileURL(userLinkedinProfileRequest.getPublicProfileURL());
			linkedinProfile.setPositionsJson(userLinkedinProfileRequest.getPositionsJson());

			this.userDAO.save(linkedinProfile);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void setCV(String cv) throws ServiceException {
		User user = this.getLoggedUser();
		user.setCV(cv);
		try {
			this.userDAO.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public String createMercadoPagoPreference(CreatePreferenceMPRequest createPreferenceMPRequest) throws ServiceException {


		User user = getLoggedUser();
		MP mp = new MP (clientId, secretId);
		JSONObject createPreferenceResult;
		try {
			createPreferenceResult = mp.createPreference("{'items':"
					+ "[{"
					+ "		'title':'" + createPreferenceMPRequest.getItem() + "',"
					+ "		'quantity':1,"
					+ "		'currency_id':'USD',"
					+ "		'unit_price':" + createPreferenceMPRequest.getPrice() + " "
					+ "}],"
					+ "'payer': "
					+ "	{"
					+ "     'name': " + user.getFirstname() + ","
					+ "     'surname': " + user.getLastname() + ","
					+ "	    'email': " + user.getEmail() + ""
					+ "	},"
					+ "'payment_methods': { " //allow only credit cards
					+      "'excluded_payment_types': "
					+ "		[{"
					+          "'id': 'ticket'"
					+ " 	 },"
					+ "      {"
					+          "'id': 'atm'"
					+ " 	 },"
					+ "      {"
					+          "'id': 'debit_card'"
					+ " 	 },"
					+ "      {"
					+          "'id': 'bank_transfer'"
					+         "}]"
					+ "}}");

			return createPreferenceResult.toString();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean createPayment(CreatePaymentRequest createPaymentRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			String idPayment = createPaymentRequest.getIdPaymentMP();
			String item = createPaymentRequest.getItem();
			BigDecimal price = new BigDecimal(createPaymentRequest.getPrice());
			paymentDAO.save(new Payment(idPayment, item, price, user));
			subscriptionService.register(user, createPaymentRequest.getSubscriptionDuration());
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public UserDetailsResponse getApprovedCandidateForOffer(long id) throws ServiceException {
		try {
			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, id);
			if(jobOffer.getJobApplication_id() == null) {
				return null;
			}
			JobApplication jobApplication = this.jobApplicationDAO.getById(JobApplication.class, jobOffer.getJobApplication_id());
			User user = this.userDAO.getById(User.class, jobApplication.getUser().getId());
			return getUserDetailsResponse(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void setPdfCV(Base64Request base64Request) throws ServiceException {
		try {
			User user = getLoggedUser();

			Media media = userDAO.getMediaBy(user.getId(), MediaType.PDF_CV);
			if(media == null) {
				media = new Media();
			}

			media.setType(MediaType.PDF_CV);
			media.setData(Base64.decodeBase64(base64Request.getData()));
			user.setPdfCV(media);
			this.userDAO.save(media);
			this.userDAO.save(user);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Base64DTO getPdfCVBase64() throws ServiceException {
		User user = getLoggedUser();
		Base64DTO base64dto = null;
		if(user != null && user.getPdfCV() != null) {
			base64dto = new Base64DTO(new String(Base64.encodeBase64(user.getPdfCV().getData())));
		}
		return base64dto;
	}


	@Override
	public Base64DTO getPdfCVBase64ById(long id) throws ServiceException {
		//		User user = getLoggedUser();
		User user = null;
		try {
			user = this.userDAO.getById(User.class, id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		Base64DTO base64dto = null;
		if(user != null && user.getPdfCV() != null) {
			base64dto = new Base64DTO(new String(Base64.encodeBase64(user.getPdfCV().getData())));
		}
		return base64dto;
	}

	@Override
	public boolean searchIfApplied(long offerId, long userId) {
		try {
			List<JobApplication> jobApplication = this.jobApplicationDAO.getJobApplicationsByUserAndOffer(offerId, userId);
			if(jobApplication.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean switchToB() {
		try {
			User user = getLoggedUser();
			user.setUserb(true);
			this.userDAO.save(user);
		} catch (DAOException | ServiceException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<MatchedUserDTO> getMatchedUsers(Long offerId) throws ServiceException {
		try {
			List<User> result = new ArrayList<>();

			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);

			UserGeoLocation geoLocation = new UserGeoLocation();
			geoLocation.setGeoLevelId(jobOffer.getGeoLevelId());
			geoLocation.setGeoLevelLevel(jobOffer.getGeoLevelLevel());

			Set<UserGeoLocation> geoLocations = new HashSet<UserGeoLocation>();
			geoLocations.add(geoLocation);

			List<GeoLevelDTO> geos = this.getGeoLevels(geoLocations);

			result = userDAO.getMatchedUsers(jobOffer, geos);

			List<MatchedUserDTO> matchedUserDTOs = new ArrayList<MatchedUserDTO>();
			for (User matchedUser : result) {
				MatchedUserDTO matchedUserDTO = toMatchedUserDto(matchedUser);
				matchedUserDTOs.add(matchedUserDTO);
			}

			return matchedUserDTOs;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


	@Override
	public List<MatchedUserDTO> getMatchedUsersNote(BONoteDTO boNoteDTO, long noteId) throws ServiceException {
		try {
			List<User> result = new ArrayList<>();



			Note note = this.noteDAO.getNoteById(noteId);


			Set<Occupation> occupationToRemove = new HashSet<>();

			//Chequea si fue seleccionada alguna especialidad para medico, en caso afirmativo se quita Medico de la lista de ocupaciones
			if(note.getSpecialties().stream().anyMatch(dto -> dto.getOccupation().getId() == 1)) {
				occupationToRemove.add(note.getOccupations().stream().filter(e -> e.getId() == 1).findFirst().get());
				note.getOccupations().remove(note.getOccupations().stream().filter(e -> e.getId() == 1).findFirst().get());
			}

			//Chequea si fue seleccionada alguna especialidad para odontologo, en caso afirmativo se quita Odontologo de la lista de ocupaciones
			if(note.getSpecialties().stream().anyMatch(dto -> dto.getOccupation().getId() == 2)) {
				occupationToRemove.add(note.getOccupations().stream().filter(e -> e.getId() == 2).findFirst().get());
				note.getOccupations().remove(note.getOccupations().stream().filter(e -> e.getId() == 2).findFirst().get());
			}



			List<Subscription> subscriptionList;

			if(note.isSendUserBAllSponsor()) {
				subscriptionList = this.subscriptionDAO.listAllSubscription();
			} else {
				List<Long> ids = note.getSponsors().stream().map(Sponsor::getId).collect(Collectors.toList());
				subscriptionList = this.subscriptionDAO.listSponsorCodeById(ids);	
			}

			List<User> userList = subscriptionList.stream().map(Subscription::getUser).collect(Collectors.toList());

			if(boNoteDTO.isSendUserA()) {
				userList.addAll(userDAO.getUsersASponsor());
			}

			if(boNoteDTO.isSendUserB()) {
				userList.addAll(userDAO.getUsersBNoSponsor());
			}

			result = userDAO.getMatchedUsersNote(note,userList);

			if(occupationToRemove != null) {
				note.getOccupations().addAll(occupationToRemove);
			}

			List<MatchedUserDTO> matchedUserDTOs = new ArrayList<MatchedUserDTO>();
			for (User matchedUser : result) {
				MatchedUserDTO matchedUserDTO = toMatchedUserDto(matchedUser);
				matchedUserDTOs.add(matchedUserDTO);
			}

			return matchedUserDTOs;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


	private MatchedUserDTO toMatchedUserDto(User user) throws ServiceException {
		MatchedUserDTO result = new MatchedUserDTO();
		result.setUserId(user.getId());
		result.setFirstname(user.getFirstname());
		result.setLastname(user.getLastname());
		result.setEmail(user.getEmail());
		result.setMobilePhone(user.getMobilePhone());

		NotificationConfigurationResponse notificationConfigurationResponse = this.getNotificationConfiguration(user.getId());
		result.setNotificationConfigurationResponse(notificationConfigurationResponse);
		return result;
	}

	@Override
	public boolean notifyToSemiMatchedUsers(Long offerId) throws ServiceException {
		List<MatchedUserDTO> matchedUserDTOs = this.getMatchedUsers(offerId);
		List<MatchedUserDTO> semiMatchedUserDTOs = this.getSemiMatchedUsers(offerId);

		semiMatchedUserDTOs.removeIf(obj -> matchedUserDTOs.stream()
				.map(MatchedUserDTO::getUserId).collect(Collectors.toList()).contains(obj.getUserId()));

		for (MatchedUserDTO matchedUserDTO : semiMatchedUserDTOs) {

			boolean alreadyApplied = this.searchIfApplied(offerId,matchedUserDTO.getUserId());

			if(!alreadyApplied) {

				try {

					User user = userDAO.getById(User.class, matchedUserDTO.getUserId());
					JobOffer offer = jobDAO.getById(JobOffer.class, offerId);

					sendNotification(NotificationType.NEW_OFFER_SEMI_MATCH, user, offer);


				} catch (DAOException e) {
					logger.error("ERROR", e);
				}
			}
		}
		return true;
	}


	@Override
	public boolean notifyToMatchedUsers(Long offerId) throws ServiceException {
		List<MatchedUserDTO> matchedUserDTOs = this.getMatchedUsers(offerId);

		try {
			JobOffer saveMatches = jobDAO.getById(JobOffer.class, offerId);
			int matchCount = (this.getMatchedUsers(saveMatches.getId()).size());
			saveMatches.setMatchesAtCreation(matchCount);
			jobDAO.save(saveMatches);
		} catch (DAOException e1) {
			e1.printStackTrace();
		}

		for (MatchedUserDTO matchedUserDTO : matchedUserDTOs) {

			boolean alreadyApplied = this.searchIfApplied(offerId,matchedUserDTO.getUserId());

			if(!alreadyApplied) {

				try {

					User user = userDAO.getById(User.class, matchedUserDTO.getUserId());
					JobOffer offer = jobDAO.getById(JobOffer.class, offerId);

					sendNotification(NotificationType.NEW_OFFER_MATCH, user,offer);


				} catch (DAOException e) {
					logger.error("ERROR", e);
				}
			}
		}
		return true;
	}





	@Override
	public void notifyNewNotesToMatchedUsers(BONoteDTO boNoteDTO, Note note) throws ServiceException {
		NotificationType type;

		switch (boNoteDTO.getCategory()) {
		case "CAT_1":
			type = NotificationType.NEW_CONGRESS;
			break;
		case "CAT_2":
			type = NotificationType.NEW_GRANT;
			break;
		case "CAT_3":
			type = NotificationType.NEW_NOTE;
			break;
		case "CAT_4":
			type = NotificationType.NEW_PROMOTION;
			break;
		case "CAT_5":
			type = NotificationType.NEW_PRODUCTANDSERVICES;
			break;
		default:
			System.out.println("Categoria erronea");
			return;
		}

		List<MatchedUserDTO> matchedUserDTOs = this.getMatchedUsersNote(boNoteDTO,note.getId());
		for (MatchedUserDTO matchedUserDTO : matchedUserDTOs) {

			//TODO - Ver si se aplica misma logica para las notas
			//			boolean alreadyApplied = this.searchIfApplied(noteId,matchedUserDTO.getUserId());

			//			if(!alreadyApplied) {

			try {

				User user = userDAO.getById(User.class, matchedUserDTO.getUserId());


				if(!(user.getIosPushId() == null && user.getAndroidRegId() == null)) {
					sendNotificationNote(type, user,note);	
				}

			} catch (DAOException e) {
				logger.error("ERROR", e);
			}
			//			}
		}
	}


	private List<MatchedUserDTO> getSemiMatchedUsers(Long offerId) throws ServiceException {
		try {
			List<User> result = new ArrayList<>();

			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);


			GeoLevel geoLevel = this.geoDAO.getGeoByIdAndLevel(jobOffer.getGeoLevelId(), jobOffer.getGeoLevelLevel());
			Geo2 offerGeo2 = null;
			if(jobOffer.getGeoLevelLevel() == 4) {
				Geo4 g4= (Geo4)geoLevel;
				offerGeo2 = g4.getGeo3().getGeo2();
			} else if(jobOffer.getGeoLevelLevel() == 3){
				Geo3 g3 = (Geo3)geoLevel;
				offerGeo2 = g3.getGeo2();
			} else {
				offerGeo2 = (Geo2)geoLevel;
			}

			List<Geo3> geo3List = this.geoDAO.getListGeo3OfGeo2(offerGeo2);
			
			List<Long> geo3IdList = geo3List.stream().map(Geo3::getId).collect(Collectors.toList());
			
			List<Geo4> geo4List = this.geoDAO.getListGeo4OfGeo3List(geo3IdList);
			
			List<Long> geo4IdList = geo4List.stream().map(Geo4::getId).collect(Collectors.toList());
			
//			List<Geo4> geo4List = this.geoDAO.getListGeo4ByGeo3(offerGeo3.getId());
			result = userDAO.getSemiMatchedUsers(jobOffer, geo4IdList, geo3IdList,offerGeo2);

			List<MatchedUserDTO> matchedUserDTOs = new ArrayList<MatchedUserDTO>();
			for (User matchedUser : result) {
				MatchedUserDTO matchedUserDTO = toMatchedUserDto(matchedUser);
				matchedUserDTOs.add(matchedUserDTO);
			}

			return matchedUserDTOs;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}


	/**
	 * Este metodo se usa para registrar eventos predefinidos, el nombre,titulo y cuerpo de la notificacion debe ser definida en NotificationType
	 */
	private void sendNotification(NotificationType type, User user, JobOffer offer){
		try {
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());
			if(notificationConfiguration!=null && notificationConfiguration.isPush()){
				Notification notification = null;
				if(type != NotificationType.NEW_APPLICATION) {
					notification = notificationDAO.getByUserOffer(user.getId(), offer.getId(), type.name());
				}
				if(notification == null) {
					notification = new Notification();
					notification.setCreationDate(new Date());
					notification.setAction(type.name());
					notification.setUser(user);
					notification.setOffer(offer);
					notification.setTitle(type.getTitle());
					notification.setMessage(type.getMessage());
					notification.setActionId(offer.getId());
					notification.setStatus("Enviado");
					this.notificationDAO.save(notification);

					boolean sendNotif = NotificationServiceImpl.validateNotificationConfig(notificationConfiguration, type);

					if(sendNotif) {
						if(user.getIosPushId()!=null && !"NONE".equals(user.getIosPushId())){
							iosNotificationService.sendNotification(notification, type);
						} else if(user.getAndroidRegId()!=null){
							androidNotificationService.sendNotification(notification, type);
						}
					}
				}
			}
		} catch (DAOException e) {
			logger.error("ERROR", e);
		}
	}


	/**
	 * Este metodo se usa para registrar eventos predefinidos, el nombre,titulo y cuerpo de la notificacion debe ser definida en NotificationType
	 */
	private void sendNotificationNote(NotificationType type, User user, Note note){

		try {

			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());
			if(notificationConfiguration!=null && notificationConfiguration.isPush()){

				Notification notification = notificationDAO.getByUserOffer(user.getId(), note.getId(), type.name());


				if(notification == null) {

					notification = new Notification();
					notification.setCreationDate(new Date());
					notification.setAction(type.name());
					notification.setUser(user);
					//					notification.setOffer(offer);
					notification.setTitle(type.getTitle());
					notification.setMessage(type.getMessage());
					notification.setActionId(note.getId());
					notification.setStatus("Enviado");
					notification.setNote(note);

					this.notificationDAO.save(notification);


					boolean sendNotif = NotificationServiceImpl.validateNotificationConfig(notificationConfiguration, type);

					if(sendNotif) {
						if(user.getIosPushId()!=null && !"NONE".equals(user.getIosPushId())){
							iosNotificationService.sendNotification(notification, type);
						} else if(user.getAndroidRegId()!=null){
							androidNotificationService.sendNotification(notification, type);
						}
					}
				}
			}
		} catch (DAOException e) {
			logger.error("ERROR", e);
		}
	}


	@Override
	public List<Long> getOfferIdsByDate(Date date) throws ServiceException {
		try {
			List<Long> jobIds = this.jobDAO.getOfferIdsByDate(date);
			return jobIds;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void sendSMS(SendSMSRequest request) throws ServiceException {
		try {

			ValidationCode validationCode = new ValidationCode();
			validationCode.setCreationDate(new Date());
			validationCode.setMobilePhone(request.getMobilePhone());
			validationCode.setCode(RandomStringUtils.randomAlphanumeric(4));
			validationCode.setEnabled(true);
			if(request.getMobilePhone().equals("94572109469428712369")) {
				validationCode.setCode("M4c1");
			}
			userDAO.save(validationCode);
			sendSMS(validationCode.getMobilePhone(), validationCode.getCode());

		} catch (IOException | DAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<UserDTO> getAll() throws ServiceException {
		try {
			return toDtoList(this.userDAO.getAll());
		} catch (DAOException e) {
			throw new ServiceException(e);
		} 
	}


	@Override 
	public List<UserDTO> getUserByIndex(String length, String start, String search) { 

		List<User> userList = this.userDAO.getUserByIndex(length, start, search); 

		return toDtoList(userList); 

	} 

	private List<UserDTO> toDtoList(Collection<User> list) {
		return list.stream().map(user -> toDto(user)).collect(Collectors.toList());
	}


	private  UserDTO toDtoWebDetails(User user) {
		UserDTO result = toDto(user);

		UserProfile userProfile = null;

		List<SpecialtyDTO> specialtyDtoList = new ArrayList<SpecialtyDTO>();

		try {
			userProfile = this.userDAO.getUserProfile(user);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		Date creationDate = user.getCreationDate();
		if(creationDate!=null){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
			result.setCreationDate(formatter.format(creationDate));
		}

		if(user.getAvatar()!=null){
			result.setAvatar(new String(user.getAvatar().getData()));
		}

		Date lastLogin = user.getLastLoginDate();
		if(lastLogin!=null){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
			result.setLastLoginDate(formatter.format(lastLogin));
		}

		if(user.isUserb()){

			if(userProfile != null) {
				if(userProfile.getInstitutionType() != null) {
					result.setInstitutionType(userProfile.getInstitutionType().name());
				}
			}
			if(user.getSpecialties().size() > 0) {
				Iterator<Specialty> iter = user.getSpecialties().iterator();

				Specialty first = iter.next();
				result.setUserOccupation(first.getOccupation().getName());
				if(userProfile != null ) {
					if(userProfile.getTasks() != null) {
						for (Iterator<Task> iterator = userProfile.getTasks().iterator(); iterator.hasNext();) {
							Task task = (Task) iterator.next();
							int index = checkIfExist(task,specialtyDtoList);
							if(index > -1) {
								specialtyDtoList.get(index).getTaskList().add(task);
							} else {
								SpecialtyDTO specialtyDTO = new SpecialtyDTO();
								specialtyDTO.setTaskList(new ArrayList<Task>());
								specialtyDTO.setId(task.getSpecialty().getId());
								if(task.getSpecialty().getName().equals("")) {
									specialtyDTO.setName(task.getSpecialty().getOccupation().getName());
								} else {
									specialtyDTO.setName(task.getSpecialty().getName());
								}

								specialtyDTO.getTaskList().add(task);
								specialtyDtoList.add(specialtyDTO);
							}
						}
						//		specialtyDtoList
						//		  .stream()
						//		  .sorted((object1, object2) -> object1.getName().compareTo(object2.getName()));

						result.setUserSpecialty(specialtyDtoList);
					}
				} else {
					result.setUserSpecialty(new ArrayList<SpecialtyDTO>());
					for (Specialty specialty : user.getSpecialties()) {
						List<Task> taskList = new ArrayList<Task>();
						Task task = new Task();
						task.setId(-1);
						task.setName("No cargo tareas");
						taskList.add(task);
						result.getUserSpecialty().add(new SpecialtyDTO(specialty.getId(),specialty.getName(),taskList));
					}
				}
			} else {
				result.setUserOccupation("No cargo la Profesión");
			}
			result.setLicense(user.getLicense());

			List<GeoLevelDTO> geoList = new ArrayList<GeoLevelDTO>();

			for (Iterator<UserGeoLocation> iterator = user.getUserGeoLocations().iterator(); iterator.hasNext();) {
				UserGeoLocation userGeo = (UserGeoLocation) iterator.next();
				geoList.add(new GeoLevelDTO(userGeo.getId(),userGeo.getGeoLevelLevel(),userGeo.getGeoLevelName()));

			}

			result.setGeoLevels(geoList);

			if(user.getPdfCV() != null){
				result.setPdfBase64(user.getPdfCV().getData());
			}
			UserLinkedinProfile userLinkedinProfile = null;
			try {
				userLinkedinProfile = this.userDAO.getUserLinkedinProfile(user);
			} catch (DAOException e) {
				e.printStackTrace();
			}

			result.setCvPlainTxt(user.getCV());
			if(userLinkedinProfile != null && userLinkedinProfile.getPublicProfileURL() != null) {
				result.setLinkedinUrl(userLinkedinProfile.getPublicProfileURL());
			}
			// TODO: Agregar en el result los tres elementos explicados a continuación
			// link de linkedin
			// El pdf en formato descargable
			// el texto del cv cargado a mano
		}
		if(user.isUserb()) {
			result.setLicense(user.getLicense());				
		}
		result.setOperativeSystem(user.getAndroidRegId()!=null?"Android":"IOS");

		return result;
	}

	private int checkIfExist(Task task, List<SpecialtyDTO> specialtyDtoList) {

		int index = -1;
		for(int i = 0; i < specialtyDtoList.size(); i++) {
			if(specialtyDtoList.get(i).getId() == task.getSpecialty().getId()) {
				index = i;
			}
		}
		return index;
	}

	private  UserDTO toDto(User user){

		UserDTO result = new UserDTO();

		result.setId(user.getId());
		result.setEmail(user.getEmail());
		result.setName(user.getFirstname());
		result.setLastname(user.getLastname());
		result.setActive(user.isPhoneValidated());
		result.setUserB(user.isUserb());
		result.setCompanyScreenName(user.getCompanyScreenName());

		result.setMobilePhone(user.getMobilePhone());

		Subscription subscription = subscriptionService.getActiveSubscription(user.getId());
		if (subscription != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(subscription.getExpirationDate().after(new Date())){
				result.setHasActiveSuscription(true);
				result.setExpirationDate(sdf.format(subscription.getExpirationDate()));
			} else {
				result.setHasActiveSuscription(false);
			}
		}


		if (user.getAvatar() != null) {
			Base64DTO avatar = new Base64DTO(new String(user.getAvatar().getData()));
			result.setAvatar(avatar.getBase64());
		}
		try {
			UserReceiptResponse receipt = subscriptionService.getLastReceipt(user.getId());

			if (receipt != null) {
				Date expireDate = new Date(receipt.getExpiresDate());
				//Check bigger date
				if(subscription==null || (subscription!=null && subscription.getExpirationDate().before(expireDate))){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if(expireDate.after(new Date())){
						result.setHasActiveSuscription(true);
						result.setExpirationDate(sdf.format(expireDate));
					} else {
						result.setHasActiveSuscription(false);
					}
				}

			}
		} catch (ServiceException e) {
			logger.error("Can't load receipts", e);
		}

		return result;
	}

	@Override
	public JobOfferStatusDTO getOfferById(long offerId) {
		try {
			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);

			JobOfferStatusDTO jobOfferStatusDTO = new JobOfferStatusDTO();

			jobOfferStatusDTO.setId(jobOffer.getId());
			jobOfferStatusDTO.setComment(jobOffer.getComment());
			jobOfferStatusDTO.setCompanyScreenName(jobOffer.getCompanyScreenName());
			jobOfferStatusDTO.setCreationDate(jobOffer.getCreationDate().toString());
			jobOfferStatusDTO.setGeoLevelId(jobOffer.getGeoLevelId());
			jobOfferStatusDTO.setGeoLevelLevel(jobOffer.getGeoLevelLevel());
			GeoLevel geoLevel;
			try {
				geoLevel = this.geoDAO.getGeoByIdAndLevel(jobOffer.getGeoLevelId(), jobOffer.getGeoLevelLevel());
				jobOfferStatusDTO.setGeoLevelName(geoLevel.getName());
			} catch (DAOException e) {
				throw new RuntimeException(e);
			}
			jobOfferStatusDTO.setOfferHour(jobOffer.getHour());
			jobOfferStatusDTO.setInstitutionType(jobOffer.getInstitutionType().toString());
			jobOfferStatusDTO.setOfferDate(jobOffer.getOfferDate().toString());
			jobOfferStatusDTO.setPermanent(jobOffer.isPermanent());
			jobOfferStatusDTO.setStatus(jobOffer.getStatus());
			jobOfferStatusDTO.setSubTitle(jobOffer.getSubtitle());
			jobOfferStatusDTO.setTitle(jobOffer.getTitle());
			jobOfferStatusDTO.setVacants(jobOffer.getVacants());
			jobOfferStatusDTO.setOccupation_id(jobOffer.getOccupation().getId());
			jobOfferStatusDTO.setOccupationName(jobOffer.getOccupation().getName());
			jobOfferStatusDTO.setOfferent_id(jobOffer.getOfferent().getId());
			jobOfferStatusDTO.setSpecialty_Id(jobOffer.getSpecialty().getId());
			jobOfferStatusDTO.setSpecialtyName(jobOffer.getSpecialty().getName());
			jobOfferStatusDTO.setTask_id(jobOffer.getTask().getId());
			jobOfferStatusDTO.setTaskName(jobOffer.getTask().getName());
			jobOfferStatusDTO.setApplications(jobOffer.getApplications());
			if(jobOffer.getOfferent().getAvatar()!=null)
				jobOfferStatusDTO.setBase64img(new String(jobOffer.getOfferent().getAvatar().getData()));
			jobOfferStatusDTO.setJobApplication_id(jobOffer.getJobApplication_id());
			jobOfferStatusDTO.setOfferentFirstname(jobOffer.getOfferent().getFirstname());
			jobOfferStatusDTO.setOfferentLastname(jobOffer.getOfferent().getLastname());

			return jobOfferStatusDTO;

		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void notifyToMatchedUsersSubscription(List<Subscription> subscriptionsList) throws ServiceException, DAOException {


		for (Subscription subscription : subscriptionsList) {
			User user = subscription.getUser();

			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());

			Notification notification = new Notification();
			notification.setCreationDate(new Date());

			notification.setTitle("Su suscripción de DOC to DOC está por vencer");
			notification.setMessage("Mantené tu suscripción activa para acceder a las ofertas laborales.");

			notification.setStatus("Enviado");
			notification.setUser(user);
			notification.setAction("Default");

			if(user.getIosPushId() != null || user.getAndroidRegId() != null) {
				this.notificationDAO.save(notification);
			}

			if(notificationConfiguration == null) {
				break;
			}

			boolean sendNotif = NotificationServiceImpl.validateNotificationConfig(notificationConfiguration,null);

			if(sendNotif) {
				if(user.getIosPushId()!=null && !"NONE".equals(user.getIosPushId())){
					iosNotificationService.sendNotification(notification,null);
				} else if(user.getAndroidRegId()!=null){
					androidNotificationService.sendNotification(notification,null);
				}
			}

			subscription.setExpirationNotified(true);
			this.subscriptionDAO.saveSubscription(subscription);

		}

	}

	@Override
	public void deleteOfferById(int offerId) throws DAOException  {
		JobOffer jobOffer = jobDAO.getById(JobOffer.class, offerId);
		jobOffer.setStatus(JobOffer.CLOSED);
		jobDAO.save(jobOffer);
	}

	@Override
	public void addOffer(BoJobDTO boJob,int offerId) {

		try {
			User user = getUserById(boJob.getUserId());
			user.setCompanyScreenName(boJob.getCompanyScreenName());
			Base64DTO avatar = null;
			if (user.getAvatar() != null) {
				avatar =  new Base64DTO(new String(user.getAvatar().getData()));
			} else {
				avatar = new Base64DTO("");
			}

			if(boJob.isPermanent()) {
				CreatePermanentJobOfferRequest createPermanentJobOfferRequest = new CreatePermanentJobOfferRequest();
				createPermanentJobOfferRequest.setBase64Image(avatar.getBase64());
				createPermanentJobOfferRequest.setComment(boJob.getOfferText());
				createPermanentJobOfferRequest.setCompanyScreenName(boJob.getCompanyScreenName());
				createPermanentJobOfferRequest.setGeoLevelId(boJob.getGeoDto().getId());
				createPermanentJobOfferRequest.setGeoLevelLevel(boJob.getGeoDto().getLevel());
				if(boJob.isPrivateInstitution()) {
					createPermanentJobOfferRequest.setInstitutionType(InstitutionType.PRIVATE);	
				} else {
					createPermanentJobOfferRequest.setInstitutionType(InstitutionType.PUBLIC);
				}
				createPermanentJobOfferRequest.setOccupationId(boJob.getOccupationId());
				createPermanentJobOfferRequest.setSpecialtyId(boJob.getSpecialtyId());
				createPermanentJobOfferRequest.setSubtitle(boJob.getSubtitle());
				createPermanentJobOfferRequest.setTaskId(boJob.getTaskId());
				createPermanentJobOfferRequest.setTitle(boJob.getTitle());
				createPermanentJobOfferRequest.setVacants(1);
				this.userDAO.save(user);
				createJobOffer(createPermanentJobOfferRequest, user,offerId);
			} else {
				CreateTemporaryJobOfferRequest createTemporaryJobOfferRequest = new CreateTemporaryJobOfferRequest();
				createTemporaryJobOfferRequest.setBase64Image(avatar.getBase64());
				createTemporaryJobOfferRequest.setComment(boJob.getOfferText());
				createTemporaryJobOfferRequest.setCompanyScreenName(boJob.getCompanyScreenName());
				createTemporaryJobOfferRequest.setGeoLevelId(boJob.getGeoDto().getId());
				createTemporaryJobOfferRequest.setGeoLevelLevel(boJob.getGeoDto().getLevel());
				if(boJob.isPrivateInstitution()) {
					createTemporaryJobOfferRequest.setInstitutionType(InstitutionType.PRIVATE);	
				} else {
					createTemporaryJobOfferRequest.setInstitutionType(InstitutionType.PUBLIC);
				}
				createTemporaryJobOfferRequest.setOccupationId(boJob.getOccupationId());

				String dateForJob = "";
				try {
					Date date = getDate(boJob.getOfferDateForView(),"dd-MM-yyyy");
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
					dateForJob = df.format(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				createTemporaryJobOfferRequest.setOfferDate(dateForJob);

				String[] parts = boJob.getOfferHour().split(":");
				String part1 = parts[0]; // 004
				String part2 = parts[1]; // 034556


				createTemporaryJobOfferRequest.setOfferHour(part1 + part2);
				createTemporaryJobOfferRequest.setSpecialtyId(boJob.getSpecialtyId());
				createTemporaryJobOfferRequest.setTaskId(boJob.getTaskId());
				createTemporaryJobOfferRequest.setVacants(1);
				this.userDAO.save(user);
				createJobOffer(createTemporaryJobOfferRequest, user,offerId);
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<UserCandidateDTO> getcandidatesForOffer(long offerId) {

		List<JobApplication> jobApplicationList;
		try {
			jobApplicationList = jobApplicationDAO.getJobApplications(offerId);
			return jobApplicationList.stream().map(elem -> toUserCandidateDTO(elem)).collect(Collectors.toList());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private UserCandidateDTO toUserCandidateDTO(JobApplication elem) {
		UserCandidateDTO result = new UserCandidateDTO();
		result.setId(elem.getUser().getId());
		result.setName(elem.getUser().getFirstname());
		result.setLastName(elem.getUser().getLastname());
		result.setEmail(elem.getUser().getEmail());
		result.setMobilePhone(elem.getUser().getMobilePhone());


		if(elem.getUser().getSpecialties().isEmpty()) {
			result.setOccupation("No cargo la Profesión");
		} else {
			Iterator<Specialty> iter = elem.getUser().getSpecialties().iterator();

			Specialty first = iter.next();

			if(first != null) {
				result.setOccupation(first.getOccupation().getName());
			}
		}
		return result;
	}

	@Override
	public List<JobOfferStatusDTO> getLastOffers() {


		List<JobOffer> offers = this.jobDAO.getLastOffers();

		List<JobOfferStatusDTO> offerDto = offers.stream().map(s -> toDTO(s)).collect(Collectors.toList());

		return offerDto;


	}

	@Override
	public int getUserCount(String search) {

		return userDAO.getCountWithFilter(search);
	}




}
