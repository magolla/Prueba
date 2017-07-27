package com.tdil.d2d.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tdil.d2d.security.CaptchaValidatorFilter;
import com.tdil.d2d.security.JwtAuthenticationEntryPoint;
import com.tdil.d2d.security.JwtAuthenticationTokenFilter;

@SuppressWarnings("SpringJavaAutowiringInspection")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {

	 @Configuration
	 @Order(1)
     public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		    @Autowired
		    private JwtAuthenticationEntryPoint unauthorizedHandler;

		    @Autowired
			@Qualifier("jwtUserDetailsService")
		    private UserDetailsService userDetailsService;

		    @Autowired
		    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		        authenticationManagerBuilder
		                .userDetailsService(this.userDetailsService)
		                .passwordEncoder(passwordEncoder());
		    }

		    @Bean
		    public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		        //return new NoOpPasswordEncoder();
		    }

		    @Bean
		    @Override
		    public AuthenticationManager authenticationManagerBean() throws Exception {
		        return super.authenticationManagerBean();
		    }

		    @Bean
		    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
		        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		        return authenticationTokenFilter;
		    }

		    @Override
		    protected void configure(HttpSecurity httpSecurity) throws Exception {
		    	
		    	 httpSecurity
	               
	                .antMatcher("/api/**") 
	                .authorizeRequests()
	                .antMatchers("/api/auth/**", "/api/user/registerA", "/api/user/registerB", "/api/user/validate", "/api/user/send",  "/api/geo/autocomplete", "/api/initDB", "/api/user/linkedin/auth/step1",
	                        "/api/specialties/**",
	                        "/api/contact/motives",
	                        "/api/contact", 
	                        "/api/sendTestNotificationIOS",
	                        "/api/sendTestNotificationAndroid",
	                		"/api/validateVersion/**").permitAll()
	                .anyRequest().authenticated().and()
	                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	                .csrf().disable()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			        // Custom JWT based security filter
			        httpSecurity
			                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		
			        // disable page caching
			        httpSecurity.headers().cacheControl();
		    	
		    }
     }
	 
	 @Configuration
	 @Order(2)
     public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		   
		    @Autowired
			@Qualifier("boUserDetailsService")
		    private UserDetailsService userDetailsService;

		    @Autowired
		    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		        authenticationManagerBuilder
		                .userDetailsService(this.userDetailsService)
		                .passwordEncoder(passwordEncoder());
		    }

		    @Bean
		    public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		        //return new NoOpPasswordEncoder();
		    }
		    
		    @Bean
		    @Override
		    public AuthenticationManager authenticationManagerBean() throws Exception {
		        return super.authenticationManagerBean();
		    }
		    
		    @Bean
		    public CaptchaValidatorFilter validationCaptchaFilterBean() throws Exception {
		    	CaptchaValidatorFilter authenticationTokenFilter = new CaptchaValidatorFilter();
		        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		        return authenticationTokenFilter;
		    }

		    @Override
		    protected void configure(HttpSecurity httpSecurity) throws Exception {
		    	httpSecurity
		    	
			    	.authorizeRequests().antMatchers("/admin/login").permitAll()
	                .antMatchers("/admin/adminlte/**").permitAll()
	                .antMatchers("/admin/bootstrap/**").permitAll()
	                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").and()
	                .csrf().and()
	                .logout().logoutSuccessUrl("/admin/login?logout").and()
					.exceptionHandling().accessDeniedPage("/403");

		    	httpSecurity
			                .addFilterAfter(validationCaptchaFilterBean(), UsernamePasswordAuthenticationFilter.class);
		    }
     }
   
}