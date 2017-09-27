package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 25/5/17.
 */

public class IsRegisterMain implements Serializable {
    public boolean isRegistered;

    public IsRegisterMain(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
