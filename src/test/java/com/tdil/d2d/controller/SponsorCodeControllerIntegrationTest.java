package com.tdil.d2d.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdil.d2d.controller.api.request.GenerateSponsorCodesRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;


public class SponsorCodeControllerIntegrationTest {

	private static final String API_URL = "http://localhost:8080/d2d";
	private ObjectMapper mapper = new ObjectMapper();


	@Test
	public void testGenerateSponsorCodes() {

		String suffix = String.valueOf(System.currentTimeMillis() % 1000);
		String mobilePhone = RandomStringUtils.randomNumeric(11);
		String deviceId = RandomStringUtils.randomAlphabetic(20);
		String smsCode = "9999";

		RegistrationRequestA registrationA = createRegistrationRequestA(suffix, mobilePhone, deviceId);

		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.body(toJson(registrationA))
				.post(API_URL + "/api/user/registerA")
				.then().log().body().statusCode(201).body("status", equalTo(201))/*.
					and().time(lessThan(100L))*/;

		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.body("{\"mobilePhone\":\"" + mobilePhone + "\",\"deviceId\":\"" + deviceId + "\",\"smsCode\":\"" + smsCode + "\"}")
				.post(API_URL + "/api/user/validate")
				.then().log().body().statusCode(200);

		String jwttokenOfferent = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\"" + mobilePhone + "\",\"password\":\"" + deviceId + "\"}")
				.post(API_URL + "/api/auth")
				.then().log().body().statusCode(200).extract().path("token");
		Assert.assertNotNull(jwttokenOfferent);


		GenerateSponsorCodesRequest request = new GenerateSponsorCodesRequest();
		request.setCodesCount(1);
		request.setSponsorId(1);
		request.setTimeUnits(SubscriptionTimeUnit.DAY.toString());
		request.setUnits(1);


		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.body(toJson(request))
				.header(new Header("Authorization", jwttokenOfferent))
				.post(API_URL + "/api/codes/generate")
				.then().log().body().statusCode(200).body("status", equalTo(200));


		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.header(new Header("Authorization", jwttokenOfferent))
				.get(API_URL + "/api/codes/1")
				.then().log().body().statusCode(200).body("status", equalTo(200));
	}

	private String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	private RegistrationRequestA createRegistrationRequestA(String suffix, String mobilePhone, String deviceId) {
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