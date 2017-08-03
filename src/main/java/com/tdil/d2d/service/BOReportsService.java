package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.SubscriptionReportDTO;
import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.exceptions.ServiceException;

public interface BOReportsService {

	List<BOJobOfferDTO> getAllJobOffers() throws ServiceException;
	
	SubscriptionReportDTO getSubscriptionReportDTO()  throws ServiceException;
}
