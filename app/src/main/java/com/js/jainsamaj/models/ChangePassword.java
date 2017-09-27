package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 10/2/17.
 */
public class ChangePassword implements Serializable {
    public String username;
    public String newPassword;
    public String oldPassword;

    public ChangePassword(String username, String newPassword, String oldPassword) {
        this.username = username;
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }
}
