package com.js.jainsamaj.models;

import java.io.Serializable;

public class UserProfileRequestMain implements Serializable{
	public String createdUser;

	public UserProfileRequestMain(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
}
