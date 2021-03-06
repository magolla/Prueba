package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.bo.dto.FilterJobOfferDailyReportDTO;
import com.tdil.d2d.bo.dto.FilterJobOfferReportDTO;
import com.tdil.d2d.bo.dto.FilterSubscriptionReportDTO;
import com.tdil.d2d.bo.dto.JobOfferDailyReportDTO;
import com.tdil.d2d.bo.dto.JobOfferReportDTO;
import com.tdil.d2d.bo.dto.SubscriptionReportDTO;
import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Subscription;

public interface BOReportsService {

	List<BOJobOfferDTO> getAllJobOffers() throws ServiceException;
	
	SubscriptionReportDTO getSubscriptionReportDTO(FilterSubscriptionReportDTO filterDTO) throws ServiceException;
	
	JobOfferReportDTO getJobOfferReportDTO(FilterJobOfferReportDTO filterDTO) throws ServiceException;
	
	JobOfferDailyReportDTO getJobOfferDailyReportDTO(FilterJobOfferDailyReportDTO filterDTO) throws ServiceException;

	List<Subscription> getAllActiveSuscriptions();
}
