package com.tdil.d2d.service;

import com.tdil.d2d.controller.api.request.UseSponsorCodeRequest;
import com.tdil.d2d.exceptions.ServiceException;

public interface SubscriptionService {

	public boolean useSponsorCode(UseSponsorCodeRequest useSponsorCodeRequest) throws ServiceException;
}
