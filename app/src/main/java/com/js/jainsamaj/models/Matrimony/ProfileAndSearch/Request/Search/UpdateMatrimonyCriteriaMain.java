package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Search;

import java.io.Serializable;

public class UpdateMatrimonyCriteriaMain implements Serializable {
    public int id;
    public String createdUser;
    public int userId;
    public int ageFrom;
    public int ageTo;
    public int heightFrom;
    public int heightTo;
    public String maritalStatus;
    public int countryLiving;
    public int stateLiving;
    public int cityLiving;
    public int education;
    public int professionArea;
    public int annualIncome;
    public String bodyType;
    public String skinTone;
    public String disability;

    public UpdateMatrimonyCriteriaMain(int id, String createdUser, int userId, int ageFrom,
                                       int ageTo, int heightFrom, int heightTo, String maritalStatus,
                                       int countryLiving, int stateLiving, int cityLiving, int education,
                                       int professionArea, int annualIncome, String bodyType, String skinTone,
                                       String disability) {
        this.id = id;
        this.createdUser = createdUser;
        this.userId = userId;
        this.ageFrom = ageFrom;
        this.ageTo = ageTo;
        this.heightFrom = heightFrom;
        this.heightTo = heightTo;
        this.maritalStatus = maritalStatus;
        this.countryLiving = countryLiving;
        this.stateLiving = stateLiving;
        this.cityLiving = cityLiving;
        this.education = education;
        this.professionArea = professionArea;
        this.annualIncome = annualIncome;
        this.bodyType = bodyType;
        this.skinTone = skinTone;
        this.disability = disability;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public int getHeightFrom() {
        return heightFrom;
    }

    public void setHeightFrom(int heightFrom) {
        this.heightFrom = heightFrom;
    }

    public int getHeightTo() {
        return heightTo;
    }

    public void setHeightTo(int heightTo) {
        this.heightTo = heightTo;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getCountryLiving() {
        return countryLiving;
    }

    public void setCountryLiving(int countryLiving) {
        this.countryLiving = countryLiving;
    }

    public int getStateLiving() {
        return stateLiving;
    }

    public void setStateLiving(int stateLiving) {
        this.stateLiving = stateLiving;
    }

    public int getCityLiving() {
        return cityLiving;
    }

    public void setCityLiving(int cityLiving) {
        this.cityLiving = cityLiving;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getProfessionArea() {
        return professionArea;
    }

    public void setProfessionArea(int professionArea) {
        this.professionArea = professionArea;
    }

    public int getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(int annualIncome) {
        this.annualIncome = annualIncome;
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

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }
}
