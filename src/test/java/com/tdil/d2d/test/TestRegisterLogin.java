package com.tdil.d2d.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import sun.misc.BASE64Decoder;

public class TestRegisterLogin {

	private static final String LICENSE = "1212121221";
	private static final String AP_URL = "https://localhost:8443/d2d";
//	private static final String AP_URL = "http://localhost:8080/d2d";

	@org.junit.Test
	public void test() {
		try {
			BasicConfigurator.configure();
			
	//		fail("Not yet implemented");
			
			String suffix = String.valueOf(System.currentTimeMillis() % 1000);
			
			String mobilePhone = RandomStringUtils.randomNumeric(11);
			String deviceId = RandomStringUtils.randomAlphabetic(20);
			String smsCode = "9999";
			
			// registro
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.body("{\"firstname\":\"marcos\",\"lastname\":\"godoy\","
							+ "\"email\":\"m"+suffix+"@m.com\","
							+ "\"deviceId\":\""+deviceId+"\",\"mobilePhone\":\""+mobilePhone+"\","
							+ "\"companyScreenName\":\"MRG\",\"tacAccepted\":true"
							+ "}")
					.post(AP_URL +"/api/user/registerA")
					.then().log().body().statusCode(201).body("status", equalTo(201))/*.
					and().time(lessThan(100L))*/;
			
			
			// Login
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\""+mobilePhone+"\",\"password\":\""+deviceId+"\"}")
					.post(AP_URL +"/api/auth")
					.then().log().body().statusCode(401);
			
			// Validate
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.body("{\"mobilePhone\":\""+mobilePhone+"\",\"deviceId\":\""+deviceId+"\",\"smsCode\":\""+smsCode+"\"}")
			.post(AP_URL +"/api/user/validate")
			.then().log().body().statusCode(200);
			
			String jwttokenOfferent = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\""+mobilePhone+"\",\"password\":\""+deviceId+"\"}")
					.post(AP_URL +"/api/auth")
					.then().log().body().statusCode(200).extract().path("token");
			Assert.assertNotNull(jwttokenOfferent);
			
