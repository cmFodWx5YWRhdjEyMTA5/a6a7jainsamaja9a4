package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;

public class DistrictId implements Serializable{
	public int id;
	public String districtName;
	public String description;
	public boolean status=true;

	public DistrictId() {
	}

	public DistrictId(int id, String districtName) {
		this.id = id;
		this.districtName = districtName;
	}

	public void setDistrictName(String districtName){
		this.districtName = districtName;
	}

	public String getDistrictName(){
		return districtName;
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
			"DistrictIdP{" +
			"districtName = '" + districtName + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
