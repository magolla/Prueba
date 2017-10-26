package com.tdil.d2d.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tdil.d2d.bo.dto.BoNotificationDTO;
import com.tdil.d2d.bo.dto.NotificationBackofficeDTO;
import com.tdil.d2d.controller.api.dto.NotificationDTO;
import com.tdil.d2d.dao.NotificationConfigurationDAO;
import com.tdil.d2d.dao.NotificationDAO;
import com.tdil.d2d.dao.UserDAO;
import com.tdil.d2d.exceptions.DAOException;
import com.tdil.d2d.persistence.Notification;
import com.tdil.d2d.persistence.NotificationConfiguration;
import com.tdil.d2d.persistence.NotificationType;
import com.tdil.d2d.persistence.User;
import com.tdil.d2d.service.NotificationBackofficeService;
import com.tdil.d2d.service.NotificationService;
import com.tdil.d2d.service.SessionService;

@Transactional
@Service
public class NotificationServiceImpl implements NotificationBackofficeService { 

	@Autowired
	private NotificationDAO notificationDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	@Qualifier(value = "androidNotificationServiceImpl")
	private NotificationService androidNotificationService;
	@Autowired
	@Qualifier(value = "iosNotificationServiceImpl")
	private NotificationService iosNotificationService;

	@Autowired
	private NotificationConfigurationDAO notificationConfigurationDAO;

	@Autowired
	private SessionService sessionService;


