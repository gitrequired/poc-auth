package com.hcsc.pier.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcsc.pier.constants.Constants;
import com.hcsc.pier.model.User;
import com.hcsc.pier.service.UserService;

@RequestMapping("user")
@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Object> validat() {
		return new ResponseEntity<Object>("Application in up and running", HttpStatus.OK);
	}

	@PostMapping(value = "validate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> Validate(@RequestParam Map<String, String> userDetails) {
		if (userDetails == null || userDetails.get(Constants.USER_NAME) == null
				|| userDetails.get(Constants.USER_PASSWORD) == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		if (userDetails.get(Constants.USER_NAME).equalsIgnoreCase("")
				|| userDetails.get(Constants.USER_PASSWORD).equalsIgnoreCase("")) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		User availableUser = userService.findUser(userDetails);
		if (availableUser != null) {
			if (availableUser.getUserBlocked()) {
				return new ResponseEntity<Object>(Constants.USER_REQUEST_LOCKED, HttpStatus.LOCKED);
			}
			if (availableUser.getFailedAttempts() > Constants.USER_MAX_FAIL_ENTRY) {
				return new ResponseEntity<Object>(
						Constants.USER_WRONG_PASSWORD_ENTRY + availableUser.getFailedAttempts(), HttpStatus.LOCKED);
			}
			return new ResponseEntity<Object>(availableUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Constants.USER_RESPONSE_MSG, HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping(value = "create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> createUser(@RequestParam Map<String, String> newUserDetails) {

		if (newUserDetails == null || newUserDetails.get(Constants.USER_NAME) == null
				|| newUserDetails.get(Constants.USER_PASSWORD) == null
				|| newUserDetails.get(Constants.USER_SSN) == null) {
			return new ResponseEntity<>(Constants.USER_DETAILS_MISSING, HttpStatus.NOT_ACCEPTABLE);
		}
		if (newUserDetails.get(Constants.USER_NAME).equalsIgnoreCase("")
				|| newUserDetails.get(Constants.USER_PASSWORD).equalsIgnoreCase("")
				|| newUserDetails.get(Constants.USER_SSN).equalsIgnoreCase("")) {
			return new ResponseEntity<>(Constants.USER_DETAILS_MISSING, HttpStatus.NOT_ACCEPTABLE);
		}
		if (userService.createUser(newUserDetails)) {
			return new ResponseEntity<>(Constants.USER_CREATED, HttpStatus.OK);
		}
		return new ResponseEntity<>(Constants.USER_EXISTS, HttpStatus.NOT_ACCEPTABLE);
	}

	@PutMapping(value = "changePassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> changePassword(@RequestParam Map<String, String> newUserDetails) {
		if (newUserDetails == null || newUserDetails.get(Constants.USER_SSN) == null
				|| newUserDetails.get(Constants.USER_PASSWORD) == null
				|| newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD) == null
				|| newUserDetails.get(Constants.CONFIRM_USER_PASSWORD) == null) {
			return new ResponseEntity<>(Constants.USER_DETAILS_MISSING, HttpStatus.NOT_ACCEPTABLE);
		}
		if (newUserDetails.get(Constants.USER_SSN).equalsIgnoreCase("")
				|| newUserDetails.get(Constants.USER_PASSWORD).equalsIgnoreCase("")
				|| newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD).equalsIgnoreCase("")
				|| newUserDetails.get(Constants.CONFIRM_USER_PASSWORD).equalsIgnoreCase("")) {
			return new ResponseEntity<>(Constants.USER_DETAILS_MISSING, HttpStatus.NOT_ACCEPTABLE);
		}
		if (newUserDetails.get(Constants.USER_PASSWORD)
				.equalsIgnoreCase(newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD))) {
			return new ResponseEntity<>(Constants.USER_OLD_NEW_PASSWORD_NOT_MATCHED, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD)
				.equalsIgnoreCase(newUserDetails.get(Constants.CONFIRM_USER_PASSWORD))) {
			return new ResponseEntity<>(Constants.USER_NEW_PASSWORD_CONFIRM_PASSWORD_NOT_MATCHED,
					HttpStatus.NOT_ACCEPTABLE);
		}
		if (userService.changePassword(newUserDetails)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(Constants.USER_DETAILS_MISSING, HttpStatus.NOT_ACCEPTABLE);
	}

}
