package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
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
	
	

}
