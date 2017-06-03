package com.tdil.d2d.controller;

import com.tdil.d2d.exceptions.DTDException;
import com.tdil.d2d.exceptions.ExceptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.security.JwtAuthenticationRequest;
import com.tdil.d2d.security.JwtAuthenticationResponse;
import com.tdil.d2d.security.JwtTokenUtil;
import com.tdil.d2d.service.UserService;

@RestController
public class AuthenticationRestController {

	private String tokenHeader = "Authentication";

	private Logger logger = LoggerFactory.getLogger(AuthenticationRestController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {


		if (logger.isDebugEnabled()) {
			logger.debug("El usuario {} se esta intentando de loguear con el deviceId {}",
					authenticationRequest.getUsername(),
					authenticationRequest.getPassword());
		}

		// Perform the security
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("Este es el token del usuario: " + authenticationRequest.getUsername() + " con token: " + token);

		// Return the token
		try {
			this.userService.updateLastLoginDate();
		} catch (ServiceException e) {
			logger.error("Error al intentar de hacer update del Last Login Date", e);
		}

		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}


}