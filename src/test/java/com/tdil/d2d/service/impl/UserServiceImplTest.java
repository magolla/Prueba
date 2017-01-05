package com.tdil.d2d.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.config.PersistenceConfiguration;
import com.tdil.d2d.config.WebSecurityConfig;
import com.tdil.d2d.controller.api.dto.JobOfferStatusDTO;
import com.tdil.d2d.controller.api.request.InstitutionType;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.controller.api.request.SetAvatarRequest;
import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.Task;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.EmailService;
import com.tdil.d2d.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebSecurityConfig.class, PersistenceConfiguration.class, UserServiceImplTest.TestConfiguration.class})
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceImplTest {

	private Logger logger = LogManager.getLogger(UserServiceImplTest.class);

	private static UserDAO userDAO = mock(UserDAO.class);

	private static JobOfferDAO jobOfferDAO = mock(JobOfferDAO.class);

	@Configurable
	@ComponentScan(basePackages = {"com.tdil.d2d"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class))
	static class TestConfiguration {

		@Bean(name = "emailService")
		public EmailService emailService() {
			return mock(EmailService.class);
		}

		@Bean(name = "userDAO")
		public UserDAO userDAO() {
			return userDAO;
		}

		@Bean(name = "activityLogDAO")
		public ActivityLogDAO activityLogDAO() {
			return mock(ActivityLogDAO.class);
		}

		@Bean(name = "jobDAO")
		public JobOfferDAO jobOfferDAO() {
			return jobOfferDAO;
		}
	}

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("spring.profiles.active", "test");
	}

	@Autowired
	private UserService userService;

	private Map<Long, User> users = new HashMap<>();

	@Before
	public void setUp() throws DAOException, ServiceException {

		when(userDAO.getById(any(Class.class), any(Long.class))).thenAnswer((InvocationOnMock mock) -> {
			long id = mock.getArgument(0);
			return users.get(id);
		});

		doAnswer((InvocationOnMock mock) -> {
			User user = mock.getArgument(0);
			users.put(user.getId(), user);
			return null;
		}).when(userDAO).save(any(User.class));


		User user = new User();
		user.setId(1);
		userDAO.save(user);

		when(jobOfferDAO.getOpenOffers(any(Long.class))).thenAnswer((InvocationOnMock mock) -> {

			Occupation occupation = new Occupation();
			occupation.setId(1);
			occupation.setName("name");

			Specialty specialty = new Specialty();
			specialty.setId(1);
			specialty.setName("speciality");
			specialty.setOccupation(occupation);

			Task task = new Task();
			task.setId(1);
			task.setName("task");
			task.setSpecialty(specialty);
			task.setCreationDate(new Date());

			JobOffer offer = new JobOffer();
			offer.setId(1);
			offer.setOfferent(user);
			offer.setCreationDate(new Date());
			offer.setInstitutionType(InstitutionType.PUBLIC);
			offer.setOfferDate(new Date());
			offer.setOccupation(occupation);
			offer.setSpecialty(specialty);
			offer.setTask(task);
			List<JobOffer> offers = Arrays.asList(offer);
			return offers;
		});


	}

	@Test
	public void testExceptionWhenSavingDuplicatedMobilePhones() throws ServiceException, DAOException {

		doThrow(new DAOException("", new RuntimeException())).when(userDAO).save(any(User.class));

		RegistrationRequestA request1 = new RegistrationRequestA();
		request1.setLastname("hendrix");
		request1.setDeviceId("123");
		request1.setMobilePhone("123");

		RegistrationRequestA request2 = new RegistrationRequestA();
		request1.setLastname("bonamassa");
		request2.setDeviceId("123");
		request2.setMobilePhone("123");

		try {
			logger.info("test");
			userService.register(request1);
			userService.register(request2);
			fail();
		} catch (ServiceException e) {
			logger.error("error ", e);
			logger.info("error " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testSetBase64ImgToJobStatusDTO() throws ServiceException, DAOException, UnsupportedEncodingException {

		SetAvatarRequest avatarRequest = new SetAvatarRequest();
		avatarRequest.setAvatarBase64("1234");

		userService.setAvatar(users.get(1l), avatarRequest);

		List<JobOfferStatusDTO> offers = userService.getMyOffers(1l);
		logger.info(avatarRequest.getAvatarBase64());
		logger.info(offers);
		logger.info(offers.get(0).getId());
		assertEquals(avatarRequest.getAvatarBase64(), new String(offers.get(0).getBase64img()));
	}
}