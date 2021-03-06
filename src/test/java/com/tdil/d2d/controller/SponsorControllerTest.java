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

public class SponsorControllerTest extends AbstractDTDTest {

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
				.body("{\"mobilePhone\":\"" + mobilePhone + "\",\"deviceId\":\"" + deviceId + "\",\"smsCode\":\"" + smsCode + "\"}")
				.post(API_URL + "/api/user/validate")
				.then().log().body().statusCode(200);

		String jwttokenOfferent = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\"" + mobilePhone + "\",\"password\":\"" + deviceId + "\"}")
				.post(API_URL + "/api/auth")
				.then().log().body().statusCode(200).extract().path("token");
		Assert.assertNotNull(jwttokenOfferent);


		CreateSponsorRequest createSponsorRequest = new CreateSponsorRequest("FARMACO");


		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.body(toJson(createSponsorRequest))
				.header(new Header("Authorization", jwttokenOfferent))
				.body(toJson(createSponsorRequest))
				.post(API_URL + "/api/sponsors")
				.then().log().body().statusCode(200).body("status", equalTo(200));


		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.header(new Header("Authorization", jwttokenOfferent))
				.get(API_URL + "/api/sponsors")
				.then().log().body().statusCode(200).body("status", equalTo(200));


	}

}