package com.js.jainsamaj.models.Temple.searchTemple.Request;

import java.io.Serializable;

public class SearchTempleMain implements Serializable {
    public String countryId;
    public String stateId;
    public String cityId;
    public String templeName;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SearchTempleMain(String countryId, String stateId, String cityId, String templeName,String id) {
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.templeName = templeName;
        this.id = id;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }
}

