package com.js.jainsamaj.adapters.PhonebookMyProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.js.jainsamaj.R;

import java.util.ArrayList;

/**
 * Created by arbaz on 7/3/17.
 */

public class EmailListAdapter extends RecyclerView.Adapter<EmailListAdapter.ViewHolder> {
    ArrayList<String> emailStringArrayList;
    Context context;
    String emailStr;

    public EmailListAdapter(ArrayList<String> emailStringArrayList, Context context) {
        this.emailStringArrayList = emailStringArrayList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_email_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        emailStr = emailStringArrayList.get(position);
        holder.etMCEmail.setText(emailStr);

    }

    @Override
    public int getItemCount() {
        return emailStringArrayList.size();
    }


    //Add record
    public void addItem(String country) {
        emailStringArrayList.add(country);
        notifyItemInserted(emailStringArrayList.size());
    }

    //remove record
    public void removeItem(int position) {
        emailStringArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, emailStringArrayList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etMCEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            etMCEmail = (EditText) itemView.findViewById(R.id.etMCEmail);
        }
    }

}
