package com.tdil.d2d.service;

import com.tdil.d2d.persistence.Sponsor;

import java.util.List;

public interface SponsorService {

	Sponsor getSponsorById(long id);

	Sponsor save(Sponsor sponsor);

	void delete(Sponsor sponsor);

	void delete(long id);

	List<Sponsor> getAllSponsors();

	Sponsor createSponsor(String name);
}
