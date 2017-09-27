package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 10/2/17.
 */

public class ApiResponseMessage implements Serializable {
    public int status;
    public String message;
    public boolean register;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }
}
