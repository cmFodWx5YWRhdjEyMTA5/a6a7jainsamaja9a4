package com.js.jainsamaj.models.Phonebook.Response.PhoneboookDetails;

import com.js.jainsamaj.models.Phonebook.Response.AddressItem;

import java.io.Serializable;
import java.util.List;

public class PhonebookDetailResponseMain implements Serializable{
	public List<String> emailAddress;
	public String userImage;
	public List<AddressItem> address;
	public List<String> mobileNumber;
	public String coverImage;
	public String name;
	public boolean isPrivate;
	public String lang;
	public List<FamilyMembersItem> familyMembers;
	public String lat;

	public List<String> getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(List<String> emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public List<AddressItem> getAddress() {
		return address;
	}

	public void setAddress(List<AddressItem> address) {
		this.address = address;
	}

	public List<String> getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(List<String> mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean aPrivate) {
		isPrivate = aPrivate;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public List<FamilyMembersItem> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<FamilyMembersItem> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
}