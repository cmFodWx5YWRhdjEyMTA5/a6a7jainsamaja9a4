package com.js.jainsamaj.models.Wall.temp;

import java.io.Serializable;

public class ProductService implements Serializable{
	public String companyName;
	public String contact;
	public long createdDateTime;
	public String description;
	public int id;
	public String video;
	public String updatedUser;
	public String title;
	public long updatedDateTime;
	public boolean status;
	public String productService;

	public String getCompanyName(){
		return companyName;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getContact(){
		return contact;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public long getCreatedDateTime(){
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime){
		this.createdDateTime = createdDateTime;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getVideo(){
		return video;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getUpdatedUser(){
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser){
		this.updatedUser = updatedUser;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public long getUpdatedDateTime(){
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime){
		this.updatedDateTime = updatedDateTime;
	}

	public boolean isStatus(){
		return status;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public String getProductService(){
		return productService;
	}

	public void setProductService(String productService){
		this.productService = productService;
	}

	@Override
 	public String toString(){
		return 
			"ProductService{" + 
			"companyName = '" + companyName + '\'' + 
			",contact = '" + contact + '\'' + 
			",createdDateTime = '" + createdDateTime + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",video = '" + video + '\'' + 
			",updatedUser = '" + updatedUser + '\'' + 
			",title = '" + title + '\'' + 
			",updatedDateTime = '" + updatedDateTime + '\'' + 
			",status = '" + status + '\'' + 
			",productService = '" + productService + '\'' + 
			"}";
		}
}
