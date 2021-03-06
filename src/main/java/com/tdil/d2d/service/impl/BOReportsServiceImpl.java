package com.tdil.d2d.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.FilterJobOfferDailyReportDTO;
import com.tdil.d2d.bo.dto.FilterJobOfferReportDTO;
import com.tdil.d2d.bo.dto.FilterSubscriptionReportDTO;
import com.tdil.d2d.bo.dto.JobOfferDailyReportDTO;
import com.tdil.d2d.bo.dto.JobOfferReportDTO;
import com.tdil.d2d.bo.dto.JobOfferReportItemDTO;
import com.tdil.d2d.bo.dto.ReportItemDTO;
import com.tdil.d2d.bo.dto.SubscriptionReportDTO;
import com.tdil.d2d.controller.api.dto.BOJobOfferDTO;
import com.tdil.d2d.controller.api.dto.GeoLevelDTO;
import com.tdil.d2d.dao.GeoDAO;
import com.tdil.d2d.dao.JobApplicationDAO;
import com.tdil.d2d.dao.JobOfferDAO;
import com.tdil.d2d.dao.SubscriptionDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.persistence.Geo3;
import com.tdil.d2d.persistence.Geo4;
import com.tdil.d2d.persistence.GeoLevel;
import com.tdil.d2d.persistence.JobApplication;
import com.tdil.d2d.persistence.JobOffer;
import com.tdil.d2d.persistence.Receipt;
import com.tdil.d2d.persistence.Subscription;
import com.tdil.d2d.persistence.SuscriptionTypeEnum;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.persistence.UserGeoLocation;
import com.tdil.d2d.service.BOReportsService;
import com.tdil.d2d.service.SubscriptionService;

@Transactional
@Service
public class BOReportsServiceImpl implements BOReportsService {
	
	
	private final JobOfferDAO jobOfferDAO;
	private final UserDAO userDAO;
	private final GeoDAO geoDAO;
	private final SubscriptionDAO subscriptionDAO;
	private final JobApplicationDAO jobApplicationDAO;
	private final SubscriptionService subscriptionService;
	
	
	
	@Autowired
	public BOReportsServiceImpl(JobOfferDAO jobOfferDAO, UserDAO userDAO,GeoDAO geoDAO, SubscriptionDAO subscriptionDAO, JobApplicationDAO jobApplicationDAO, SubscriptionService subscriptionService) {
		this.jobOfferDAO = jobOfferDAO;
		this.userDAO = userDAO;
		this.geoDAO = geoDAO;
		this.subscriptionDAO = subscriptionDAO;
		this.jobApplicationDAO = jobApplicationDAO;
		this.subscriptionService = subscriptionService;
	}

