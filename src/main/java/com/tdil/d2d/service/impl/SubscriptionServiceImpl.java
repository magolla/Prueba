package com.tdil.d2d.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.request.RedeemSponsorCodeRequest;
import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;
import com.tdil.d2d.utils.ServiceLocator;

@Transactional
@Service()
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SessionService sessionService;
	private final SubscriptionDAO subscriptionDAO;

	@Autowired
	public SubscriptionServiceImpl(SessionService sessionService, SubscriptionDAO subscriptionDAO) {
		this.sessionService = sessionService;
		this.subscriptionDAO = subscriptionDAO;
	}

	/**
	 * Deprecado, utilizar {@link com.tdil.d2d.service.SponsorCodeService.consumeSponsorCode()}
	 *
	 * @param useSponsorCodeRequest
	 * @return
	 * @throws ServiceException
	 */
	@Deprecated
	@Override
	public boolean useSponsorCode(RedeemSponsorCodeRequest useSponsorCodeRequest) throws ServiceException {
		try {
			User user = sessionService.getUserLoggedIn();
			SponsorCode sponsorCode = subscriptionDAO.getSponsorCode(SponsorCode.class, useSponsorCodeRequest.getSponsorCode());
			// si es rc, y es de test, genero datos de test
			// Busco un sponsor code con ese codigo
			if (sponsorCode == null && !ServiceLocator.isProd() && useSponsorCodeRequest.getSponsorCode().startsWith("TEST")) {
				Sponsor sponsor = subscriptionDAO.getSponsorByName(Sponsor.class, "TEST");
				if (sponsor == null) {
					sponsor = new Sponsor();
					sponsor.setName("TEST");
					subscriptionDAO.saveSponsor(sponsor);
				}
				sponsorCode = new SponsorCode();
//				sponsorCode.setRemainingUses(10);
				sponsorCode.setSponsor(sponsor);
				sponsorCode.setUnits(30);
				sponsorCode.setTimeUnit(SubscriptionTimeUnit.DAY);
				subscriptionDAO.saveSponsorCode(sponsorCode);
			}
			if (sponsorCode == null) {
				return false;
			}
//			if (sponsorCode.getRemainingUses() < 1) {
//				return false;
//			}
			List<Subscription> subscriptions = subscriptionDAO.listSubscriptions(user.getId());
			if (hasSubscription(subscriptions, sponsorCode)) {
				return false;
			}
//			sponsorCode.setRemainingUses(sponsorCode.getRemainingUses() - 1);
			subscriptionDAO.saveSponsorCode(sponsorCode);

			Subscription subscription = new Subscription();
			subscription.setSponsorCode(sponsorCode);
			subscription.setExpirationDate(getExpirationDate(Calendar.getInstance(), sponsorCode));
			subscription.setUser(user);
			subscription.setCreationDate(new Date());
			subscriptionDAO.saveSubscription(subscription);
			return true;
		} catch (Exception e) {
			LoggerManager.error(this, e);
			return false;
		}
	}

	@Override
	public Subscription getActiveSubscription(long userID) {
		try {
			User user = sessionService.getUserLoggedIn();
			List<Subscription> subscriptions = subscriptionDAO.listSubscriptions(user.getId());
			if (subscriptions == null || subscriptions.isEmpty()) {
				throw new DTDException(ExceptionDefinition.DTD_2003, String.valueOf(userID));
			} else {
				Subscription subscription = subscriptions.get(0);
				if (subscription.getExpirationDate().before(new Date())) {
					throw new DTDException(ExceptionDefinition.DTD_2003, String.valueOf(userID));
				} else {
					return subscription;
				}
			}
		} catch (DAOException e) {
			throw new DTDException(ExceptionDefinition.DTD_2002, e, String.valueOf(userID));
		}
	}

	private Date getExpirationDate(Calendar instance, SponsorCode sponsorCode) {
		Calendar cal = Calendar.getInstance();
		cal = sponsorCode.getTimeUnit().add(cal, sponsorCode.getUnits());
		return cal.getTime();
	}

	private boolean hasSubscription(List<Subscription> subscriptions, SponsorCode sponsorCode) {
		for (Subscription s : subscriptions) {
			if (s.getSponsorCode().getCode().equals(s.getSponsorCode().getCode())) {
				return true;
			}
		}
		return false;
	}
}
