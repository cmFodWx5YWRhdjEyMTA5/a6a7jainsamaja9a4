package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile;

import java.io.Serializable;

/**
 * Created by arbaz on 25/5/17.
 */

public class WorkAsIdRequest implements Serializable {
    int workAsId;

    public WorkAsIdRequest(int workAsId) {
        this.workAsId = workAsId;
    }

    public int getWorkAsId() {
        return workAsId;
    }

    public void setWorkAsId(int workAsId) {
        this.workAsId = workAsId;
    }
}

