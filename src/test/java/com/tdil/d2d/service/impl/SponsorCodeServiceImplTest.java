package com.tdil.d2d.service.impl;

import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.*;
import com.tdil.d2d.service.SponsorCodeService;
import com.tdil.d2d.utils.SponsorCodeGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SponsorCodeServiceImplTest {

	private SponsorCodeService sponsorCodeService;

	@Mock
	private SubscriptionDAO subscriptionDAO;

	@Mock
	private UserDAO userDAO;

	private Sponsor sponsor;

	private List<SponsorCode> codes;
	private User user;

	@Before
	public void setUp() throws DAOException {

		codes = new ArrayList<>();
		sponsor = new Sponsor();
		user = new User();

//		when(userDAO.getById(any(), anyLong())).thenReturn(user);

		when(subscriptionDAO.getSponsorById(anyLong())).thenReturn(sponsor);

		doAnswer((InvocationOnMock mock) -> {
			List<SponsorCode> sponsorCodes = mock.getArgument(0);
			codes.addAll(sponsorCodes);
			return sponsorCodes;
		}).when(subscriptionDAO).saveSponsorCode(anyList());

		doAnswer((InvocationOnMock mock) ->
				codes.stream().filter((code) -> code.getCode().equals(mock.getArgument(1))).collect(Collectors.toList()).get(0)
		).when(subscriptionDAO).getSponsorCode(any(), anyString());

		this.sponsorCodeService = new SponsorCodeServiceImpl(new SponsorCodeGenerator(), subscriptionDAO, userDAO);
	}

	@Test
	public void testGenerateOneSponsorCode() {
		List<SponsorCode> codes = this.sponsorCodeService.generateSponsorCodes(1, 1, 1, SubscriptionTimeUnit.DAY);

		assertEquals(1, codes.size());

		SponsorCode code = codes.get(0);

		assertEquals(1, code.getUnits());
		assertNotNull(code.getCreationDate());
		assertEquals(32, code.getCode().length());
		assertEquals(sponsor, code.getSponsor());
		assertEquals(SubscriptionTimeUnit.DAY, code.getTimeUnit());
	}

	@Test
	public void testGenerateSeveralSponsorCodes() {
		List<SponsorCode> codes = this.sponsorCodeService.generateSponsorCodes(1, 10, 1, SubscriptionTimeUnit.DAY);

		assertEquals(10, codes.size());

		SponsorCode code = codes.get(0);

		assertEquals(1, code.getUnits());
		assertNotNull(code.getCreationDate());
		assertEquals(32, code.getCode().length());
		assertEquals(sponsor, code.getSponsor());
		assertEquals(SubscriptionTimeUnit.DAY, code.getTimeUnit());
	}

	@Test
	public void testUseSponsorCode() throws DAOException {

		SponsorCode code = this.sponsorCodeService.generateSponsorCodes(1, 1, 1, SubscriptionTimeUnit.DAY).get(0);
		SponsorCode sponsorCode = this.sponsorCodeService.consumeSponsorCode(user, code.getCode());
		assertEquals(false, sponsorCode.isEnabled());
		assertNotNull(sponsorCode.getConsumeDate());
		assertEquals(user, sponsorCode.getConsumer());
		verify(subscriptionDAO).saveSubscription(any(Subscription.class));
	}

}