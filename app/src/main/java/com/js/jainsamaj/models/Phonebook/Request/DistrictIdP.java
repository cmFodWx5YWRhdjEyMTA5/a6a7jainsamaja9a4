package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

public class DistrictIdP implements Serializable{
	public int id;

	public DistrictIdP(int id) {
		this.id = id;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
