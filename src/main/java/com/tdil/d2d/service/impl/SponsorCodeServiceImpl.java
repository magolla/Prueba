package com.tdil.d2d.service.impl;

import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.persistence.*;
import com.tdil.d2d.service.SponsorCodeService;
import com.tdil.d2d.utils.SponsorCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SponsorCodeServiceImpl implements SponsorCodeService {

	private final SponsorCodeGenerator sponsorCodeGenerator;
	private final SubscriptionDAO subscriptionDAO;
	private final UserDAO userDAO;

	@Autowired
	public SponsorCodeServiceImpl(SponsorCodeGenerator sponsorCodeGenerator, SubscriptionDAO subscriptionDAO, UserDAO userDAO) {
		this.sponsorCodeGenerator = sponsorCodeGenerator;
		this.subscriptionDAO = subscriptionDAO;
		this.userDAO = userDAO;
	}

	@Override
	public List<SponsorCode> generateSponsorCodes(long sponsorId, int codesCount, int units, SubscriptionTimeUnit timeUnit) {

		List<SponsorCode> codes = new ArrayList<>();

		Sponsor sponsor = subscriptionDAO.getSponsorById(sponsorId);

		for (int i = 0; i < codesCount; i++) {
			String code = this.sponsorCodeGenerator.generate(sponsor);
			SponsorCode sponsorCode = new SponsorCode();
			sponsorCode.setCode(code);
			sponsorCode.setEnabled(true);
			sponsorCode.setCreationDate(new Date());
			sponsorCode.setSponsor(sponsor);
			sponsorCode.setTimeUnit(timeUnit);
			sponsorCode.setUnits(units);
			codes.add(sponsorCode);
		}

		return subscriptionDAO.saveSponsorCode(codes);

	}

	@Override
	public SponsorCode consumeSponsorCode(User user, String code) {
		try {
			SponsorCode sponsorCode = this.subscriptionDAO.getSponsorCode(SponsorCode.class, code);
			sponsorCode.setEnabled(false);
			sponsorCode.setConsumer(user);
			sponsorCode.setConsumeDate(new Date());
			this.subscriptionDAO.saveSponsorCode(sponsorCode);

			Subscription subscription = new Subscription();
			subscription.setSponsorCode(sponsorCode);
			subscription.setExpirationDate(getExpirationDate(Calendar.getInstance(), sponsorCode));
			subscription.setUser(user);
			subscription.setCreationDate(new Date());
			subscriptionDAO.saveSubscription(subscription);

			return sponsorCode;
		} catch (DAOException e) {
			throw new DTDException(ExceptionDefinition.DTD_1000, e, code);
		}
	}

	@Override
	public List<SponsorCode> listSponsorCodesBySponsorId(long sponsorId) {
		List<SponsorCode> codes = this.subscriptionDAO.listSponsorCodes(sponsorId);
		return codes;
	}

	protected Date getExpirationDate(Calendar instance, SponsorCode sponsorCode) {
		Calendar cal = Calendar.getInstance();
		cal = sponsorCode.getTimeUnit().add(cal, sponsorCode.getUnits());
		return cal.getTime();
	}

}