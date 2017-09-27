package com.js.jainsamaj.utils.PhoneBook;

import android.content.Intent;

/**
 * Created by arbaz on 27/2/17.
 */

public interface SectionIndexer {
    Object[] getSections();

    int getPositionForSection(int var1);

    int getSectionForPosition(int var1);

    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //
    //
    //         if (data != null) {
    //
    //            myContactList = (MyContactList) data.getExtras().getSerializable("MyContactDetails");
    //            Uri uri = Uri.parse(myContactList.getUserImage());
    //            holder.sdPBMPDMeImg.setImageURI(uri);
    //        }
    //
    //    }
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
