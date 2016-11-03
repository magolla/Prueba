package com.tdil.d2d.service;

import com.tdil.d2d.controller.api.request.UseSponsorCodeRequest;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Subscription;

public interface SubscriptionService {

	public boolean useSponsorCode(UseSponsorCodeRequest useSponsorCodeRequest) throws ServiceException;

	public Subscription getActiveSubscription(long userID) throws ServiceException;
}
