package com.tdil.d2d.service;

import com.tdil.d2d.controller.api.request.RedeemSponsorCodeRequest;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.User;

public interface SubscriptionService {

	public boolean useSponsorCode(RedeemSponsorCodeRequest useSponsorCodeRequest) throws ServiceException;

	public Subscription getActiveSubscription(long userID);

	public Subscription register(User user, int duration) throws ServiceException;

	public Subscription registerByDays(User user, int duration) throws ServiceException;
}

