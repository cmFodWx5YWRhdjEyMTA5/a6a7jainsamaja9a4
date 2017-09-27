package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class SchoolCollegeDetails implements Serializable{
	public String boardOrUniversityName;
	public String passingYear;
	public String schoolOrcollegeName;
	public Degree degree;
	public int id;
	public String moreInfo;
	public boolean status;

	public String getBoardOrUniversityName() {
		return boardOrUniversityName;
	}

	public void setBoardOrUniversityName(String boardOrUniversityName) {
		this.boardOrUniversityName = boardOrUniversityName;
	}

	public String getPassingYear() {
		return passingYear;
	}

	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
	}

	public String getSchoolOrcollegeName() {
		return schoolOrcollegeName;
	}

	public void setSchoolOrcollegeName(String schoolOrcollegeName) {
		this.schoolOrcollegeName = schoolOrcollegeName;
	}

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
