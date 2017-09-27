package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common;

import java.io.Serializable;

public class StateLiving implements Serializable{
	public String stateName;
	public String description;
	public int id;
	public boolean status;

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}
