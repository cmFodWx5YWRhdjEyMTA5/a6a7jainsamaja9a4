package com.js.jainsamaj.models.Wall.temp;

import java.io.Serializable;

public class ViewIntrest implements Serializable{
	public PostedBy postedBy;
	public InterestedBy interestedBy;
	public String postType;
	public long createdDateTime;
	public int id;
	public long updatedDateTime;
	public boolean status;
	public ProductService productService;

	public PostedBy getPostedBy(){
		return postedBy;
	}

	public void setPostedBy(PostedBy postedBy){
		this.postedBy = postedBy;
	}

	public InterestedBy getInterestedBy(){
		return interestedBy;
	}

	public void setInterestedBy(InterestedBy interestedBy){
		this.interestedBy = interestedBy;
	}

	public String getPostType(){
		return postType;
	}

	public void setPostType(String postType){
		this.postType = postType;
	}

	public long getCreatedDateTime(){
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime){
		this.createdDateTime = createdDateTime;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
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

	public ProductService getProductService(){
		return productService;
	}

	public void setProductService(ProductService productService){
		this.productService = productService;
	}

	@Override
 	public String toString(){
		return 
			"ViewIntrest{" + 
			"postedBy = '" + postedBy + '\'' + 
			",interestedBy = '" + interestedBy + '\'' + 
			",postType = '" + postType + '\'' + 
			",createdDateTime = '" + createdDateTime + '\'' + 
			",id = '" + id + '\'' + 
			",updatedDateTime = '" + updatedDateTime + '\'' + 
			",status = '" + status + '\'' + 
			",productService = '" + productService + '\'' + 
			"}";
		}
}
