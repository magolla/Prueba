package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.security.JwtUserFactory;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private com.tdil.d2d.service.UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
			User user = userService.getUserByEmail(username);

			if (user == null) {
			    throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			} else {
			    return JwtUserFactory.create(user);
			}
		} catch (ServiceException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
    }
}