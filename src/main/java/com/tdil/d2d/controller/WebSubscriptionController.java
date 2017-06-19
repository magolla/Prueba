package com.tdil.d2d.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.controller.api.request.CreateSubscriptionRequest;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.service.SponsorCodeService;

@Controller
public class WebSubscriptionController {
	
	@Autowired
	private SponsorCodeService sponsorCodeService;
	
	@RequestMapping(value = {"/suscribirme"} , method = RequestMethod.GET)
	public ModelAndView getSubscription() {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("subscription");

		return model;

	}
	
	@RequestMapping(value = {"/suscribirme"} , method = RequestMethod.POST)
	public ModelAndView postSubscription(@ModelAttribute("formRequest") CreateSubscriptionRequest createSubscriptionRequest) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("subscription");
		
		try{
			String number = createSubscriptionRequest.getNumber();
			String code = createSubscriptionRequest.getCode();
			
			if(number==null || "".equals(number)){
				model.addObject("error", "Número Obligatorio.");
				return model;
	         }
			
			if(code==null || "".equals(code)){
				model.addObject("error", "Código Obligatorio.");
				return model;
	         }
			
			Subscription subscription = sponsorCodeService.consumeWebSponsorCode(number, code);
			if(subscription==null){
				model.addObject("error", "Código Inválido.");
				return model;
			}
			model.addObject("msg", "La suscripción fue realizada exitosamente para el número: " + number + ".");
			
			return  model;
	
		} catch (ServiceException e) {
			model.addObject("error", "Hubo un error. Por favor intentá nuevamente mas tarde.");
			return model;
		}
		
	}
	
	
}
