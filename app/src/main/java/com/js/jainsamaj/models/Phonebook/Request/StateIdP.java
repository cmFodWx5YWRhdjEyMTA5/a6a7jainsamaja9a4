package com.js.jainsamaj.models.Phonebook.Request;

import java.io.Serializable;

public class StateIdP implements Serializable{
	public int id;

	public StateIdP(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
