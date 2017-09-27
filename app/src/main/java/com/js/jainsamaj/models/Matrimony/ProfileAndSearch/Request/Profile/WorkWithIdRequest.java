package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile;

import java.io.Serializable;

/**
 * Created by arbaz on 25/5/17.
 */

public class WorkWithIdRequest implements Serializable {
    int id;

    public WorkWithIdRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

