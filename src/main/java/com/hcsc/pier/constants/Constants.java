package com.hcsc.pier.constants;

public interface Constants {

	public static final String USER_NAME = "username";
	public static final String USER_PASSWORD = "password";
	public static final String USER_RESPONSE_MSG = "User Login Details are not matched please check.";
	public static final String USER_REQUEST_LOCKED = "User locked due to security reasons. Please try next day.";
	public static final Integer USER_MAX_FAIL_ENTRY = 3;
	
	public static final String USER_SSN = "ssn";
	public static final String USER_DETAILS_MISSING = "Some Details are Missing please Check";
	public static final String USER_EXISTS = "User With same SSN already exists";
	public static final String USER_CREATED = "User Created";
	public static final String USER_WRONG_PASSWORD_ENTRY = "Password incorrect attempted times +";
	public static final String CONFIRM_USER_PASSWORD = "cpassword";
	public static final String CONFIRM_NEW_USER_PASSWORD = "npassword";
	public static final String USER_OLD_NEW_PASSWORD_NOT_MATCHED = "user old password and new password are same";
	public static final String USER_NEW_PASSWORD_CONFIRM_PASSWORD_NOT_MATCHED = "User new password and confirmed password are not matching";
}
