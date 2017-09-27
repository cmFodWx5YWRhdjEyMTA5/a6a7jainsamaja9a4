package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;

public class CityId implements Serializable{
	public int id;
	public String cityName;
	public String cityCode;
	public String description;
	public boolean status=true;

	public CityId() {
	}

	public CityId(int id, String cityName) {
		this.id = id;
		this.cityName = cityName;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setCityCode(String cityCode){
		this.cityCode = cityCode;
	}

	public String getCityCode(){
		return cityCode;
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
			"CityIdP{" +
			"cityName = '" + cityName + '\'' + 
			",cityCode = '" + cityCode + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
