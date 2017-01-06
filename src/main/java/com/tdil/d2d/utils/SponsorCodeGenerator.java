package com.tdil.d2d.utils;

import com.tdil.d2d.persistence.Sponsor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SponsorCodeGenerator {

	public String generate(Sponsor sponsor) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
}
