package com.js.jainsamaj.models;

import java.io.Serializable;

/**
 * Created by arbaz on 11/2/17.
 */

public class SelectionListMain implements Serializable {
    public int id;
    public String status;
    public String description;

    //For Category
    public String categoryName;

    //For Country
    public String countryName;
    public String countryCode;

    //For state
    public String stateName;

    //For district
    public String districtName;

    //For City
    public String cityName;
    public String cityCode;

    //For Area
    public String areaName;
    public String areaPincode;

    //For HeightRequest
    public String height;

    //For Education Level
    public String educationLevel;

    //For Education Field
    public String educationField;

    //For work
    String workWithCategory;
    String workAs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaPincode() {
        return areaPincode;
    }

    public void setAreaPincode(String areaPincode) {
        this.areaPincode = areaPincode;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getEducationField() {
        return educationField;
    }

    public void setEducationField(String educationField) {
        this.educationField = educationField;
    }


    public String getWorkWithCategory() {
        return workWithCategory;
    }

    public void setWorkWithCategory(String workWithCategory) {
        this.workWithCategory = workWithCategory;
    }

    public String getWorkAs() {
        return workAs;
    }

    public void setWorkAs(String workAs) {
        this.workAs = workAs;
    }
}