			// Update regid
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent)).body("{\"androidRegId\":\"123456789\"}")
			.post(AP_URL +"/api/user/androidRegId")
			.then().log().body().statusCode(200);
			
			// Update regid
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent)).body("{\"iosPushId\":\"123456789\"}")
			.post(AP_URL +"/api/user/iosPushId")
			.then().log().body().statusCode(200);
			
			
			// Ocupaciones
			int idFirstOccupation = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					//.header(new Header("Authorization", jwttoken))
					.get(AP_URL +"/api/specialties/occupations")
					.then().log().body().statusCode(200).extract().path("data[0].id");
			
			// Especialidades
			int idFirstSpecialty = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					//.header(new Header("Authorization", jwttoken))
					.get(AP_URL +"/api/specialties/occupation/"+idFirstOccupation+"/specialties")
					.then().log().body().statusCode(200).extract().path("data[0].id");

			// Tareas
			int idFirstTask = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					//.header(new Header("Authorization", jwttoken))
					.get(AP_URL +"/api/specialties/specialty/"+idFirstSpecialty+"/tasks")
					.then().log().body().statusCode(200).extract().path("data[0].id");
			
			int idSecondTask = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					//.header(new Header("Authorization", jwttoken))
					.get(AP_URL +"/api/specialties/specialty/"+idFirstSpecialty+"/tasks")
					.then().log().body().statusCode(200).extract().path("data[1].id");
			
			ExtractableResponse<Response> extract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					//.header(new Header("Authorization", jwttoken))
					.get(AP_URL +"/api/geo/autocomplete?searchString=CABALLITO")
					.then().log().body().statusCode(200).extract();
			int idCaballito = extract.path("data[0].id");
			int idLevel = extract.path("data[0].level");
			
			
			// Creo una oferta temporal
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.body("{\"occupationId\":"+idFirstOccupation+",\"specialtyId\":"+idFirstSpecialty+","
							+ "\"taskId\":"+idFirstTask+","
							+ "\"geoLevelLevel\":"+idLevel+",\"geoLevelId\":"+idCaballito+","
							+ "\"offerDate\":\"20170813\","
							+ "\"offerHour\":\"1830\",\"comment\":\"Temporal\","
							+ "\"companyScreenName\":\"AA BB\",\"institutionType\":\"PRIVATE\",\"comment\":\"NA\","
							+ "\"tasks\":\"NA\",\"vacants\":1"
							+ "}")
					.post(AP_URL +"/api/temporaryOffer/create")
					.then().log().body().statusCode(201).body("status", equalTo(201));
			
			// Creo una oferta permanente
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.body("{\"occupationId\":"+idFirstOccupation+",\"specialtyId\":"+idFirstSpecialty+","
							+ "\"taskId\":"+idFirstTask+","
							+ "\"geoLevelLevel\":"+idLevel+",\"geoLevelId\":"+idCaballito+","
							+ "\"comment\":\"Permanente\","
							+ "\"title\":\"Medico clinico\",\"subtitle\":\"Meses de verano\","
							+ "\"companyScreenName\":\"AA BB\",\"institutionType\":\"PRIVATE\",\"comment\":\"NA\","
							+ "\"tasks\":\"NA\",\"vacants\":1"
							+ "}")
					.post(AP_URL +"/api/permanentOffer/create")
					.then().log().body().statusCode(201).body("status", equalTo(201));
			
			// Consulta de ofertas
			ExtractableResponse<Response> offerExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.get(AP_URL +"/api/user/offers")
					.then().log().body().statusCode(200).extract();
			int idOfferT = offerExtract.path("data[0].id");
			String comment0 = offerExtract.path("data[0].comment");
			Assert.assertNotEquals("TEMPORAL", comment0);
			int idOfferP = offerExtract.path("data[1].id");
			String comment1 = offerExtract.path("data[1].comment");
			Assert.assertNotEquals("PERMAMENT", comment1);
			
			// registro un nuevo usuario
			String mobilePhone1 = RandomStringUtils.randomNumeric(11);
			String deviceId1 = RandomStringUtils.randomAlphabetic(20);
			suffix = String.valueOf(System.currentTimeMillis() % 1000);
			// registro
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.body("{\"firstname\":\"marcos app\",\"lastname\":\"godoy app\","
							+ "\"email\":\"m"+suffix+"@m.com\","
							+ "\"deviceId\":\""+deviceId1+"\",\"mobilePhone\":\""+mobilePhone1+"\","
							+ "\"tacAccepted\":true"
							+ "}")
					.post(AP_URL +"/api/user/registerB")
					.then().log().body().statusCode(201).body("status", equalTo(201))/*.
					and().time(lessThan(100L))*/;
			// Validacion 
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.body("{\"mobilePhone\":\""+mobilePhone1+"\",\"deviceId\":\""+deviceId1+"\",\"smsCode\":\""+smsCode+"\"}")
			.post(AP_URL +"/api/user/validate")
			.then().log().body().statusCode(200);
			
			// Login
			String jwttokenApplicant = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\""+mobilePhone1+"\",\"password\":\""+deviceId1+"\"}")
					.post(AP_URL +"/api/auth")
					.then().log().body().statusCode(200).extract().path("token");
			Assert.assertNotNull(jwttokenApplicant);
			// Agrego specialidad
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"specialtyId\":"+idFirstSpecialty+"}")
			.post(AP_URL +"/api/user/specialty")
			.then().log().body().statusCode(201);
			// Agrego location
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"geoLevelLevel\":"+idLevel+",\"geoLevelId\":"+idCaballito+"}")
			.post(AP_URL +"/api/user/location")
			.then().log().body().statusCode(201);
			
			// Seteo Licencia
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"license\":\""+LICENSE+"\"}")
			.post(AP_URL +"/api/user/license")
			.then().log().body().statusCode(200);
			
			
			// Obtengo datos de subscripciones
			ExtractableResponse<Response> meExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/me")
					.then().log().body().statusCode(200).extract();
			boolean hasSubscription =(Boolean)meExtract.path("data.hasSubscription");
			Assert.assertFalse(hasSubscription);
			
			// Cargo un codigo de sponsor
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"sponsorCode\":\"TESTCODE\"}")
			.post(AP_URL +"/api/subscription/sponsor")
			.then().log().body().statusCode(200);
			
			meExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/me")
					.then().log().body().statusCode(200).extract();
			hasSubscription = (Boolean)(meExtract.path("data.hasSubscription"));
			String sponsor = meExtract.path("data.sponsorName");
			String subscriptionExpirationDate = meExtract.path("data.subscriptionExpirationDate");
			Assert.assertTrue(hasSubscription);
			Assert.assertEquals("TEST", sponsor);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 30);
