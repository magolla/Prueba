package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.controller.api.response.GenericResponse;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class AuthenticationRestControllerTest extends AbstractDTDTest {

	@Test
	public void testLoginSinUsuario() {

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

		Response response = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\"" + "" + "\",\"password\":\"" + deviceId + "\"}")
				.post(API_URL + "/api/auth")
				.then().log().body().statusCode(500).extract().response();

		assertEquals(9999, response.getBody().as(GenericResponse.class).getStatus());
	}

}