package com.js.jainsamaj.models.Matrimony;

import java.io.Serializable;

/**
 * Created by arbaz on 21/2/17.
 */
public class ListMain implements Serializable {
    String name;

    public ListMain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
