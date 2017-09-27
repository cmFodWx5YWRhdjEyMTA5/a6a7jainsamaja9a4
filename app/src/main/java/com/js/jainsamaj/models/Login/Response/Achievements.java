package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;

public class Achievements implements Serializable{

	public String expirationYear;
	public String certificationMonthDetails;
	public String certificationYear;
	public long createdDateTime;
	public String description;
	public String updatedUser;
	public String expirationMonthDetails;
	public long updatedDateTime;
	public String achievementTitle;
	public String expired;
	public String authority;
	public String achievementPhotoLink;
	public int id;
	public String createdUser;
	public boolean status;

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getCertificationMonthDetails() {
		return certificationMonthDetails;
	}

	public void setCertificationMonthDetails(String certificationMonthDetails) {
		this.certificationMonthDetails = certificationMonthDetails;
	}

	public String getCertificationYear() {
		return certificationYear;
	}

	public void setCertificationYear(String certificationYear) {
		this.certificationYear = certificationYear;
	}

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getExpirationMonthDetails() {
		return expirationMonthDetails;
	}

	public void setExpirationMonthDetails(String expirationMonthDetails) {
		this.expirationMonthDetails = expirationMonthDetails;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getAchievementTitle() {
		return achievementTitle;
	}

	public void setAchievementTitle(String achievementTitle) {
		this.achievementTitle = achievementTitle;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAchievementPhotoLink() {
		return achievementPhotoLink;
	}

	public void setAchievementPhotoLink(String achievementPhotoLink) {
		this.achievementPhotoLink = achievementPhotoLink;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
