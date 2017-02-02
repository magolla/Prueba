package com.tdil.d2d.jobs;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.UserService;

public class NotifyNewOffersJob implements Job {

	@Autowired
    private UserService userService;

	public NotifyNewOffersJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("NotifyNewOffersJob is executing.");

		//Do injection with spring
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		try {
			List<Long> offerIds = userService.getOfferIdsByDate(new Date());
			
			for (Long offerId : offerIds) {
				//TODO Check if he has already been notified
				userService.notifyToMatchedUsers(offerId);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new JobExecutionException(e);
		}
	}
	
	
}