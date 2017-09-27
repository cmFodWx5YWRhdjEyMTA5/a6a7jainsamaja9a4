package com.js.jainsamaj.models.Wall;

import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.CityLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.CountryLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.EducationField;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.EducationLevel;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.Height;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.StateLiving;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.User;
import com.js.jainsamaj.models.Temple.searchTemple.Response.AreaId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CityId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CountryId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.DistrictId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.StateId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.TempleCategory;

import java.io.Serializable;
import java.util.List;

public class WallMain implements Serializable {

    /*Matrimony*/
    public String bodyType;
    public String educationLevelDetails;
    public String workAsStr;
    public int annualIncome;
    public String gender;
    public String mobileNumber;
    public String disability;
    public String heightDetails;
    public String skinTone;
    public CountryLiving countryLiving;
    public String aboutMyself;
    public EducationLevel educationLevel;
    public int id;
    public Height height;
    public String workWithStr;
    public StateLiving stateLiving;
    public EducationField educationField;
    public String gotra;
    public String postType;
    public String weight;
    public String updatedUser;
    public String userName;
    public boolean publishOnMatrimony;
    public long updatedDateTime;
    public CityLiving cityLiving;
    public String educationFieldDetails;
    public String hobbies;
    public User user;
    public int age;
    public String maritalStatus;
    public boolean status;
    public String userProfileImageLink;


    /*Interested  By*/
    public String username;
    public String deviceUDID;

    /*Job Post*/
    public boolean adminApproval;
    public String endDate;
    public String uploadMediaLink;
    public String companyName;
    public String jobTitle;
    public String packageTo;
    public long createdDateTime;
    public String experience;
    public String jobLocation;
    public String packageFrom;
    public String contactNumber;
    public String jobRole;
    public String jobDescription;
    public String createdUser;
    public String startDate;
    public String contact;
    public String description;
    public String video;
    public String title;
    public String productService;

    /*Product Service Post*/
    public String productServiceImageLink;
    public String productServiceCategoryDetails;

    /*Temple Finder*/
    public String address;
    public StateId stateId;
    public CityId cityId;
    public CountryId countryId;
    public TempleCategory templeCategory;
    public DistrictId districtId;
    public AreaId areaId;
    public List<String> logoLink;
    public String templeName;
    public String fullAddress;
    public boolean userInactiveStatus;
    public boolean adminInactiveStatus;

    /*View Interest*/
    public User postedBy;
    public User interestedBy;

    /*AdvertisementMain*/
    public AdvertisementMain advertisementMain;

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getEducationLevelDetails() {
        return educationLevelDetails;
    }

    public void setEducationLevelDetails(String educationLevelDetails) {
        this.educationLevelDetails = educationLevelDetails;
    }



    public int getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(int annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getHeightDetails() {
        return heightDetails;
    }

    public void setHeightDetails(String heightDetails) {
        this.heightDetails = heightDetails;
    }

    public String getSkinTone() {
        return skinTone;
    }

    public void setSkinTone(String skinTone) {
        this.skinTone = skinTone;
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

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
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

    public String getGotra() {
        return gotra;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isPublishOnMatrimony() {
        return publishOnMatrimony;
    }

    public void setPublishOnMatrimony(boolean publishOnMatrimony) {
        this.publishOnMatrimony = publishOnMatrimony;
    }

    public long getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(long updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public CityLiving getCityLiving() {
        return cityLiving;
    }

    public void setCityLiving(CityLiving cityLiving) {
        this.cityLiving = cityLiving;
    }

    public String getEducationFieldDetails() {
        return educationFieldDetails;
    }

    public void setEducationFieldDetails(String educationFieldDetails) {
        this.educationFieldDetails = educationFieldDetails;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
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

    public String getUserProfileImageLink() {
        return userProfileImageLink;
    }

    public void setUserProfileImageLink(String userProfileImageLink) {
        this.userProfileImageLink = userProfileImageLink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceUDID() {
        return deviceUDID;
    }

    public void setDeviceUDID(String deviceUDID) {
        this.deviceUDID = deviceUDID;
    }

    public boolean isAdminApproval() {
        return adminApproval;
    }

    public void setAdminApproval(boolean adminApproval) {
        this.adminApproval = adminApproval;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public String getProductServiceImageLink() {
        return productServiceImageLink;
    }

    public void setProductServiceImageLink(String productServiceImageLink) {
        this.productServiceImageLink = productServiceImageLink;
    }

    public String getProductServiceCategoryDetails() {
        return productServiceCategoryDetails;
    }

    public void setProductServiceCategoryDetails(String productServiceCategoryDetails) {
        this.productServiceCategoryDetails = productServiceCategoryDetails;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StateId getStateId() {
        return stateId;
    }

    public void setStateId(StateId stateId) {
        this.stateId = stateId;
    }

    public CityId getCityId() {
        return cityId;
    }

    public void setCityId(CityId cityId) {
        this.cityId = cityId;
    }

    public CountryId getCountryId() {
        return countryId;
    }

    public void setCountryId(CountryId countryId) {
        this.countryId = countryId;
    }

    public TempleCategory getTempleCategory() {
        return templeCategory;
    }

    public void setTempleCategory(TempleCategory templeCategory) {
        this.templeCategory = templeCategory;
    }

    public DistrictId getDistrictId() {
        return districtId;
    }

    public void setDistrictId(DistrictId districtId) {
        this.districtId = districtId;
    }

    public AreaId getAreaId() {
        return areaId;
    }

    public void setAreaId(AreaId areaId) {
        this.areaId = areaId;
    }

    public List<String> getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(List<String> logoLink) {
        this.logoLink = logoLink;
    }

    public String getTempleName() {
        return templeName;
    }

    public void setTempleName(String templeName) {
        this.templeName = templeName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public boolean isUserInactiveStatus() {
        return userInactiveStatus;
    }

    public void setUserInactiveStatus(boolean userInactiveStatus) {
        this.userInactiveStatus = userInactiveStatus;
    }

    public boolean isAdminInactiveStatus() {
        return adminInactiveStatus;
    }

    public void setAdminInactiveStatus(boolean adminInactiveStatus) {
        this.adminInactiveStatus = adminInactiveStatus;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public User getInterestedBy() {
        return interestedBy;
    }

    public void setInterestedBy(User interestedBy) {
        this.interestedBy = interestedBy;
    }

    public AdvertisementMain getAdvertisementMain() {
        return advertisementMain;
    }

    public void setAdvertisementMain(AdvertisementMain advertisementMain) {
        this.advertisementMain = advertisementMain;
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
}
