package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Common;

import java.io.Serializable;

public class GetMatrimony implements Serializable {
    public String createdUser;




    public GetMatrimony(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
}
