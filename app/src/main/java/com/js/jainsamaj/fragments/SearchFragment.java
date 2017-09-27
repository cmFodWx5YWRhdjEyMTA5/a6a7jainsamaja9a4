package com.js.jainsamaj.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.ListActivity;
import com.js.jainsamaj.activities.SelectionActivity;
import com.js.jainsamaj.activities.WebsiteActivity;
import com.js.jainsamaj.fragments.common.RootFragment;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Common.GetMatrimony;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Search.UpdateMatrimonyCriteriaMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search.MatrimonyCriteriaMain;
import com.js.jainsamaj.models.SelectionListMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


public class SearchFragment extends RootFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, OnApiCallListener {

    //request
    public static final int REQUEST_AGE_FROM = 4444;
    public static final int REQUEST_AGE_TO = 4455;
    public static final int REQUEST_HEIGHT_FROM = 4466;
    public static final int REQUEST_HEIGHT_TO = 4477;
    public static final int REQUEST_MARITAL_STATUS = 4488;
    private static final int REQUEST_TEMPLE_COUNTRY = 4499;
    private static final int REQUEST_TEMPLE_STATE = 4400;
    private static final int REQUEST_TEMPLE_CITY = 4422;
    private static final int REQUEST_EDUCATION_FIELD = 4423;
    private static final int REQUEST_EDUCATION_LEVEL = 4424;
    private static final int REQUEST_INCOME = 4425;
    private static final int REQUEST_WORK_AS = 4426;
    private static final int REQUEST_WORK_WITH = 4427;
    EditText etPSAgeFrom, etPSAgeTo,
            etPSHeightFrom, etPSHeightTo,
            etPSMStatus,
            etPSCountry, etPSState, etPSCity,
            etPSPLevel, etPSPArea, etPSPWork, etPSWorkAs, etPSPAnnual;
    CheckBox
            //createdBy
            cbPSTCBy1, cbPSTCBy2, cbPSTCBy3, cbPSTCBy4,
    //bodyType
    cbPBType1, cbPBType2, cbPBType3, cbPBType4,
    //skinTone
    cbPSTone1, cbPSTone2, cbPSTone3, cbPSTone4,
    //disability
    cbDisability1, cbDisability2;
    TextView tvPage, tvPheight, tvPCreated, tvPLocation, tvPEducation, tvPlife, tvPBodyType, tvPSkinTone, tvPDisability;
    Button btnPSSave;
    SimpleDraweeView sdsProfileSearchBanner;
    //set Font
    Typeface tfRegular, tfBold;
    ArrayList<SelectionListMain> selectionListMainArrayList = new ArrayList<>();
    //All ids
    String CountryId, StateId, DistrictId, CityId;
    int heightFromId, heightToId,
            eduLevelId, eduFieldId, incomeId;
    String bodyTypeName, skinToneName, createdByName, disabilityName;

    //For APi
    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    MatrimonyCriteriaMain matrimonyCriteriaMain;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;

    List<String> bodyTypeList = new ArrayList<String>();
    List<String> skinToneList = new ArrayList<String>();
    List<String> disabilityList = new ArrayList<String>();
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;

    int id;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.activity_search, container, false);
        apiFunctions = new ApiFunctions(getActivity(), this);


        tvPage = (TextView) view.findViewById(R.id.tvPage);
        tvPheight = (TextView) view.findViewById(R.id.tvPheight);
        tvPCreated = (TextView) view.findViewById(R.id.tvPCreated);
        tvPLocation = (TextView) view.findViewById(R.id.tvPLocation);
        tvPEducation = (TextView) view.findViewById(R.id.tvPEducation);
        tvPlife = (TextView) view.findViewById(R.id.tvPlife);
        tvPBodyType = (TextView) view.findViewById(R.id.tvPBodyType);
        tvPSkinTone = (TextView) view.findViewById(R.id.tvPSkinTone);
        tvPDisability = (TextView) view.findViewById(R.id.tvPDisability);

        etPSAgeFrom = (EditText) view.findViewById(R.id.etPSAgeFrom);
//        etPSAgeFrom.setText("0");
        etPSAgeTo = (EditText) view.findViewById(R.id.etPSAgeTo);
//        etPSAgeTo.setText("0");
        etPSHeightFrom = (EditText) view.findViewById(R.id.etPSHeightFrom);
