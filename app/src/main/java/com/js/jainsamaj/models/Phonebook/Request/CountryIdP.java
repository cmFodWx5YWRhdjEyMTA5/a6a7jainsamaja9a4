package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

public class CountryIdP implements Serializable{
	public int id;

	public CountryIdP(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
