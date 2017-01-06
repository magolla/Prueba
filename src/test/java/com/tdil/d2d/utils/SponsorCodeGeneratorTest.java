package com.tdil.d2d.utils;

import com.tdil.d2d.persistence.Sponsor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class SponsorCodeGeneratorTest {

	private Logger logger = LoggerFactory.getLogger(SponsorCodeGeneratorTest.class);
	private SponsorCodeGenerator generator = new SponsorCodeGenerator();

	@Test
	public void generteCode() {

		Sponsor sponsor = new Sponsor();
		sponsor.setId(1);

		String code = generator.generate(sponsor);
		logger.info(code);
		assertEquals(32, code.length());
	}

}