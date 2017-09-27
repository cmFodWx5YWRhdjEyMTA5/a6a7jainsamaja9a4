package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 25/5/17.
 */

public class MatrimonialStatus implements Serializable{
    public String username;

    public MatrimonialStatus(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
