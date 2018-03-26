package com.tdil.d2d.dao;

import java.util.List;
import java.util.Set;

import com.tdil.d2d.bo.dto.BoNotificationDTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Geo2;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.Note;
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

	public List<User> getMatchedUsers(JobOffer offer, List<GeoLevelDTO> locations) throws DAOException;
	
	public List<User> getSemiMatchedUsers(JobOffer offer, List<Long> geo4IdList, List<Long> geo3IdList, Geo2 offerGeo2) throws DAOException;

	public ValidationCode getValidationCode(String mobilePhone, String smsCode) throws DAOException;

	public void save(ValidationCode validationCode)  throws DAOException ;
	
	public List<User> getAll() throws DAOException;

	public Set<Long> getByGeo(List<GeoLevelDTO> geos) throws DAOException; 
	
	public long getCount() throws DAOException;

	public List<User> getMatchedUsersNote(Note note, List<User> userList) throws DAOException;
	
	public List<User> getUsersBoNotification(BoNotificationDTO boNotificationDTO, List<Long> userIdList) throws DAOException;

	public List<User> getUsersBNoSponsor();

	public List<User> getUsersASponsor();

	public List<User> getUserByIndex(String length, String start, String search);

	public int getCountWithFilter(String search);

	public List<User> getUserNotIn(List<User> userList);
	
}
