package com.tdil.d2d.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;

public class TestRegisterLogin {

	private static final String AP_URL = "https://localhost:8443/d2d";
//	private static final String AP_URL = "http://localhost:8080/d2d";

	@org.junit.Test
	public void test() {
		
		BasicConfigurator.configure();
		
//		fail("Not yet implemented");
		
		String suffix = String.valueOf(System.currentTimeMillis() % 1000);
		
		// registro
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.body("{\"password\":\"123456\","
						+ "\"email\":\"m"+suffix+"@m.com\","
						+ "\"deviceId\":\"zyryr23123\",\"phoneNumber\":\"0221232323\"}}")
				.post(AP_URL +"/api/user/register")
				.then().log().body().statusCode(201).body("status", equalTo(201))/*.
				and().time(lessThan(100L))*/;
		
		// Login
		String jwttoken = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\"m"+suffix+"@m.com\",\"password\":\"123456\"}}")
				.post(AP_URL +"/api/auth")
				.then().log().body().statusCode(200).extract().path("token");
		Assert.assertNotNull(jwttoken);
		
		// Update regid
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").header(new Header("Authorization", jwttoken)).body("{\"androidRegId\":\"123456789\"}}")
		.post(AP_URL +"/api/user/androidRegId")
		.then().log().body().statusCode(200);
		
		// Update regid
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").header(new Header("Authorization", jwttoken)).body("{\"iosPushId\":\"123456789\"}}")
		.post(AP_URL +"/api/user/iosPushId")
		.then().log().body().statusCode(200);
		
		
//		login
//		
//		create job offer
//		
//		get offfers
//		
//		get activity log
		
		/*
		// Create Credit Card
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
			.header(new Header("Authorization", jwttoken))
			.body("{\"cardNumber\":\"4319407297370202\",\"expirationDate\":\"1020\",\"securityCode\":\"123\"}}")
			.post(AP_URL +"/api/user/creditCard")
			.then().log().body().statusCode(200);
		
		// Pedido de tarjetas de credito
		int idCreditCard = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.header(new Header("Authorization", jwttoken))
				.get(AP_URL +"/api/user/creditCards")
				.then().log().body().statusCode(200).extract().path("[0].id");
		
		// Request Payment
		int id = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
			.header(new Header("Authorization", jwttoken))
			.body("{\"paymentDestinationId\":1,\"installments\":1,\"amount\":9000}}")
			.post(AP_URL +"/api/user/payment")
			.then().log().body().statusCode(200).extract().path("id");
		
		// Accept Payment
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
			.header(new Header("Authorization", jwttoken))
			.body("{\"password\":\"marcos1\",\"cardId\":"+idCreditCard+"}")
			.post(AP_URL +"/api/user/payment/"+id+"/accept")
			.then().log().body().statusCode(200).extract().path("id");
		
		id = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
			.header(new Header("Authorization", jwttoken))
			.body("{\"paymentDestinationId\":1,\"installments\":1,\"amount\":8000}}")
			.post(AP_URL +"/api/user/payment")
			.then().log().body().statusCode(200).extract().path("id");
		
		// Accept Payment
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
			.header(new Header("Authorization", jwttoken))
			.body("{\"password\":\"marcos1\"}")
			.post(AP_URL +"/api/user/payment/"+id+"/deny")
			.then().log().body().statusCode(200).extract().path("id");
		
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
				.header(new Header("Authorization", jwttoken))
				.get(AP_URL +"/api/user/payments")
				.then().log().body().statusCode(200);
		
		// Borrado de tarjeta
		
		
		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
			.header(new Header("Authorization", jwttoken))
			.post(AP_URL +"/api/user/creditCard/"+idCreditCard+"/delete")
			.then().log().body().statusCode(200);
		
		*/
		//System.out.println(response);
	}

}
