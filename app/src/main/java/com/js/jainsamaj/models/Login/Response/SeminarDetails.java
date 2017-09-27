package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class SeminarDetails implements Serializable {
	public String seminarStartYear;
	public String seminarSubject;
	public String seminarEndYear;
	public String seminarEndMonth;
	public String description;
	public int id;
	public String instituteAddress;
	public boolean status;
	public String instituteName;
	public String seminarStartMonth;

	public String getSeminarStartYear() {
		return seminarStartYear;
	}

	public void setSeminarStartYear(String seminarStartYear) {
		this.seminarStartYear = seminarStartYear;
	}

	public String getSeminarSubject() {
		return seminarSubject;
	}

	public void setSeminarSubject(String seminarSubject) {
		this.seminarSubject = seminarSubject;
	}

	public String getSeminarEndYear() {
		return seminarEndYear;
	}

	public void setSeminarEndYear(String seminarEndYear) {
		this.seminarEndYear = seminarEndYear;
	}

	public String getSeminarEndMonth() {
		return seminarEndMonth;
	}

	public void setSeminarEndMonth(String seminarEndMonth) {
		this.seminarEndMonth = seminarEndMonth;
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

	public String getInstituteAddress() {
		return instituteAddress;
	}

	public void setInstituteAddress(String instituteAddress) {
		this.instituteAddress = instituteAddress;
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

	public String getSeminarStartMonth() {
		return seminarStartMonth;
	}

	public void setSeminarStartMonth(String seminarStartMonth) {
		this.seminarStartMonth = seminarStartMonth;
	}
}