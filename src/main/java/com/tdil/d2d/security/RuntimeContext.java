package com.tdil.d2d.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class RuntimeContext {
	
	public static JwtUser getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			JwtUser jwtUser = (JwtUser)authentication.getPrincipal();
			return jwtUser;
		} else {
			return null;
		}
	}

}
