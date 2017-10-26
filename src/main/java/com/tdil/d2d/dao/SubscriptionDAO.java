package com.tdil.d2d.dao;

import java.util.List;
import java.util.Set;

import com.tdil.d2d.controller.api.dto.InAppPurchaseDTO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Receipt;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;

public interface SubscriptionDAO {

	public void saveSponsorCode(SponsorCode sponsorCode) throws DAOException;

	public void saveSponsor(Sponsor sponsor) throws DAOException;

	public void saveSubscription(Subscription subscription) throws DAOException;

	public Sponsor getSponsorByName(Class<Sponsor> class1, String name) throws DAOException;

	public List<Subscription> listSubscriptions(Long userId) throws DAOException;

	public SponsorCode getSponsorCode(Class<SponsorCode> aClass, String code) throws DAOException;

	Sponsor getSponsorById(long sponsorId);

	List<SponsorCode> saveSponsorCode(List<SponsorCode> codes);

	List<SponsorCode> listSponsorCodes(Long sponsorId);
	
	boolean checkIfSponsorCodeExist(Long sponsorId,String sponsorCode);

	public List<String> getStoredTransationIds(List<InAppPurchaseDTO> latestPurchases);
	
	public void saveReceipt(Receipt receipt) throws DAOException;

	public Receipt getLastReceipt(Long userId);

	public List<Subscription> listAllSubscriptions(Set<Long> ids) throws DAOException;

	public List<Receipt> listAllReceipts() throws DAOException;
	
	public List<Subscription> getSuscriptionCloseExpire() throws DAOException;
	
	List<SponsorCode> listSponsorCodeById(List<Long> sponsorsId);
}
