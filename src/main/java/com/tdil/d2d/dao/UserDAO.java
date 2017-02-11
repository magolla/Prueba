package com.tdil.d2d.dao;

import java.util.List;

import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserLinkedinProfile;
import com.tdil.d2d.persistence.UserProfile;
import com.tdil.d2d.persistence.ValidationCode;

public interface UserDAO {
	
	public User getById(Class<User> aClass, long id) throws DAOException;
	
	public User getUserByUsername(String username) throws DAOException;

	public void save(User user) throws DAOException;

	public User getLastLoginUser() throws DAOException;

	public User getUserByEmail(String email) throws DAOException;

	public User getUserByMobilePhone(String mobilePhone) throws DAOException;

	public UserProfile getUserProfile(User user) throws DAOException;
	
	public void save(UserProfile userProfile) throws DAOException;
	
	public UserLinkedinProfile getUserLinkedinProfile(User user) throws DAOException;

    public void save(UserLinkedinProfile linkedinProfile) throws DAOException;
    
    public void save(Media media) throws DAOException;

	public Media getMediaBy(long userId, MediaType mediaType) throws DAOException;
    
	public void deleteUserGeoLocations(User user) throws DAOException;

	public List<User> getMatchedUsers(JobOffer offer, List<Long> locations) throws DAOException;

	public ValidationCode getValidationCode(String mobilePhone, String smsCode) throws DAOException;

	public void save(ValidationCode validationCode)  throws DAOException ;
}
