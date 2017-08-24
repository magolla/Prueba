package com.tdil.d2d.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdil.d2d.bo.dto.NotificationBackofficeDTO;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.persistence.Notification;
import com.tdil.d2d.service.NotificationBackofficeService;

/**
 *
 */
@Controller
public class NotificationsController {

	@Autowired
	private NotificationBackofficeService notificationBackofficeService;


	@RequestMapping(value = "/notification/sendNotification", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> sendNotification(@RequestBody NotificationBackofficeDTO notification) {
		boolean result = this.notificationBackofficeService.sendNotification(notification);
		if(result) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
		} else {
			return null;
		}
	}
	
	 @RequestMapping(value = "/notification/getAllNotifications", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<GenericResponse<List<Notification>>> sendNotification() {
			List<Notification> list = this.notificationBackofficeService.getAllNotifications();
			if(list != null) {
				return new ResponseEntity<GenericResponse<List<Notification>>>(new GenericResponse<List<Notification>>(list,HttpStatus.OK.value()), HttpStatus.OK);
			} else {
				return new ResponseEntity<GenericResponse<List<Notification>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

}
