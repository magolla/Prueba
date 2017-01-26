package com.tdil.d2d.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.Base64DTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.JobApplicationDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.dto.MatchesSummaryDTO;
import com.tdil.d2d.controller.api.dto.OccupationDTO;
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
import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.dao.JobApplicationDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.NotificationConfigurationDAO;
import com.tdil.d2d.dao.PaymentDAO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.ActivityAction;
import com.tdil.d2d.persistence.ActivityLog;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.persistence.GeoLevel;
import com.tdil.d2d.persistence.JobApplication;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.NotificationConfiguration;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Payment;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.Task;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserGeoLocation;
import com.tdil.d2d.persistence.UserLinkedinProfile;
import com.tdil.d2d.persistence.UserProfile;
import com.tdil.d2d.security.RuntimeContext;
import com.tdil.d2d.service.CryptographicService;
import com.tdil.d2d.service.EmailService;
import com.tdil.d2d.service.NotificationService;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.ServiceLocator;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${mercadopago.client_id}")
	private String clientId;

	@Value("${mercadopago.secret_id}")
	private String secretId;

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
	private ActivityLogDAO activityLogDAO;
	@Autowired
	private GeoDAO geoDAO;

	@Autowired
	private PaymentDAO paymentDAO;

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
			user.setPhoneValidated(false);
			user.setEmailValidated(false);
			user.setEmailHash(RandomStringUtils.randomAlphanumeric(4));
			user.setCompanyScreenName(registrationRequest.getCompanyScreenName());
			user.setTacAccepted(registrationRequest.isTacAccepted());
			user.setTacAcceptDate(registrationDate);
			if (ServiceLocator.isLocalhost()) {
				user.setMobileHash("9999");
			} else {
				user.setMobileHash(RandomStringUtils.randomAlphanumeric(4));
			}
			user.setPassword(passwordEncoder.encode(registrationRequest.getDeviceId()));
			this.userDAO.save(user);

			activityLogDAO.save(new ActivityLog(user, ActivityAction.REGISTER));
			// TODO ENVIAR SMS DE VALIDACION

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
			user.setMobilePhone(registrationRequest.getMobilePhone());
			user.setUserb(true);
			user.setPhoneValidated(false);
			user.setEmailValidated(false);
			user.setEmailHash(RandomStringUtils.randomAlphanumeric(4));
			user.setTacAccepted(registrationRequest.isTacAccepted());
			user.setTacAcceptDate(registrationDate);
			if (ServiceLocator.isLocalhost()) {
				user.setMobileHash("9999");
			} else {
				user.setMobileHash(RandomStringUtils.randomAlphanumeric(4));
			}
			user.setPassword(passwordEncoder.encode(registrationRequest.getDeviceId()));
			this.userDAO.save(user);

			activityLogDAO.save(new ActivityLog(user, ActivityAction.REGISTER));
			// TODO ENVIAR SMS DE VALIDACION

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
			User user = this.userDAO.getUserByMobilePhone(validationRequest.getMobilePhone());
			logger.info("User found = {}", user.getId());
			if (user != null && user.getDeviceId().equals(encriptDeviceId(validationRequest.getDeviceId(), user))
					&& user.getMobileHash().equals(validationRequest.getSmsCode())) {
				user.setPhoneValidated(true);
				this.userDAO.save(user);
				return true;
			}
			return false;
		} catch (DAOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			throw new ServiceException(e);
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_SPECIALTY));
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean addSpecialties(AddSpecialtiesRequest addSpecialtiesRequest) throws ServiceException {
		try {
			User user = getLoggedUser();
			// TODO ver si hay que vaciar primero las especialidades y en ese
			// caso cambiar el nombre por setSpecialities
			user.getSpecialties().clear();
			for (long id : addSpecialtiesRequest.getSpecialtyId()) {
				Specialty specialty = this.specialtyDAO.getSpecialtyById(id);
				user.getSpecialties().add(specialty);
			}
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_SPECIALTY));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_GEO_LEVEL));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_GEO_LEVEL));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.SET_LICENSE));
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
			outputStream.write(user.getBase64img());
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Base64DTO getAvatarBase64() throws ServiceException {
		User user = getLoggedUser();
		if (user.getBase64img() != null) {
			return new Base64DTO(new String(user.getBase64img()));
		} else {
			return new Base64DTO("");
		}
	}

	@Override
	public void getAvatar(long userId, ServletOutputStream outputStream) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, userId);
			outputStream.write(user.getBase64img());
		} catch (DAOException | IOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Base64DTO getAvatarBase64(long userId) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, userId);
			if (user.getBase64img() != null) {
				return new Base64DTO(new String(user.getBase64img()));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.CHANGE_INSTITUTION_TYPE));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_TASK_TO_PROFILE));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_TASK_TO_PROFILE));
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
				activityLogDAO.save(new ActivityLog(user, ActivityAction.REMOVE_TASK_FROM_PROFILE));
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
			activityLogDAO.save(new ActivityLog(user, ActivityAction.LOGIN));
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
	public boolean createJobOffer(CreateTemporaryJobOfferRequest createOfferRequest) throws ServiceException {
		try {

			JobOffer jobOffer = new JobOffer();
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
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.POST_TEMPORARY_OFFER));
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
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.POST_TEMPORARY_OFFER));
			return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean createJobOffer(CreatePermanentJobOfferRequest createOfferRequest) throws ServiceException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			createOfferRequest.setOfferDate(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()));
			createOfferRequest.setOfferHour("0000");
			JobOffer jobOffer = new JobOffer();
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
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.POST_PERMANENT_OFFER));
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
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.POST_PERMANENT_OFFER));
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
			User user = getLoggedUser();
			UserLinkedinProfile linkedinProfile = this.userDAO.getUserLinkedinProfile(user);
			if(linkedinProfile != null) {
				jobApplication.setLinkedInCv(linkedinProfile.getPublicProfileURL());
			}
			jobApplication.setOffer(jobOffer);
			jobApplication.setUser(user);
			this.jobApplicationDAO.save(jobApplication);
			jobOffer.setApplications(jobOffer.getApplications() + 1);
			this.jobDAO.save(jobOffer);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.APPLY_TO_OFFER));
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
		// TODO PENDING
		try {
			List<JobOffer> result = new ArrayList<>();
			User user = getLoggedUser();
			for (Specialty specialty : user.getSpecialties()) {
				for (UserGeoLocation location : user.getUserGeoLocations()) {
					result.addAll(this.jobDAO.getOffers(specialty.getId(), location.getGeoLevelId(), true));
					GeoLevel geoLevel = this.geoDAO.getGeoByIdAndLevel(location.getGeoLevelId(), location.getGeoLevelLevel());
					if (location.getGeoLevelLevel() == 4) {
						Geo4 geo4 = (Geo4) geoLevel;
						result.addAll(this.jobDAO.getOffers(specialty.getId(), geo4.getGeo3().getId(), true));
						result.addAll(
								this.jobDAO.getOffers(specialty.getId(), geo4.getGeo3().getGeo2().getId(), true));
					}
					if (location.getGeoLevelLevel() == 3) {
						Geo3 geo3 = (Geo3) geoLevel;
						result.addAll(this.jobDAO.getOffers(specialty.getId(), geo3.getGeo2().getId(), true));
					}
				}
			}
			return result.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private List<JobOfferStatusDTO> getMatchedOffers(boolean permament) throws ServiceException {
		// TODO: BUG no busca bien
		/* No veo que esté matcheando bien todos los niveles, tampoco matchea el institutionType */
		try {
			List<JobOffer> result = new ArrayList<>();
			User user = getLoggedUser();
			for (Specialty specialty : user.getSpecialties()) {
				for (UserGeoLocation location : user.getUserGeoLocations()) {
					result.addAll(this.jobDAO.getOffers(specialty.getId(), location.getGeoLevelId(), permament));
					GeoLevel geoLevel = this.geoDAO.getGeoByIdAndLevel(location.getGeoLevelId(), location.getGeoLevelLevel());
					if (location.getGeoLevelLevel() == 4) {
						Geo4 geo4 = (Geo4) geoLevel;
						result.addAll(this.jobDAO.getOffers(specialty.getId(), geo4.getGeo3().getId(), permament));
						result.addAll(
								this.jobDAO.getOffers(specialty.getId(), geo4.getGeo3().getGeo2().getId(), permament));
					}
					if (location.getGeoLevelLevel() == 3) {
						Geo3 geo3 = (Geo3) geoLevel;
						result.addAll(this.jobDAO.getOffers(specialty.getId(), geo3.getGeo2().getId(), permament));
					}
				}
			}
			return result.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
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
				// TODO: enviar notificaciones a los que quedan afuera
				// TODO: enviar notificacion al que aceptaron
			}
			offer.setJobApplication_id((int) (long) application.getId());

			this.jobDAO.save(offer);
			this.jobApplicationDAO.save(application);
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.ACCEPT_OFFER));
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * <<<<<<< HEAD ESTE MÉTODO ESTÁ DEPRECADO YA QUE NO SE VAN A RECHAZAR
	 * PERFILES POR AHORA ======= ESTE M�TODO EST� DEPRECADO YA QUE NO SE VAN A
	 * RECHAZAR PERFILES POR AHORA >>>>>>> master
	 */
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
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.REJECT_OFFER));
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
			activityLogDAO.save(new ActivityLog(getLoggedUser(), ActivityAction.CLOSED_OFFER));
			//TODO: Enviar notificacion a los apliccants avisando que se cerro el aviso
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date = sdf.format(new Date());
		try {
			User user = getLoggedUser();
			if (!StringUtils.isEmpty(user.getAndroidRegId())) {
				androidNotificationService.sendNotification(NotificationType.NEW_APPLICATION, null, "Title " + date,
						"Message " + date, user.getAndroidRegId());
			}
			return false;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean sendTestNotificationIOS() throws ServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date = sdf.format(new Date());
		try {
			User user = getLoggedUser();
			if (!StringUtils.isEmpty(user.getIosPushId())) {
				iosNotificationService.sendNotification(NotificationType.NEW_APPLICATION, null, "Title " + date,
						"Message " + date, user.getIosPushId());
			}
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
		if(s.getUser().getBase64img() != null) {
			result.setBase64Image(new String(s.getUser().getBase64img()));
		}
		// Linkedin CV
		result.setLinkedinInCv(s.getLinkedInCv());
		// Falta cvAttach
		if(s.getCvAttach() != null) {
			result.setCvAttach(new String(s.getCvAttach()));
		}
		// cvPlain
		result.setCvPlain(s.getCvPlain());
		// FirstName
		result.setFirstname(s.getUser().getFirstname());
		// LastName
		result.setLastname(s.getUser().getLastname());
		// numberPhone
		result.setMobilePhone(s.getUser().getMobilePhone());
		// Occupation
		result.setOccupationName(s.getOffer().getOccupation().getName());
		// Specialty
		result.setSpecialtyName(s.getOffer().getSpecialty().getName());

		//geolevel
		result.setGeoLevelId(s.getOffer().getGeoLevelId());
		result.setGeoLevelLevel(s.getOffer().getGeoLevelLevel());

		GeoLevel geoLevel;
		try {
			geoLevel = this.geoDAO.getGeoByIdAndLevel(s.getOffer().getGeoLevelId(), s.getOffer().getGeoLevelLevel());
			result.setGeoLevelName(geoLevel.getName());
		} catch (DAOException e) {
			throw new RuntimeException(e);
		}

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
		result.setBase64img(s.getOfferent().getBase64img());
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

		if (user.getBase64img() != null) {
			resp.setBase64img(new String(user.getBase64img()));
		}
		resp.setUserb(user.isUserb());
		if (resp != null) {
			Subscription subscription = subscriptionService.getActiveSubscription(user.getId());
			if (subscription != null) {
				if (subscription.getSponsorCode() != null && subscription.getSponsorCode().getSponsor() != null) {
					resp.setSponsorName(subscription.getSponsorCode().getSponsor().getName());
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
			List<JobOffer> offers = this.jobDAO.getAllPermanentOffersOpen();
			return offers.stream().map(s -> toDTO(s)).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean setAvatar(User user, SetAvatarRequest setAvatarRequest) throws ServiceException {
		try {
			user.setBase64img(setAvatarRequest.getAvatarBase64().getBytes());
			this.userDAO.save(user);
			activityLogDAO.save(new ActivityLog(user, ActivityAction.SET_AVATAR));
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
            subscriptionService.register(createPaymentRequest.getSubscriptionDuration());
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
	}

	@Override
	public UserDetailsResponse getApprovedCandidateForOffer(long id) throws ServiceException {
		try {
			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, id);
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
			Media media = new Media();
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
		Base64DTO base64dto = new Base64DTO(
			new String(Base64.encodeBase64(user.getPdfCV().getData()))
		);
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
}
