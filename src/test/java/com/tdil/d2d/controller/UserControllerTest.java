package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.request.CreateSponsorRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by adriel on 1/21/17.
 */
public class UserControllerTest extends AbstractDTDTest {


	@Test
	public void test() {

		RegistrationRequestA registrationA = createRegistrationRequestA(suffix, mobilePhone, deviceId);

		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.body(toJson(registrationA))
				.post(API_URL + "/api/user/registerA")
				.then().log().body().statusCode(201).body("status", equalTo(201))/*.
					and().time(lessThan(100L))*/;

		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.body(toJson(registrationA))
				.post(API_URL + "/api/user/registerA")
				.then().log().body().statusCode(201).body("status", equalTo(201))/*.
					and().time(lessThan(100L))*/;


	}


}