package com.js.jainsamaj.models.Phonebook.Response.Phonebook;

import java.io.Serializable;
import java.util.List;

public class PhonebookMain implements Serializable {
    public MyContactList myContactList;
    public List<OtherContactListItem> otherContactList;

    public MyContactList getMyContactList() {
        return myContactList;
    }

    public void setMyContactList(MyContactList myContactList) {
        this.myContactList = myContactList;
    }

    public List<OtherContactListItem> getOtherContactList() {
        return otherContactList;
    }

    public void setOtherContactList(List<OtherContactListItem> otherContactList) {
        this.otherContactList = otherContactList;
    }
}
