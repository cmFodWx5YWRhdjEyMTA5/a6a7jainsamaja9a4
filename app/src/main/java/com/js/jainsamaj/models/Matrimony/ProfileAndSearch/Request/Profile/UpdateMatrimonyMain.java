package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile;

import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.WorkAsIdResponse;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.WorkWithIdResponse;

import java.io.Serializable;

public class UpdateMatrimonyMain implements Serializable {
	public int id;
	public String createdUser;
	public UserRequest user;
	public CountryLivingRequest countryLiving;
	public StateLivingRequest stateLiving;
	public CityLivingRequest cityLiving;
	public String maritalStatus;
	public EducationLevelRequest educationLevel;
	public EducationFieldRequest educationField;
	public WorkAsIdResponse workAs;
	public WorkWithIdResponse workWith;
	public long annualIncome;
	public HeightRequest height;
	public String weight;
	public String bodyType;
	public String skinTone;
	public String hobbies;
	public String mobileNumber;
	public String aboutMyself;

	public String disability;
	public String gender;
	public String gotra;
	public String diet;
	public int age;
	public boolean status;

	public UpdateMatrimonyMain() {
	}



	public
	UpdateMatrimonyMain(int id, String createdUser, UserRequest user, CountryLivingRequest countryLiving,
							   StateLivingRequest stateLiving, CityLivingRequest cityLiving, String maritalStatus,
							   EducationLevelRequest educationLevel, EducationFieldRequest educationField, WorkAsIdResponse workAs,
							   WorkWithIdResponse workWith, long annualIncome, HeightRequest height, String weight, String bodyType,
							   String skinTone, String hobbies, String mobileNumber, String aboutMyself,String disability) {
		this.id = id;
		this.createdUser = createdUser;
		this.user = user;
		this.countryLiving = countryLiving;
		this.stateLiving = stateLiving;
		this.cityLiving = cityLiving;
		this.maritalStatus = maritalStatus;
		this.educationLevel = educationLevel;
		this.educationField = educationField;
		this.workAs = workAs;
		this.workWith = workWith;
		this.annualIncome = annualIncome;
		this.height = height;
		this.weight = weight;
		this.bodyType = bodyType;
		this.skinTone = skinTone;
		this.hobbies = hobbies;
		this.mobileNumber = mobileNumber;
		this.aboutMyself = aboutMyself;
		this.disability=disability;

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

	public CountryLivingRequest getCountryLiving() {
		return countryLiving;
	}

	public void setCountryLiving(CountryLivingRequest countryLiving) {
		this.countryLiving = countryLiving;
	}

	public StateLivingRequest getStateLiving() {
		return stateLiving;
	}

	public void setStateLiving(StateLivingRequest stateLiving) {
		this.stateLiving = stateLiving;
	}

	public CityLivingRequest getCityLiving() {
		return cityLiving;
	}

	public void setCityLiving(CityLivingRequest cityLiving) {
		this.cityLiving = cityLiving;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public EducationLevelRequest getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevelRequest educationLevel) {
		this.educationLevel = educationLevel;
	}

	public EducationFieldRequest getEducationField() {
		return educationField;
	}

	public void setEducationField(EducationFieldRequest educationField) {
		this.educationField = educationField;
	}

	public WorkAsIdResponse getWorkAs() {
		return workAs;
	}

	public void setWorkAs(WorkAsIdResponse workAs) {
		this.workAs = workAs;
	}

	public WorkWithIdResponse getWorkWith() {
		return workWith;
	}

	public void setWorkWith(WorkWithIdResponse workWith) {
		this.workWith = workWith;
	}

	public long getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(long annualIncome) {
		this.annualIncome = annualIncome;
	}

	public UpdateMatrimonyMain(long annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public HeightRequest getHeight() {
		return height;
	}

	public void setHeight(HeightRequest height) {
		this.height = height;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getSkinTone() {
		return skinTone;
	}

	public void setSkinTone(String skinTone) {
		this.skinTone = skinTone;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAboutMyself() {
		return aboutMyself;
	}

	public void setAboutMyself(String aboutMyself) {
		this.aboutMyself = aboutMyself;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
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

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public UserRequest getUser() {
		return user;
	}

	public void setUser(UserRequest user) {
		this.user = user;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}