//			Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()), subscriptionExpirationDate);
			
			// Perfil
			// Seteo el tipo de institucion
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"institutionType\":\"BOTH\"}")
			.post(AP_URL +"/api/user/profile/institutionType")
			.then().log().body().statusCode(200);
			
			// Agrego una tarea al perfil
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"taskId\":"+idFirstTask+"}")
			.post(AP_URL +"/api/user/profile/task")
			.then().log().body().statusCode(200);
			
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"taskId\":"+idSecondTask+"}")
			.post(AP_URL +"/api/user/profile/task")
			.then().log().body().statusCode(200);
			
			// Quito la primera
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"taskId\":"+idFirstTask+"}")
			.post(AP_URL +"/api/user/profile/task/delete")
			.then().log().body().statusCode(200);
			
			// Agrego el avatar
			String avatar = IOUtils.toString(TestRegisterLogin.class.getResourceAsStream("avatar.txt"));
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"avatarBase64\":\""+avatar+"\"}")
			.post(AP_URL +"/api/user/profile/avatar")
			.then().log().body().statusCode(200);
			// Obtengo el avatar
			String resultAvatar = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/profile/avatar").asString();
			Assert.assertEquals(avatar, resultAvatar);
			String test = resultAvatar.substring(resultAvatar.indexOf("base64,") + 7);
			System.out.println("Avatar dest " + System.getProperty("java.io.tmpdir"));
			
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte = decoder.decodeBuffer(test);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			BufferedImage image = ImageIO.read(bis);
			bis.close();
			// write the image to a file
			File outputfile = new File(System.getProperty("java.io.tmpdir")+ "/avatar" + System.currentTimeMillis()+ ".png");
			ImageIO.write(image, "png", outputfile);
			
			// Pido el perfil
			ExtractableResponse<Response> profileExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/profile")
					.then().log().body().statusCode(200).extract();
			String license = profileExtract.path("data.license");
			String inst = profileExtract.path("data.institutionType");
			int taskId = profileExtract.path("data.tasks[0].id");
			Assert.assertEquals(LICENSE, license);
			Assert.assertEquals("BOTH", inst);
			Assert.assertEquals(idSecondTask, taskId);
			
			// Busco el resumen de ofertas
			ExtractableResponse<Response> sumamryExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/offers/matchesSummary")
					.then().log().body().statusCode(200).extract();
			int temp = sumamryExtract.path("data.temporal");
			int perm = sumamryExtract.path("data.permament");
			Assert.assertTrue(1 < temp);
			Assert.assertTrue(1 < perm);
			
			// Busco ofertas que matcheen mi perfil
			int idMatchedOffer = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/temporalOffers/matches")
					.then().log().body().statusCode(200).extract().path("data[0].id");
			Assert.assertNotEquals(0, idOfferT);
			// aplico a la oferta
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"comment\":\"comentario\",\"cvPlain\":\"Soy un groso\"}")
			.post(AP_URL +"/api/user/offer/"+idOfferT+"/apply")
			.then().log().body().statusCode(201);
			
			// Busco ofertas permanentes que matcheen mi perfil
			int idMatchedOfferP = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/permanentOffers/matches")
					.then().log().body().statusCode(200).extract().path("data[0].id");
			// aplico a la oferta
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"comment\":\"comentario\",\"cvPlain\":\"Soy un groso\"}")
			.post(AP_URL +"/api/user/offer/"+idOfferP+"/apply")
			.then().log().body().statusCode(201);
			
			createUserAndApplyToOffer(idFirstSpecialty, idLevel, idCaballito, idOfferT);
			
			// El oferente busca los aplicantes
			ExtractableResponse<Response> applicationsExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.get(AP_URL +"/api/user/offer/"+idOfferT+"/applications")
					.then().log().body().statusCode(200).extract();
			
			int idApplication = applicationsExtract.path("data[0].id");
			int idApplication1 = applicationsExtract.path("data[1].id");
			
			// El oferente busca los aplicantes de la oferta permanente
			ExtractableResponse<Response> applicationsPExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.get(AP_URL +"/api/user/offer/"+idOfferP+"/applications")
					.then().log().body().statusCode(200).extract();
			
			int idApplicationP = applicationsExtract.path("data[0].id");
			Assert.assertNotEquals(0, idApplicationP);
			
			Assert.assertNotEquals(0, idApplication);
			// El oferente ve un postulante
			ExtractableResponse<Response> applicationExtract = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.get(AP_URL +"/api/user/offer/"+idOfferT+"/application/"+idApplication)
					.then().log().body().statusCode(200).extract();
			String firstname = applicationExtract.path("data.firstname");
			String lastname = applicationExtract.path("data.lastname");
			Assert.assertEquals("marcos app", firstname);
			Assert.assertEquals("godoy app", lastname);
			
			// El oferente rechaza un postulante
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
			.post(AP_URL +"/api/user/offer/"+idOfferT+"/application/"+idApplication1+"/reject")
			.then().log().body().statusCode(200);
			
			// El oferente acepta un postulante
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
			.post(AP_URL +"/api/user/offer/"+idOfferT+"/application/"+idApplication+"/accept")
			.then().log().body().statusCode(200);
			
			int idActivity = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenOfferent))
					.get(AP_URL +"/api/user/log")
					.then().log().body().statusCode(200).extract().path("data[0].id");
			
			// Test congress
			boolean isCongress = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/notifications")
					.then().log().body().statusCode(200).extract().path("congress");
			Assert.assertFalse(isCongress);
			// Set congress
			given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant)).body("{\"congress\":true}")
				.post(AP_URL +"/api/user/notifications")
				.then().log().body().statusCode(200);
			// Test congress
			isCongress = given().config(RestAssured.config().sslConfig(
					new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
					.header(new Header("Authorization", jwttokenApplicant))
					.get(AP_URL +"/api/user/notifications")
					.then().log().body().statusCode(200).extract().path("congress");
			Assert.assertTrue(isCongress);
	/*		
	 */
			
			
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
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	private void createUserAndApplyToOffer(int idFirstSpecialty, int idLevel, int idCaballito, int idOffer) {
		String mobilePhone1 = RandomStringUtils.randomNumeric(11);
		String deviceId1 = RandomStringUtils.randomAlphabetic(20);
		// registro un nuevo usuario
					String suffix = String.valueOf(System.currentTimeMillis() % 1000);
					// registro
					given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
							.body("{\"firstname\":\"marcos app\",\"lastname\":\"godoy app\","
									+ "\"email\":\"m"+suffix+"@m.com\","
									+ "\"deviceId\":\""+deviceId1+"\",\"mobilePhone\":\""+mobilePhone1+"\","
									+ "\"tacAccepted\":true"
									+ "}")
							.post(AP_URL +"/api/user/registerB")
							.then().log().body().statusCode(201).body("status", equalTo(201))/*.
							and().time(lessThan(100L))*/;
					// Validacion 
					given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
							.body("{\"mobilePhone\":\""+mobilePhone1+"\",\"deviceId\":\""+deviceId1+"\",\"smsCode\":\"9999\"}")
					.post(AP_URL +"/api/user/validate")
					.then().log().body().statusCode(200);
					// Login
					String jwttokenApplicant = given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json").body("{\"username\":\""+mobilePhone1+"\",\"password\":\""+deviceId1+"\"}")
							.post(AP_URL +"/api/auth")
							.then().log().body().statusCode(200).extract().path("token");
					Assert.assertNotNull(jwttokenApplicant);
					// Agrego specialidad
					given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
							.header(new Header("Authorization", jwttokenApplicant)).body("{\"specialtyId\":"+idFirstSpecialty+"}")
					.post(AP_URL +"/api/user/specialty")
					.then().log().body().statusCode(201);
					// Agrego location
					given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
							.header(new Header("Authorization", jwttokenApplicant)).body("{\"geoLevelLevel\":"+idLevel+",\"geoLevelId\":"+idCaballito+"}")
					.post(AP_URL +"/api/user/location")
					.then().log().body().statusCode(201);
					// Busco ofertas que matcheen mi perfil
					int idMatchedOffer = given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
							.header(new Header("Authorization", jwttokenApplicant))
							.get(AP_URL +"/api/user/temporalOffers/matches")
							.then().log().body().statusCode(200).extract().path("data[0].id");
					Assert.assertNotEquals(0, idOffer);
					// aplico a la oferta
					given().config(RestAssured.config().sslConfig(
							new SSLConfig().allowAllHostnames().relaxedHTTPSValidation())).contentType("application/json")
							.header(new Header("Authorization", jwttokenApplicant)).body("{\"comment\":\"comentario\",\"cvPlain\":\"Soy un groso\"}")
					.post(AP_URL +"/api/user/offer/"+idOffer+"/apply")
					.then().log().body().statusCode(201);
					
		
	}

}
