package com.tdil.d2d.test;

import org.apache.log4j.BasicConfigurator;

import com.tdil.d2d.persistence.ActivityActionEnum;

public class TestActivityAction {

	//	private static final String AP_URL = "https://localhost:8443/d2d";
	private static final String AP_URL = "http://localhost:8080/d2d";

	@org.junit.Test
	public void test() {

		BasicConfigurator.configure();

		System.out.println(ActivityActionEnum.ACCEPT_OFFER);
		System.out.println(ActivityActionEnum.ACCEPT_OFFER.getMessage());
		System.out.println(ActivityActionEnum.ACCEPT_OFFER.getValue());
	}

}
