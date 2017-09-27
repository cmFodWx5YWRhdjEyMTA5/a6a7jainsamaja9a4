package com.js.jainsamaj.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import com.js.jainsamaj.R;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.models.Matrimony.ListMain;

/**
 * Created by arbaz on 6/2/17.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<ListMain> listMainArrayList;
    ArrayList<ListMain> listMainArrayFilter;
    Context context;
    ListMain listMain;

    Typeface tfRegular, tfBold;

    public ListAdapter(ArrayList<ListMain> listMainArrayList, Context context) {
        this.listMainArrayList = listMainArrayList;
        this.context = context;
        listMainArrayFilter = new ArrayList<>();
        listMainArrayFilter.addAll(listMainArrayList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        listMain = listMainArrayList.get(position);

        //For Set Font
        tfRegular = Global.setRegularFont(context);
        tfBold = Global.setBoldFont(context);
        holder.tvListText.setTypeface(tfRegular);
        holder.tvListText.setText(listMain.getName());
    }

    @Override
    public int getItemCount() {
        return listMainArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;


        public TextView tvListText;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvListText = (TextView) view.findViewById(R.id.tvListText);

        }
    }

    //For Search
    public void searchRecord(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listMainArrayList.clear();
        if (charText.length() == 0) {
            listMainArrayList.addAll(listMainArrayFilter);
        } else {
            for (ListMain listMain: listMainArrayFilter) {
                if (listMain.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listMainArrayList.add(listMain);
                }
            }
        }
        notifyDataSetChanged();
    }
}
