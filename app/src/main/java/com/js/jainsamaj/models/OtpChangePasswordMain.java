package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 16/5/17.
 */

public class OtpChangePasswordMain implements Serializable {


    public String otp;
    public String username;
    public String newPassword;

    public OtpChangePasswordMain(String otp, String username, String newPassword) {
        this.otp = otp;
        this.username = username;
        this.newPassword = newPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

