package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search;

import java.io.Serializable;

public class MarriageDetails implements Serializable {
	public String engagedWith;
	public String currentCityInLaw;
	public String anniversaryDate;
	public String fatherInLaw;
	public String engagementDate;
	public String nativePlaceInLaw;
	public String maritalstatus;
	public int id;
	public String updatedUser;
	public long updatedDateTime;
	public boolean status;
	public String marriedWith;

	public String getEngagedWith() {
		return engagedWith;
	}

	public void setEngagedWith(String engagedWith) {
		this.engagedWith = engagedWith;
	}

	public String getCurrentCityInLaw() {
		return currentCityInLaw;
	}

	public void setCurrentCityInLaw(String currentCityInLaw) {
		this.currentCityInLaw = currentCityInLaw;
	}

	public String getAnniversaryDate() {
		return anniversaryDate;
	}

	public void setAnniversaryDate(String anniversaryDate) {
		this.anniversaryDate = anniversaryDate;
	}

	public String getFatherInLaw() {
		return fatherInLaw;
	}

	public void setFatherInLaw(String fatherInLaw) {
		this.fatherInLaw = fatherInLaw;
	}

	public String getEngagementDate() {
		return engagementDate;
	}

	public void setEngagementDate(String engagementDate) {
		this.engagementDate = engagementDate;
	}

	public String getNativePlaceInLaw() {
		return nativePlaceInLaw;
	}

	public void setNativePlaceInLaw(String nativePlaceInLaw) {
		this.nativePlaceInLaw = nativePlaceInLaw;
	}

	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMarriedWith() {
		return marriedWith;
	}

	public void setMarriedWith(String marriedWith) {
		this.marriedWith = marriedWith;
	}
}