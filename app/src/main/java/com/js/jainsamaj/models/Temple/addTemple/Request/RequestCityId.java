package com.js.jainsamaj.models.Temple.addTemple.Request;

import java.io.Serializable;

public class RequestCityId implements Serializable {
    public int districtId;
    public int stateId;

    public RequestCityId(int districtId) {
        this.districtId = districtId;
    }

    public RequestCityId(int districtId, int stateId) {
        this.districtId = districtId;
        this.stateId = stateId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
