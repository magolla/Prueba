package com.tdil.d2d.controller;

import com.tdil.d2d.controller.api.request.CreateNoteRequest;
import com.tdil.d2d.controller.api.request.IdRequest;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.persistence.NoteCategory;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NoteControllerTest extends AbstractDTDTest {

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


		CreateNoteRequest note = new CreateNoteRequest();
		note.setTitle("title");
		note.setSubtitle("subtitle");
		note.setContent("This is article content");
		note.setCategory(NoteCategory.CAT_1.toString());
		note.setExpirationDate(new Date());

		int id = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.body(toJson(note))
				.header(new Header("Authorization", jwttokenOfferent))
				.body(toJson(note))
				.post(API_URL + "/api/notes")
				.then().log().body().statusCode(200).body("status", equalTo(200)).extract().path("data.id");

		int occupationId = given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.body(toJson(note))
				.header(new Header("Authorization", jwttokenOfferent))
				.body(toJson(note))
				.post(API_URL + "/api/specialties/occupations")
				.then().log().body().statusCode(200).body("status", equalTo(200)).extract().path("data[0].id");

		IdRequest request = new IdRequest();
		request.setId(Long.valueOf(occupationId));

		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.body(toJson(note))
				.header(new Header("Authorization", jwttokenOfferent))
				.body(toJson(request))
				.post(API_URL + "/api/notes/" + id + "/occupations")
				.then().log().body().statusCode(200).body("status", equalTo(200));

		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.body(toJson(note))
				.header(new Header("Authorization", jwttokenOfferent))
				.body(toJson(request))
				.post(API_URL + "/api/notes/" + id + "/specialities")
				.then().log().body().statusCode(200).body("status", equalTo(200));


		given().config(RestAssured.config().sslConfig(
				new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
				.contentType(MediaType.APPLICATION_JSON.toString())
				.header(new Header("Authorization", jwttokenOfferent))
				.get(API_URL + "/api/notes?category=CAT_1")
				.then().log().body().statusCode(200).body("status", equalTo(200));

	}

}