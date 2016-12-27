package com.tdil.d2d.service.impl;

import com.tdil.d2d.config.*;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.EmailService;
import com.tdil.d2d.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserServiceImplTest.TestConfiguration.class, WebSecurityConfig.class, PersistenceConfiguration.class})
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceImplTest {

    private Logger logger = LogManager.getLogger(UserServiceImplTest.class);

    @Configurable
    @ComponentScan(basePackages = {"com.tdil.d2d"}, excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class))
    static class TestConfiguration {

        @Bean(name = "emailService")
        public EmailService emailService() {
            return mock(EmailService.class);
        }
    }


    @BeforeClass
    public static void beforeClass() {
        System.setProperty("spring.profiles.active", "test");
    }

    @Autowired
    private UserService userService;

    @Test
    public void testExceptionWhenSavingDuplicatedMobilePhones() throws ServiceException {

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


}