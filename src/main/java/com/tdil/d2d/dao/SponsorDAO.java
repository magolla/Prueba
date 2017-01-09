package com.tdil.d2d.dao;

import com.tdil.d2d.persistence.Sponsor;

import java.util.List;

public interface SponsorDAO {

	Sponsor getSponsorById(long id);

	Sponsor save(Sponsor sponsor);

	void delete(Sponsor sponsor);

	void delete(long id);

	List<Sponsor> getAllSponsors();
}
