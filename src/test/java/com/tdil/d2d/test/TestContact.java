package com.tdil.d2d.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.BasicConfigurator;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

public class TestContact {

//	private static final String AP_URL = "https://localhost:8443/d2d";
	private static final String AP_URL = "http://localhost:8080/d2d";

	@org.junit.Test
	public void test() {
		
		BasicConfigurator.configure();
		
//		fail("Not yet implemented");
		
		int idFirstContactMotive = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				//.header(new Header("Authorization", jwttoken))
				.get(AP_URL +"/api/contact/motives")
				.then().log().body().statusCode(200).extract().path("data[0].id");
		
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.body("{\"contactMotiveId\":"+idFirstContactMotive+",\"comment\":\"godoy app\"}")
				.post(AP_URL +"/api/contact")
				.then().log().body().statusCode(201).body("status", equalTo(201));
						
	}

}
