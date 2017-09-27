package com.js.jainsamaj.models.Wall;

import java.io.Serializable;

/**
 * Created by arbaz on 22/3/17.
 */

public class AdvertisementMain implements Serializable {

    public String title;
    public String companyName;
    public String description;
    public String companyAddress;
    public String companyContact;
    public String companyEmail;
    public String mediumImageUrl;
    public String companyImageUrl;

    public AdvertisementMain() {
    }

    public AdvertisementMain(String title, String companyName, String description, String companyAddress, String companyContact, String companyEmail, String mediumImageUrl, String companyImageUrl) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.companyAddress = companyAddress;
        this.companyContact = companyContact;
        this.companyEmail = companyEmail;
        this.mediumImageUrl = mediumImageUrl;
        this.companyImageUrl = companyImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getCompanyImageUrl() {
        return companyImageUrl;
    }

    public void setCompanyImageUrl(String companyImageUrl) {
        this.companyImageUrl = companyImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
}
