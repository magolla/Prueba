package com.tdil.d2d.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.dao.SystemPropertyDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.SystemProperty;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.AppSupportService;
import com.tdil.d2d.utils.Constants;

@Transactional
@Service
public class AppSupportServiceImpl implements AppSupportService {

	private final SystemPropertyDAO systemPropertyDAO;
	private final UserDAO userDAO;
	private int ANDROID_PLATFORM = 1;
	private int IOS_PLATFORM = 2;
	
	@Autowired
	public AppSupportServiceImpl(SystemPropertyDAO systemPropertyDAO, UserDAO userDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
		this.userDAO = userDAO;
	}

	
	public User getLoggedUser() throws ServiceException {
		try {
			return userDAO.getById(User.class, com.tdil.d2d.security.RuntimeContext.getCurrentUser().getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean validateVersion(int appVersion, int platform) {
		try {
			SystemProperty spVersion = null;
			if(platform == ANDROID_PLATFORM ) {
				spVersion = systemPropertyDAO.getSystemPropertyByKey(Constants.SYSTEM_PROPERTY_ANDROID_LAST_IMPORTANT_VERSION);
				
				if(spVersion.getValue() == null) {
					return false;
				}
				
				if(appVersion >= Integer.parseInt(spVersion.getValue())) {
					return false;
				} else {
					return true;
				}
			} else {
				spVersion = systemPropertyDAO.getSystemPropertyByKey(Constants.SYSTEM_PROPERTY_IOS_LAST_IMPORTANT_VERSION);
				if(spVersion.getValue() == null) {
					return false;
				}
				if(appVersion >= Integer.parseInt(spVersion.getValue())) {
					return false;
				} else {
					return true;
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
