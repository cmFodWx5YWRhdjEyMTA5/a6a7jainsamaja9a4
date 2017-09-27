package com.js.jainsamaj.models.Advertisement;

import java.io.Serializable;

public class ImagesLinkMain implements Serializable{
	public String imgLink;

	public ImagesLinkMain(String imgLink) {
		this.imgLink = imgLink;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
}
