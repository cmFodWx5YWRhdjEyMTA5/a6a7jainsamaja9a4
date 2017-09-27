package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

public class AreaIdP implements Serializable{
	public int id;

	public AreaIdP(int id) {
		this.id = id;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
