package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class TrainingDetails implements Serializable{
	public String trainingSubject;
	public String trainingStartYear;
	public String description;
	public int id;
	public String trainingStartMonth;
	public String trainingEndYear;
	public String instituteAddress;
	public String trainingEndMonth;
	public boolean status;
	public String instituteName;

	public String getTrainingSubject() {
		return trainingSubject;
	}

	public void setTrainingSubject(String trainingSubject) {
		this.trainingSubject = trainingSubject;
	}

	public String getTrainingStartYear() {
		return trainingStartYear;
	}

	public void setTrainingStartYear(String trainingStartYear) {
		this.trainingStartYear = trainingStartYear;
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

	public String getTrainingStartMonth() {
		return trainingStartMonth;
	}

	public void setTrainingStartMonth(String trainingStartMonth) {
		this.trainingStartMonth = trainingStartMonth;
	}

	public String getTrainingEndYear() {
		return trainingEndYear;
	}

	public void setTrainingEndYear(String trainingEndYear) {
		this.trainingEndYear = trainingEndYear;
	}

	public String getInstituteAddress() {
		return instituteAddress;
	}

	public void setInstituteAddress(String instituteAddress) {
		this.instituteAddress = instituteAddress;
	}

	public String getTrainingEndMonth() {
		return trainingEndMonth;
	}

	public void setTrainingEndMonth(String trainingEndMonth) {
		this.trainingEndMonth = trainingEndMonth;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
}
