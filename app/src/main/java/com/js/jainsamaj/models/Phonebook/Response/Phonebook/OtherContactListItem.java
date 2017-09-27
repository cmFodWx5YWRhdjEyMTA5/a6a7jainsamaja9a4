package com.js.jainsamaj.models.Phonebook.Response.Phonebook;

import java.io.Serializable;

public class OtherContactListItem implements Serializable{
	public String name;
	public boolean isPrivate;
	public int userId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean aPrivate) {
		isPrivate = aPrivate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
