package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 24/1/17.
 */

public class ForgotPasswordMain implements Serializable {
    public String username;

    public ForgotPasswordMain(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
