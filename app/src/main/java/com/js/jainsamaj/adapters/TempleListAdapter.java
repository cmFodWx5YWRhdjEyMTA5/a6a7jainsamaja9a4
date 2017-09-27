package com.js.jainsamaj.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.models.Temple.searchTemple.Response.TempleMain;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by arbaz on 6/2/17.
 */
public class TempleListAdapter extends RecyclerView.Adapter<TempleListAdapter.ViewHolder> {
    ArrayList<TempleMain> templeMainArrayList;
    ArrayList<TempleMain> templeMainArrayFilter;
    Context context;
    TempleMain templeMain;

    Typeface tfRegular, tfBold;

    public TempleListAdapter(ArrayList<TempleMain> templeMainArrayList, Context context) {
        this.templeMainArrayList = templeMainArrayList;
        this.context = context;
        templeMainArrayFilter = new ArrayList<>();
        templeMainArrayFilter.addAll(templeMainArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.temple_list_row, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        templeMain = templeMainArrayList.get(position);

        //For Set Font
        tfRegular = Global.setRegularFont(context);
        tfBold = Global.setBoldFont(context);

        holder.tvTLName.setTypeface(tfBold);
        holder.tvTLAddress.setTypeface(tfRegular);
        try {
            if (templeMain != null) {
                String country = templeMain.getCountryId().getCountryName();
                String state = templeMain.getStateId().getStateName();
                String district = templeMain.getDistrictId().getDistrictName();
                String city = templeMain.getCityId().getCityName();
                String area = templeMain.getAreaId().getAreaName();
                String address = templeMain.getAddress();
                Uri templeIcon = Uri.parse(templeMain.getLogoLink().get(0));
                holder.sdTLImg.setImageURI(templeIcon);
                holder.tvTLName.setText(templeMain.getTempleName());

                holder.tvTLAddress.setText(templeMain.getFullAddress());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return templeMainArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public SimpleDraweeView sdTLImg;
        public TextView tvTLName;
        public TextView tvTLAddress;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sdTLImg = (SimpleDraweeView) view.findViewById(R.id.sdTLImg);
            tvTLName = (TextView) view.findViewById(R.id.tvTLName);
            tvTLAddress = (TextView) view.findViewById(R.id.tvTLAddress);

        }
    }

    //For Search
    public void searchRecord(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        templeMainArrayList.clear();
        if (charText.length() == 0) {
            templeMainArrayList.addAll(templeMainArrayFilter);
        } else {
            for (TempleMain templeMain : templeMainArrayFilter) {

                if (templeMain.getTempleName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    templeMainArrayList.add(templeMain);
                }
            }
        }
        notifyDataSetChanged();
    }
}
