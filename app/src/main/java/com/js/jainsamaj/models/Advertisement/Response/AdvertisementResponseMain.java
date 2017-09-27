package com.js.jainsamaj.models.Advertisement.Response;

import java.io.Serializable;
import java.util.List;

public class AdvertisementResponseMain implements Serializable{
	public List<DeleteListItem> deleteList;
	public List<InsertListItem> insertList;
	public List<UpdateListItem> updateList;
	public String timestamp;

	public List<DeleteListItem> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<DeleteListItem> deleteList) {
		this.deleteList = deleteList;
	}

	public List<InsertListItem> getInsertList() {
		return insertList;
	}

	public void setInsertList(List<InsertListItem> insertList) {
		this.insertList = insertList;
	}

	public List<UpdateListItem> getUpdateList() {
		return updateList;
	}

	public void setUpdateList(List<UpdateListItem> updateList) {
		this.updateList = updateList;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}