//        etPSHeightFrom.setText("0");
        etPSHeightTo = (EditText) view.findViewById(R.id.etPSHeightTo);
//        etPSHeightTo.setText("0");
        etPSMStatus = (EditText) view.findViewById(R.id.etPSMStatus);
        etPSCountry = (EditText) view.findViewById(R.id.etPSCountry);
        etPSState = (EditText) view.findViewById(R.id.etPSState);
        etPSCity = (EditText) view.findViewById(R.id.etPSCity);


        etPSPLevel = (EditText) view.findViewById(R.id.etPSPLevel);
        etPSPArea = (EditText) view.findViewById(R.id.etPSPArea);
        etPSPWork = (EditText) view.findViewById(R.id.etPSPWork);
        etPSWorkAs = (EditText) view.findViewById(R.id.etPSWorkAs);
        etPSPAnnual = (EditText) view.findViewById(R.id.etPSPAnnual);


        cbPSTCBy1 = (CheckBox) view.findViewById(R.id.cbPSTCBy1);
        cbPSTCBy2 = (CheckBox) view.findViewById(R.id.cbPSTCBy2);
        cbPSTCBy3 = (CheckBox) view.findViewById(R.id.cbPSTCBy3);
        cbPSTCBy4 = (CheckBox) view.findViewById(R.id.cbPSTCBy4);

        cbPBType1 = (CheckBox) view.findViewById(R.id.cbPBType1);
        cbPBType2 = (CheckBox) view.findViewById(R.id.cbPBType2);
        cbPBType3 = (CheckBox) view.findViewById(R.id.cbPBType3);
        cbPBType4 = (CheckBox) view.findViewById(R.id.cbPBType4);

        cbPSTone1 = (CheckBox) view.findViewById(R.id.cbPSTone1);
        cbPSTone2 = (CheckBox) view.findViewById(R.id.cbPSTone2);
        cbPSTone3 = (CheckBox) view.findViewById(R.id.cbPSTone3);
        cbPSTone4 = (CheckBox) view.findViewById(R.id.cbPSTone4);

        cbDisability1 = (CheckBox) view.findViewById(R.id.cbDisability1);
        cbDisability2 = (CheckBox) view.findViewById(R.id.cbDisability2);

        btnPSSave = (Button) view.findViewById(R.id.btnPSSave);

        sdsProfileSearchBanner = (SimpleDraweeView) view.findViewById(R.id.sdsProfileSearchBanner);
        sdsProfileSearchBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent iCompanyUrl = new Intent(getActivity(), WebsiteActivity.class);
                    iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                    startActivity(iCompanyUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        //For Set Font
        tfRegular = Global.setRegularFont(getActivity());
        tfBold = Global.setBoldFont(getActivity());

        tvPage.setTypeface(tfRegular);
        tvPheight.setTypeface(tfRegular);
        tvPCreated.setTypeface(tfRegular);
        tvPLocation.setTypeface(tfRegular);
        tvPEducation.setTypeface(tfRegular);
        tvPlife.setTypeface(tfRegular);
        tvPBodyType.setTypeface(tfRegular);
        tvPSkinTone.setTypeface(tfRegular);
        tvPDisability.setTypeface(tfRegular);

        etPSAgeFrom.setTypeface(tfRegular);
        etPSAgeTo.setTypeface(tfRegular);
        etPSHeightFrom.setTypeface(tfRegular);
        etPSHeightTo.setTypeface(tfRegular);
        etPSMStatus.setTypeface(tfRegular);
        etPSCountry.setTypeface(tfRegular);
        etPSState.setTypeface(tfRegular);
        etPSCity.setTypeface(tfRegular);

        etPSPLevel.setTypeface(tfRegular);
        etPSPArea.setTypeface(tfRegular);
        etPSPWork.setTypeface(tfRegular);
        etPSWorkAs.setTypeface(tfRegular);
        etPSPAnnual.setTypeface(tfRegular);


        cbPSTCBy1.setTypeface(tfRegular);
        cbPSTCBy2.setTypeface(tfRegular);
        cbPSTCBy3.setTypeface(tfRegular);
        cbPSTCBy4.setTypeface(tfRegular);

        cbPBType1.setTypeface(tfRegular);
        cbPBType2.setTypeface(tfRegular);
        cbPBType3.setTypeface(tfRegular);
        cbPBType4.setTypeface(tfRegular);

        cbPSTone1.setTypeface(tfRegular);
        cbPSTone2.setTypeface(tfRegular);
        cbPSTone3.setTypeface(tfRegular);
        cbPSTone4.setTypeface(tfRegular);

        cbDisability1.setTypeface(tfRegular);
        cbDisability2.setTypeface(tfRegular);

        btnPSSave.setTypeface(tfBold);

        //EditText click here
        etPSAgeFrom.setOnClickListener(this);
        etPSAgeTo.setOnClickListener(this);

        etPSHeightFrom.setOnClickListener(this);
        etPSHeightTo.setOnClickListener(this);

        etPSMStatus.setOnClickListener(this);

        etPSCountry.setOnClickListener(this);
        etPSState.setOnClickListener(this);
        etPSCity.setOnClickListener(this);

        etPSPLevel.setOnClickListener(this);
        etPSPArea.setOnClickListener(this);
        etPSPWork.setOnClickListener(this);
        etPSWorkAs.setOnClickListener(this);
        etPSPAnnual.setOnClickListener(this);

        btnPSSave.setOnClickListener(this);

        //CheckBoxes Click
        //Created by
        cbPSTCBy1.setOnCheckedChangeListener(this);
        cbPSTCBy2.setOnCheckedChangeListener(this);
        cbPSTCBy3.setOnCheckedChangeListener(this);
        cbPSTCBy4.setOnCheckedChangeListener(this);
        //Body type
        cbPBType1.setOnCheckedChangeListener(this);
        cbPBType2.setOnCheckedChangeListener(this);
        cbPBType3.setOnCheckedChangeListener(this);
        cbPBType4.setOnCheckedChangeListener(this);
        //Skin tone
        cbPSTone1.setOnCheckedChangeListener(this);
        cbPSTone2.setOnCheckedChangeListener(this);
        cbPSTone3.setOnCheckedChangeListener(this);
        cbPSTone4.setOnCheckedChangeListener(this);

        cbDisability1.setOnCheckedChangeListener(this);
        cbDisability2.setOnCheckedChangeListener(this);


        //Api call Here
        getCriteria();

        return view;
    }

    private void getCriteria() {
        if (Global.isNetworkAvailable(getActivity())) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            if (loginMain != null && loginMain.getUsername() != null) {
                AppDialog.showProgressDialog(getActivity());
                GetMatrimony getMatrimony = new GetMatrimony(loginMain.getUsername());
                apiFunctions.getMatrimony(Api.MainUrl + Api.ActionGetMatrimonyCriteria, getMatrimony);
            }


        } else {
            AppDialog.noNetworkDialog(getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(R.string.nav_menu_search));

        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(getActivity());
            imagesLinkMains = new ArrayList<>();
            randomGenerator = new Random();

            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + Constants.TABLE_SMALL_IMAGE_LINK + "", null);

            if (cursor.moveToFirst()) {
                do {
                    imagesLinkMain = new ImagesLinkMain(cursor.getString(cursor.getColumnIndex(Constants.SMALL_TABLE_URL)));
                    companyUrl = cursor.getString(cursor.getColumnIndex(Constants.SMALL_TABLE_COMPANY_URL));
                    imagesLinkMains.add(imagesLinkMain);

                } while (cursor.moveToNext());
            }
            cursor.close();

            //Shuffle array list
            Collections.shuffle(imagesLinkMains);
            String ImageLink = String.valueOf(imagesLinkMains.get(0).getImgLink());
