package com.js.jainsamaj.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.adapters.SelectionListAdapter;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.WorkWithIdRequest;
import com.js.jainsamaj.models.SelectionListMain;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestAreaId;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestCityId;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestDistrictId;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestStateId;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener, OnApiCallListener {
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    ListView lvSection;
    EditText etSearch;
    TextView tvEmptySelection;

    //For Api call
    SelectionListAdapter selectionListAdapter;
    SelectionListMain selectionListMain;
    ApiFunctions apiFunctions;
    Gson gson = new Gson();
    ApiResponseMessage apiResponseMessage;
    ArrayList<SelectionListMain> selectionListMainArrayList = new ArrayList<>();

    Bundle bSelection;
    boolean isTCategory,
            isTCategoryAll,
            isTCountry, isTState, isTDistrict, isTCity, isTArea,
            isEduLevel, isEduField,
            isHeight, isHeightFrom, isHeightTo,
            isProfileCity,
            isWorkWith, isWorkAs;

    int CountryId, StateId, DistrictId, CityId,
            workWithId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        apiFunctions = new ApiFunctions(this, this);
        bSelection = new Bundle(getIntent().getExtras());

        isTCategory = bSelection.getBoolean("isTCategory");
        isTCategoryAll = bSelection.getBoolean("isTCategoryAll");
        isTCountry = bSelection.getBoolean("isTCountry");
        isTState = bSelection.getBoolean("isTState");
        isTDistrict = bSelection.getBoolean("isTDistrict");
        isTCity = bSelection.getBoolean("isTCity");
        isTArea = bSelection.getBoolean("isTArea");

        isProfileCity = bSelection.getBoolean("isProfileCity");

        isEduLevel = bSelection.getBoolean("isEduLevel");
        isEduField = bSelection.getBoolean("isEduField");

        isHeight = bSelection.getBoolean("isHeight");
        isHeightFrom = bSelection.getBoolean("isHeightFrom");
        isHeightTo = bSelection.getBoolean("isHeightTo");

        isWorkWith = bSelection.getBoolean("isWorkWith");
        isWorkAs = bSelection.getBoolean("isWorkAs");

        CountryId = bSelection.getInt("country");
        StateId = bSelection.getInt("state");
        DistrictId = bSelection.getInt("district");
        CityId = bSelection.getInt("city");

        workWithId = bSelection.getInt("workWithId");


        bindHere();
        clickHere();
        tbIvBack.setOnClickListener(this);

        if (Global.isNetworkAvailable(this)) {
            if (bSelection != null) {
                /*******************************Api call here*******************************/
                if (isTCategory) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    apiFunctions.getList(Api.MainUrl + Api.ActionTempleCategory);
                } else if (isTCountry) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    apiFunctions.getList(Api.MainUrl + Api.ActionTempleCountry);
                } else if (isTState) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    RequestStateId requestStateId = new RequestStateId(CountryId);
                    apiFunctions.getStateList(Api.MainUrl + Api.ActionTempleState, requestStateId);
                } else if (isTDistrict) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    RequestDistrictId requestDistrictId = new RequestDistrictId(StateId);
                    apiFunctions.getDistrictList(Api.MainUrl + Api.ActionTempleDistrict, requestDistrictId);
                } else if (isTCity) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    RequestCityId requestCityId = new RequestCityId(DistrictId);
                    apiFunctions.getCityList(Api.MainUrl + Api.ActionTempleCity, requestCityId);
                } else if (isTArea) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    RequestAreaId requestAreaId = new RequestAreaId(CityId);
                    apiFunctions.getAreaList(Api.MainUrl + Api.ActionTempleArea, requestAreaId);
                } else if (isHeight || isHeightFrom || isHeightTo) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    apiFunctions.getList(Api.MainUrl + Api.ActionHeight);
                } else if (isEduLevel) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    apiFunctions.getList(Api.MainUrl + Api.ActionEducationLevel);
                } else if (isEduField) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    apiFunctions.getList(Api.MainUrl + Api.ActionEducationField);
                } else if (isProfileCity) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    RequestCityId requestCityId = new RequestCityId(DistrictId, StateId);
                    apiFunctions.getCityList(Api.MainUrl + Api.ActionTempleCity, requestCityId);
                } else if (isWorkWith) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    apiFunctions.getList(Api.MainUrl + Api.ActionWorkWith);
                } else if (isWorkAs) {
                    AppDialog.showProgressDialog(SelectionActivity.this);
                    WorkWithIdRequest workWithIdRequest= new WorkWithIdRequest(workWithId);
                    apiFunctions.getWorkAsList(Api.MainUrl + Api.ActionWorkAs,workWithIdRequest);
                }
            } else {
                AppDialog.showAlertDialog(SelectionActivity.this, null, getString(R.string.error_record_not_available),
                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
            }
        } else {
            AppDialog.noNetworkDialog(this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
        }

    }

    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvEmptySelection = (TextView) findViewById(R.id.tvEmptySelection);
        etSearch = (EditText) findViewById(R.id.etSearch);
        lvSection = (ListView) findViewById(R.id.lvSection);

        //set Toolbar Title and EditText Hint Here
        if (isTCategory) {
            tbTitle.setText(getResources().getString(R.string.temple_category));
//            etSearch.setHint(R.string.temple_category_Search);
        } else if (isTCountry) {
            tbTitle.setText(getResources().getString(R.string.temple_county));
//            etSearch.setHint(R.string.temple_county_Search);
        } else if (isTState) {
            tbTitle.setText(getResources().getString(R.string.temple_state));
//            etSearch.setHint(R.string.temple_state_Search);
        } else if (isTDistrict) {
            tbTitle.setText(getResources().getString(R.string.temple_district));
//            etSearch.setHint(R.string.temple_district_Search);
        } else if (isTCity) {
//            etSearch.setHint(R.string.temple_city_Search);
            tbTitle.setText(getResources().getString(R.string.temple_city));
        } else if (isTArea) {
            tbTitle.setText(getResources().getString(R.string.temple_area));
//            etSearch.setHint(R.string.temple_area_Search);
        } else if (isHeight || isHeightFrom || isHeightTo) {
            tbTitle.setText(getResources().getString(R.string.profile_Height));
//            etSearch.setHint(R.string.profile_srch_height);
        } else if (isEduLevel) {
            tbTitle.setText(getResources().getString(R.string.profile_edu_level));
//            etSearch.setHint(R.string.profile_srch_edu_level);
        } else if (isEduField) {
            tbTitle.setText(getResources().getString(R.string.profile_edu_field));
//            etSearch.setHint(R.string.profile_srch_edu_field);
        } else if (isWorkWith) {
            tbTitle.setText(getResources().getString(R.string.profile_wwith));
//            etSearch.setHint(R.string.profile_srch_edu_field);
        } else if (isWorkAs) {
            tbTitle.setText(getResources().getString(R.string.profile_wAs));
//            etSearch.setHint(R.string.profile_srch_edu_field);
        }


        lvSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectionListAdapter.toggleSelection(position);
                SparseBooleanArray selected = selectionListAdapter.getSelectedIds();
                ArrayList<SelectionListMain> listMainArrayList = new ArrayList<>();
                for (int j = (selected.size() - 1); j >= 0; j--) {
                    if (selected.valueAt(j)) {
                        SelectionListMain selecteditem = selectionListAdapter.getItem(selected.keyAt(j));
                        listMainArrayList.add(selecteditem);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("SelectionList", listMainArrayList);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public void clickHere() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                selectionListAdapter.searchRecord(text);

            }
        });

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

    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        AppDialog.dismissProgressDialog();
        Log.e("List Success" + responseString);
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);

                /******************************Getting Temple Category*****************************/
                if ((requestType == Api.MainUrl + Api.ActionTempleCategory) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);

                    if (isTCategoryAll && jsonArray.length() > 0) {
                        SelectionListMain selectionListMain1 = new SelectionListMain();
                        selectionListMain1.setId(0);
                        selectionListMain1.setCategoryName("All");
                        selectionListMainArrayList.add(selectionListMain1);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject getFirstObj = jsonArray.getJSONObject(i);
                            selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                            selectionListMainArrayList.add(selectionListMain);
                        }
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject getFirstObj = jsonArray.getJSONObject(i);
                            selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                            selectionListMainArrayList.add(selectionListMain);
                        }
                    }
                   /* }*/


                }
                /*****************************Getting Temple Country*******************************/
                else if ((requestType == Api.MainUrl + Api.ActionTempleCountry) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting Temple State*******************************/
                else if ((requestType == Api.MainUrl + Api.ActionTempleState) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting Temple District *******************************/
                else if ((requestType == Api.MainUrl + Api.ActionTempleDistrict) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting Temple City or Profile City*******************************/
                else if ((requestType == Api.MainUrl + Api.ActionTempleCity) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }

                /*****************************Getting Temple Area*******************************/
                else if ((requestType == Api.MainUrl + Api.ActionTempleArea) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting HeightRequest**************************************/
                else if ((requestType == Api.MainUrl + Api.ActionHeight) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting Education Level******************************/
                else if ((requestType == Api.MainUrl + Api.ActionEducationLevel) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting Education Field******************************/
                else if ((requestType == Api.MainUrl + Api.ActionEducationField) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }
                /*****************************Getting Work with Field******************************/
                else if ((requestType == Api.MainUrl + Api.ActionWorkWith) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                }

                /*****************************Getting Work As Field******************************/
                else if ((requestType == Api.MainUrl + Api.ActionWorkAs) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirstObj = jsonArray.getJSONObject(i);
                        selectionListMain = gson.fromJson(getFirstObj.toString(), SelectionListMain.class);
                        selectionListMainArrayList.add(selectionListMain);
                    }
                } else {

                }


                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /************************Getting Temple Category***************************/
                        if ((requestType == Api.MainUrl + Api.ActionTempleCategory) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);
                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_CATEGORY);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Temple Country**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionTempleCountry) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);

                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_COUNTY);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Temple State**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionTempleState) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);

                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_STATE);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Temple District**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionTempleDistrict) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);

                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_DISTRICT);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Temple City**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionTempleCity) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);

                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_CITY);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Temple Area**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionTempleArea) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);
                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_AREA);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting HeightRequest**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionHeight) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);
                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_HEIGHT);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Education Level*************************/
                        else if ((requestType == Api.MainUrl + Api.ActionEducationLevel) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);
                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_EDU_LEVEL);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }/**************************Getting Education Field*************************/
                        else if ((requestType == Api.MainUrl + Api.ActionEducationField) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);
                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_EDU_FIELD);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }

                        /**************************Getting Work With**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionWorkWith) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);

                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_WORK_WITH);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        }
                        /**************************Getting Work AS**************************/
                        else if ((requestType == Api.MainUrl + Api.ActionWorkAs) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (!selectionListMainArrayList.isEmpty()) {
                                lvSection.setVisibility(View.VISIBLE);
                                tvEmptySelection.setVisibility(View.GONE);
                                etSearch.setVisibility(View.VISIBLE);

                                selectionListAdapter = new SelectionListAdapter(SelectionActivity.this, selectionListMainArrayList, Constants.IS_WORK_AS);
                                lvSection.setAdapter(selectionListAdapter);
                            } else {
                                lvSection.setVisibility(View.GONE);
                                tvEmptySelection.setVisibility(View.VISIBLE);
                                etSearch.setVisibility(View.GONE);
                            }
                        } else {
                            lvSection.setVisibility(View.GONE);
                            tvEmptySelection.setVisibility(View.VISIBLE);
                            etSearch.setVisibility(View.GONE);
                            AppDialog.showAlertDialog(SelectionActivity.this, null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                        }
                    }
                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(final String errorMessage) {

        try {
            AppDialog.dismissProgressDialog();
            Log.e("List Failure" + errorMessage);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(SelectionActivity.this, null, errorMessage,
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
