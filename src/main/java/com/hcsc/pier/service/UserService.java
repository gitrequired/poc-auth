package com.hcsc.pier.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcsc.pier.constants.Constants;
import com.hcsc.pier.dao.UserDao;
import com.hcsc.pier.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public User findUser(Map<String, String> userDetails) {
		User user = userDao.findByUserId(userDetails.get(Constants.USER_NAME).toUpperCase());
		if (user != null) {
			if (user.getPassword().equalsIgnoreCase(userDetails.get(Constants.USER_PASSWORD))) {
				return user;
			} else {
				int failedAttemps = user.getFailedAttempts();
				if(failedAttemps == Constants.USER_MAX_FAIL_ENTRY) {
					user.setUserBlocked(true);
				}
				user.setFailedAttempts(failedAttemps+1);
				userDao.save(user);
				System.out.println("Failed Attempts :: "+user.getFailedAttempts());
				return null;
			}
		}
		return null;
	}

	public Boolean createUser(Map<String, String> newUserDetails) {
		
		User userAvailability = userDao.findByUserId(newUserDetails.get(Constants.USER_SSN));
		if (userAvailability == null) {
			User newUser = new User();
			newUser.setUsername(newUserDetails.get(Constants.USER_NAME));
			newUser.setPassword(newUserDetails.get(Constants.USER_PASSWORD).toUpperCase());
			newUser.setUserId(newUserDetails.get(Constants.USER_SSN).toUpperCase());
			newUser.setUserBlocked(false);
			newUser.setFailedAttempts(0);
			try {
				userDao.save(newUser);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public boolean changePassword(Map<String, String> newUserDetails) {
		User userAvailability = userDao.findByUserId(newUserDetails.get(Constants.USER_SSN).toUpperCase());
		if (userAvailability != null) {
			if(userAvailability.getPassword().equalsIgnoreCase(newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD))) {
				System.out.println("check :: "+userAvailability.getPassword().equalsIgnoreCase(newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD)));
				return false;
			}
			else {
				userAvailability.setPassword(newUserDetails.get(Constants.CONFIRM_NEW_USER_PASSWORD));
				userDao.save(userAvailability);
				return true;
			}
		}
		return false;
	}
}
