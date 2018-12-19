package com.hcsc.pier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonIgnore
	private long id;

	@Column
	private String username;
	
	@Column(unique=true)
	private String userId;

	@Column
	@JsonIgnore
	private String password;

	@Column
	private Integer failedAttempts;

	@Column
	private Boolean userBlocked;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public Boolean getUserBlocked() {
		return userBlocked;
	}

	public void setUserBlocked(Boolean userBlocked) {
		this.userBlocked = userBlocked;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", userId=" + userId + ", password=" + password
				+ ", failedAttempts=" + failedAttempts + ", userBlocked=" + userBlocked + "]";
	}

}