	/**
	 * Este metodo se usa para registrar eventos genericos, se deben enviar el titulo,cuerpo,etc por medio de la llamada POST
	 */
	@Override
	public boolean sendNotification(NotificationBackofficeDTO notificationBackofficeDTO) {
		try {
			User user = this.userDAO.getById(User.class, notificationBackofficeDTO.getUserId());
			NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());

			//			JobOffer jobOffer = this.jobOfferDAO.getById(JobOffer.class, 1);

			NotificationType type;
			try {
				type = NotificationType.valueOf(notificationBackofficeDTO.getAction());	
			} catch (IllegalArgumentException e) {
				type = null;
			}

			Notification notification = new Notification();
			notification.setAction(notificationBackofficeDTO.getAction());
			notification.setActionId(notificationBackofficeDTO.getActionId());
			notification.setCreationDate(new Date());

			if(type == null) {
				notification.setTitle(notificationBackofficeDTO.getTitle());
				notification.setMessage(notificationBackofficeDTO.getMessage());

			} else {
				if(notificationBackofficeDTO.getTitle().equals("") && notificationBackofficeDTO.getMessage().equals("")) {
					notification.setTitle(type.getTitle());
					notification.setMessage(type.getMessage());
				} else {
					notification.setTitle(notificationBackofficeDTO.getTitle());
					notification.setMessage(notificationBackofficeDTO.getMessage());
				}
			}

			notification.setStatus("Enviado");
			notification.setUser(user);

			this.notificationDAO.save(notification);


			boolean sendNotif = validateNotificationConfig(notificationConfiguration,type);

			if(sendNotif) {
				if(user.getIosPushId()!=null && !"NONE".equals(user.getIosPushId())){
					iosNotificationService.sendNotification(notification,type);
				} else if(user.getAndroidRegId()!=null){
					androidNotificationService.sendNotification(notification,type);
				}
			}
			return true;

		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}

	}


	@Override
	public List<NotificationDTO> getAllNotifications() {
		User user = this.sessionService.getUserLoggedIn();

		List<Notification> list = this.notificationDAO.getAllNotificationByUserId(user.getId());
		List<NotificationDTO> response = null;
		if(list != null) {
			response = list.stream().map((elem) -> toDTO(elem)).collect(Collectors.toList());
		}
		return response;
	}


	private NotificationDTO toDTO(Notification elem) {
		NotificationDTO dto = new NotificationDTO();

		dto.setId(elem.getId());
		dto.setAction(elem.getAction());
		dto.setActionId(elem.getActionId());
		dto.setCreationDate(elem.getCreationDate().toString());
		dto.setMessage(elem.getMessage());
		if(elem.getOffer() != null) {
			dto.setOfferId(elem.getOffer().getId());
		}
		dto.setStatus(elem.getStatus());
		dto.setTitle(elem.getTitle());
		dto.setUserId(elem.getUser().getId());


		if(elem.getOffer() != null) {

			NotificationType type;
			try {
				type = NotificationType.valueOf(elem.getAction());	

				switch (type) {
				case NEW_OFFER_MATCH:
					if(elem.getOffer().isPermanent()) {
						dto.setMessage(elem.getOffer().getTitle() + " " + elem.getOffer().getSubtitle());
					} else {
						dto.setMessage(elem.getOffer().getOccupation().getName() + 
								(elem.getOffer().getSpecialty().getName().isEmpty() ? "" :  ", " + elem.getOffer().getSpecialty().getName() + ",") +
								" para trabajos de " + elem.getOffer().getTask().getName());
					}
					break;
				case NEW_APPLICATION:
					if(elem.getOffer().isPermanent()) {
						dto.setMessage("Alguien se postulo a tu oferta " + elem.getOffer().getTitle() + " " + elem.getOffer().getSubtitle());
					} else {
						dto.setMessage("Alguien se postulo a tu oferta " + elem.getOffer().getOccupation().getName() +
								(elem.getOffer().getSpecialty().getName().isEmpty() ? "" :  ", " + elem.getOffer().getSpecialty().getName() + ",") +
								" para trabajos de " + elem.getOffer().getTask().getName());
					}
					break;
				default:
					break;
				}
			} catch (IllegalArgumentException e) {
				System.out.println("La notificacion no posee action predefinido, no se cambiara el titulo y message");
			}
		}

		if(elem.getNote() != null) {
			//			dto.setTitle(elem.getNote().getTitle());
			dto.setMessage(elem.getNote().getTitle() + " " + elem.getNote().getSubtitle());
		}

		return dto;
	}


	public static boolean validateNotificationConfig(NotificationConfiguration notificationConfiguration, NotificationType type) {
		boolean result = true;

		NotificationType notification = type;

		if(type != null) {
			switch (notification) {
			case NEW_CONGRESS:
				if(!notificationConfiguration.isCongress()) {
					result = false;
				}
				break;
			case NEW_GRANT:
				if(!notificationConfiguration.isGrantOffers()) {
					result = false;
				}
				break;
			case NEW_NOTE:
				if(!notificationConfiguration.isNotes()) {
					result = false;
				}
				break;
			case NEW_PRODUCTANDSERVICES:
				if(!notificationConfiguration.isProductAndServices()) {
					result = false;
				}
				break;
			case NEW_PROMOTION:
				if(!notificationConfiguration.isPromotionsOffers()) {
					result = false;
				}
				break;
			default:
				break;
			}
		}
		if(!notificationConfiguration.isPush()) {
			result = false;
		}

		//Los 2 que siguen no se estan usando pero se dejan modelados
		//		if(!notificationConfiguration.isNotif9to20() && "verifica que horario actual no sea entre 9 a 20") {
		//			result = false;
		//		}

		//		if(!notificationConfiguration.isNotifAllDay()) {
		//			result = false;
		//		}

		// Este no se setea nunca, teoricamente va dentro de Cursos, congresos y jornadas
		//		if(!notificationConfiguration.isCourses()) {
		//			result = false;
		//		}

		return result;
	}


	@Override
	public Integer getUnreadNotifications() {
		User user = this.sessionService.getUserLoggedIn();

		Integer count = this.notificationDAO.getCountNotificationByUserId(user.getId());

		return count;
	}


	@Override
	public boolean sendBackOfficeNotification(BoNotificationDTO boNotificationDTO) {
		try {

			List<User> userList = userDAO.getUsersBoNotification(boNotificationDTO);

			for (User user : userList) {
				NotificationConfiguration notificationConfiguration = this.notificationConfigurationDAO.getByUser(user.getId());

				Notification notification = new Notification();
				notification.setCreationDate(new Date());

				notification.setTitle(boNotificationDTO.getTitulo());
				notification.setMessage(boNotificationDTO.getMessage());

				notification.setStatus("Enviado");
				notification.setUser(user);
				notification.setAction("Default");

				if(user.getIosPushId() != null || user.getAndroidRegId() != null) {
					this.notificationDAO.save(notification);
				}

				boolean sendNotif = validateNotificationConfig(notificationConfiguration,null);

				if(sendNotif) {
					if(user.getIosPushId()!=null && !"NONE".equals(user.getIosPushId())){
						iosNotificationService.sendNotification(notification,null);
					} else if(user.getAndroidRegId()!=null){
						androidNotificationService.sendNotification(notification,null);
					}
				}
			}

			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
		
	}


}
