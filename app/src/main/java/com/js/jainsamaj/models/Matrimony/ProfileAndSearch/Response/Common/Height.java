package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common;

import java.io.Serializable;

public class Height implements Serializable {
    public int id;
    public boolean status;
    public String height;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }
}
