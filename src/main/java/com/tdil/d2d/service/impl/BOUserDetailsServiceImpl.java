package com.tdil.d2d.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.BOUserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.Role;

@Transactional
@Service("boUserDetailsService")
public class BOUserDetailsServiceImpl implements UserDetailsService {


	@Autowired
	private BOUserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
        	BOUser user = userDAO.findByEmail(email);

        	if(!user.isActive()){
        		throw new UsernameNotFoundException("User inactive");
        	}
        	List<GrantedAuthority> authorities =
                    buildUserAuthority(user.getRoles());

            return buildUserForAuthentication(user, authorities);

		} catch (DAOException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
        
    }
    
    
 	private User buildUserForAuthentication(BOUser user,
 		List<GrantedAuthority> authorities) {
 		return new User(user.getEmail(), user.getPassword(), authorities);
 	}

 	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

 		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

 		for (Role userRole : userRoles) {
 			setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
 		}

 		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

 		return Result;
 	}
}