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
import com.tdil.d2d.controller.api.dto.NotificationDTO;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
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
	public ResponseEntity<GenericResponse<List<NotificationDTO>>> getAllNotifications() {
		try {
			List<NotificationDTO> list = this.notificationBackofficeService.getAllNotifications();
			if(list != null) {
				return new ResponseEntity<GenericResponse<List<NotificationDTO>>>(new GenericResponse<List<NotificationDTO>>(list,HttpStatus.OK.value()), HttpStatus.OK);
			} else {
				return new ResponseEntity<GenericResponse<List<NotificationDTO>>>(new GenericResponse<List<NotificationDTO>>(null,HttpStatus.OK.value()), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<List<NotificationDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@RequestMapping(value = "/notification/getUnreadNotification", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<Integer>> getUnread() {
		try {
			Integer count = this.notificationBackofficeService.getUnreadNotifications();
			return new ResponseEntity<GenericResponse<Integer>>(new GenericResponse<Integer>(count,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<GenericResponse<Integer>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
