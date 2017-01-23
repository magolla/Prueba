package com.tdil.d2d.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdil.d2d.dao.SponsorDAO;
import com.tdil.d2d.persistence.Sponsor;
import com.tdil.d2d.service.SponsorService;

@Service
public class SponsorServiceImpl implements SponsorService {

	private final SponsorDAO sponsorDAO;

	@Autowired
	public SponsorServiceImpl(SponsorDAO sponsorDAO) {
		this.sponsorDAO = sponsorDAO;
	}

	@Override
	public Sponsor getSponsorById(long id) {
		return this.sponsorDAO.getSponsorById(id);
	}

	@Override
	public Sponsor save(Sponsor sponsor) {
		return this.sponsorDAO.save(sponsor);
	}

	@Override
	public void delete(Sponsor sponsor) {
		this.sponsorDAO.delete(sponsor);
	}

	@Override
	public void delete(long id) {
		this.sponsorDAO.delete(id);
	}

	@Override
	public List<Sponsor> getAllSponsors() {
		return this.sponsorDAO.getAllSponsors();
	}

	@Override
	public Sponsor createSponsor(String name, String image) {
		Sponsor sponsor = new Sponsor();
		sponsor.setName(name);
		sponsor.setBase64img(image.getBytes());
		sponsor.setCreationDate(new Date());
		return this.save(sponsor);
	}
}
