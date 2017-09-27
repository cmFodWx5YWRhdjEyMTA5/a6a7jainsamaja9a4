package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

public class AddressListMainRequest implements Serializable{
	public String pincode;
	public String address;
	public String districtName;
	public String addressType;
	public StateIdP stateId;
	public CityIdP cityId;
	public CountryIdP countryId;
	public DistrictIdP districtId;
	public AreaIdP areaId;
	public String cityName;
	public String stateName;
	public String areaName;
	public String countryName;
	public String id;
	public String lang;
	public String lat;

	public AddressListMainRequest() {
	}

	public void setPincode(String pincode){
		this.pincode = pincode;
	}

	public String getPincode(){
		return pincode;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDistrictName(String districtName){
		this.districtName = districtName;
	}

	public String getDistrictName(){
		return districtName;
	}

	public void setAddressType(String addressType){
		this.addressType = addressType;
	}

	public String getAddressType(){
		return addressType;
	}

	public void setStateId(StateIdP stateId){
		this.stateId = stateId;
	}

	public StateIdP getStateId(){
		return stateId;
	}

	public void setCityId(CityIdP cityId){
		this.cityId = cityId;
	}

	public CityIdP getCityId(){
		return cityId;
	}

	public void setCountryId(CountryIdP countryId){
		this.countryId = countryId;
	}

	public CountryIdP getCountryId(){
		return countryId;
	}

	public void setDistrictId(DistrictIdP districtId){
		this.districtId = districtId;
	}

	public DistrictIdP getDistrictId(){
		return districtId;
	}

	public void setAreaId(AreaIdP areaId){
		this.areaId = areaId;
	}

	public AreaIdP getAreaId(){
		return areaId;
	}

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setStateName(String stateName){
		this.stateName = stateName;
	}

	public String getStateName(){
		return stateName;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return areaName;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public String getLang(){
		return lang;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}


}
