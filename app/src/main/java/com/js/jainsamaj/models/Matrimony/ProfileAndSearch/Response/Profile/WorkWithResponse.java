package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Profile;

import java.io.Serializable;

/**
 * Created by arbaz on 10/6/17.
 */

public class WorkWithResponse implements Serializable {

    public String workWithCategory;
    public String description;
    public int id;
    public int status;

    public String getWorkWithCategory() {
        return workWithCategory;
    }

    public void setWorkWithCategory(String workWithCategory) {
        this.workWithCategory = workWithCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
