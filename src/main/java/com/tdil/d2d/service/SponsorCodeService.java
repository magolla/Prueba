package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.persistence.User;

public interface SponsorCodeService {

	List<SponsorCode> generateSponsorCodes(long sponsorId, int codesCount, int units, SubscriptionTimeUnit timeUnit);

	Subscription consumeSponsorCode(User user, String code);

	List<SponsorCode> listSponsorCodesBySponsorId(long sponsorId);
}