//            byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            sdsProfileSearchBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdsProfileSearchBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Check Boxes click
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();

        //*************************************For Body Type************************************
        if (id == R.id.cbPBType1) {
            if (cbPBType1.isChecked()) {
                bodyTypeList.add(getString(R.string.profile_body_1));
            } else {
                bodyTypeList.remove(getString(R.string.profile_body_1));
            }
        }
        if (id == R.id.cbPBType2) {
            if (cbPBType2.isChecked()) {
                bodyTypeList.add(getString(R.string.profile_body_2));
            } else {
                bodyTypeList.remove(getString(R.string.profile_body_2));
            }

        }
        if (id == R.id.cbPBType3) {
            if (cbPBType3.isChecked()) {
                bodyTypeList.add(getString(R.string.profile_body_3));
            } else {
                bodyTypeList.remove(getString(R.string.profile_body_3));
            }

        }
        if (id == R.id.cbPBType4) {
            if (cbPBType4.isChecked()) {
                bodyTypeList.add(getString(R.string.profile_body_4));
            } else {
                bodyTypeList.remove(getString(R.string.profile_body_4));
            }

        }
        //***********************************For Skin Tone**************************************
        if (id == R.id.cbPSTone1) {
            if (cbPSTone1.isChecked()) {
                skinToneList.add(getString(R.string.profile_skin_1));
            } else {
                skinToneList.remove(getString(R.string.profile_skin_1));
            }
        }
        if (id == R.id.cbPSTone2) {
            if (cbPSTone2.isChecked()) {
                skinToneList.add(getString(R.string.profile_skin_2));
            } else {
                skinToneList.remove(getString(R.string.profile_skin_2));
            }
        }
        if (id == R.id.cbPSTone3) {
            if (cbPSTone3.isChecked()) {
                skinToneList.add(getString(R.string.profile_skin_3));
            } else {
                skinToneList.remove(getString(R.string.profile_skin_3));
            }

        }
        if (id == R.id.cbPSTone4) {
            if (cbPSTone3.isChecked()) {
                skinToneList.add(getString(R.string.profile_skin_4));
            } else {
                skinToneList.remove(getString(R.string.profile_skin_4));
            }


        }
        //**********************************For Disability**************************************
        if (id == R.id.cbDisability1) {
            if (cbDisability1.isChecked()) {
                disabilityName = getString(R.string.profile_disabilites_doesnt_metter);
                cbDisability2.setChecked(false);
            }
        }
        if (id == R.id.cbDisability2) {
            if (cbDisability2.isChecked()) {
                disabilityName = getString(R.string.profile_disabilites_doesnt_incude);
                cbDisability1.setChecked(false);
            }


        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /************************************For Age click************************************/
            case R.id.etPSAgeFrom:
                Intent iFromAge = new Intent(getActivity(), ListActivity.class);
                iFromAge.putExtra("isAgeFrom", true);
                startActivityForResult(iFromAge, REQUEST_AGE_FROM);
                break;
            case R.id.etPSAgeTo:
                Intent iToAge = new Intent(getActivity(), ListActivity.class);
                iToAge.putExtra("isAgeTo", true);
                startActivityForResult(iToAge, REQUEST_AGE_TO);
                break;
            /************************************For HeightRequest click************************************/
            case R.id.etPSHeightFrom:
                Intent iHeightFrom = new Intent(getActivity(), SelectionActivity.class);
                iHeightFrom.putExtra("isHeightFrom", true);
                startActivityForResult(iHeightFrom, REQUEST_HEIGHT_FROM);
                break;
            case R.id.etPSHeightTo:
                Intent iHeightTo = new Intent(getActivity(), SelectionActivity.class);
                iHeightTo.putExtra("isHeightTo", true);
                startActivityForResult(iHeightTo, REQUEST_HEIGHT_TO);
                break;
            /************************************For Marital Status********************************/
            case R.id.etPSMStatus:
                Intent iMarital = new Intent(getActivity(), ListActivity.class);
                iMarital.putExtra("isMarital", true);
                startActivityForResult(iMarital, REQUEST_MARITAL_STATUS);
                break;

            /*****************************For Location And Grew click******************************/
            case R.id.etPSCountry:
                Intent iTCounty = new Intent(getActivity(), SelectionActivity.class);
                iTCounty.putExtra("isTCountry", true);
                startActivityForResult(iTCounty, REQUEST_TEMPLE_COUNTRY);
                break;
            case R.id.etPSState:
                if (CountryId != null) {
                    int id = Integer.parseInt(CountryId);
                    Intent iTState = new Intent(getActivity(), SelectionActivity.class);
                    iTState.putExtra("isTState", true);
                    iTState.putExtra("country", id);
                    startActivityForResult(iTState, REQUEST_TEMPLE_STATE);
                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.add_temple_country_error),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
                break;
            case R.id.etPSCity:
                if (DistrictId != null) {
                    int id = Integer.parseInt(DistrictId);
                    Intent iTState = new Intent(getActivity(), SelectionActivity.class);
                    iTState.putExtra("isTCity", true);
                    iTState.putExtra("district", id);
                    startActivityForResult(iTState, REQUEST_TEMPLE_CITY);
                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_record_not_available),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
                break;
            /************************************For Education on Profession click***************/
            case R.id.etPSPLevel:
                Intent iEduLevel = new Intent(getActivity(), SelectionActivity.class);
                iEduLevel.putExtra("isEduLevel", true);
                startActivityForResult(iEduLevel, REQUEST_EDUCATION_LEVEL);
                break;
            case R.id.etPSPArea:
                Intent iEduField = new Intent(getActivity(), SelectionActivity.class);
                iEduField.putExtra("isEduField", true);
                startActivityForResult(iEduField, REQUEST_EDUCATION_FIELD);
                break;
            case R.id.etPSPWork:
                Intent iWorkWith = new Intent(getActivity(), ListActivity.class);
                iWorkWith.putExtra("isWorkWith", true);
                startActivityForResult(iWorkWith, REQUEST_WORK_WITH);
                break;
            case R.id.etPSWorkAs:
                Intent iWorkAs = new Intent(getActivity(), ListActivity.class);
                iWorkAs.putExtra("isWorkAs", true);
                startActivityForResult(iWorkAs, REQUEST_WORK_AS);
                break;
            case R.id.etPSPAnnual:
                Intent isAnnualIncome = new Intent(getActivity(), ListActivity.class);
                isAnnualIncome.putExtra("isAnnualIncome", true);
                startActivityForResult(isAnnualIncome, REQUEST_INCOME);
                break;
            case R.id.btnPSSave:
                String bodyArray = bodyTypeList.toString();
                String bodyTypes = bodyArray.substring(1, bodyArray.length() - 1).replace(", ", ",");
                String skinArray = skinToneList.toString();
                String skinTypes = skinArray.substring(1, skinArray.length() - 1).replace(", ", ",");
                if (Global.isNetworkAvailable(getActivity())) {

                    int responseId = id;
                    int loginId = 0;
                    String userEmail = null;
                    if (loginMain != null) {
                        userEmail = loginMain.getUsername();
                        loginId = loginMain.getId();
                    }


                    int ageFrom = Integer.parseInt(etPSAgeFrom.getText().toString());
                    int ageTo = Integer.parseInt(etPSAgeTo.getText().toString());

                    String heightFrom = etPSHeightFrom.getText().toString();
                    String heightTo = etPSHeightTo.getText().toString();

                    String maritalStatus = etPSMStatus.getText().toString();


  /*public UpdateMatrimonyCriteriaMain(int id, String createdUser, int userId, int ageFrom,
                    int ageTo, int heightFrom, int heightTo, String maritalStatus,
                    int countryLiving, int stateLiving, int cityLiving, int education,
                    int professionArea, int annualIncome, String bodyType, String skinTone,
                            String disability) {*/
                    UpdateMatrimonyCriteriaMain updateMatrimonyCriteriaMain = new UpdateMatrimonyCriteriaMain(responseId,
                            userEmail, loginId, ageFrom, ageTo,
                            heightFromId, heightToId, maritalStatus, Integer.parseInt(CountryId), Integer.parseInt(StateId),
                            Integer.parseInt(CityId), eduLevelId,
                            eduFieldId, incomeId, bodyTypes, skinTypes, disabilityName);
                    AppDialog.showProgressDialog(getActivity());
                    apiFunctions.updateMatrimonyCriteria(Api.MainUrl + Api.ActionInsertMatrimonyCriteria, updateMatrimonyCriteriaMain);
                } else {
                    AppDialog.noNetworkDialog(getActivity(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            /******************************Result For From Age**************************************/
            if (requestCode == REQUEST_AGE_FROM && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                etPSAgeFrom.setText(name);
//                matrimonyMain.setAgeFrom(name);
            }
            /******************************Result For To Age**************************************/
            if (requestCode == REQUEST_AGE_TO && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                etPSAgeTo.setText(name);
//                matrimonyMain.setAgeTo(name);
            }
            /******************************Result For  From HeightRequest*****************************************/
            if (requestCode == REQUEST_HEIGHT_FROM && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getHeight();
                    id = selectionListMainArrayList.get(i).getId();
                    heightFromId = id;
                }
//                matrimonyMain.setHeightFrom(name);
                etPSHeightFrom.setText(name);
            }
            /******************************Result For  To HeightRequest*****************************************/
            if (requestCode == REQUEST_HEIGHT_TO && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getHeight();
                    id = selectionListMainArrayList.get(i).getId();
                    heightToId = id;
                }
                etPSHeightTo.setText(name);
//                matrimonyMain.setHeightTo(name);
            }
            /******************************Result For Marital**************************************/
            if (requestCode == REQUEST_MARITAL_STATUS && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                etPSMStatus.setText(name);
                //  matrimonyMain.setMaritalStatus(name);
            }

            /******************************Result For Country**************************************/
            if (requestCode == REQUEST_TEMPLE_COUNTRY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCountryName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    CountryId = id;
                }
                etPSCountry.setText(name);
                //  matrimonyMain.setCountryLiving(name);
            }
            /******************************Result For State****************************************/
            if (requestCode == REQUEST_TEMPLE_STATE && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getStateName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    StateId = id;
                    DistrictId = "0";
                }
                etPSState.setText(name);
//                matrimonyMain.setStateLiving(name);

            }
            /******************************Result For City*****************************************/
            if (requestCode == REQUEST_TEMPLE_CITY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCityName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    CityId = id;

                }
                etPSCity.setText(name);
