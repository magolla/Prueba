package com.tdil.d2d.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.CreateJobOfferRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequest;
import com.tdil.d2d.controller.api.response.RegistrationResponse;
import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.SpecialtyDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.ActivityLog;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Task;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.security.RuntimeContext;
import com.tdil.d2d.service.AndroidNotificationService;
import com.tdil.d2d.service.CryptographicService;
import com.tdil.d2d.service.EmailService;
import com.tdil.d2d.service.UserService;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private JobOfferDAO jobDAO;
	@Autowired
	private SpecialtyDAO specialtyDAO;
	@Autowired
	private ActivityLogDAO activityLogDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CryptographicService cryptographicService;
	
	@Autowired
	private AndroidNotificationService androidNotificationService;
	
	@Autowired
	private EmailService emailService;
	
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
	public RegistrationResponse register(RegistrationRequest registrationRequest) throws ServiceException {
		try {
			RegistrationResponse response = new RegistrationResponse(HttpStatus.CREATED.value());
			User user = new User();
			user.setCreationDate(new Date());
			user.setEmail(registrationRequest.getEmail());
			//user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			user.setEnabled(true);
			user.setDeviceId(cryptographicService.encrypt(registrationRequest.getDeviceId(), "", user.getSalt()));
			//user.setPhoneNumber(cryptographicService.encrypt(registrationRequest.getPhoneNumber(), "", user.getSalt()));
			user.setEmailValidated(false);
			user.setEmailHash(RandomStringUtils.randomAlphanumeric(4));
			user.setPassword(passwordEncoder.encode(registrationRequest.getDeviceId()));
			this.userDAO.save(user);
			
			// TODO ENVIAR EMAIL DE VALIDACION
			
			String body = "Para terminar la registracion use el siguiente codigo en la app o cliquea el siguiente link " + user.getEmailHash();
			emailService.sendEmail(registrationRequest.getEmail(), EmailServiceImpl.defaultFrom, "Registracion", body);
			
			return response;
		} catch (IllegalBlockSizeException | BadPaddingException | DAOException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean updateAndroidRegId(AndroidRegIdRequest androidRegIdRequest) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setAndroidRegId(androidRegIdRequest.getAndroidRegId());
			this.userDAO.save(user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean updateIOsPushId(IOsPushIdRequest iOsPushIdRequest) throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setIosPushId(iOsPushIdRequest.getIosPushId());
			this.userDAO.save(user);
			return true;
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
		
	@Override
	public void updateLastLoginDate() throws ServiceException {
		try {
			User user = this.userDAO.getById(User.class, RuntimeContext.getCurrentUser().getId());
			user.setLastLoginDate(new Date());
			this.userDAO.save(user);
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
	public boolean createJobOffer(CreateJobOfferRequest createOfferRequest) throws ServiceException {
		try {
			JobOffer jobOffer = new JobOffer();
			jobOffer.setOfferent(userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId()));
			jobOffer.setCreationDate(new Date());
			jobOffer.setGeoLevelLevel(createOfferRequest.getGeoLevelLevel());
			jobOffer.setGeoLevelId(createOfferRequest.getGeoLevelId());
			jobOffer.setOccupation(specialtyDAO.getOccupationById(createOfferRequest.getOccupationId()));
			jobOffer.setSpecialty(specialtyDAO.getSpecialtyById(createOfferRequest.getSpecialtyId()));
			jobOffer.setTask(specialtyDAO.getTaskById(createOfferRequest.getTaskId()));
			jobOffer.setAddress(createOfferRequest.getAddress());
			jobOffer.setOfferDate(getDate(createOfferRequest.getOfferDate(), "yyyyMMdd"));
			jobOffer.setHour(createOfferRequest.getOfferHour());
			jobOffer.setPermanent(createOfferRequest.isPermanent());
			jobOffer.setComment(createOfferRequest.getComment());
//			jobOffer.setTasks(createOfferRequest.getTasks());
			jobOffer.setVacants(createOfferRequest.getVacants());
			jobOffer.setStatus(JobOffer.VACANT);
			this.jobDAO.save(jobOffer);
			return true;
		} catch (Exception e) {
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
		try {
			List<JobOffer> creditCards = this.jobDAO.getOffers(RuntimeContext.getCurrentUser().getId());
			return creditCards.stream().map(s -> toDTO(s))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private JobOfferStatusDTO toDTO(JobOffer s) {
		JobOfferStatusDTO result = new JobOfferStatusDTO();
		result.setId(s.getId());
		// TODO format
		result.setCreationDate(s.getCreationDate().toString());
		result.setSpecialtyName(s.getSpecialty().getName());
//		result.setSubspecialtyName(s.getSubSpecialty().getName());
		result.setAddress(s.getAddress());
		result.setOfferDate(s.getOfferDate().toString());
		result.setOfferHour(s.getHour());
		result.setVacants(s.getVacants());
		// TODO 
//		result.setApplications(applications);
		result.setStatus(s.getStatus());
		return result;
	}
	
	@Override
	public List<ActivityLogDTO> getActivityLog() throws ServiceException {
		try {
			List<ActivityLog> creditCards = this.activityLogDAO.getLastLog(RuntimeContext.getCurrentUser().getId());
			return creditCards.stream().map(s -> toDTO(s))
					.collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	private ActivityLogDTO toDTO(ActivityLog s) {
		ActivityLogDTO r = new ActivityLogDTO();
		r.setId(s.getId());
		r.setCreationDate(s.getCreationDate().toString());
		r.setLog(s.getLog());
		return null;
	}
	
	@Override
	public void initDbWithTestData() throws ServiceException {
		long l = this.addOccupation("Medico");
		l = this.addSpecialty(l, "Alergista");
		l = this.addTask(l, "reemplazo de consultorio");
	}
}
