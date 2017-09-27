package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 10/2/17.
 */
public class UserLogin implements Serializable {
    public String username;
    public String password;
    public String deviceUDID;

    public UserLogin(String username, String password, String deviceUDID) {
        this.username = username;
        this.password = password;
        this.deviceUDID = deviceUDID;
    }

}
