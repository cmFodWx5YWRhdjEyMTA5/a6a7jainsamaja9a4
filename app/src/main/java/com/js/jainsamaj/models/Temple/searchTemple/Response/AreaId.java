package com.js.jainsamaj.models.Temple.searchTemple.Response;

import java.io.Serializable;

public class AreaId implements Serializable {
    public int id;
    public String areaName;
    public String description;
    public boolean status=true;

    public AreaId() {
    }

    public AreaId(int id, String areaName) {
        this.id = id;
        this.areaName = areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return
                "AreaIdP{" +
                        "areaName = '" + areaName + '\'' +
                        ",description = '" + description + '\'' +
                        ",id = '" + id + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
