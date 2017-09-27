package com.js.jainsamaj.models.Temple.addTemple.Request;

import java.io.Serializable;

public class RequestStateId implements Serializable {
	public int countryId;

	public RequestStateId(int countryId) {
		this.countryId = countryId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
}
