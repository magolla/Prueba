package com.tdil.d2d.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.persistence.Sponsor;

@Service
public class SponsorCodeGenerator {
	
	@Autowired
	private SubscriptionDAO subscriptionDAO;
	
	public String generate(Sponsor sponsor) {
		
		boolean isInDatabase = true;
		
		String upToNCharacters = "";
		String uuid ="";
		
		while(isInDatabase) {
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
			upToNCharacters = uuid.substring(0, 10);
			isInDatabase = this.subscriptionDAO.checkIfSponsorCodeExist(sponsor.getId(), upToNCharacters);
		}
		return upToNCharacters;
	}
}
