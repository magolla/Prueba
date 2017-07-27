package com.tdil.d2d.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.request.InstitutionType;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.BOUser;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Media;
import com.tdil.d2d.persistence.MediaType;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserGeoLocation;
import com.tdil.d2d.persistence.UserLinkedinProfile;
import com.tdil.d2d.persistence.UserProfile;
import com.tdil.d2d.persistence.ValidationCode;

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
			queryString.append("JOIN user.pdfCV media ");
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
			
			queryString.append("order by userProfile.user.lastLoginDate desc");

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

}
