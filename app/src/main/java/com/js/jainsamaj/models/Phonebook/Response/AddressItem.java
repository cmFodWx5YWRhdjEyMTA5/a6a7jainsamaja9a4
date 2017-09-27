package com.js.jainsamaj.models.Phonebook.Response;

import com.js.jainsamaj.models.Temple.searchTemple.Response.AreaId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CityId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CountryId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.DistrictId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.StateId;

import java.io.Serializable;

public class AddressItem implements Serializable {
    public String address;
    public String fullAddress;
    public String lang;
    public String lat;
    public String addressType;
    public String pincode;
    public int id;
    public CountryId countryId;
    public StateId stateId;
    public DistrictId districtId;
    public CityId cityId;
    public AreaId areaId;

    /**
     *
     * 	public String pincode;
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
     public String lat;*/

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryId getCountryId() {
        return countryId;
    }

    public void setCountryId(CountryId countryId) {
        this.countryId = countryId;
    }

    public StateId getStateId() {
        return stateId;
    }

    public void setStateId(StateId stateId) {
        this.stateId = stateId;
    }

    public DistrictId getDistrictId() {
        return districtId;
    }

    public void setDistrictId(DistrictId districtId) {
        this.districtId = districtId;
    }

    public CityId getCityId() {
        return cityId;
    }

    public void setCityId(CityId cityId) {
        this.cityId = cityId;
    }

    public AreaId getAreaId() {
        return areaId;
    }

    public void setAreaId(AreaId areaId) {
        this.areaId = areaId;
    }
}
