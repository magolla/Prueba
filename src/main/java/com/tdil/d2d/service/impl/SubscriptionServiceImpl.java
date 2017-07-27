package com.tdil.d2d.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tdil.d2d.controller.api.dto.InAppPurchaseDTO;
import com.tdil.d2d.controller.api.dto.VerifyReceiptResultDTO;
import com.tdil.d2d.controller.api.request.ReceiptSuscriptionRequest;
import com.tdil.d2d.controller.api.request.RedeemSponsorCodeRequest;
import com.tdil.d2d.controller.api.response.UserReceiptResponse;
import com.tdil.d2d.dao.ActivityLogDAO;
import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.dao.SystemPropertyDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.ActivityAction;
import com.tdil.d2d.persistence.ActivityLog;
import com.tdil.d2d.persistence.Receipt;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.persistence.SystemProperty;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.SessionService;
import com.tdil.d2d.service.SubscriptionService;
import com.tdil.d2d.utils.Constants;
import com.tdil.d2d.utils.LoggerManager;
import com.tdil.d2d.utils.ServiceLocator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Transactional
@Service()
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SessionService sessionService;
	private final SubscriptionDAO subscriptionDAO;
	private final ActivityLogDAO activityLogDAO;
	private final UserDAO userDAO;

	@Autowired
	private SystemPropertyDAO systemPropertyDAO;

	@Autowired
	public SubscriptionServiceImpl(SessionService sessionService, SubscriptionDAO subscriptionDAO,
			ActivityLogDAO activityLogDAO,
			UserDAO userDAO) {
		this.sessionService = sessionService;
		this.subscriptionDAO = subscriptionDAO;
		this.activityLogDAO = activityLogDAO;
		this.userDAO = userDAO;
	}

	/**
	 * Deprecado, utilizar
	 * {@link com.tdil.d2d.service.SponsorCodeService}.consumeSponsorCode()
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
				// sponsorCode.setRemainingUses(10);
				sponsorCode.setSponsor(sponsor);
				sponsorCode.setUnits(30);
				sponsorCode.setTimeUnit(SubscriptionTimeUnit.DAY);
				subscriptionDAO.saveSponsorCode(sponsorCode);
			}
			if (sponsorCode == null) {
				return false;
			}
			// if (sponsorCode.getRemainingUses() < 1) {
			// return false;
			// }
			List<Subscription> subscriptions = subscriptionDAO.listSubscriptions(user.getId());
			if (hasSubscription(subscriptions, sponsorCode)) {
				return false;
			}
			// sponsorCode.setRemainingUses(sponsorCode.getRemainingUses() - 1);
			subscriptionDAO.saveSponsorCode(sponsorCode);
			activityLogDAO.save(new ActivityLog(user, ActivityAction.ADD_SUBSCRIPTION));
			Subscription subscription = new Subscription();
			subscription.setSponsorCode(sponsorCode);
			subscription.setExpirationDate(getExpirationDate(Calendar.getInstance(), sponsorCode));
			subscription.setUser(user);
			subscription.setCreationDate(new Date());
			subscription.setFreeSuscription(false);
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
			List<Subscription> subscriptions = subscriptionDAO.listSubscriptions(userID);
			if (subscriptions == null || subscriptions.isEmpty()) {
				// throw new DTDException(ExceptionDefinition.DTD_2003,
				// String.valueOf(userID));
				return null;
			} else {
				for (Subscription subscriptionDB : subscriptions) {
					if(subscriptionDB.getSponsorCode() != null) {
						return subscriptionDB;
					}
				}
				
				return subscriptions.get(subscriptions.size()-1);
			/*
				Subscription subscription = subscriptions.get(0);
				if (subscription.getExpirationDate().before(new Date())) {
					return null;
				} else {
					return subscription;
				}
			 */
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

	@Override
	public Subscription register(User user, int duration) {
		try {
			Subscription subscription = new Subscription();
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, duration);
			subscription.setExpirationDate(cal.getTime());
			subscription.setUser(user);
			subscription.setCreationDate(new Date());
			subscription.setFreeSuscription(false);
			subscriptionDAO.saveSubscription(subscription);
			return subscription;
		} catch (DAOException e) {
			LoggerManager.error(this, e);
			throw new DTDException(ExceptionDefinition.DTD_2004, e, String.valueOf(duration));
		}
	}
	
	@Override
	public Subscription registerByDays(User user, int duration, boolean freeSuscription) {
		try {
			Subscription subscription = new Subscription();
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, duration);
			subscription.setExpirationDate(cal.getTime());
			subscription.setUser(user);
			subscription.setCreationDate(new Date());
			subscription.setFreeSuscription(freeSuscription);
			subscriptionDAO.saveSubscription(subscription);
			return subscription;
		} catch (DAOException e) {
			LoggerManager.error(this, e);
			throw new DTDException(ExceptionDefinition.DTD_2004, e, String.valueOf(duration));
		}
	}
	
	@Override
	public Subscription createFreeSubscription(User user) throws ServiceException {
		if(user.isAlreadyUsedFreeSuscription()) {
			LoggerManager.error(this, "This user has already used the free suscription");
			return null;
		}
		
		try {
			SystemProperty spEnabled = systemPropertyDAO.getSystemPropertyByKey(Constants.SYSTEM_PROPERTY_PROMO_SUSCRIPTION_ENABLED);
			if(spEnabled != null){
				
				String value = spEnabled.getValue();
			
				if("1".equals(value)){
					
					SystemProperty spDays = systemPropertyDAO.getSystemPropertyByKey(Constants.SYSTEM_PROPERTY_PROMO_SUSCRIPTION_DAYS);
					if(spDays!=null){
						int days = Integer.valueOf(spDays.getValue());
						Subscription suscription = this.registerByDays(user, days, true);
					   
						user.setAlreadyUsedFreeSuscription(true);
						this.userDAO.save(user);
						
						return suscription;
					}
				}
			}
		} catch (DAOException e) {
			LoggerManager.error(this, e);
			throw new DTDException(ExceptionDefinition.DTD_2004, e);
		}
		
		return null;
	}
	
	@Override
	public UserReceiptResponse verifyAndRegisterSuscription(User user, ReceiptSuscriptionRequest receiptSuscriptionRequest) throws ServiceException {
		String url = "https://buy.itunes.apple.com/verifyReceipt";
		if(receiptSuscriptionRequest.getIsDev()) {
			url = "https://sandbox.itunes.apple.com/verifyReceipt";
		}
		
        try {
        	org.json.JSONObject data = new org.json.JSONObject();
			data.put("receipt-data", receiptSuscriptionRequest.getReceiptData());
			data.put("password", receiptSuscriptionRequest.getPassword());
			
        	okhttp3.MediaType jsonType = okhttp3.MediaType.parse("application/x-www-form-urlencoded");
        	
			OkHttpClient client = new OkHttpClient();
			
			okhttp3.RequestBody body = okhttp3.RequestBody.create(jsonType, data.toString());
			Request request = new Request.Builder()
			      .url(url)
			      .post(body)
			      .build();
			Response response = client.newCall(request).execute();
        	
            String responseBody = response.body().string();
            
            Gson gson = new GsonBuilder().create();
            VerifyReceiptResultDTO receiptResult = gson.fromJson(responseBody, VerifyReceiptResultDTO.class);
            
            InAppPurchaseDTO lastPurchase = null;
            if(receiptResult.getStatus() == 0 && !receiptResult.getLatestPurchases().isEmpty()) {
            	
            	Comparator<InAppPurchaseDTO> comp = (InAppPurchaseDTO a, InAppPurchaseDTO b) -> {
            		if (b.getExpiresDate() > a.getExpiresDate()){
                        return -1;
                    } else {
                        return 1;
                    }
            	};
            	
            	List<InAppPurchaseDTO> latestPurchases = receiptResult.getLatestPurchases();
            	
            	Collections.sort(latestPurchases, comp);
            	
            	int quantity = latestPurchases.size();
            	
            	List<String> storedTransationIds = subscriptionDAO.getStoredTransationIds(latestPurchases);
            	this.saveReceipts(latestPurchases, storedTransationIds);
            	
            	lastPurchase = latestPurchases.get(quantity - 1);
            }
            
            if(lastPurchase != null) {
            	Long expiresDate = lastPurchase.getExpiresDate();
            	Long purchaseDate = lastPurchase.getPurchaseDate();
            	Boolean isTrialPeriod = lastPurchase.getIsTrialPeriod();
            	
            	UserReceiptResponse receiptInfo = new UserReceiptResponse();
            	receiptInfo.setExpiresDate(expiresDate);
            	receiptInfo.setPurchaseDate(purchaseDate);
            	receiptInfo.setIsTrialPeriod(isTrialPeriod);
            	
            	return receiptInfo;
            }
        } catch (Exception ex) {
        	LoggerManager.error(this, ex);
			throw new DTDException(ExceptionDefinition.DTD_2004, ex);
        }
        
		return null;
	}
	
	private void saveReceipts(List<InAppPurchaseDTO> latestPurchases, List<String> storedTransationIds) throws ServiceException {
		try {
			User loggedUser = this.getLoggedUser();
			for (InAppPurchaseDTO inApp : latestPurchases) {
				if(!storedTransationIds.contains(inApp.getTransactionID())) {
					
					Receipt newReceipt = new Receipt();
					newReceipt.setUser(loggedUser);
					newReceipt.setCreationDate(new Date());
					newReceipt.setExpiresDate(inApp.getExpiresDate());
					newReceipt.setIsTrialPeriod(inApp.getIsTrialPeriod());
					newReceipt.setOrderItemId(inApp.getOrderItemID());
					newReceipt.setTransationId(inApp.getTransactionID());
					newReceipt.setTransactionIdOriginal(inApp.getTransactionIDOriginal());
					newReceipt.setPurchaseDate(inApp.getPurchaseDate());
					newReceipt.setPurchaseDateOriginal(inApp.getPurchaseDateOriginal());
					newReceipt.setQuantity(inApp.getQuantity());
					newReceipt.setProductId(inApp.getProductID());
					
		            subscriptionDAO.saveReceipt(newReceipt);
				}
			}
			
		} catch (DAOException e) {
            throw new ServiceException(e);
        }
		
	}
	
	private User getLoggedUser() throws ServiceException {
		try {
			return userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public UserReceiptResponse getLastReceipt(Long userId) throws ServiceException {
        try {
            Receipt lastReceipt = null;
            lastReceipt = subscriptionDAO.getLastReceipt(userId);
            
            if(lastReceipt != null) {
            	UserReceiptResponse receiptInfo = new UserReceiptResponse();
            	receiptInfo.setExpiresDate(lastReceipt.getExpiresDate());
            	receiptInfo.setPurchaseDate(lastReceipt.getPurchaseDate());
            	receiptInfo.setIsTrialPeriod(lastReceipt.getIsTrialPeriod());
            	
            	return receiptInfo;	
            }
            
            
        } catch (Exception ex) {
        	LoggerManager.error(this, ex);
			throw new DTDException(ExceptionDefinition.DTD_2004, ex);
        }
        
		return null;
	}
}
