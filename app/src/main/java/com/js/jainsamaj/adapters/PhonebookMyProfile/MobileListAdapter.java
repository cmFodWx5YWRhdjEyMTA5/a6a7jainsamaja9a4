package com.js.jainsamaj.adapters.PhonebookMyProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.js.jainsamaj.R;

import java.util.ArrayList;

/**
 * Created by arbaz on 6/3/17.
 */

public class MobileListAdapter extends RecyclerView.Adapter<MobileListAdapter.ViewHolder> {
    ArrayList<String> mobileStringArrayList;
    Context context;
    String mobileStr;

    public MobileListAdapter(ArrayList<String> mobileStringArrayList, Context context) {
        this.mobileStringArrayList = mobileStringArrayList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_mobile_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        mobileStr = mobileStringArrayList.get(position);
        holder.etMCMobile.setText(mobileStr);
        holder.ivMobileRemove.setTag(position);
        holder.ivMobileRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mobilePos= (int) holder.ivMobileRemove.getTag();
                mobileStringArrayList.remove(mobilePos);
                notifyItemRemoved(mobilePos);
                notifyItemRangeChanged(mobilePos, mobileStringArrayList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mobileStringArrayList.size();
    }


    //Add record
    public void addItem(String mobile) {
        mobileStringArrayList.add(mobile);
        notifyItemInserted(mobileStringArrayList.size());
    }

    //remove record
    public void removeItem(int position) {
        mobileStringArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mobileStringArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etMCMobile;
        ImageView ivMobileRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            etMCMobile = (EditText) itemView.findViewById(R.id.etMCMobile);
            ivMobileRemove = (ImageView) itemView.findViewById(R.id.ivMobileRemove);
        }
    }

}
