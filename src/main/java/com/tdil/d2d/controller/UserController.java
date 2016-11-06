package com.tdil.d2d.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.ProfileResponseDTO;
import com.tdil.d2d.controller.api.request.AddLocationRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtyRequest;
import com.tdil.d2d.controller.api.request.AddTaskToProfileRequest;
import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.ConfigureNotificationsRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.NotificationConfigurationResponse;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.controller.api.request.RegistrationRequestB;
import com.tdil.d2d.controller.api.request.SetAvatarRequest;
import com.tdil.d2d.controller.api.request.SetInstitutionTypeRequest;
import com.tdil.d2d.controller.api.request.SetLicenseRequest;
import com.tdil.d2d.controller.api.request.ValidationRequest;
import com.tdil.d2d.controller.api.response.ApiResponse;
import com.tdil.d2d.controller.api.response.GenericResponse;
import com.tdil.d2d.controller.api.response.RegistrationResponse;
import com.tdil.d2d.controller.api.response.UserDetailsResponse;
import com.tdil.d2d.exceptions.ServiceException;
import com.tdil.d2d.security.JwtTokenUtil;
import com.tdil.d2d.service.UserService;
import com.tdil.d2d.utils.LoggerManager;

/**
 *
 */
@Controller
public class UserController extends AbstractController {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    private static final String UNKNOWN_HOST = "unknown";

    public static String HOSTNAME;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    static {
        HOSTNAME = UNKNOWN_HOST;
        try {
            HOSTNAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            HOSTNAME = UNKNOWN_HOST;
        }
    }

    @RequestMapping(value = "/api/user/registerA", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequestA registrationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<RegistrationResponse>(getErrorResponse(bidingResult, new RegistrationResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			RegistrationResponse response = this.userService.register(registrationRequest);
			return new ResponseEntity<RegistrationResponse>(response, HttpStatus.CREATED);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<RegistrationResponse>((RegistrationResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@RequestMapping(value = "/api/user/registerB", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequestB registrationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<RegistrationResponse>(getErrorResponse(bidingResult, new RegistrationResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			RegistrationResponse response = this.userService.register(registrationRequest);
			return new ResponseEntity<RegistrationResponse>(response, HttpStatus.CREATED);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<RegistrationResponse>((RegistrationResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	
    
    // TODO 
//    profesion (1) - especialidades cada ve que toca graba
//    
//    intereses labolarales especialidad + tarea combo
//    tipo de institucion
//    
//    my perfil zonas
    
    
    @RequestMapping(value = "/api/user/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody ValidationRequest validationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean result = this.userService.validate(validationRequest);
			if (result) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/androidRegId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> androidRegId(@Valid @RequestBody AndroidRegIdRequest androidRegIdRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.updateAndroidRegId(androidRegIdRequest);
			return new ResponseEntity<ApiResponse>(new ApiResponse(response == true ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/iosPushId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> iosPushId(@Valid @RequestBody IOsPushIdRequest iOsPushIdRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.updateIOsPushId(iOsPushIdRequest);
			return new ResponseEntity<ApiResponse>(new ApiResponse(response == true ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/user/validateEmail", method = RequestMethod.GET)
    public ModelAndView validateEmail(@RequestParam("email") String email, @RequestParam("hash") String hash) {
    	try {
			boolean validated = this.userService.validateEmail(email, hash);
			if (validated) {
				return new ModelAndView("emailValidated");
			} else {
				return new ModelAndView("emailNotValidated");
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ModelAndView("emailNotValidated");
		}
    }
    
    @RequestMapping(value = "/api/user/specialty", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addSpecialty(@Valid @RequestBody AddSpecialtyRequest addSpecialtyRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.addSpecialty(addSpecialtyRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.CREATED.value()), HttpStatus.CREATED);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/location", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addLocation(@Valid @RequestBody AddLocationRequest addLocationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.addLocation(addLocationRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.CREATED.value()), HttpStatus.CREATED);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/license", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setLicense(@Valid @RequestBody SetLicenseRequest setLicenseRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.setLicense(setLicenseRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/notifications", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationConfigurationResponse> getNotication() {
    	try {
    		NotificationConfigurationResponse response = this.userService.getNotificationConfiguration();
			return new ResponseEntity<NotificationConfigurationResponse>(response, HttpStatus.OK);	
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<NotificationConfigurationResponse>((NotificationConfigurationResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setNotication(@Valid @RequestBody ConfigureNotificationsRequest notificationConfiguration, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		boolean response = this.userService.setNotificationConfiguration(notificationConfiguration);
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/me", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserDetailsResponse>> me() {
    	try {
    		UserDetailsResponse me = this.userService.me();
			return new ResponseEntity<GenericResponse<UserDetailsResponse>>(new GenericResponse<UserDetailsResponse>(me,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<UserDetailsResponse>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    // TODO
//    terminos y condiciones
//    
//    /POST de upload de avatar
    
    @RequestMapping(value = "/api/user/profile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<ProfileResponseDTO>> getProfile() {
    	try {
    		ProfileResponseDTO me = this.userService.profile();
			return new ResponseEntity<GenericResponse<ProfileResponseDTO>>(new GenericResponse<ProfileResponseDTO>(me,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<ProfileResponseDTO>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/profile/avatar", method = RequestMethod.GET)
    public void getAvatar(HttpServletResponse response) {
    	try {
    		this.userService.getAvatar(response.getOutputStream());
		} catch (ServiceException | IOException e) {
			LoggerManager.error(this, e);
		}
    }
    
    @RequestMapping(value = "/api/user/profile/avatar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setAvatar(@Valid @RequestBody SetAvatarRequest setAvatarRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		boolean response = this.userService.setAvatar(setAvatarRequest);
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/profile/institutionType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setNotification(@Valid @RequestBody SetInstitutionTypeRequest institutionTypeRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		boolean response = this.userService.setInstitutionType(institutionTypeRequest);
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/profile/task", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addTask(@Valid @RequestBody AddTaskToProfileRequest taskToProfileRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		boolean response = this.userService.addTask(taskToProfileRequest);
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/profile/task/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> removeTask(@Valid @RequestBody AddTaskToProfileRequest taskToProfileRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		boolean response = this.userService.removeTask(taskToProfileRequest);
    		if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/api/user/log", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<List<ActivityLogDTO>>> activityLog() {
    	try {
			List<ActivityLogDTO> myOffers = this.userService.getActivityLog();
			return new ResponseEntity<GenericResponse<List<ActivityLogDTO>>>(new GenericResponse<List<ActivityLogDTO>>(myOffers,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<List<ActivityLogDTO>>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/test111", method = RequestMethod.GET)
    public ModelAndView test() {
		return new ModelAndView("index");

    }
    
}
