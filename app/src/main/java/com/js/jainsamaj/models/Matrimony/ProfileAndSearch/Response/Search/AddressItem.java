package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search;

import java.io.Serializable;

public class AddressItem implements Serializable {
	public String pincode;
	public String address;
	public DistrictId districtId;
	public AreaId areaId;
	public String addressType;
	public StateId stateId;
	public String fullAddress;
	public int id;
	public CityId cityId;
	public String lang;
	public String lat;
	public CountryId countryId;

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public DistrictId getDistrictId() {
		return districtId;
	}

	public void setDistrictId(DistrictId districtId) {
		this.districtId = districtId;
	}

	public AreaId getAreaId() {
		return areaId;
	}

	public void setAreaId(AreaId areaId) {
		this.areaId = areaId;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public StateId getStateId() {
		return stateId;
	}

	public void setStateId(StateId stateId) {
		this.stateId = stateId;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CityId getCityId() {
		return cityId;
	}

	public void setCityId(CityId cityId) {
		this.cityId = cityId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public CountryId getCountryId() {
		return countryId;
	}

	public void setCountryId(CountryId countryId) {
		this.countryId = countryId;
	}
}