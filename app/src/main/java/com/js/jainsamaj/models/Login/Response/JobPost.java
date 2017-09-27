package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class JobPost implements Serializable{
	public EducationField educationField;
	public String endDate;
	public String uploadMediaLink;
	public String companyName;
	public String jobTitle;
	public String packageTo;
	public long createdDateTime;
	public String experience;
	public String jobLocation;
	public EducationLevel educationLevel;
	public String packageFrom;
	public String contactNumber;
	public String jobRole;
	public String jobDescription;
	public int id;
	public String createdUser;
	public String startDate;
	public boolean status;

	public EducationField getEducationField() {
		return educationField;
	}

	public void setEducationField(EducationField educationField) {
		this.educationField = educationField;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUploadMediaLink() {
		return uploadMediaLink;
	}

	public void setUploadMediaLink(String uploadMediaLink) {
		this.uploadMediaLink = uploadMediaLink;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPackageTo() {
		return packageTo;
	}

	public void setPackageTo(String packageTo) {
		this.packageTo = packageTo;
	}

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getPackageFrom() {
		return packageFrom;
	}

	public void setPackageFrom(String packageFrom) {
		this.packageFrom = packageFrom;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
