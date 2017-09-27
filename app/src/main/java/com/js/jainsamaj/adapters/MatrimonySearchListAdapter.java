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
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search.MatrimonyListMain;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by arbaz on 10/4/17.
 */

public class MatrimonySearchListAdapter extends RecyclerView.Adapter<MatrimonySearchListAdapter.ViewHolder> {

    MatrimonyListMain matrimonyListMain;
    ArrayList<MatrimonyListMain> matrimonyListMainArrayList;
    ArrayList<MatrimonyListMain> matrimonyListMainArrayListFilter;
    Context context;
    Typeface tfRegular, tfBold;

    public MatrimonySearchListAdapter(ArrayList<MatrimonyListMain> matrimonyListMainArrayList, Context context) {
        this.matrimonyListMainArrayList = matrimonyListMainArrayList;
        this.matrimonyListMainArrayList = matrimonyListMainArrayList;
        this.context = context;
        matrimonyListMainArrayListFilter = new ArrayList<>();
        matrimonyListMainArrayListFilter.addAll(matrimonyListMainArrayList);
    }

    @Override
    public MatrimonySearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matrimony_search_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MatrimonySearchListAdapter.ViewHolder holder, int position) {
        matrimonyListMain = matrimonyListMainArrayList.get(position);
        //For Set Font
        tfRegular = Global.setRegularFont(context);
        tfBold = Global.setBoldFont(context);
        holder.tvSMLName.setTypeface(tfRegular);
        holder.tvSMLStatus.setTypeface(tfBold);

        if (matrimonyListMain != null) {
            try {
                holder.sdSMLImg.setImageURI(Uri.parse(matrimonyListMain.getUserDetails().getUserProfileImageLink()));
                holder.tvSMLName.setText(matrimonyListMain.getUserDetails().getName());
                holder.tvSMLStatus.setText(matrimonyListMain.getMarriageDetails().getMaritalstatus());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return matrimonyListMainArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public SimpleDraweeView sdSMLImg;
        public TextView tvSMLName;
        public TextView tvSMLStatus;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sdSMLImg = (SimpleDraweeView) view.findViewById(R.id.sdSMLImg);
            tvSMLName = (TextView) view.findViewById(R.id.tvSMLName);
            tvSMLStatus = (TextView) view.findViewById(R.id.tvSMLStatus);

        }
    }

    //For Search
    public void searchRecord(String charText) {
        try {


            charText = charText.toLowerCase(Locale.getDefault());
            matrimonyListMainArrayList.clear();
            if (charText.length() == 0) {
                matrimonyListMainArrayList.addAll(matrimonyListMainArrayListFilter);
            } else {
                for (MatrimonyListMain matrimonyListMain : matrimonyListMainArrayListFilter) {
                    if (matrimonyListMain.getUserDetails().getName().toLowerCase().contains(charText.toString().toLowerCase())) {
                        matrimonyListMainArrayList.add(matrimonyListMain);
                    }
                }
            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
