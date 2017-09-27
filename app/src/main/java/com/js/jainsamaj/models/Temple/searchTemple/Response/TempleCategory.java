package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;

public class TempleCategory implements Serializable{
	public int id;
	public String categoryName;
	public boolean status;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
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
			"TempleCategory{" + 
			"id = '" + id + '\'' + 
			",categoryName = '" + categoryName + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
