package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile;

import java.io.Serializable;

public class CityLivingRequest implements Serializable{
	public int id;

	public CityLivingRequest(int id) {
		this.id = id;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}
}