//                matrimonyMain.setCityLiving(name);
            }


            /******************************Result For Education Level*****************************************/
            if (requestCode == REQUEST_EDUCATION_LEVEL && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id = 0;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getEducationLevel();
                    id = selectionListMainArrayList.get(i).getId();
                    eduLevelId = id;
                }
//                matrimonyMain.setEducation(String.valueOf(id));
                etPSPLevel.setText(name);
            }
            /******************************Result For Education Field*****************************************/
            if (requestCode == REQUEST_EDUCATION_FIELD && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getEducationField();
                    id = selectionListMainArrayList.get(i).getId();
                    eduFieldId = id;
                }
                etPSPArea.setText(name);
            }
            /******************************Result For Work With **************************************/
            if (requestCode == REQUEST_WORK_WITH && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                etPSPWork.setText(name);
            }
            /******************************Result For Work As**************************************/
            if (requestCode == REQUEST_WORK_AS && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                etPSWorkAs.setText(name);
            }
            /******************************Result For Annual Income**************************************/
            if (requestCode == REQUEST_INCOME && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                if (name.equalsIgnoreCase(getString(R.string.profile_search_education_and_professional_work_income_1))) {
                    incomeId = 1;
                }
                if (name.equalsIgnoreCase(getString(R.string.profile_search_education_and_professional_work_income_2))) {
                    incomeId = 2;
                }
                if (name.equalsIgnoreCase(getString(R.string.profile_search_education_and_professional_work_income_3))) {
                    incomeId = 3;
                }
                if (name.equalsIgnoreCase(getString(R.string.profile_search_education_and_professional_work_income_4))) {
                    incomeId = 4;
                }

                etPSPAnnual.setText(name);

            }
        }

    }

    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        Log.e("Matrimony Search Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                // Get Matrimony Criteria
                if ((requestType == Api.MainUrl + Api.ActionGetMatrimonyCriteria) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                    matrimonyCriteriaMain = gson.fromJson(getFirst.toString(), MatrimonyCriteriaMain.class);
                    if (getFirst.has("id")) {
                        id = (int) getFirst.get("id");
                    } else {
                        id = 0;
                    }

                } else if ((requestType == Api.MainUrl + Api.ActionInsertMatrimonyCriteria) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                } else {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionGetMatrimonyCriteria) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            try {
                                if (matrimonyCriteriaMain != null) {

                                    etPSAgeFrom.setText(String.valueOf(matrimonyCriteriaMain.getAgeFrom()));
                                    etPSAgeTo.setText(String.valueOf(matrimonyCriteriaMain.getAgeTo()));

                                    etPSHeightFrom.setText(matrimonyCriteriaMain.getHeightFrom().getHeight());
                                    heightFromId = matrimonyCriteriaMain.getHeightFrom().getId();

                                    etPSHeightTo.setText(matrimonyCriteriaMain.getHeightTo().getHeight());
                                    heightToId = matrimonyCriteriaMain.getHeightTo().getId();

                                    etPSMStatus.setText(matrimonyCriteriaMain.getMaritalStatus());

                                    etPSCountry.setText(matrimonyCriteriaMain.getCountryLiving().getCountryName());
                                    CountryId = String.valueOf(matrimonyCriteriaMain.getCountryLiving().getId());

                                    etPSState.setText(matrimonyCriteriaMain.getStateLiving().getStateName());
                                    StateId = String.valueOf(matrimonyCriteriaMain.getStateLiving().getId());

                                    etPSCity.setText(matrimonyCriteriaMain.getCityLiving().getCityName());
                                    CityId = String.valueOf(matrimonyCriteriaMain.getCityLiving().getId());

                                    etPSPLevel.setText(matrimonyCriteriaMain.getEducation().getEducationLevel());
                                    eduLevelId = matrimonyCriteriaMain.getEducation().getId();

                                    etPSPArea.setText(matrimonyCriteriaMain.getProfessionArea().getEducationField());
                                    eduFieldId = matrimonyCriteriaMain.getProfessionArea().getId();

                                    if (matrimonyCriteriaMain.getAnnualIncome() == 1) {
                                        etPSPAnnual.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_1)));
                                        incomeId = 1;
                                    }
                                    if (matrimonyCriteriaMain.getAnnualIncome() == 2) {
                                        etPSPAnnual.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_2)));
                                        incomeId = 2;
                                    }
                                    if (matrimonyCriteriaMain.getAnnualIncome() == 3) {
                                        etPSPAnnual.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_3)));
                                        incomeId = 3;
                                    }
                                    if (matrimonyCriteriaMain.getAnnualIncome() == 4) {
                                        etPSPAnnual.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_4)));
                                        incomeId = 4;
                                    }


                                    //Set body Type
                                    int bodySize = matrimonyCriteriaMain.getBodyType().size();
                                    if (bodySize > 0) {
                                        for (int i = 0; i < bodySize; i++) {
                                            String bodyStr = matrimonyCriteriaMain.getBodyType().get(i);
                                            if (bodyStr.equalsIgnoreCase(getString(R.string.profile_body_1))) {
                                                cbPBType1.setChecked(true);
                                            }
                                            if (bodyStr.equalsIgnoreCase(getString(R.string.profile_body_2))) {
                                                cbPBType2.setChecked(true);
                                            }
                                            if (bodyStr.equalsIgnoreCase(getString(R.string.profile_body_3))) {
                                                cbPBType3.setChecked(true);
                                            }
                                            if (bodyStr.equalsIgnoreCase(getString(R.string.profile_body_4))) {
                                                cbPBType4.setChecked(true);
                                            }
                                        }
                                    }

                                    //Set Skin Tone
                                    int skinSize = matrimonyCriteriaMain.getSkinTone().size();
                                    if (skinSize > 0) {
                                        for (int i = 0; i < skinSize; i++) {
                                            String skinStr = matrimonyCriteriaMain.getSkinTone().get(i);
                                            if (skinStr.equalsIgnoreCase(getString(R.string.profile_skin_1))) {
                                                cbPSTone1.setChecked(true);
                                            }
                                            if (skinStr.equalsIgnoreCase(getString(R.string.profile_skin_2))) {
                                                cbPSTone2.setChecked(true);
                                            }
                                            if (skinStr.equalsIgnoreCase(getString(R.string.profile_skin_3))) {
                                                cbPSTone3.setChecked(true);
                                            }
                                            if (skinStr.equalsIgnoreCase(getString(R.string.profile_skin_4))) {
                                                cbPSTone4.setChecked(true);
                                            }
                                        }
                                    }

                                    //Set Disability
                                    if (matrimonyCriteriaMain.getDisability() != null) {
                                        String disability = matrimonyCriteriaMain.getDisability();
                                        if (disability.equalsIgnoreCase(Constants.IS_MATTER)) {
                                            disabilityName = getString(R.string.profile_disabilites_doesnt_metter);
                                            cbDisability1.setChecked(true);


                                        } else {
                                            disabilityName = getString(R.string.profile_disabilites_doesnt_incude);
                                            cbDisability2.setChecked(true);

                                        }
                                    }

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if ((requestType == Api.MainUrl + Api.ActionInsertMatrimonyCriteria) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            AppDialog.showAlertDialog(getActivity(), null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });

                        } else {
                            AppDialog.showAlertDialog(getActivity(), null, apiResponseMessage.getMessage(),
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
            Log.e("Matrimony Search Failure " + errorMessage);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(getActivity(), null, errorMessage,
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
