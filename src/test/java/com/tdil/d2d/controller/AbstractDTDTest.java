package com.tdil.d2d.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import org.apache.commons.lang3.RandomStringUtils;

public class AbstractDTDTest {

	protected static final String API_URL = "http://localhost:8080/d2d";

	protected ObjectMapper mapper = new ObjectMapper();

	protected String suffix = String.valueOf(System.currentTimeMillis() % 1000);
	protected String mobilePhone = RandomStringUtils.randomNumeric(11);
	protected String deviceId = RandomStringUtils.randomAlphabetic(20);
	protected String smsCode = "9999";

	protected String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "";
		}
	}


	protected RegistrationRequestA createRegistrationRequestA(String suffix, String mobilePhone, String deviceId) {
		RegistrationRequestA registrationA = new RegistrationRequestA();
		registrationA.setFirstname("Adriel");
		registrationA.setLastname("Paredes");
		registrationA.setEmail(suffix + "@m.com");
		registrationA.setDeviceId(deviceId);
		registrationA.setMobilePhone(mobilePhone);
		registrationA.setCompanyScreenName("MRG");
		registrationA.setTacAccepted(true);
		return registrationA;
	}
}
