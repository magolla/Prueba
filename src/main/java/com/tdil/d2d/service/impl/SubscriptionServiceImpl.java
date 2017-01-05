package com.tdil.d2d.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.controller.api.request.UseSponsorCodeRequest;
import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.ActivityAction;
import com.tdil.d2d.persistence.ActivityLog;
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

	@Autowired
	private SubscriptionDAO subscriptionDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityLogDAO activityLogDAO;

	@Override
	public boolean useSponsorCode(UseSponsorCodeRequest useSponsorCodeRequest) throws ServiceException {
		try {
			User user = userService.getLoggedUser();
			SponsorCode sponsorCode = subscriptionDAO.getSponsorCode(SponsorCode.class,
					useSponsorCodeRequest.getSponsorCode());
			// si es rc, y es de test, genero datos de test
			// Busco un sponsor code con ese codigo
			if (sponsorCode == null && !ServiceLocator.isProd()
					&& useSponsorCodeRequest.getSponsorCode().startsWith("TEST")) {
				Sponsor sponsor = subscriptionDAO.getSponsorByName(Sponsor.class, "TEST");
				if (sponsor == null) {
					sponsor = new Sponsor();
					sponsor.setName("TEST");
					subscriptionDAO.saveSponsor(sponsor);
				}
				sponsorCode = new SponsorCode();
				sponsorCode.setRemainingUses(10);
				sponsorCode.setSponsor(sponsor);
				sponsorCode.setUnits(30);
				sponsorCode.setTimeUnit(SubscriptionTimeUnit.DAY);
				subscriptionDAO.saveSponsorCode(sponsorCode);
			}
			if (sponsorCode == null) {
				return false;
			}
			if (sponsorCode.getRemainingUses() < 1) {
				return false;
			}
			List<Subscription> subscriptions = subscriptionDAO.listSubscriptions(user.getId());
			if (hasSubscription(subscriptions, sponsorCode)) {
				return false;
			}
			sponsorCode.setRemainingUses(sponsorCode.getRemainingUses() - 1);
			subscriptionDAO.saveSponsorCode(sponsorCode);
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_SUBSCRIPTION));
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
	public Subscription getActiveSubscription(long userID) throws ServiceException {
		try {
			User user = userService.getLoggedUser();
			List<Subscription> subscriptions = subscriptionDAO.listSubscriptions(user.getId());
			if (subscriptions == null || subscriptions.isEmpty()) {
				return null;
			} else {
				Subscription subscription = subscriptions.get(0);
				if (subscription.getExpirationDate().before(new Date())) {
					return null;
				} else {
					return subscription;
				}
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
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
