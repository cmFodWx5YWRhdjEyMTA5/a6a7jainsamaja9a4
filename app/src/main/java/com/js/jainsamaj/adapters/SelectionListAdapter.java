package com.js.jainsamaj.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.js.jainsamaj.R;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.models.SelectionListMain;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by arbaz on 14/12/16.
 */

public class SelectionListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private ArrayList<SelectionListMain> selectionListMainArrayList;
    private ArrayList<SelectionListMain> selectionFilterList;
    private SparseBooleanArray mSelectedItemsIds;
    SelectionListMain selectionListMain;
    String type;
    //set Font
    Typeface tfRegular, tfBold;

    public SelectionListAdapter(Context context, ArrayList<SelectionListMain> selectionListMainArrayList, String type) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mSelectedItemsIds = new SparseBooleanArray();
        this.selectionListMainArrayList = selectionListMainArrayList;
        this.selectionFilterList = new ArrayList<SelectionListMain>();
        this.selectionFilterList.addAll(selectionListMainArrayList);
        this.type = type;
    }

    public class ViewHolder {
        TextView tvSelectionName;
    }

    @Override
    public int getCount() {
        return selectionListMainArrayList.size();
    }

    @Override
    public SelectionListMain getItem(int position) {
        return selectionListMainArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        //For Set Font
        tfRegular = Global.setRegularFont(mContext);
        tfBold = Global.setBoldFont(mContext);

        holder = new ViewHolder();
        view = inflater.inflate(R.layout.selection_list_row, null);
        holder.tvSelectionName = (TextView) view.findViewById(R.id.tvSelectionName);
        holder.tvSelectionName.setTypeface(tfRegular);
        view.setTag(holder);
        if (selectionListMainArrayList != null) {
            try {
                if (type.equals(Constants.IS_CATEGORY)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getCategoryName());
                } else if (type.equals(Constants.IS_COUNTY)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getCountryName());
                } else if (type.equals(Constants.IS_STATE)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getStateName());
                } else if (type.equals(Constants.IS_DISTRICT)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getDistrictName());
                } else if (type.equals(Constants.IS_CITY)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getCityName());
                } else if (type.equals(Constants.IS_AREA)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getAreaName());
                } else if (type.equals(Constants.IS_HEIGHT)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getHeight());
                }else if (type.equals(Constants.IS_EDU_LEVEL)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getEducationLevel());
                }else if (type.equals(Constants.IS_EDU_FIELD)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getEducationField());
                }else if (type.equals(Constants.IS_WORK_WITH)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getWorkWithCategory());
                }else if (type.equals(Constants.IS_WORK_AS)) {
                    holder.tvSelectionName.setText(selectionListMainArrayList.get(position).getWorkAs());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    //For Search
    public void searchRecord(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        selectionListMainArrayList.clear();
        if (charText.length() == 0) {
            selectionListMainArrayList.addAll(selectionFilterList);
        } else {
            for (SelectionListMain selectionListMain : selectionFilterList) {
                if (type.equals(Constants.IS_CATEGORY) && selectionListMain.getCategoryName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                } else if (type.equals(Constants.IS_COUNTY) && selectionListMain.getCountryName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                } else if (type.equals(Constants.IS_STATE) && selectionListMain.getStateName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                } else if (type.equals(Constants.IS_DISTRICT) && selectionListMain.getDistrictName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                } else if (type.equals(Constants.IS_CITY) && selectionListMain.getCityName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                } else if (type.equals(Constants.IS_AREA) && selectionListMain.getAreaName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                }else if (type.equals(Constants.IS_HEIGHT) && selectionListMain.getHeight().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                }else if (type.equals(Constants.IS_EDU_LEVEL) && selectionListMain.getEducationLevel().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                }else if (type.equals(Constants.IS_EDU_FIELD) && selectionListMain.getEducationField().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                }else if (type.equals(Constants.IS_WORK_WITH) && selectionListMain.getWorkWithCategory().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                }else if (type.equals(Constants.IS_WORK_AS) && selectionListMain.getWorkAs().toLowerCase(Locale.getDefault()).contains(charText)) {
                    selectionListMainArrayList.add(selectionListMain);
                }
            }
        }
        notifyDataSetChanged();
    }
}