package com.hcsc.pier.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hcsc.pier.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

	User findByUserId(String userId);
	
	
}
