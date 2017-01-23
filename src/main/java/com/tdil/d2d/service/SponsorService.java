package com.tdil.d2d.service;

import java.util.List;

import com.tdil.d2d.persistence.Sponsor;

public interface SponsorService {

	Sponsor getSponsorById(long id);

	Sponsor save(Sponsor sponsor);

	void delete(Sponsor sponsor);

	void delete(long id);

	List<Sponsor> getAllSponsors();

	Sponsor createSponsor(String name, String image);
}
