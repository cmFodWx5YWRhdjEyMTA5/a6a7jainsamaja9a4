package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

/**
 * Created by arbaz on 1/3/17.
 */

public class PhoneBookDetailsRequestMain implements Serializable {
    public String id;

    public PhoneBookDetailsRequestMain(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
