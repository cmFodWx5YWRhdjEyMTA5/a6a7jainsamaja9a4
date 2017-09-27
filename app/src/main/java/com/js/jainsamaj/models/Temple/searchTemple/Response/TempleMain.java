package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;
import java.util.List;

public class TempleMain implements Serializable{
	public boolean adminApproval;
	public String templeVideo;
	public long createdDateTime;
	public String description;
	public CityId cityId;
	public CountryId countryId;
	public TempleCategory templeCategory;
	public List<String> logoLink;
	public String contact;
	public String templeName;
	public int id;
	public boolean userInactiveStatus;
	public String lang;
	public String createdUser;
	public boolean adminInactiveStatus;
	public String lat;
	public String address;
	public StateId stateId;
	public String updatedUser;
	public long updatedDateTime;
	public DistrictId districtId;
	public AreaId areaId;
	public String fullAddress;
	public User user;
	public boolean status;

	public void setAdminApproval(boolean adminApproval){
		this.adminApproval = adminApproval;
	}

	public boolean isAdminApproval(){
		return adminApproval;
	}

	public void setTempleVideo(String templeVideo){
		this.templeVideo = templeVideo;
	}

	public String getTempleVideo(){
		return templeVideo;
	}

	public void setCreatedDateTime(long createdDateTime){
		this.createdDateTime = createdDateTime;
	}

	public long getCreatedDateTime(){
		return createdDateTime;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCityId(CityId cityId){
		this.cityId = cityId;
	}

	public CityId getCityId(){
		return cityId;
	}

	public void setCountryId(CountryId countryId){
		this.countryId = countryId;
	}

	public CountryId getCountryId(){
		return countryId;
	}

	public void setTempleCategory(TempleCategory templeCategory){
		this.templeCategory = templeCategory;
	}

	public TempleCategory getTempleCategory(){
		return templeCategory;
	}

	public void setLogoLink(List<String> logoLink){
		this.logoLink = logoLink;
	}

	public List<String> getLogoLink(){
		return logoLink;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setTempleName(String templeName){
		this.templeName = templeName;
	}

	public String getTempleName(){
		return templeName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUserInactiveStatus(boolean userInactiveStatus){
		this.userInactiveStatus = userInactiveStatus;
	}

	public boolean isUserInactiveStatus(){
		return userInactiveStatus;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public String getLang(){
		return lang;
	}

	public void setCreatedUser(String createdUser){
		this.createdUser = createdUser;
	}

	public String getCreatedUser(){
		return createdUser;
	}

	public void setAdminInactiveStatus(boolean adminInactiveStatus){
		this.adminInactiveStatus = adminInactiveStatus;
	}

	public boolean isAdminInactiveStatus(){
		return adminInactiveStatus;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setStateId(StateId stateId){
		this.stateId = stateId;
	}

	public StateId getStateId(){
		return stateId;
	}

	public void setUpdatedUser(String updatedUser){
		this.updatedUser = updatedUser;
	}

	public String getUpdatedUser(){
		return updatedUser;
	}

	public void setUpdatedDateTime(long updatedDateTime){
		this.updatedDateTime = updatedDateTime;
	}

	public long getUpdatedDateTime(){
		return updatedDateTime;
	}

	public void setDistrictId(DistrictId districtId){
		this.districtId = districtId;
	}

	public DistrictId getDistrictId(){
		return districtId;
	}

	public void setAreaId(AreaId areaId){
		this.areaId = areaId;
	}

	public AreaId getAreaId(){
		return areaId;
	}

	public void setFullAddress(String fullAddress){
		this.fullAddress = fullAddress;
	}

	public String getFullAddress(){
		return fullAddress;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
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
			"TempleMain{" + 
			"adminApproval = '" + adminApproval + '\'' + 
			",templeVideo = '" + templeVideo + '\'' + 
			",createdDateTime = '" + createdDateTime + '\'' + 
			",description = '" + description + '\'' + 
			",cityId = '" + cityId + '\'' + 
			",countryId = '" + countryId + '\'' + 
			",templeCategory = '" + templeCategory + '\'' + 
			",logoLink = '" + logoLink + '\'' + 
			",contact = '" + contact + '\'' + 
			",templeName = '" + templeName + '\'' + 
			",id = '" + id + '\'' + 
			",userInactiveStatus = '" + userInactiveStatus + '\'' + 
			",lang = '" + lang + '\'' + 
			",createdUser = '" + createdUser + '\'' + 
			",adminInactiveStatus = '" + adminInactiveStatus + '\'' + 
			",lat = '" + lat + '\'' + 
			",address = '" + address + '\'' + 
			",stateId = '" + stateId + '\'' + 
			",updatedUser = '" + updatedUser + '\'' + 
			",updatedDateTime = '" + updatedDateTime + '\'' + 
			",districtId = '" + districtId + '\'' + 
			",areaId = '" + areaId + '\'' + 
			",fullAddress = '" + fullAddress + '\'' + 
			",user = '" + user + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}