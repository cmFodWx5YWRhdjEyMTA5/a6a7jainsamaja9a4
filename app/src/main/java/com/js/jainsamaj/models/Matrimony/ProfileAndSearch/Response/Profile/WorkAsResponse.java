package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Profile;

import java.io.Serializable;

/**
 * Created by arbaz on 10/6/17.
 */

public class WorkAsResponse implements Serializable {
    public String workAs;
    public String description;
    public int id;
    public int status;

    public String getWorkAs() {
        return workAs;
    }

    public void setWorkAs(String workAs) {
        this.workAs = workAs;
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
