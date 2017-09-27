package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

public class GetPhonebookListMain implements Serializable {
    public String createdUser;

    public GetPhonebookListMain(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }
}
