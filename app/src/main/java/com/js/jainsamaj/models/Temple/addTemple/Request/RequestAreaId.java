package com.js.jainsamaj.models.Temple.addTemple.Request;

import java.io.Serializable;

public class RequestAreaId implements Serializable {
    public int cityId;

    public RequestAreaId(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
