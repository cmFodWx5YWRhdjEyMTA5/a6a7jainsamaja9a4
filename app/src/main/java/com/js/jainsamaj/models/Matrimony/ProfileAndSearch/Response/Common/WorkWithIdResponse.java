package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common;

import java.io.Serializable;

/**
 * Created by arbaz on 25/5/17.
 */

public class WorkWithIdResponse implements Serializable {

    public String workWithCategory;
    public String description;
    public int id;
    public int status;
    public WorkWithIdResponse(int id) {
        this.id = id;
    }

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

