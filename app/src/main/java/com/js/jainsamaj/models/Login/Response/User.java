package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class User implements Serializable{
	public int id;
	public String username;
	public boolean status;
	public String deviceUDID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDeviceUDID() {
		return deviceUDID;
	}

	public void setDeviceUDID(String deviceUDID) {
		this.deviceUDID = deviceUDID;
	}
}
