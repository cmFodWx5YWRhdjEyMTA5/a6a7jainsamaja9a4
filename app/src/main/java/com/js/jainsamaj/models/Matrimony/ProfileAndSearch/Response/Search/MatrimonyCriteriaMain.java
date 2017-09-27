package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search;

import java.io.Serializable;
import java.util.List;

import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.CityLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.CountryLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.EducationField;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.EducationLevel;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.Height;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.StateLiving;

public class MatrimonyCriteriaMain implements Serializable {
    public  List<String> bodyType;
    public  EducationField professionArea;
    public  EducationLevel education;
    public  int annualIncome;
    public  StateLiving stateLiving;
    public  String gender;
    public  String disability;
    public  long createdDateTime;
    public  List<String> skinTone;
    public  String updatedUser;
    public  int userId;
    public  long updatedDateTime;
    public  CountryLiving countryLiving;
    public  CityLiving cityLiving;
    public  int ageFrom;
    public  Height heightTo;
    public  int id;
    public  int ageTo;
    public  String createdUser;
    public  Height heightFrom;
    public  String maritalStatus;
    public  boolean status;

    public List<String> getBodyType() {
        return bodyType;
    }

    public void setBodyType(List<String> bodyType) {
        this.bodyType = bodyType;
    }

    public EducationField getProfessionArea() {
        return professionArea;
    }

    public void setProfessionArea(EducationField professionArea) {
        this.professionArea = professionArea;
    }

    public EducationLevel getEducation() {
        return education;
    }

    public void setEducation(EducationLevel education) {
        this.education = education;
    }

    public int getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(int annualIncome) {
        this.annualIncome = annualIncome;
    }

    public StateLiving getStateLiving() {
        return stateLiving;
    }

    public void setStateLiving(StateLiving stateLiving) {
        this.stateLiving = stateLiving;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<String> getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(List<String> skinTone) {
        this.skinTone = skinTone;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public CityLiving getCityLiving() {
        return cityLiving;
    }

    public void setCityLiving(CityLiving cityLiving) {
        this.cityLiving = cityLiving;
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public Height getHeightTo() {
        return heightTo;
    }

    public void setHeightTo(Height heightTo) {
        this.heightTo = heightTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Height getHeightFrom() {
        return heightFrom;
    }

    public void setHeightFrom(Height heightFrom) {
        this.heightFrom = heightFrom;
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
}