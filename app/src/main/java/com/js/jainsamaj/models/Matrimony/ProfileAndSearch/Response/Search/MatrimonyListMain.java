package com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search;

import java.io.Serializable;
import java.util.List;

public class MatrimonyListMain implements Serializable {
	private List<AddressItem> address;
	private MarriageDetails marriageDetails;
	private EducationalDetails educationalDetails;
	private UserDetails userDetails;

	public List<AddressItem> getAddress() {
		return address;
	}

	public void setAddress(List<AddressItem> address) {
		this.address = address;
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

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}