package com.js.jainsamaj.models.Temple.addTemple.Request;

import java.io.Serializable;

public class RequestDistrictId implements Serializable{
	public int stateId;

	public RequestDistrictId(int stateId) {
		this.stateId = stateId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
}
