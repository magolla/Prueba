package com.tdil.d2d.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.BasicConfigurator;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

public class TestGEO {

	private static final String AP_URL = "https://localhost:8443/d2d";
//	private static final String AP_URL = "http://localhost:8080/d2d";

	@org.junit.Test
	public void test() {
		
		BasicConfigurator.configure();
		
//		fail("Not yet implemented");
		
		String suffix = String.valueOf(System.currentTimeMillis());
		
		// registro
		Integer countryId = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.get(AP_URL +"/api/countries")
				.then().log().body().statusCode(200).body("[0].isoCode2", equalTo("AR")).extract().path("[0].id");/*.
				and().time(lessThan(100L))*/;
		
		Integer personalIdTypeId = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.get(AP_URL +"/api/country/" + countryId + "/identityCardTypes")
				.then().log().body().statusCode(200).body("[0].name", equalTo("DNI")).extract().path("[0].id");
						
	}

}
