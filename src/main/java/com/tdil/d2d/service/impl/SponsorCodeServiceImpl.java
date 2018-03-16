package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.SponsorCodeService;
import com.tdil.d2d.utils.SponsorCodeGenerator;

@Transactional
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
	public Subscription consumeSponsorCode(User user, String code) {
		try {
			SponsorCode sponsorCode = this.subscriptionDAO.getSponsorCode(SponsorCode.class, code);

			if(sponsorCode==null || !sponsorCode.isEnabled()){
				return null;
			}
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

			return subscription;
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

		switch (sponsorCode.getTimeUnit()) {
		case DAY:
			cal.add(Calendar.DAY_OF_YEAR, sponsorCode.getUnits());
			break;
		case MONTH:
			cal.add(Calendar.MONTH, sponsorCode.getUnits());
			break;
		case YEAR:
			cal.add(Calendar.YEAR, sponsorCode.getUnits());
			break;
		default:
			break;
		}

		return cal.getTime();
	}



	@Override
	public Subscription consumeWebSponsorCode(String mobilePhone, String code)  throws ServiceException{
		try{

			User user = userDAO.getUserByMobilePhone(mobilePhone);

			if(user==null){
				user = register(mobilePhone);
			}
			Subscription subscription = this.consumeSponsorCode(user, code);

			return subscription;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public User register(String mobilePhone) throws ServiceException {
		try {
			User user = new User();
			user.setMobilePhone(mobilePhone);
			Date registrationDate = new Date();
			user.setCreationDate(registrationDate);

			this.userDAO.save(user);

			return user;
		} catch (DAOException e){
			throw new ServiceException(e);
		}
	}



	@Override
	public SponsorCode validateSponsorCode(String subscriptionCode) throws DAOException {
			SponsorCode sponsorCode = this.subscriptionDAO.getSponsorCode(SponsorCode.class, subscriptionCode);
			return sponsorCode;
	}	

}
