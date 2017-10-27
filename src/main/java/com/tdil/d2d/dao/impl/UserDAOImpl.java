package com.tdil.d2d.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.bo.dto.BoNotificationDTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.request.InstitutionType;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.Note;
import com.tdil.d2d.persistence.Occupation;
import com.tdil.d2d.persistence.Specialty;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserGeoLocation;
import com.tdil.d2d.persistence.UserLinkedinProfile;
import com.tdil.d2d.persistence.UserProfile;
import com.tdil.d2d.persistence.ValidationCode;
import com.tdil.d2d.utils.Utilidades;

@Repository
public class UserDAOImpl extends GenericDAO<User> implements UserDAO {

	@Override
	public User getUserByUsername(String username) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("username", username));
			List<User> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User getUserByEmail(String email) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			List<User> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User getUserByMobilePhone(String mobilePhone) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
			criteria.add(Restrictions.eq("mobilePhone", mobilePhone));
			List<User> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User getLastLoginUser() throws DAOException {
		DetachedCriteria maxDateQuery = DetachedCriteria.forClass(User.class);
		ProjectionList proj = Projections.projectionList();
		proj.add(Projections.max("lastLoginDate"));
		maxDateQuery.setProjection(proj);

		Criteria crit = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
		crit.add(Subqueries.propertiesEq(new String[]{"lastLoginDate"}, maxDateQuery));

		List<User> dtlList = crit.list();
		if (dtlList.size() > 0) {
			return dtlList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public UserProfile getUserProfile(User user) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserProfile.class);
			criteria.add(Restrictions.eq("user.id", user.getId()));
			List<UserProfile> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void save(User entity) throws DAOException {
		String invocationDetails = "save(" + entity.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@Override
	public void save(UserProfile userProfile) throws DAOException {
		String invocationDetails = "save(" + userProfile.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().save(userProfile);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@Override
	public UserLinkedinProfile getUserLinkedinProfile(User user) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserLinkedinProfile.class);
			criteria.add(Restrictions.eq("user.id", user.getId()));
			List<UserLinkedinProfile> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void save(UserLinkedinProfile linkedinProfile) throws DAOException {
		String invocationDetails= "save(" + linkedinProfile.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().save(linkedinProfile);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Media getMediaBy(long userId, MediaType mediaType) throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT media ");
			queryString.append("FROM User user ");
			if(mediaType.name().equals(MediaType.AVATAR.name())) {
				queryString.append("JOIN user.avatar media ");
			} else {
				queryString.append("JOIN user.pdfCV media ");
			}
			queryString.append("WHERE media.type = :mediaType ");
			queryString.append("AND user.id = :userId ");
			queryString.append("order by media.id desc");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameter("mediaType", mediaType);
			query.setParameter("userId", userId);

			List<Media> list = query.list();

			return !list.isEmpty() ? list.get(0) : null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public void save(Media media) throws DAOException {
		String invocationDetails= "save(" + Media.class.getName() + ") ";
		try {
			this.getHibernateTemplate().save(media);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@Override
	public void deleteUserGeoLocations(User user) throws DAOException{
		String invocationDetails= "deleteUserGeoLocations(" + user.getClass().getName() + ") ";
		try {

			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserGeoLocation.class);
			criteria.add(Restrictions.eq("user.id", user.getId()));
			List<UserGeoLocation> list = criteria.list();

			for(UserGeoLocation geoLocation : list){
				this.getHibernateTemplate().delete(geoLocation);
			}
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getMatchedUsers(JobOffer offer, List<GeoLevelDTO> locations) throws DAOException {
		try {
			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct user ");
			queryString.append("FROM UserProfile userProfile ");
			queryString.append("JOIN userProfile.user user ");
			queryString.append("JOIN user.userGeoLocations location ");
			queryString.append("WHERE (userProfile.institutionType = :both OR userProfile.institutionType = :institutionType) ");
			queryString.append("AND :task in elements(userProfile.tasks) ");
			queryString.append("AND user.userb = true ");
			queryString.append("AND user.id != :offerentId ");

			if(!locations.isEmpty()) {
				queryString.append("AND (");
				String OR = "";
				for (GeoLevelDTO location : locations) {
					//queryString.append(OR + location + " in elements(userProfile.user.userGeoLocations.id) ");
					queryString.append(OR + "(location.geoLevelId = " + location.getId() + " AND location.geoLevelLevel = " + location.getLevel() + ") ");
					OR = "OR ";
				}
				queryString.append(") ");
			}

			queryString.append("order by user.lastLoginDate desc");

			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			query.setParameter("both", InstitutionType.BOTH);
			query.setParameter("institutionType", offer.getInstitutionType());
			query.setParameter("task", offer.getTask());
			query.setParameter("offerentId", offer.getOfferent().getId());

			return query.list();
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}



	@Override
	public ValidationCode getValidationCode(String mobilePhone, String smsCode) throws DAOException {
		try {
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ValidationCode.class);
			criteria.add(Restrictions.eq("mobilePhone", mobilePhone));
			criteria.add(Restrictions.eq("code", smsCode));
			criteria.add(Restrictions.eq("enabled", true));
			List<ValidationCode> list = criteria.list();
			if (CollectionUtils.isEmpty(list)) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void save(ValidationCode validationCode) throws DAOException {
		String invocationDetails = "save(" + validationCode.getClass().getName() + ") ";
		try {
			this.getHibernateTemplate().save(validationCode);
			this.getHibernateTemplate().flush();
		} catch (DataIntegrityViolationException e) {
			this.handleException(invocationDetails, e);
		} catch (Exception e) {
			this.handleException(invocationDetails, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() throws DAOException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
		return criteria.list();
	}

	@Override
	public Set<Long> getByGeo(List<GeoLevelDTO> locations) throws DAOException {
		try {

			Set<Long> result = new HashSet<Long>();

			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct user.id ");
			queryString.append("FROM User user ");
			if(!locations.isEmpty()) {

				String whereIn = "";
				int count = 1;
				for (GeoLevelDTO location : locations) {
					whereIn = whereIn + "(" + location.getId() + ", " + location.getLevel() + "),";

					if(count > 1000){

						whereIn = whereIn.substring(0, whereIn.length() - 1);

						queryString.append("JOIN user.userGeoLocations location ");
						queryString.append("WHERE user.userb=1 AND (location.geoLevelId, location.geoLevelLevel) IN (" + whereIn + ")");

						Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());

						result.addAll(query.list());

						queryString = new StringBuilder("");
						queryString.append("SELECT distinct user.id ");
						queryString.append("FROM User user ");

						whereIn = "";

						count = 0;
					}

					count = count + 1;

				}

				whereIn = whereIn.substring(0, whereIn.length() - 1);

				queryString.append("JOIN user.userGeoLocations location ");
				queryString.append("WHERE  user.userb=1 AND (location.geoLevelId, location.geoLevelLevel) IN (" + whereIn + ")");

				Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
				result.addAll(query.list());

				return result;

			} else {

				queryString.append("WHERE  user.userb=1 ");
				Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
				result.addAll(query.list());
				return result;
			}


		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public long getCount() throws DAOException {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(User.class);
		return (long)criteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public List<User> getMatchedUsersNote(Note note, List<User> userList) throws DAOException {
		try {

			boolean usedWhere = false;

			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct user ");
			queryString.append("FROM User user ");
			if(!Utilidades.isNullOrEmpty(note.getOccupations())) {
				queryString.append("JOIN user.specialties spec ");	
			}

			if(userList != null && !userList.isEmpty()) {
				queryString.append("where user in (:userList) ");
				usedWhere = true;
			}
			queryString.append(checkInterests(note.getSpecialties(), note.getOccupations(), usedWhere));
			queryString.append("order by user.lastLoginDate desc");
			Query query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());

			if(!userList.isEmpty()) {
				query.setParameterList("userList", userList);
			}

			if(note.getSpecialties() != null && !note.getSpecialties().isEmpty()) {
				query.setParameterList("specialties", note.getSpecialties());
			}

			if(note.getOccupations() != null && !note.getOccupations().isEmpty()) {
				query.setParameterList("occupations", note.getOccupations());
			}

			return query.list();

		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	private Object checkInterests(Set<Specialty> specialties, Set<Occupation> occupations, boolean usedWhere) {

		StringBuilder query = new StringBuilder("");

		if(!isNullOrEmpty(occupations) && !isNullOrEmpty(specialties)) {

			if(usedWhere) {
				query.append(" and ");
			} else {
				query.append(" where ");
			}

			query.append(" (spec in (:specialties) or spec.occupation in (:occupations)) " );

		} else if(isNullOrEmpty(occupations) && !isNullOrEmpty(specialties)) {
			query.append(" and spec in (:specialties)  " );
		} else if(!isNullOrEmpty(occupations) && isNullOrEmpty(specialties)) {
			query.append(" and spec.occupation in (:occupations) " );
		} else {
			query = new StringBuilder("");
		}

		return query;
	}


	public static boolean isNullOrEmpty( final Collection< ? > c ) {
		return c == null || c.isEmpty();
	}

	private List<User> filterUsers(List<User> list, Note note) {

		List<User> userList = new ArrayList<User>();

		if(!note.getSpecialties().isEmpty()) {

			for (User user : list) {
				boolean encontrado = false;
				if(!user.isUserb()) {
					userList.add(user);
					encontrado = true;
				}

				if(!encontrado) {
					for (Specialty userSpecialty : user.getSpecialties()) {
						for (Specialty noteSpecialty : note.getSpecialties()) {
							if(userSpecialty.getId() == noteSpecialty.getId() || !user.isUserb()) {
								userList.add(user);
								encontrado = true;
								break;
							}
						}
						if(encontrado) {
							break;
						}
					}	

				}
			}
		}

		if(!userList.isEmpty()) {
			return userList;	
		} else {
			return list;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersBoNotification(BoNotificationDTO boNotificationDTO,List<Long> idList) throws DAOException {
		try {
			
			List<Long> userIdsList = new ArrayList<Long>();
			Query query;

			StringBuilder queryString = new StringBuilder("");
			queryString.append("SELECT distinct user ");
			queryString.append("FROM User user ");
			if((boNotificationDTO.isAllUser() && boNotificationDTO.getUserTestIds().isEmpty())) {
				query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
			} else {
				queryString.append("JOIN user.specialties spec ");
				//			queryString.append("JOIN userProfile.user user ");
				//			queryString.append("JOIN user.userGeoLocations location ");


				if(!boNotificationDTO.getUserIds().isEmpty() || !boNotificationDTO.getUserTestIds().isEmpty()) {
					queryString.append("where user.id in :users ");
				} else {
					//Se agrega esto para agregar una clausula "where" y poder manejar los "or" de abajo
					queryString.append("where user.id = -1 ");
				}
				if(boNotificationDTO.getSpecialties() != null) {
					queryString.append("or spec.id in :specialties ");	
				}
				if(boNotificationDTO.getOccupations() != null) {
					queryString.append("or spec.occupation.id in :occupations ");
				}
				queryString.append("order by user.lastLoginDate desc");
				query =  this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());

				if(boNotificationDTO.getSpecialties() != null) {
					query.setParameterList("specialties", boNotificationDTO.getSpecialties());
				}
				if(boNotificationDTO.getOccupations() != null) {
					query.setParameterList("occupations", boNotificationDTO.getOccupations());
				}
				if(!boNotificationDTO.getUserIds().isEmpty()) {
					userIdsList.addAll(Arrays.asList(Stream.of(boNotificationDTO.getUserIds().split("\\s*,\\s*")).map(Long::valueOf).toArray(Long[]::new)));
				}
				
				if(!idList.isEmpty()) {
					userIdsList.addAll(idList);
				}
				
				if(!idList.isEmpty() || !boNotificationDTO.getUserIds().isEmpty()) {
					query.setParameterList("users", userIdsList);
				}

				if(!boNotificationDTO.getUserTestIds().isEmpty()) {
					query.setParameterList("users", Arrays.asList(Stream.of(boNotificationDTO.getUserTestIds().split("\\s*,\\s*")).map(Long::valueOf).toArray(Long[]::new)));
				}

			}
			List<User> filterUser = query.list();

			return filterUser;
		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersBNoSponsor() {

		StringBuilder queryString = new StringBuilder("select * from d2d_prod.D2D_USER where D2D_USER.userb is true && D2D_USER.id not in(select  distinct something.id from (SELECT * FROM D2D_USER) AS something \n" + 
				"join D2D_SPONSOR_CODE on something.id = D2D_SPONSOR_CODE.consumer_id join D2D_SUBSCRIPTION on D2D_SUBSCRIPTION.sponsorCode_id = D2D_SPONSOR_CODE.id where D2D_SUBSCRIPTION.expirationDate > now())");

		SQLQuery query = this.getSessionFactory().getCurrentSession().createSQLQuery(queryString.toString());
		query.addEntity(User.class);
		List<User> filterUser = (List<User>)query.list();

		return filterUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersASponsor() {
		StringBuilder queryString = new StringBuilder("");
		queryString.append("SELECT distinct user ");
		queryString.append("FROM User user ");
		queryString.append("where user.userb = :userB ");

		Query query = this.getSessionFactory().getCurrentSession().createQuery(queryString.toString());
		query.setParameter("userB", false);

		return query.list();
	}

}
