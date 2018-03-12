package com.tdil.d2d.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.config.DaoConfig;
import com.tdil.d2d.config.PersistenceConfiguration;
import com.tdil.d2d.config.WebInitializer;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.controller.api.dto.MatchedUserDTO;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.persistence.GeoLevel;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserGeoLocation;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebInitializer.class, PersistenceConfiguration.class, DaoConfig.class})
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DatabaseTest {


	
	@BeforeClass
	public static void beforeClass() {
		
		System.setProperty("spring.profiles.active", "test");
		System.setProperty("log4j.configurationFile", "conf/app/test/log4j2.xml");

	}
	
//	@Autowired
//	private UserService userService;
	
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private JobOfferDAO jobDAO;
	
	@Autowired
	private GeoDAO geoDAO;
	
	@org.junit.Test
	public void test() {
		
		long offerId = 21;
		
		try {
			List<MatchedUserDTO> semiMatchedUserDTOs = this.getSemiMatchedUsers(offerId);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List<MatchedUserDTO> getSemiMatchedUsers(Long offerId) throws ServiceException {
		try {
			List<User> result = new ArrayList<>();

			JobOffer jobOffer = this.jobDAO.getById(JobOffer.class, offerId);

			UserGeoLocation geoLocation = new UserGeoLocation();
			geoLocation.setGeoLevelId(jobOffer.getGeoLevelId());
			geoLocation.setGeoLevelLevel(jobOffer.getGeoLevelLevel());

			Set<UserGeoLocation> geoLocations = new HashSet<UserGeoLocation>();
			geoLocations.add(geoLocation);

			List<GeoLevelDTO> geos = this.getGeoLevels(geoLocations);

			GeoLevel geoLevel = this.geoDAO.getGeoByIdAndLevel(jobOffer.getGeoLevelId(), jobOffer.getGeoLevelLevel());
			
			result = userDAO.getSemiMatchedUsers(jobOffer, geos);
			
			
			List<User> userwithoutFilter = userDAO.getMatchedUsers(jobOffer, geos);
			
			result.removeAll(userwithoutFilter);

			List<MatchedUserDTO> matchedUserDTOs = new ArrayList<MatchedUserDTO>();
//			for (User matchedUser : result) {
//				MatchedUserDTO matchedUserDTO = toMatchedUserDto(matchedUser);
//				matchedUserDTOs.add(matchedUserDTO);
//			}

			return matchedUserDTOs;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<GeoLevelDTO> getGeoLevels(Set<UserGeoLocation> userGeoLocations) throws DAOException {
		List<GeoLevelDTO> geos = new ArrayList<GeoLevelDTO>();
		GeoLevelDTO geoDto;

		for (UserGeoLocation location : userGeoLocations) {
			geoDto = new GeoLevelDTO(location.getGeoLevelId(), location.getGeoLevelLevel());
			geos.add(geoDto);
			GeoLevel geoLevel = this.geoDAO.getGeoByIdAndLevel(location.getGeoLevelId(), location.getGeoLevelLevel());
			if (location.getGeoLevelLevel() == 4) {
				Geo4 geo4 = (Geo4) geoLevel;
				geos.add(new GeoLevelDTO(geo4.getGeo3().getId(), 3));
				geos.add(new GeoLevelDTO(geo4.getGeo3().getGeo2().getId(), 2));
			}
			if (location.getGeoLevelLevel() == 3) {
				Geo3 geo3 = (Geo3) geoLevel;
				geos.add(new GeoLevelDTO(geo3.getGeo2().getId(), 2));

				List<Geo4> geos4 = this.geoDAO.getListGeo4ByGeo3(location.getGeoLevelId());
				for (Geo4 geo4 : geos4) {
					geos.add(new GeoLevelDTO(geo4.getId(), 4));
				}
			}
			if (location.getGeoLevelLevel() == 2) {
				List<Geo4> geos4 = this.geoDAO.getListGeo4ByGeo2(location.getGeoLevelId());
				for (Geo4 geo4 : geos4) {
					geos.add(new GeoLevelDTO(geo4.getId(), 4));
					geos.add(new GeoLevelDTO(geo4.getGeo3().getId(), 3));
				}
			}
		}

		return geos;
	}

}


