package com.tdil.d2d.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tdil.d2d.controller.api.dto.ActivityLogDTO;
import com.tdil.d2d.controller.api.dto.Base64DTO;
import com.tdil.d2d.controller.api.dto.ProfileResponseDTO;
import com.tdil.d2d.controller.api.request.AddLocationRequest;
import com.tdil.d2d.controller.api.request.AddLocationsRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtiesRequest;
import com.tdil.d2d.controller.api.request.AddSpecialtyRequest;
import com.tdil.d2d.controller.api.request.AddTaskToProfileRequest;
import com.tdil.d2d.controller.api.request.AndroidRegIdRequest;
import com.tdil.d2d.controller.api.request.Base64Request;
import com.tdil.d2d.controller.api.request.ConfigureNotificationsRequest;
import com.tdil.d2d.controller.api.request.CreatePaymentRequest;
import com.tdil.d2d.controller.api.request.CreatePreferenceMPRequest;
import com.tdil.d2d.controller.api.request.IOsPushIdRequest;
import com.tdil.d2d.controller.api.request.NotificationConfigurationResponse;
import com.tdil.d2d.controller.api.request.RegistrationRequestA;
import com.tdil.d2d.controller.api.request.RegistrationRequestB;
import com.tdil.d2d.controller.api.request.SendSMSRequest;
import com.tdil.d2d.controller.api.request.SetAvatarRequest;
import com.tdil.d2d.controller.api.request.SetInstitutionTypeRequest;
import com.tdil.d2d.controller.api.request.SetLicenseRequest;
import com.tdil.d2d.controller.api.request.SetProfileARequest;
import com.tdil.d2d.controller.api.request.SetProfileBRequest;
import com.tdil.d2d.controller.api.request.SetTasksToProfileRequest;
import com.tdil.d2d.controller.api.request.UserLinkedinProfileRequest;
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

	private Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @RequestMapping(value = "/user/registerA", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequestA registrationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<RegistrationResponse>(getErrorResponse(bidingResult, new RegistrationResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			RegistrationResponse response = this.userService.register(registrationRequest);
			return new ResponseEntity<RegistrationResponse>(response, HttpStatus.CREATED);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
            RegistrationResponse response = new RegistrationResponse(0);
            response.addError(e.getLocalizedMessage());
			return new ResponseEntity<RegistrationResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@RequestMapping(value = "/user/registerB", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequestB registrationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<RegistrationResponse>(getErrorResponse(bidingResult, new RegistrationResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			RegistrationResponse response = this.userService.register(registrationRequest);
			return new ResponseEntity<RegistrationResponse>(response, HttpStatus.CREATED);
		} catch (ServiceException e) {
            LoggerManager.error(this, e);
            RegistrationResponse response = new RegistrationResponse(0);
            response.addError(e.getLocalizedMessage());
            return new ResponseEntity<RegistrationResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

 

    // TODO 
//    profesion (1) - especialidades cada ve que toca graba
//    
//    intereses labolarales especialidad + tarea combo
//    tipo de institucion
//    
//    my perfil zonas


    @RequestMapping(value = "/user/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> validate(@Valid @RequestBody ValidationRequest validationRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean result = this.userService.validate(validationRequest);
			logger.info("User is valid? {}", result);
			if (result) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/user/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> sendSMS(@Valid @RequestBody SendSMSRequest request, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			this.userService.sendSMS(request);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/androidRegId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/iosPushId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/specialty", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/specialty/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addSpecialties(@Valid @RequestBody AddSpecialtiesRequest addSpecialtiesRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.addSpecialties(addSpecialtiesRequest);
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

    @RequestMapping(value = "/user/location", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/location/addAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> addLocations(@Valid @RequestBody AddLocationsRequest addLocationsRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
			boolean response = this.userService.addLocations(addLocationsRequest);
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
    
    @RequestMapping(value = "/user/linkedin/auth/step1", method = RequestMethod.GET)
    public ResponseEntity<GenericResponse<String>> linkedinAuthStep1(HttpServletResponse response, HttpServletRequest request) {
        String code = request.getParameter("code");
        return new ResponseEntity<GenericResponse<String>>(new GenericResponse<String>(code, HttpStatus.OK.value()), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/linkedin/profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setUserLinkedinProfile(@Valid @RequestBody UserLinkedinProfileRequest userLinkedinProfileRequest, BindingResult bidingResult) {
        if (bidingResult.hasErrors()) {
            return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
        }
        try {
            this.userService.updateUserLinkedinProfile(userLinkedinProfileRequest);
            return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
        } catch (ServiceException e) {
            LoggerManager.error(this, e);
            return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/cv", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setCV(@Valid @RequestBody Map<String, Object> cv, BindingResult bidingResult) {
        if (bidingResult.hasErrors()) {
            return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
        }
        try {
            this.userService.setCV(cv.get("cv").toString());
            return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
        } catch (ServiceException e) {
            LoggerManager.error(this, e);
            return new ResponseEntity<ApiResponse>((ApiResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/user/cv/pdf", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setPdfCV(@Valid @RequestBody Base64Request base64Request, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		this.userService.setPdfCV(base64Request);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			ApiResponse apiResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiResponse.addError(e.getLocalizedMessage());
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @RequestMapping(value = "/user/cv/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<Base64DTO>> getPdfCV() {
    	try {
    		Base64DTO pdfCV = this.userService.getPdfCVBase64();
			return new ResponseEntity<GenericResponse<Base64DTO>>(new GenericResponse<Base64DTO>(pdfCV,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Base64DTO>>(new GenericResponse<Base64DTO>(null,HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/license", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/notifications", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationConfigurationResponse> getNotication() {
    	try {
    		NotificationConfigurationResponse response = this.userService.getNotificationConfiguration();
			return new ResponseEntity<NotificationConfigurationResponse>(response, HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<NotificationConfigurationResponse>((NotificationConfigurationResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/me", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<UserDetailsResponse>> me() {
    	try {
    		UserDetailsResponse me = this.userService.me();
			return new ResponseEntity<GenericResponse<UserDetailsResponse>>(new GenericResponse<UserDetailsResponse>(me,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<UserDetailsResponse>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }


	@RequestMapping(value = "/user/{userId}/get", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse<UserDetailsResponse>> getUser(@PathVariable long userId) {
		try {
			UserDetailsResponse me = this.userService.getUser(userId);
			return new ResponseEntity<GenericResponse<UserDetailsResponse>>(new GenericResponse<UserDetailsResponse>(me, HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<UserDetailsResponse>>((GenericResponse) null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    // TODO
//    terminos y condiciones
//    
//    /POST de upload de avatar

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse<ProfileResponseDTO>> getProfile() {
    	try {
    		ProfileResponseDTO me = this.userService.profile();
			return new ResponseEntity<GenericResponse<ProfileResponseDTO>>(new GenericResponse<ProfileResponseDTO>(me,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<ProfileResponseDTO>>((GenericResponse)null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/profileA", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setProfileA(@Valid @RequestBody SetProfileARequest setProfileARequest, BindingResult bidingResult) {
    	try {
    		this.userService.setProfileA(setProfileARequest);
    		return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/profileB", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setProfileB(@Valid @RequestBody SetProfileBRequest setProfileBRequest, BindingResult bidingResult) {
    	try {
    		this.userService.setProfileB(setProfileBRequest);
    		return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/profile/avatar", method = RequestMethod.GET)
    public void getAvatar(HttpServletResponse response) {
    	try {
    		this.userService.getAvatar(response.getOutputStream());
		} catch (ServiceException | IOException e) {
			LoggerManager.error(this, e);
		}
    }

    @RequestMapping(value = "/user/profile/avatarBase64", method = RequestMethod.GET)
    public ResponseEntity<GenericResponse<Base64DTO>> getAvatarBase64() {
    	try {
    		Base64DTO me = this.userService.getAvatarBase64();
			return new ResponseEntity<GenericResponse<Base64DTO>>(new GenericResponse<Base64DTO>(me,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Base64DTO>>(new GenericResponse<Base64DTO>(null,HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/{userId}/profile/avatar", method = RequestMethod.GET)
    public void getOtherUserAvatar(@PathVariable long userId, HttpServletResponse response) {
    	try {
    		this.userService.getAvatar(userId, response.getOutputStream());
		} catch (ServiceException | IOException e) {
			LoggerManager.error(this, e);
		}
    }

    @RequestMapping(value = "/user/{userId}/profile/avatarBase64", method = RequestMethod.GET)
    public ResponseEntity<GenericResponse<Base64DTO>> getOtherUserAvatar(@PathVariable long userId) {
    	try {
    		Base64DTO me = this.userService.getAvatarBase64(userId);
			return new ResponseEntity<GenericResponse<Base64DTO>>(new GenericResponse<Base64DTO>(me,HttpStatus.OK.value()), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return new ResponseEntity<GenericResponse<Base64DTO>>(new GenericResponse<Base64DTO>(null,HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/profile/avatar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
			ApiResponse apiResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiResponse.addError(e.getLocalizedMessage());
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @RequestMapping(value = "/user/profile/institutionType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/profile/task", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/profile/task/setAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> setTask(@Valid @RequestBody SetTasksToProfileRequest tasksToProfileRequest, BindingResult bidingResult) {
    	if (bidingResult.hasErrors()) {
    		return new ResponseEntity<ApiResponse>(getErrorResponse(bidingResult, new ApiResponse(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
    	}
    	try {
    		boolean response = this.userService.setTasks(tasksToProfileRequest);
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

    @RequestMapping(value = "/user/profile/task/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/user/log", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    
    @RequestMapping(value = "/user/mercadopago/preference", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> createPreference(@Valid @RequestBody CreatePreferenceMPRequest createPreferenceMPRequest, BindingResult bidingResult) {
    	try {
			return new ResponseEntity<String>(this.userService.createMercadoPagoPreference(createPreferenceMPRequest), HttpStatus.OK);
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return null;
		}
    }
    
    @RequestMapping(value = "/user/mercadopago/payment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createPayment(@Valid @RequestBody CreatePaymentRequest createPaymentRequest, BindingResult bidingResult) {
    	try {
			boolean response = this.userService.createPayment(createPaymentRequest);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return null;
		}
    }

    @RequestMapping(value = "/user/switchToB", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> switchToB() {
    	boolean response = this.userService.switchToB();
		if (response) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.CREATED.value()), HttpStatus.CREATED);	
		} else {
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@RequestMapping(value = "/user/offer/{offerId}/notify", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   	public ResponseEntity<ApiResponse> notifyNewOfferToMatchedUsers(@PathVariable long offerId) {
		try {
			boolean response = this.userService.notifyToMatchedUsers(offerId);
			if (response) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value()), HttpStatus.OK);	
			} else {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ServiceException e) {
			LoggerManager.error(this, e);
			return null;
		}
		
    }
}
