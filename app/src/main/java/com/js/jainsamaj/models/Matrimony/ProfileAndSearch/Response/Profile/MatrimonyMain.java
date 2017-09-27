package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Profile;

import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.CityLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.CountryLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.EducationField;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.EducationLevel;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.Height;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.StateLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.User;

import java.io.Serializable;

public class MatrimonyMain implements Serializable {
	public String bodyType;
	public WorkAsResponse workAs;
	public WorkWithResponse workWith;
	public String workAsStr;
	public String workWithStr;
	public long annualIncome;
	public StateLiving stateLiving;
	public EducationField educationField;
	public String gender;
	public String gotra;
	public String mobileNumber;
	public String disability;
	public String weight;
	public String skinTone;
	public long updatedDateTime;
	public CountryLiving countryLiving;
	public String aboutMyself;
	public CityLiving cityLiving;
	public EducationLevel educationLevel;
	public String hobbies;
	public int id;
	public User user;
	public int age;
	public String maritalStatus;
	public boolean status;
	public Height height;

	public MatrimonyMain() {
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public WorkAsResponse getWorkAs() {
		return workAs;
	}

	public void setWorkAs(WorkAsResponse workAs) {
		this.workAs = workAs;
	}

	public WorkWithResponse getWorkWith() {
		return workWith;
	}

	public void setWorkWith(WorkWithResponse workWith) {
		this.workWith = workWith;
	}

	public String getWorkAsStr() {
		return workAsStr;
	}

	public void setWorkAsStr(String workAsStr) {
		this.workAsStr = workAsStr;
	}

	public String getWorkWithStr() {
		return workWithStr;
	}

	public void setWorkWithStr(String workWithStr) {
		this.workWithStr = workWithStr;
	}

	public long getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(long annualIncome) {
		this.annualIncome = annualIncome;
	}

	public StateLiving getStateLiving() {
		return stateLiving;
	}

	public void setStateLiving(StateLiving stateLiving) {
		this.stateLiving = stateLiving;
	}

	public EducationField getEducationField() {
		return educationField;
	}

	public void setEducationField(EducationField educationField) {
		this.educationField = educationField;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGotra() {
		return gotra;
	}

	public void setGotra(String gotra) {
		this.gotra = gotra;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSkinTone() {
		return skinTone;
	}

	public void setSkinTone(String skinTone) {
		this.skinTone = skinTone;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public CountryLiving getCountryLiving() {
		return countryLiving;
	}

	public void setCountryLiving(CountryLiving countryLiving) {
		this.countryLiving = countryLiving;
	}

	public String getAboutMyself() {
		return aboutMyself;
	}

	public void setAboutMyself(String aboutMyself) {
		this.aboutMyself = aboutMyself;
	}

	public CityLiving getCityLiving() {
		return cityLiving;
	}

	public void setCityLiving(CityLiving cityLiving) {
		this.cityLiving = cityLiving;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Height getHeight() {
		return height;
	}

	public void setHeight(Height height) {
		this.height = height;
	}
}
