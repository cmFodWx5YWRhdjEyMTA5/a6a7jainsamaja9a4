package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;

public class User implements Serializable{
	public int id;
	public String username;
	public boolean status;
	public String deviceUDID;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setDeviceUDID(String deviceUDID){
		this.deviceUDID = deviceUDID;
	}

	public String getDeviceUDID(){
		return deviceUDID;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"id = '" + id + '\'' + 
			",username = '" + username + '\'' + 
			",status = '" + status + '\'' + 
			",deviceUDID = '" + deviceUDID + '\'' + 
			"}";
		}
}
