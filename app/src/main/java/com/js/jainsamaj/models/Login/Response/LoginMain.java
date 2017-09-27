package com.js.jainsamaj.models.Login.Response;

import java.io.Serializable;
import java.util.List;

public class LoginMain implements Serializable {
    public Achievements achievements;
    public UserDetails userDetails;
    public SeminarDetails seminarDetails;
    public ProjectDetails projectDetails;
    public List<Address> address;
    public ExperianceDetails experianceDetails;
    public TrainingDetails trainingDetails;
    public MarriageDetails marriageDetails;
    public EducationalDetails educationalDetails;
    public JobPost jobPost;
    public SchoolCollegeDetails schoolCollegeDetails;
    public int userType;
    public int id;
    public String username;


    public Achievements getAchievements() {
        return achievements;
    }

    public void setAchievements(Achievements achievements) {
        this.achievements = achievements;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public SeminarDetails getSeminarDetails() {
        return seminarDetails;
    }

    public void setSeminarDetails(SeminarDetails seminarDetails) {
        this.seminarDetails = seminarDetails;
    }

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public ExperianceDetails getExperianceDetails() {
        return experianceDetails;
    }

    public void setExperianceDetails(ExperianceDetails experianceDetails) {
        this.experianceDetails = experianceDetails;
    }

    public TrainingDetails getTrainingDetails() {
        return trainingDetails;
    }

    public void setTrainingDetails(TrainingDetails trainingDetails) {
        this.trainingDetails = trainingDetails;
    }

    public MarriageDetails getMarriageDetails() {
        return marriageDetails;
    }

    public void setMarriageDetails(MarriageDetails marriageDetails) {
        this.marriageDetails = marriageDetails;
    }

    public EducationalDetails getEducationalDetails() {
        return educationalDetails;
    }

    public void setEducationalDetails(EducationalDetails educationalDetails) {
        this.educationalDetails = educationalDetails;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public SchoolCollegeDetails getSchoolCollegeDetails() {
        return schoolCollegeDetails;
    }

    public void setSchoolCollegeDetails(SchoolCollegeDetails schoolCollegeDetails) {
        this.schoolCollegeDetails = schoolCollegeDetails;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
