package com.tdil.d2d.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.service.UserService;

public class NotifySuscriptionExpirancy implements Job {

	@Autowired
    private UserService userService;
	
	@Autowired
    private SubscriptionService subscriptionService;

	public NotifySuscriptionExpirancy() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("NotifySuscriptionExpirancy is executing.");

		//Do injection with spring
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		try {
			subscriptionService.getSuscriptionCloseExpire();
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new JobExecutionException(e);
		}
	}
	
	
}