	@Override
	public List<BOJobOfferDTO> getAllJobOffers() throws ServiceException {
		try {
			
			 List<JobOffer> jobOffers = this.jobOfferDAO.getAllOffers();
			
			
			return toBoDtoList(jobOffers);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	private List<BOJobOfferDTO> toBoDtoList(Collection<JobOffer> list) {
		return list.stream().map(jobOffer -> {
			try {
				return toBoDto(jobOffer);
			} catch (DAOException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}
	
	
	
	private  BOJobOfferDTO toBoDto(JobOffer jobOffer) throws DAOException {
		BOJobOfferDTO result = new BOJobOfferDTO();
		
		result.setId(jobOffer.getId());
		result.setApplications(jobOffer.getApplications());
		result.setComment(jobOffer.getComment());
		result.setCompanyScreenName(jobOffer.getCompanyScreenName());
		result.setCreationDate(jobOffer.getCreationDate().toString());
		result.setGeoLevelName(this.geoDAO.getGeoByIdAndLevel(jobOffer.getGeoLevelId(),	 jobOffer.getGeoLevelLevel()).getName());
		//Preguntar si quiere geoname
		DateFormat df = new SimpleDateFormat("hh:'00' a");
		String hour = df.format(jobOffer.getOfferDate());
		result.setOfferHour(hour);
		result.setInstitutionType(jobOffer.getInstitutionType().name());
		if(jobOffer.getJobApplication_id() != null) {
			User usuarioApplicant =  	this.jobApplicationDAO.getById(JobApplication.class, jobOffer.getJobApplication_id()).getUser();
//			User usuarioApplicant = userDAO.getById(User.class, jobOffer.get);	
			result.setJobApplication_detail(usuarioApplicant.getFirstname() + " " + usuarioApplicant.getLastname() + "<br>" 
					+ usuarioApplicant.getEmail() + "<br>" + usuarioApplicant.getMobilePhone() + "<br>" + usuarioApplicant.getCompanyScreenName());
		} else {
			result.setJobApplication_detail("N/A");
		}
		
		df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(jobOffer.getOfferDate());
		result.setOfferDate(date);
		result.setPermanent(jobOffer.isPermanent());
		result.setStatus(jobOffer.getStatus());
		result.setSubTitle(jobOffer.getSubtitle());
		result.setTitle(jobOffer.getTitle());
		result.setVacants(jobOffer.getVacants());
		result.setOccupationName(jobOffer.getOccupation().getName());
		result.setOfferent_detail(jobOffer.getOfferent().getFirstname() + " " + jobOffer.getOfferent().getLastname() + "<br>" 
				+ jobOffer.getOfferent().getEmail() + "<br>" + jobOffer.getOfferent().getMobilePhone() + "<br>" + jobOffer.getOfferent().getCompanyScreenName());
		result.setSpecialtyName(jobOffer.getSpecialty().getName());
		result.setTaskName(jobOffer.getTask().getName());

		result.setMatchesAtCreation(jobOffer.getMatchesAtCreation());
		return result;
	}




	@Override
	public SubscriptionReportDTO getSubscriptionReportDTO(FilterSubscriptionReportDTO filterDTO)
			throws ServiceException {
		try {
			SubscriptionReportDTO result = new SubscriptionReportDTO();
			int freeSubcriptions = 0;
			int paidSubcriptions = 0;
			int sponsorSubcriptions = 0;
			int iosSubcriptions = 0;
			
			Set<UserGeoLocation> geoLocations = new HashSet<UserGeoLocation>();
			if(filterDTO.getGeoLevels2()!=null && !containsFilterAll(filterDTO.getGeoLevels2())){
				
				for(Long geoId : filterDTO.getGeoLevels2()){
				
					UserGeoLocation geoLocation = new UserGeoLocation();
					geoLocation.setGeoLevelId(geoId);
					geoLocation.setGeoLevelLevel(2);
					
					geoLocations.add(geoLocation);
				}
				
			}
			
			Long countAllUsers = userDAO.getCount();
			List<GeoLevelDTO> geos = this.getGeoLevels(geoLocations);
			result.setCountAllUsers(countAllUsers);
			
			Set<Long> usersIdByGeo = userDAO.getByGeo(geos);
			result.setCountUsersB(usersIdByGeo.size());
			
			if(!usersIdByGeo.isEmpty()){
				
				List<Subscription> subscriptions = subscriptionDAO.listAllSubscriptions(usersIdByGeo);
				
				
				Set<Long> addedUserIds = new HashSet<Long>();
				
				List<Receipt> receipts = subscriptionDAO.listAllReceipts();
				if(filterDTO.isIos()){
					for(Receipt subscription : receipts){
						iosSubcriptions = iosSubcriptions + 1;
						addedUserIds.add(subscription.getUser().getId());
					}
				}
	
				for(Subscription subscription : subscriptions){
					long id = subscription.getUser().getId();
					if(!addedUserIds.contains(id)){
						if(subscription.getSponsorCode()!=null){
							if(filterDTO.isSponsor()){
								sponsorSubcriptions = sponsorSubcriptions + 1;
							}
						} else {
							if(subscription.isFreeSuscription()){
								if(filterDTO.isFree()){
									freeSubcriptions = freeSubcriptions + 1;
								}
							} else {
								if(filterDTO.getAndroid()){
									paidSubcriptions = paidSubcriptions + 1;
								}
							}
						}
					}
				}
			
			}
			
			result.setCountSubscriptions(freeSubcriptions + paidSubcriptions + sponsorSubcriptions + iosSubcriptions);
			
			
			List<ReportItemDTO> items = new ArrayList<ReportItemDTO>();
			if(filterDTO.getAndroid()){
				items.add(new ReportItemDTO("Suscripciones pagas android mercado pago", paidSubcriptions, "rgba(255, 165, 0, 1)"));
			}
			if(filterDTO.isSponsor()){
				items.add(new ReportItemDTO("Suscripciones por sponsor", sponsorSubcriptions, "rgba(0, 128, 128, 1)"));
			}
			if(filterDTO.isIos()){
				items.add(new ReportItemDTO("Suscripciones inapp iOS", iosSubcriptions, "rgba(3, 72, 123, 1)"));
			}
			if(filterDTO.isFree()){
				items.add(new ReportItemDTO("Suscripciones gratuitas dtd", freeSubcriptions, "rgba(238,67,100, 1)"));
			}	
			items.add(new ReportItemDTO("Otros usuarios candidatos", (int)(usersIdByGeo.size() - result.getCountSubscriptions()), "rgba(192,192,192, 1)"));
			result.setList(items);
		   
			return result;
		
		} catch (DAOException e) {

			throw new ServiceException(e);
		}
	}
	
	private boolean containsFilterAll(List<Long> geoLevels2) {
		for(Long geoId : geoLevels2){
			if(geoId == -1L){
				return true;
			}
		}
		return false;
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
					GeoLevelDTO geo3 = new GeoLevelDTO(geo4.getGeo3().getId(), 3);
					if(!containts(geos, geo3)){
						geos.add(geo3);
					}

				}
			}
		}
		
		return geos;
	}

	private boolean containts(List<GeoLevelDTO> geos, GeoLevelDTO geo3) {
		for(GeoLevelDTO geoDTO : geos){
			if(geoDTO.getId() == geo3.getId() 
					&& geoDTO.getLevel() == geo3.getLevel()){
				return true;
			}
		}
		return false;
	}
	
	/* BEGIN Method for monthly report */
	
	@Override
	public JobOfferReportDTO getJobOfferReportDTO(FilterJobOfferReportDTO filterDTO) throws ServiceException {
		
		Set<UserGeoLocation> geoLocations = new HashSet<UserGeoLocation>();
		if(filterDTO.getGeoLevels2()!=null && !containsFilterAll(filterDTO.getGeoLevels2())){
			
			for(Long geoId : filterDTO.getGeoLevels2()){
			
				UserGeoLocation geoLocation = new UserGeoLocation();
				geoLocation.setGeoLevelId(geoId);
				geoLocation.setGeoLevelLevel(2);
				
				geoLocations.add(geoLocation);
			}
			
		}
		
		Set<Long> offersIdByGeo = new HashSet<Long>();
		
		try {
			List<GeoLevelDTO> geos = this.getGeoLevels(geoLocations);
			offersIdByGeo = jobOfferDAO.getByGeo(geos);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		JobOfferReportDTO result = new JobOfferReportDTO();
		result.setStartMonth(filterDTO.getStartMonth());
		result.setStartYear(filterDTO.getStartYear());
		result.setEndMonth(filterDTO.getEndMonth());
		result.setEndYear(filterDTO.getEndYear());
		
		List<JobOfferReportItemDTO> items = new ArrayList<JobOfferReportItemDTO>();
		
		if(filterDTO.isTotalOffers()) {
			List<Integer> totals = this.getTotalJobOfferQuantitiesMonthly(filterDTO, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos creados TOTALES", "rgba(255, 165, 0, 1)", totals));
		}
		
		if(filterDTO.isTemporalOffers()) {
			List<Integer> temporals = this.getTotalJobOfferQuantitiesMonthly(filterDTO, false, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos temporales", "rgba(0, 128, 128, 1)", temporals));
		}
		
		if(filterDTO.isPermanentOffers()) {
			List<Integer> permanents = this.getTotalJobOfferQuantitiesMonthly(filterDTO, true, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos permanentes", "rgba(3, 72, 123, 1)", permanents));
		}
		
		if(filterDTO.isActiveOffers()) {
			List<Integer> actives = this.getActiveJobOfferQuantitiesMonthly(filterDTO, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos creados Activos", "rgba(238,67,100, 1)", actives));
		}
		
		if(filterDTO.isContracted()) {
			List<Integer> contrateds = this.getJobOfferContractedMonthly(filterDTO, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Contrataciones", "rgba(111,111,111, 1)", contrateds));
		}
		
		result.setList(items);
		
		return result;
	}
	
	private List<Integer> getTotalJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getJobOfferQuantitiesMonthly(filterDTO, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
			now.set(Calendar.YEAR, filterDTO.getStartYear());
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfMonths(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	private List<Integer> getTotalJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, boolean permanent, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getJobOfferQuantitiesMonthly(filterDTO, permanent, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
			now.set(Calendar.YEAR, filterDTO.getStartYear());
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfMonths(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	private List<Integer> getActiveJobOfferQuantitiesMonthly(FilterJobOfferReportDTO filterDTO, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getActiveJobOfferQuantitiesMonthly(filterDTO, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
			now.set(Calendar.YEAR, filterDTO.getStartYear());
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfMonths(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	private List<Integer> getJobOfferContractedMonthly(FilterJobOfferReportDTO filterDTO, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getJobOfferContractedMonthly(filterDTO, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.MONTH, filterDTO.getStartMonth() - 1);
			now.set(Calendar.YEAR, filterDTO.getStartYear());
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfMonths(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	/* END Method for monthly report */
	
	/* BEGIN Method for daily report */
	
	@Override
	public JobOfferDailyReportDTO getJobOfferDailyReportDTO(FilterJobOfferDailyReportDTO filterDTO) throws ServiceException {
		
		Set<UserGeoLocation> geoLocations = new HashSet<UserGeoLocation>();
		if(filterDTO.getGeoLevels2()!=null && !containsFilterAll(filterDTO.getGeoLevels2())){
			
			for(Long geoId : filterDTO.getGeoLevels2()){
			
				UserGeoLocation geoLocation = new UserGeoLocation();
				geoLocation.setGeoLevelId(geoId);
				geoLocation.setGeoLevelLevel(2);
				
				geoLocations.add(geoLocation);
			}
			
		}
		
		Set<Long> offersIdByGeo = new HashSet<Long>();
		
		try {
			List<GeoLevelDTO> geos = this.getGeoLevels(geoLocations);
			offersIdByGeo = jobOfferDAO.getByGeo(geos);
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(filterDTO.getToDate());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		JobOfferDailyReportDTO result = new JobOfferDailyReportDTO();
		result.setToDate(calendar.getTime());
		
		calendar.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
		result.setFromDate(calendar.getTime());
		
		List<JobOfferReportItemDTO> items = new ArrayList<JobOfferReportItemDTO>();
		
		if(filterDTO.isTotalOffers()) {
			List<Integer> totals = this.getTotalJobOfferQuantitiesDaily(filterDTO, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos creados TOTALES", "rgba(255, 165, 0, 1)", totals));
		}
		
		if(filterDTO.isTemporalOffers()) {
			List<Integer> temporals = this.getTotalJobOfferQuantitiesDaily(filterDTO, false, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos temporales", "rgba(0, 128, 128, 1)", temporals));
		}
		
		if(filterDTO.isPermanentOffers()) {
			List<Integer> permanents = this.getTotalJobOfferQuantitiesDaily(filterDTO, true, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos permanentes", "rgba(3, 72, 123, 1)", permanents));
		}
		
		if(filterDTO.isActiveOffers()) {
			List<Integer> actives = this.getActiveJobOfferQuantitiesDaily(filterDTO, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Avisos creados Activos", "rgba(238,67,100, 1)", actives));
		}
		
		if(filterDTO.isContracted()) {
			List<Integer> contrateds = this.getJobOfferContractedDaily(filterDTO, offersIdByGeo);
			items.add(new JobOfferReportItemDTO("Contrataciones", "rgba(111,111,111, 1)", contrateds));
		}
		
		result.setList(items);
		
		return result;
	}
	
	private List<Integer> getTotalJobOfferQuantitiesDaily(FilterJobOfferDailyReportDTO filterDTO, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;
		int DAY_COLUMN = 3;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getJobOfferQuantitiesDaily(filterDTO, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.setTime(filterDTO.getToDate());
			now.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfDays(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[DAY_COLUMN] == now.get(Calendar.DAY_OF_MONTH) &&
					   (int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	private List<Integer> getTotalJobOfferQuantitiesDaily(FilterJobOfferDailyReportDTO filterDTO, boolean permanent, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;
		int DAY_COLUMN = 3;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getJobOfferQuantitiesDaily(filterDTO, permanent, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.setTime(filterDTO.getToDate());
			now.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfDays(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[DAY_COLUMN] == now.get(Calendar.DAY_OF_MONTH) &&
					   (int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	private List<Integer> getActiveJobOfferQuantitiesDaily(FilterJobOfferDailyReportDTO filterDTO, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;
		int DAY_COLUMN = 3;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getActiveJobOfferQuantitiesDaily(filterDTO, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.setTime(filterDTO.getToDate());
			now.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfDays(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[DAY_COLUMN] == now.get(Calendar.DAY_OF_MONTH) &&
					   (int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}
	
	private List<Integer> getJobOfferContractedDaily(FilterJobOfferDailyReportDTO filterDTO, Set<Long> offersIdByGeo) {
		int QUANTITY_COLUMN = 0;
		int YEAR_COLUMN = 1;
		int MONTH_COLUMN = 2;
		int DAY_COLUMN = 3;

		List<Integer >totals = new ArrayList<Integer>();
		
		List<Object> quantityObjects = new ArrayList<Object>();
		try {
			quantityObjects = jobOfferDAO.getJobOfferContractedDaily(filterDTO, offersIdByGeo);
			
			Calendar now = Calendar.getInstance();
			now.setTime(filterDTO.getToDate());
			now.add(Calendar.DAY_OF_MONTH, filterDTO.getQuantityOfDays() * -1);
			
			int initValue = 0;
			for (int i=0; i<filterDTO.getQuantityOfDays(); i++) {
				int qty = 0;
				for(int j=initValue; j<quantityObjects.size(); j++) {
					Object[] objects = (Object[]) quantityObjects.get(j);
					if((int)objects[DAY_COLUMN] == now.get(Calendar.DAY_OF_MONTH) &&
					   (int)objects[MONTH_COLUMN] - 1 == now.get(Calendar.MONTH) &&
					   (int)objects[YEAR_COLUMN] == now.get(Calendar.YEAR)) {
						initValue = j;
						qty = Integer.valueOf(""+objects[QUANTITY_COLUMN]);
						break;
					}
				}
				totals.add(qty);
				now.add(Calendar.DAY_OF_MONTH, 1);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return totals;
	}

	@Override
	public List<Subscription> getAllActiveSuscriptions() {
		List<Subscription> subscriptionList = subscriptionDAO.listAllSubscription();
		
		List<User> userSubscribedList = subscriptionList.stream().map(Subscription::getUser).collect(Collectors.toList());
		List<User> userUnSubscribedList = this.userDAO.getUserNotIn(userSubscribedList);
		
		Date todayDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(todayDate);
		c.add(Calendar.DATE, 90);
		Date datePlus90 = c.getTime();
		
		for (Subscription subscription : subscriptionList) {
			
			if(subscription.isFreeSuscription() == null || subscription.isFreeSuscription() == true) {
				subscription.setExpirationDate(datePlus90);
				subscription.setSubscriptionDetail(SuscriptionTypeEnum.EXTENDED_SUSCRIPTION.getMessage());
				try {
					subscriptionDAO.saveSubscription(subscription);
				} catch (DAOException e) {
					e.printStackTrace();
				}
			}

		}
		
		for (User user : userUnSubscribedList) {
			try {
				Subscription subscription = this.subscriptionService.createFreeSubscription(user, true);
				subscription.setSubscriptionDetail(SuscriptionTypeEnum.NEW_FREE_SUSCRIPTION.getMessage());
				subscriptionDAO.saveSubscription(subscription);
			} catch (ServiceException | DAOException e) {
				e.printStackTrace();
			}
		}
		return subscriptionList;
	}
	
	/* END Method for daily report */
}
