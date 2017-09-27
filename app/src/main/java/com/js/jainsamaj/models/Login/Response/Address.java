package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class Address implements Serializable{
	public StatePrimary statePrimary;
	public CityPrimary cityPrimary;
	public CountryPrimary countryPrimary;
	public String addressPrimary;
	public String pincodeSecondary;
	public DistrictPrimary districtPrimary;
	public String pincodePrimary;
	public AreaPrimary areaPrimary;
	public String phoneSecondary;
	public String phonePrimary;
	public int id;
	public String addressSecondary;
	public String fullAddress;
	public String lat;
	public String lang;
	public boolean status;

	public StatePrimary getStatePrimary() {
		return statePrimary;
	}

	public void setStatePrimary(StatePrimary statePrimary) {
		this.statePrimary = statePrimary;
	}

	public CityPrimary getCityPrimary() {
		return cityPrimary;
	}

	public void setCityPrimary(CityPrimary cityPrimary) {
		this.cityPrimary = cityPrimary;
	}

	public CountryPrimary getCountryPrimary() {
		return countryPrimary;
	}

	public void setCountryPrimary(CountryPrimary countryPrimary) {
		this.countryPrimary = countryPrimary;
	}

	public String getAddressPrimary() {
		return addressPrimary;
	}

	public void setAddressPrimary(String addressPrimary) {
		this.addressPrimary = addressPrimary;
	}

	public String getPincodeSecondary() {
		return pincodeSecondary;
	}

	public void setPincodeSecondary(String pincodeSecondary) {
		this.pincodeSecondary = pincodeSecondary;
	}

	public DistrictPrimary getDistrictPrimary() {
		return districtPrimary;
	}

	public void setDistrictPrimary(DistrictPrimary districtPrimary) {
		this.districtPrimary = districtPrimary;
	}

	public String getPincodePrimary() {
		return pincodePrimary;
	}

	public void setPincodePrimary(String pincodePrimary) {
		this.pincodePrimary = pincodePrimary;
	}

	public AreaPrimary getAreaPrimary() {
		return areaPrimary;
	}

	public void setAreaPrimary(AreaPrimary areaPrimary) {
		this.areaPrimary = areaPrimary;
	}

	public String getPhoneSecondary() {
		return phoneSecondary;
	}

	public void setPhoneSecondary(String phoneSecondary) {
		this.phoneSecondary = phoneSecondary;
	}

	public String getPhonePrimary() {
		return phonePrimary;
	}

	public void setPhonePrimary(String phonePrimary) {
		this.phonePrimary = phonePrimary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddressSecondary() {
		return addressSecondary;
	}

	public void setAddressSecondary(String addressSecondary) {
		this.addressSecondary = addressSecondary;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
