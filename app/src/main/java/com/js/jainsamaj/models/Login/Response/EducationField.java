package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class EducationField implements Serializable{
	public String educationField;
	public String description;
	public int id;
	public boolean status;

	public String getEducationField() {
		return educationField;
	}

	public void setEducationField(String educationField) {
		this.educationField = educationField;
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
