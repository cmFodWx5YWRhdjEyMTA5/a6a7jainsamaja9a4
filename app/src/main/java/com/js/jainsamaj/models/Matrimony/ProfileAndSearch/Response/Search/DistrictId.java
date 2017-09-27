package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search;

import java.io.Serializable;

public class DistrictId implements Serializable{
	public String districtName;
	public String description;
	public int id;
	public boolean status;

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
