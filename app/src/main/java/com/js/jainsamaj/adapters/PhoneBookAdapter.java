package com.js.jainsamaj.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.models.Phonebook.Response.Phonebook.MyContactList;
import com.js.jainsamaj.models.Phonebook.Response.Phonebook.OtherContactListItem;
import com.js.jainsamaj.utils.PhoneBook.SectionIndexer;
import com.js.jainsamaj.utils.PhoneBook.StringMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Created by arbaz on 6/2/17.
 */
public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.ContactViewHolder> implements SectionIndexer {
    OtherContactListItem otherContactListItem;
    Context context;
    Typeface tfRegular, tfBold;
    MyContactList myContactList;
    private ArrayList<OtherContactListItem> otherContactListItemArrayList;
    private ArrayList<OtherContactListItem> otherContactfilter;
    private LinkedHashMap<String, Integer> mMapIndex;
    private ArrayList<String> mSectionList;
    private String[] mSections;
    private String mSectionsAZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ContactViewHolder contactViewHolder;

    public PhoneBookAdapter() {
    }

    public PhoneBookAdapter(ArrayList<OtherContactListItem> otherContactListItemArrayList, MyContactList myContactList, Context context) {
        this.otherContactListItemArrayList = otherContactListItemArrayList;
        this.context = context;
        this.myContactList = myContactList;
        otherContactfilter = new ArrayList<>();
        otherContactfilter.addAll(otherContactListItemArrayList);
        fillSections();
    }

    private void fillSections() {
        mMapIndex = new LinkedHashMap<String, Integer>();

        for (int x = 0; x < otherContactListItemArrayList.size(); x++) {
            String str = otherContactListItemArrayList.get(x).getName();
            if (str.length() > 1) {
                String ch = str.substring(0, 1);
                ch = ch.toUpperCase();
                if (!mMapIndex.containsKey(ch)) {
                    mMapIndex.put(ch, x);
                }
            }

        }
        Set<String> sectionLetters = mMapIndex.keySet();
        // create a list from the set to sort
        mSectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(mSectionList);

        mSections = new String[mSectionList.size()];
        mSectionList.toArray(mSections);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        contactViewHolder = new ContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phone_book_row, parent, false));
        return contactViewHolder;

    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        //For Set Font
        try {
            tfRegular = Global.setRegularFont(context);
            tfBold = Global.setBoldFont(context);
            holder.tvNameTitle.setTypeface(tfRegular);
            holder.tvPBRName.setTypeface(tfBold);

            holder.tvPBMPDMeName.setTypeface(tfRegular);
            if (position == 0) {
                holder.llPhoneBookMe.setVisibility(View.VISIBLE);
                holder.llPhoneBookRow.setVisibility(View.GONE);
                holder.tvPBMPDMeName.setText(context.getString(R.string.phone_book_my_details_me_txt));
                holder.sdPBMPDMeImg.setImageURI(Uri.parse(myContactList.getUserImage()));
            } else {
                holder.llPhoneBookMe.setVisibility(View.GONE);
                holder.llPhoneBookRow.setVisibility(View.VISIBLE);
                OtherContactListItem otherContactListItem = getItem(position);
                String section = getSection(otherContactListItem);
                holder.bind(otherContactListItem, section, mMapIndex.get(section) == position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSection(OtherContactListItem otherContactListItem) {
        return otherContactListItem.getName().substring(0, 1).toUpperCase();
    }

    private OtherContactListItem getItem(int pPosition) {
        return otherContactListItemArrayList.get(pPosition);
    }

    @Override
    public int getItemCount() {
        return otherContactListItemArrayList.size();
    }

    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getItemCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(otherContactListItemArrayList.get(j).getName().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(otherContactListItemArrayList.get(j).getName().charAt(0)), String.valueOf(mSectionsAZ.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    public int getSectionForPosition(int position) {
        return 0;
    }

    public Object[] getSections() {
        String[] sections = new String[mSectionsAZ.length()];
        for (int i = 0; i < mSectionsAZ.length(); i++)
            sections[i] = String.valueOf(mSectionsAZ.charAt(i));
        return sections;
    }

    //For Search
    public void searchRecord(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        otherContactListItemArrayList.clear();
        if (charText.length() == 0) {
            otherContactListItemArrayList.addAll(otherContactfilter);
        } else {
            for (OtherContactListItem otherContactListItem : otherContactfilter) {
                if (otherContactListItem.getName().toLowerCase().contains(charText.toString().toLowerCase())) {
                    otherContactListItemArrayList.add(otherContactListItem);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvPBRName;
        private final TextView tvNameTitle;
        private View viewPb;
        private LinearLayout llPhoneBookMe, llPhoneBookRow;
        private SimpleDraweeView sdPBMPDMeImg;
        private TextView tvPBMPDMeName;


        public ContactViewHolder(View itemView) {
            super(itemView);
            tvPBRName = (TextView) itemView.findViewById(R.id.tvPBRName);
            tvNameTitle = (TextView) itemView.findViewById(R.id.tvNameTitle);
            viewPb = itemView.findViewById(R.id.viewPb);
            llPhoneBookRow = (LinearLayout) itemView.findViewById(R.id.llPhoneBookRow);

            //For First Row
            llPhoneBookMe = (LinearLayout) itemView.findViewById(R.id.llPhoneBookMe);
            sdPBMPDMeImg = (SimpleDraweeView) itemView.findViewById(R.id.sdPBMPDMeImg);
            tvPBMPDMeName = (TextView) itemView.findViewById(R.id.tvPBMPDMeName);

        }


        public void bind(OtherContactListItem otherContactListItem, String pSection, boolean bShowSection) {
            tvPBRName.setText(otherContactListItem.getName());
            tvNameTitle.setText(pSection);
            tvNameTitle.setVisibility(bShowSection ? View.VISIBLE : View.GONE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null) {

            myContactList = (MyContactList) data.getExtras().getSerializable("MyContactDetails");

            Uri uri = Uri.parse(myContactList.getUserImage());
            contactViewHolder.sdPBMPDMeImg.setImageURI(uri);
            notifyDataSetChanged();
        }

    }

}
