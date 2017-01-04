package com.tdil.d2d.service;

import com.tdil.d2d.persistence.SponsorCode;
import com.tdil.d2d.persistence.SubscriptionTimeUnit;
import com.tdil.d2d.persistence.User;

import java.util.List;

public interface SponsorCodeService {

	List<SponsorCode> generateSponsorCodes(long sponsorId, int codesCount, int units, SubscriptionTimeUnit timeUnit);

	SponsorCode consumeSponsorCode(User user, String code);

	List<SponsorCode> listSponsorCodesBySponsorId(long sponsorId);
}
