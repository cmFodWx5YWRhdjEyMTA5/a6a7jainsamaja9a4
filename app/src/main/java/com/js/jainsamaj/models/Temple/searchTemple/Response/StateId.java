package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;

public class StateId implements Serializable{
	public int id;
	public String stateName;
	public String description;
	public boolean status=true;

	public StateId() {
	}

	public StateId(int id, String stateName) {
		this.id = id;
		this.stateName = stateName;
	}

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

	@Override
 	public String toString(){
		return 
			"StateIdP{" +
			"stateName = '" + stateName + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
