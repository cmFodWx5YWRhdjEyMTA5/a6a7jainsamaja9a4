package com.js.jainsamaj.models.Advertisement.Response;

import java.io.Serializable;
import java.util.List;

public class DeleteListItem implements Serializable{
	public List<String> smallImageLink;
	public String companyName;
	public long createdDateTime;
	public String description;
	public List<String> attachmentLink;
	public boolean active;
	public String updatedUser;
	public String title;
	public Type type;
	public long updatedDateTime;
	public String companyEmail;
	public String companyAddress;
	public List<String> mediumImageLink;
	public int id;
	public String companyContact;
	public String createdUser;
	public boolean status;
	public String companyURL;

	public List<String> getSmallImageLink() {
		return smallImageLink;
	}

	public void setSmallImageLink(List<String> smallImageLink) {
		this.smallImageLink = smallImageLink;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAttachmentLink() {
		return attachmentLink;
	}

	public void setAttachmentLink(List<String> attachmentLink) {
		this.attachmentLink = attachmentLink;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public List<String> getMediumImageLink() {
		return mediumImageLink;
	}

	public void setMediumImageLink(List<String> mediumImageLink) {
		this.mediumImageLink = mediumImageLink;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}
}