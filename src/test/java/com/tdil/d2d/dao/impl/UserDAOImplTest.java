package com.tdil.d2d.dao.impl;

import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.config.DaoConfig;
import com.tdil.d2d.config.PersistenceConfiguration;
import com.tdil.d2d.config.WebInitializer;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebInitializer.class, PersistenceConfiguration.class, DaoConfig.class})
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDAOImplTest {

	private Logger logger = LoggerFactory.getLogger(UserDAOImplTest.class);

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("spring.profiles.active", "test");
		System.setProperty("log4j.configurationFile", "conf/app/test/log4j2.xml");

	}

	@Autowired
	private UserDAO userDAO;

	@Test(expected = DAOException.class)
	public void testExceptionWhenSavingDuplicatedMobilePhones() throws DAOException {
		User user1 = new User();
		user1.setMobilePhone("123");
		User user2 = new User();
		user2.setMobilePhone("123");

		userDAO.save(user1);
		userDAO.save(user2);

		System.out.println(user1.getId());
		System.out.println(user2.getId());
	}

	@Test
	public void testSaveImageWithRightSize() throws DAOException {
		//256k - 1 byte
		int max_size = 262143;

		User user = new User();
		byte[] b = new byte[max_size];
		new Random().nextBytes(b);

		logger.info("{} kb", b.length / 1024);
		
		Media media = new Media();
		media.setType(MediaType.AVATAR);
		media.setData(Base64.decodeBase64(b));
		user.setAvatar(media);
		
		this.userDAO.save(media);
		this.userDAO.save(user);
		
	}

	@Test(expected = DAOException.class)
	public void testExceptionWhenImageIsTooBig() throws DAOException {
		int max_size = 282700;

		User user = new User();
		byte[] b = new byte[max_size];
		new Random().nextBytes(b);

		logger.info("{} kb", b.length / 1024);
		Media media = new Media();
		media.setType(MediaType.AVATAR);
		media.setData(Base64.decodeBase64(b));
		user.setAvatar(media);
		
		this.userDAO.save(media);
		this.userDAO.save(user);

	}

}