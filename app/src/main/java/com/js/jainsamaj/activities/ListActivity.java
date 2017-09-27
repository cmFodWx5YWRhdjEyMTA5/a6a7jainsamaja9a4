package com.js.jainsamaj.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Locale;

import com.js.jainsamaj.R;
import com.js.jainsamaj.adapters.ListAdapter;
import com.js.jainsamaj.listeners.RecyclerItemClickListener;
import com.js.jainsamaj.models.Matrimony.ListMain;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    RecyclerView rvList;
    TextView tvLEmptyTxt;
    EditText etLSearch;
    SimpleDraweeView sdListBanner;

    ArrayList<ListMain> listMainArrayList = new ArrayList<>();
    ListAdapter listAdapter;
    ListMain listMain;

    Bundle bListData;
    boolean isMarital,
            isWorkWith, isWorkAs, isAnnualIncome,
            isAgeFrom, isAgeTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bListData = new Bundle(getIntent().getExtras());
        isMarital = bListData.getBoolean("isMarital");
        isWorkWith = bListData.getBoolean("isWorkWith");
        isWorkAs = bListData.getBoolean("isWorkAs");
        isAnnualIncome = bListData.getBoolean("isAnnualIncome");

        isAgeFrom = bListData.getBoolean("isAgeFrom");
        isAgeTo = bListData.getBoolean("isAgeTo");

        bindHere();
        clickHere();
    }

    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvList = (RecyclerView) findViewById(R.id.rvList);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLayoutManager1);
        tvLEmptyTxt = (TextView) findViewById(R.id.tvLEmptyTxt);
        etLSearch = (EditText) findViewById(R.id.etLSearch);
        //set Toolbar Title and EditText Hint Here
        if (isMarital) {
            tbTitle.setText(getResources().getString(R.string.profile_marital));
//            etLSearch.setHint(R.string.profile_srch_marital);
        }
        if (isWorkWith) {
            tbTitle.setText(getResources().getString(R.string.profile_wwith));
//            etLSearch.setHint(R.string.profile_srch_wrok_with);
        }
        if (isWorkAs) {
            tbTitle.setText(getResources().getString(R.string.profile_wAs));
//            etLSearch.setHint(R.string.profile_srch_wrok_as);
        }
        if (isAnnualIncome) {
            tbTitle.setText(getResources().getString(R.string.profile_a_income));
//            etLSearch.setHint(R.string.profile_srch_a_income);
        }
        if (isAgeFrom || isAgeTo) {
            tbTitle.setText(getResources().getString(R.string.profile_search_ft_age));
//            etLSearch.setHint(R.string.profile_srch_ft_age);
        }

        //Data Set in to ArrayList
        if (isMarital) {
            listMainArrayList.clear();
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_marital_status_single)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_marital_status_divorced)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_marital_status_windowed)));
        }
        if (isWorkWith) {
            listMainArrayList.clear();
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_pc)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_g_ps)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_d_cs)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_b_se)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_not)));
        }
        if (isWorkAs) {
            listMainArrayList.clear();
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_programmer)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_doctor)));
        }
        if (isAnnualIncome) {
            listMainArrayList.clear();
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_income_1)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_income_2)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_income_3)));
            listMainArrayList.add(new ListMain(getResources().getString(R.string.profile_search_education_and_professional_work_income_4)));
        }
        if (isAgeFrom || isAgeTo) {
            listMainArrayList.clear();
            String age;
            for (int i = 0; i <= 60; i++) {
                age = String.valueOf(i);
                listMain = new ListMain(age);
                listMainArrayList.add(listMain);

            }
        }


        //Set Adapter Here
        if (!listMainArrayList.isEmpty()) {
            rvList.setVisibility(View.VISIBLE);
            tvLEmptyTxt.setVisibility(View.GONE);
            etLSearch.setVisibility(View.VISIBLE);
            listAdapter = new ListAdapter(listMainArrayList, this);
            rvList.setAdapter(listAdapter);
        } else {
            rvList.setVisibility(View.GONE);
            tvLEmptyTxt.setVisibility(View.VISIBLE);
            etLSearch.setVisibility(View.GONE);
        }

        etLSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = etLSearch.getText().toString().toLowerCase(Locale.getDefault());
                listAdapter.searchRecord(text);
            }
        });

    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);
        rvList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                listMain = listMainArrayList.get(position);
                try {
                    if (listMain.getName() != null) {
                        Intent intent = new Intent();
                        intent.putExtra("ListData", listMain.getName());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tbIvBack:
                finish();
                break;
            default:
                break;
        }

    }


}
