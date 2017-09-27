package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common;

import java.io.Serializable;

public class EducationField implements Serializable{
	public String educationField;
	public String description;
	public int id;
	public boolean status;

	public void setEducationField(String educationField){
		this.educationField = educationField;
	}

	public String getEducationField(){
		return educationField;
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
