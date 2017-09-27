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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.CityLivingRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.CountryLivingRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.EducationFieldRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.EducationLevelRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.HeightRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.StateLivingRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.UpdateMatrimonyMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.UserRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.WorkAsIdResponse;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Common.WorkWithIdResponse;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Profile.MatrimonyMain;
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

public class ProfileFragment extends RootFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, OnApiCallListener {
    public static final int REQUEST_MARITAL_STATUS = 3333;
    public static final int REQUEST_HEIGHT = 3344;
    public static final int REQUEST_EDUCATION_LEVEL = 3355;
    public static final int REQUEST_EDUCATION_FIELD = 3366;
    public static final int REQUEST_WORK_WITH = 3377;
    public static final int REQUEST_WORK_AS = 3388;
    public static final int REQUEST_INCOME = 3399;
    private static final int REQUEST_TEMPLE_COUNTRY = 3321;
    private static final int REQUEST_TEMPLE_STATE = 3322;
    private static final int REQUEST_TEMPLE_CITY = 3324;
    EditText etPCountryIn, etPStateIn, etPCityIn,
            etPMStatus, etPELevel, etPEFields, etPWWith, etPWAs,
            etPIncome, etPHeight, etPWeight, etPDeit, etPHobbies, etPMobile,
            etPAboutSelf;
    RadioButton rbPBodyType1, rbPBodyType2, rbPBodyType3, rbPBodyType4,
            rbPSkinTone1, rbPSkinTone2, rbPSkinTone3, rbPSkinTone4,
            rbPDisability1, rbPDisability2;
    Button btnPSave;
    TextView tvPNote, tvPBodyType, tvPSkinTone, tvPAboutSelf, tvPDisability;
    SimpleDraweeView sdProfileBanner;
    //set Font
    Typeface tfRegular, tfBold;
    //Result
    ArrayList<SelectionListMain> selectionListMainArrayList = new ArrayList<>();
    int id, heightId, eduLevelId, eduFieldId, incomeId;
    String CountryId, StateId, CityId,
            WorkWithId, WorkAsId;
    //For Api
    ApiFunctions apiFunctions;
    Gson gson = new Gson();
    ApiResponseMessage apiResponseMessage;
    MatrimonyMain matrimonyMain;
    String bodyTypeName, skinToneName, disabilityName;

    UpdateMatrimonyMain updateMatrimonyMain;
    CountryLivingRequest countryLivingRequest;
    StateLivingRequest stateLivingRequest;
    CityLivingRequest cityLivingRequest;
    EducationLevelRequest educationLevelRequest;
    EducationFieldRequest educationFieldRequest;
    HeightRequest heightRequest;
    UserRequest userRequest;
    WorkAsIdResponse workAsIdResponse;
    WorkWithIdResponse workWithIdResponse;


    LoginMain loginMain;
    String LoginResponse;

    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;


    public ProfileFragment() {
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
                R.layout.fragment_profile, container, false);

        apiFunctions = new ApiFunctions(getActivity(), this);
        updateMatrimonyMain = new UpdateMatrimonyMain();

        etPCountryIn = (EditText) view.findViewById(R.id.etPCountryIn);
        etPStateIn = (EditText) view.findViewById(R.id.etPStateIn);
        etPCityIn = (EditText) view.findViewById(R.id.etPCityIn);
        etPMStatus = (EditText) view.findViewById(R.id.etPMStatus);
        etPELevel = (EditText) view.findViewById(R.id.etPELevel);
        etPEFields = (EditText) view.findViewById(R.id.etPEFields);
        etPWWith = (EditText) view.findViewById(R.id.etPWWith);
        etPWAs = (EditText) view.findViewById(R.id.etPWAs);

        etPIncome = (EditText) view.findViewById(R.id.etPIncome);
        etPHeight = (EditText) view.findViewById(R.id.etPHeight);
        etPWeight = (EditText) view.findViewById(R.id.etPWeight);
        etPDeit = (EditText) view.findViewById(R.id.etPDeit);
        etPHobbies = (EditText) view.findViewById(R.id.etPHobbies);
        etPMobile = (EditText) view.findViewById(R.id.etPMobile);
        etPAboutSelf = (EditText) view.findViewById(R.id.etPAboutSelf);
        tvPNote = (TextView) view.findViewById(R.id.tvPNote);
        tvPBodyType = (TextView) view.findViewById(R.id.tvPBodyType);
        tvPSkinTone = (TextView) view.findViewById(R.id.tvPSkinTone);
        tvPAboutSelf = (TextView) view.findViewById(R.id.tvPAboutSelf);
        tvPDisability = (TextView) view.findViewById(R.id.tvPDisability);

        //for setting cursor
        etPHobbies.setSelection(etPHobbies.getText().length());
        etPMobile.setSelection(etPMobile.getText().length());
        etPAboutSelf.setSelection(etPAboutSelf.getText().length());


        rbPBodyType1 = (RadioButton) view.findViewById(R.id.rbPBodyType1);
        rbPBodyType2 = (RadioButton) view.findViewById(R.id.rbPBodyType2);
        rbPBodyType3 = (RadioButton) view.findViewById(R.id.rbPBodyType3);
        rbPBodyType4 = (RadioButton) view.findViewById(R.id.rbPBodyType4);


        rbPSkinTone1 = (RadioButton) view.findViewById(R.id.rbPSkinTone1);
        rbPSkinTone2 = (RadioButton) view.findViewById(R.id.rbPSkinTone2);
        rbPSkinTone3 = (RadioButton) view.findViewById(R.id.rbPSkinTone3);
        rbPSkinTone4 = (RadioButton) view.findViewById(R.id.rbPSkinTone4);

        rbPDisability1 = (RadioButton) view.findViewById(R.id.rbPDisability1);
        rbPDisability2 = (RadioButton) view.findViewById(R.id.rbPDisability2);

        btnPSave = (Button) view.findViewById(R.id.btnPSave);

        //For Set Font
        tfRegular = Global.setRegularFont(getActivity());
        tfBold = Global.setBoldFont(getActivity());

        tvPNote.setTypeface(tfRegular);
        tvPBodyType.setTypeface(tfRegular);
        tvPSkinTone.setTypeface(tfRegular);
        tvPAboutSelf.setTypeface(tfRegular);
        tvPDisability.setTypeface(tfRegular);

        etPCountryIn.setTypeface(tfRegular);
        etPStateIn.setTypeface(tfRegular);
        etPCityIn.setTypeface(tfRegular);
        etPMStatus.setTypeface(tfRegular);
        etPELevel.setTypeface(tfRegular);
        etPEFields.setTypeface(tfRegular);
        etPWWith.setTypeface(tfRegular);
        etPWAs.setTypeface(tfRegular);
        etPIncome.setTypeface(tfRegular);
        etPHeight.setTypeface(tfRegular);
        etPWeight.setTypeface(tfRegular);
        etPDeit.setTypeface(tfRegular);
        etPHobbies.setTypeface(tfRegular);
        etPMobile.setTypeface(tfRegular);
        etPAboutSelf.setTypeface(tfRegular);


        rbPBodyType1.setTypeface(tfRegular);
        rbPBodyType2.setTypeface(tfRegular);
        rbPBodyType3.setTypeface(tfRegular);
        rbPBodyType4.setTypeface(tfRegular);

        rbPSkinTone1.setTypeface(tfRegular);
        rbPSkinTone2.setTypeface(tfRegular);
        rbPSkinTone3.setTypeface(tfRegular);
        rbPSkinTone4.setTypeface(tfRegular);

        rbPDisability1.setTypeface(tfRegular);
        rbPDisability2.setTypeface(tfRegular);

        btnPSave.setTypeface(tfBold);

        sdProfileBanner = (SimpleDraweeView) view.findViewById(R.id.sdProfileBanner);
        sdProfileBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyUrl != null) {
                    try {
                        Intent iCompanyUrl = new Intent(getActivity(), WebsiteActivity.class);
                        iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                        startActivity(iCompanyUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //Click Here
        etPCountryIn.setOnClickListener(this);
        etPStateIn.setOnClickListener(this);
        etPCityIn.setOnClickListener(this);

        etPMStatus.setOnClickListener(this);
        etPELevel.setOnClickListener(this);
        etPEFields.setOnClickListener(this);
        etPWWith.setOnClickListener(this);
        etPWAs.setOnClickListener(this);

        etPIncome.setOnClickListener(this);
        etPHeight.setOnClickListener(this);
        etPWeight.setOnClickListener(this);
        etPDeit.setOnClickListener(this);
        etPHobbies.setOnClickListener(this);
        etPMobile.setOnClickListener(this);
        etPAboutSelf.setOnClickListener(this);

        btnPSave.setOnClickListener(this);


        rbPBodyType1.setOnCheckedChangeListener(this);
        rbPBodyType2.setOnCheckedChangeListener(this);
        rbPBodyType3.setOnCheckedChangeListener(this);
        rbPBodyType4.setOnCheckedChangeListener(this);

        rbPSkinTone1.setOnCheckedChangeListener(this);
        rbPSkinTone2.setOnCheckedChangeListener(this);
        rbPSkinTone3.setOnCheckedChangeListener(this);
        rbPSkinTone4.setOnCheckedChangeListener(this);

        rbPDisability1.setOnCheckedChangeListener(this);
        rbPDisability2.setOnCheckedChangeListener(this);

        //Api call Here(getting profile)
        getProfileData();

        return view;
    }

    //getting Profile Data
    private void getProfileData() {
        if (Global.isNetworkAvailable(getActivity())) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            if (loginMain != null) {
                AppDialog.showProgressDialog(getActivity());
                GetMatrimony getMatrimony = new GetMatrimony(loginMain.getUsername());
                apiFunctions.getMatrimony(Api.MainUrl + Api.ActionGetMatrimonyProfile, getMatrimony);
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
        setTitle(getString(R.string.menu_user_profile));
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
//            sdProfileBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL = Uri.parse(ImageLink);
            sdProfileBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //**************************************radio button click**************************************
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            //********************************Radio button for body*********************************
            if (compoundButton.getId() == R.id.rbPBodyType1) {
                bodyTypeName = getString(R.string.profile_body_1);
                rbPBodyType1.setChecked(true);
                rbPBodyType2.setChecked(false);
                rbPBodyType3.setChecked(false);
                rbPBodyType4.setChecked(false);
            }
            if (compoundButton.getId() == R.id.rbPBodyType2) {
                bodyTypeName = getString(R.string.profile_body_2);
                rbPBodyType1.setChecked(false);
                rbPBodyType2.setChecked(true);
                rbPBodyType3.setChecked(false);
                rbPBodyType4.setChecked(false);
            }
            if (compoundButton.getId() == R.id.rbPBodyType3) {
                bodyTypeName = getString(R.string.profile_body_3);
                rbPBodyType1.setChecked(false);
                rbPBodyType2.setChecked(false);
                rbPBodyType3.setChecked(true);
                rbPBodyType4.setChecked(false);
            }
            if (compoundButton.getId() == R.id.rbPBodyType4) {
                bodyTypeName = getString(R.string.profile_body_4);
                rbPBodyType1.setChecked(false);
                rbPBodyType2.setChecked(false);
                rbPBodyType3.setChecked(false);
                rbPBodyType4.setChecked(true);
            }


            //********************************Radio button for skin*********************************
            if (compoundButton.getId() == R.id.rbPSkinTone1) {
                skinToneName = getString(R.string.profile_skin_1);
                rbPSkinTone1.setChecked(true);
                rbPSkinTone2.setChecked(false);
                rbPSkinTone3.setChecked(false);
                rbPSkinTone4.setChecked(false);
            }

            if (compoundButton.getId() == R.id.rbPSkinTone2) {
                skinToneName = getString(R.string.profile_skin_2);
                rbPSkinTone1.setChecked(false);
                rbPSkinTone2.setChecked(true);
                rbPSkinTone3.setChecked(false);
                rbPSkinTone4.setChecked(false);
            }
            if (compoundButton.getId() == R.id.rbPSkinTone3) {
                skinToneName = getString(R.string.profile_skin_3);
                rbPSkinTone1.setChecked(false);
                rbPSkinTone2.setChecked(false);
                rbPSkinTone3.setChecked(true);
                rbPSkinTone4.setChecked(false);
            }
            if (compoundButton.getId() == R.id.rbPSkinTone4) {
                skinToneName = getString(R.string.profile_skin_4);
                rbPSkinTone1.setChecked(false);
                rbPSkinTone2.setChecked(false);
                rbPSkinTone3.setChecked(false);
                rbPSkinTone4.setChecked(true);
            }
            //****************************Radio button for disability*******************************
            if (compoundButton.getId() == R.id.rbPDisability1) {

                disabilityName = getString(R.string.profile_disabilites_none_profile);
                rbPDisability1.setChecked(true);
                rbPDisability2.setChecked(false);

            }
            if (compoundButton.getId() == R.id.rbPDisability2) {
                disabilityName = getString(R.string.profile_disabilites_Profile);

                rbPDisability1.setChecked(false);
                rbPDisability2.setChecked(true);
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etPCountryIn:
                Intent iTCounty = new Intent(getActivity(), SelectionActivity.class);
                iTCounty.putExtra("isTCountry", true);
                startActivityForResult(iTCounty, REQUEST_TEMPLE_COUNTRY);
                break;
            case R.id.etPStateIn:
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
            case R.id.etPCityIn:
                if (StateId != null) {
                    int id = Integer.parseInt(StateId);
                    int districtCityId = 0;
                    Intent iTState = new Intent(getActivity(), SelectionActivity.class);
                    iTState.putExtra("isProfileCity", true);
                    iTState.putExtra("district", districtCityId);
                    iTState.putExtra("state", id);
                    startActivityForResult(iTState, REQUEST_TEMPLE_CITY);
                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.add_temple_district_error),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
                break;
            case R.id.etPMStatus:
                Intent iMarital = new Intent(getActivity(), ListActivity.class);
                iMarital.putExtra("isMarital", true);
                startActivityForResult(iMarital, REQUEST_MARITAL_STATUS);
                break;
            case R.id.etPELevel:
                Intent iEduLevel = new Intent(getActivity(), SelectionActivity.class);
                iEduLevel.putExtra("isEduLevel", true);
                startActivityForResult(iEduLevel, REQUEST_EDUCATION_LEVEL);
                break;
            case R.id.etPEFields:
                Intent iEduField = new Intent(getActivity(), SelectionActivity.class);
                iEduField.putExtra("isEduField", true);
                startActivityForResult(iEduField, REQUEST_EDUCATION_FIELD);
                break;

            //add because (Softvan developers said)17-05-2017
            case R.id.etPWWith:
                Intent iWorkWith = new Intent(getActivity(), SelectionActivity.class);
                iWorkWith.putExtra("isWorkWith", true);
                startActivityForResult(iWorkWith, REQUEST_WORK_WITH);
                break;
            case R.id.etPWAs:
                if (WorkWithId != null) {
                    int id = Integer.parseInt(WorkWithId);
                    Intent iWorkAs = new Intent(getActivity(), SelectionActivity.class);
                    iWorkAs.putExtra("isWorkAs", true);
                    iWorkAs.putExtra("workWithId", id);
                    startActivityForResult(iWorkAs, REQUEST_WORK_AS);
                }
                break;
            case R.id.etPIncome:
                Intent isAnnualIncome = new Intent(getActivity(), ListActivity.class);
                isAnnualIncome.putExtra("isAnnualIncome", true);
                startActivityForResult(isAnnualIncome, REQUEST_INCOME);
                break;
            case R.id.etPHeight:
                Intent iHeight = new Intent(getActivity(), SelectionActivity.class);
                iHeight.putExtra("isHeight", true);
                startActivityForResult(iHeight, REQUEST_HEIGHT);
                break;
            case R.id.btnPSave:
                if (Global.isNetworkAvailable(getActivity())) {


                    int userId = id;
                    String userEmail = null;
                    if (loginMain != null) {
                        userEmail = loginMain.getUsername();
                        userRequest = new UserRequest(loginMain.getId());
                    }

                    String countryStr = etPCountryIn.getText().toString();
                    int countryLid = updateMatrimonyMain.getCountryLiving().getId();

                    String stateStr = etPStateIn.getText().toString();
                    int stateLid = updateMatrimonyMain.getStateLiving().getId();

                    String cityStr = etPCityIn.getText().toString();
                    int cityLid = updateMatrimonyMain.getCityLiving().getId();

                    String maritalStr = etPMStatus.getText().toString();

                    String workAsStr = etPWAs.getText().toString();
                    String workWithStr = etPWWith.getText().toString();

                    String incomeLong = etPIncome.getText().toString();


                    String weightStr = etPWeight.getText().toString();

                    String bodyStr = bodyTypeName;
                    String skinStr = skinToneName;
                    String hobbiesStr = etPHobbies.getText().toString();
                    String mobileNo = etPMobile.getText().toString();
                    String aboutStr = etPAboutSelf.getText().toString();


                                        /*public UpdateMatrimonyMain(int id, String createdUser, CountryLivingRequest
                    countryLiving,
                            StateLivingRequest stateLiving, CityLivingRequest cityLiving, String maritalStatus,
                            EducationLevelRequest educationLevel, EducationFieldRequest educationField, String workAs,
                            String workWith, int annualIncome, String weight, HeightRequest height, String bodyType,
                            String skinTone, String hobbies, String mobileNumber, String aboutMyself, String disability) {*/


//                    if (!TextUtils.isEmpty(countryStr) && !TextUtils.isEmpty(stateStr) && !TextUtils.isEmpty(cityStr) &&
//                            !TextUtils.isEmpty(maritalStr) && !TextUtils.isEmpty(workAsStr) && !TextUtils.isEmpty(workWithStr) && !TextUtils.isEmpty(incomeLong) &&
//                            !TextUtils.isEmpty(weightStr) && !TextUtils.isEmpty(hobbiesStr) && !TextUtils.isEmpty(mobileNo) && !TextUtils.isEmpty(aboutStr)) {
                    updateMatrimonyMain = new UpdateMatrimonyMain(id, userEmail, userRequest, countryLivingRequest, stateLivingRequest, cityLivingRequest,
                            maritalStr, educationLevelRequest, educationFieldRequest, workAsIdResponse, workWithIdResponse, incomeId, heightRequest, weightStr,
                            bodyTypeName, skinToneName, hobbiesStr, mobileNo, aboutStr, disabilityName);

                    //CHANG:: disability Not Send AS per client talk
                    // disabilityName

                    AppDialog.showProgressDialog(getActivity());
                    apiFunctions.updateMatrimony(Api.MainUrl + Api.ActionUpdateMatrimonyProfile, updateMatrimonyMain);
//                    } else {
//                        AppDialog.showAlertDialog(getActivity(), null, getString(R.string.error_enter_add_profile),
//                                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.dismiss();
//                                    }
//                                });
//
//                    }
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

    /******************************
     * Activity Result
     ******************************/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            /******************************Result For Country**************************************/
            if (requestCode == REQUEST_TEMPLE_COUNTRY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCountryName();
                    id = selectionListMainArrayList.get(i).getId();
                    CountryId = String.valueOf(id);

                    countryLivingRequest = new CountryLivingRequest(id);
                    updateMatrimonyMain.setCountryLiving(countryLivingRequest);
                }
                etPCountryIn.setText(name);
            }
            /******************************Result For State****************************************/
            if (requestCode == REQUEST_TEMPLE_STATE && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getStateName();
                    id = selectionListMainArrayList.get(i).getId();
                    StateId = String.valueOf(id);

                    stateLivingRequest = new StateLivingRequest(id);
                    updateMatrimonyMain.setStateLiving(stateLivingRequest);

                }
                etPStateIn.setText(name);
            }
            /******************************Result For City*****************************************/
            if (requestCode == REQUEST_TEMPLE_CITY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCityName();
                    id = selectionListMainArrayList.get(i).getId();
                    CityId = String.valueOf(id);

                    cityLivingRequest = new CityLivingRequest(id);
                    updateMatrimonyMain.setCityLiving(cityLivingRequest);
                }
                etPCityIn.setText(name);
            }
            /******************************Result For Marital**************************************/
            if (requestCode == REQUEST_MARITAL_STATUS && resultCode == RESULT_OK) {
                String name = "";
                name = data.getStringExtra("ListData");
                etPMStatus.setText(name);
            }
            /******************************Result For Education Level*****************************************/
            if (requestCode == REQUEST_EDUCATION_LEVEL && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getEducationLevel();
                    id = selectionListMainArrayList.get(i).getId();
                    eduLevelId = id;

                    educationLevelRequest = new EducationLevelRequest(id);
                    updateMatrimonyMain.setEducationLevel(educationLevelRequest);

                }
                etPELevel.setText(name);
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

                    educationFieldRequest = new EducationFieldRequest(id);
                    updateMatrimonyMain.setEducationField(educationFieldRequest);

                }
                etPEFields.setText(name);
            }
            /******************************Result For Work With **************************************/
            if (requestCode == REQUEST_WORK_WITH && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getWorkWithCategory();
                    id = selectionListMainArrayList.get(i).getId();
                    workWithIdResponse = new WorkWithIdResponse(id);
                    updateMatrimonyMain.setWorkWith(workWithIdResponse);
                    WorkWithId = String.valueOf(id);
                }

                etPWWith.setText(name);
            }
            /******************************Result For Work As**************************************/
            if (requestCode == REQUEST_WORK_AS && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getWorkAs();
                    id = selectionListMainArrayList.get(i).getId();
                    workAsIdResponse = new WorkAsIdResponse(id);
                    updateMatrimonyMain.setWorkAs(workAsIdResponse);
                    WorkAsId = String.valueOf(id);
                }

                etPWAs.setText(name);
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

                etPIncome.setText(name);
            }
            /******************************Result For HeightRequest*****************************************/
            if (requestCode == REQUEST_HEIGHT && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                int id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getHeight();
                    id = selectionListMainArrayList.get(i).getId();
                    heightId = id;

                    heightRequest = new HeightRequest(id);
                    updateMatrimonyMain.setHeight(heightRequest);
                }
                etPHeight.setText(name);
            }
        }

    }

    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        Log.e("Matrimony Profile Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                //Get Matrimony Profile

                if ((requestType == Api.MainUrl + Api.ActionGetMatrimonyProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {


                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                    matrimonyMain = gson.fromJson(getFirst.toString(), MatrimonyMain.class);
                    if (getFirst.has("id")) {
                        id = (int) getFirst.get("id");
                    } else {
                        id = 0;
                    }

                }

                //Update Matrimony Profile
                else if ((requestType == Api.MainUrl + Api.ActionUpdateMatrimonyProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                } else {

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Get Matrimony Profile
                        if ((requestType == Api.MainUrl + Api.ActionGetMatrimonyProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            try {
                                if (matrimonyMain != null) {
                                    try {
                                        if (matrimonyMain.getCountryLiving() != null) {
                                            etPCountryIn.setText(matrimonyMain.getCountryLiving().getCountryName());
                                            countryLivingRequest = new CountryLivingRequest(matrimonyMain.getCountryLiving().getId());
                                            updateMatrimonyMain.setCountryLiving(countryLivingRequest);
                                        }
                                        if (matrimonyMain.getStateLiving() != null) {
                                            etPStateIn.setText(matrimonyMain.getStateLiving().getStateName());
                                            stateLivingRequest = new StateLivingRequest(matrimonyMain.getStateLiving().getId());
                                            updateMatrimonyMain.setStateLiving(stateLivingRequest);
                                        }
                                        if (matrimonyMain.getCityLiving() != null) {
                                            etPCityIn.setText(matrimonyMain.getCityLiving().getCityName());
                                            cityLivingRequest = new CityLivingRequest(matrimonyMain.getCityLiving().getId());
                                            updateMatrimonyMain.setCityLiving(cityLivingRequest);
                                        }
                                        etPMStatus.setText(matrimonyMain.getMaritalStatus());

                                        if (matrimonyMain.getEducationLevel() != null) {
                                            etPELevel.setText(matrimonyMain.getEducationLevel().getEducationLevel());
                                            educationLevelRequest = new EducationLevelRequest(matrimonyMain.getEducationLevel().getId());
                                            updateMatrimonyMain.setEducationLevel(educationLevelRequest);
                                        }
                                        if (matrimonyMain.getEducationField() != null) {
                                            etPEFields.setText(matrimonyMain.getEducationField().getEducationField());
                                            educationFieldRequest = new EducationFieldRequest(matrimonyMain.getEducationField().getId());
                                            updateMatrimonyMain.setEducationField(educationFieldRequest);
                                        }

//                                    etPIncome.setText(String.valueOf(matrimonyMain.getAnnualIncome()));
                                        if (matrimonyMain.getAnnualIncome() == 1) {
                                            etPIncome.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_1)));
                                            incomeId = 1;
                                        }
                                        if (matrimonyMain.getAnnualIncome() == 2) {
                                            etPIncome.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_2)));
                                            incomeId = 2;
                                        }
                                        if (matrimonyMain.getAnnualIncome() == 3) {
                                            etPIncome.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_3)));
                                            incomeId = 3;
                                        }
                                        if (matrimonyMain.getAnnualIncome() == 4) {
                                            etPIncome.setText(String.valueOf(getResources().getString(R.string.profile_search_education_and_professional_work_income_4)));
                                            incomeId = 4;
                                        }


                                        if (matrimonyMain.getHeight() != null) {
                                            etPHeight.setText(matrimonyMain.getHeight().getHeight());
                                            heightRequest = new HeightRequest(matrimonyMain.getHeight().getId());
                                            updateMatrimonyMain.setHeight(heightRequest);
                                        }
                                        if (matrimonyMain.getWorkWith() != null) {
                                            etPWWith.setText(matrimonyMain.getWorkWith().getWorkWithCategory());
                                            workWithIdResponse = new WorkWithIdResponse(matrimonyMain.getWorkWith().getId());
                                            updateMatrimonyMain.setWorkWith(workWithIdResponse);
                                        }
                                        if (matrimonyMain.getWorkAs() != null) {
                                            etPWAs.setText(matrimonyMain.getWorkAs().getWorkAs());
                                            workAsIdResponse = new WorkAsIdResponse(matrimonyMain.getWorkAs().getId());
                                            updateMatrimonyMain.setWorkAs(workAsIdResponse);
                                        }


                                        etPWeight.setText(matrimonyMain.getWeight());
                                        etPHobbies.setText(matrimonyMain.getHobbies());
                                        etPMobile.setText(matrimonyMain.getMobileNumber());
                                        etPAboutSelf.setText(matrimonyMain.getAboutMyself());

                                        if (matrimonyMain.getBodyType() != null) {
                                            //set Body Type
                                            if (matrimonyMain.getBodyType().equalsIgnoreCase(getString(R.string.profile_body_1))) {
                                                bodyTypeName = getString(R.string.profile_body_1);

                                                rbPBodyType1.setChecked(true);
                                                rbPBodyType2.setChecked(false);
                                                rbPBodyType3.setChecked(false);
                                                rbPBodyType4.setChecked(false);
                                            }
                                            if (matrimonyMain.getBodyType().equalsIgnoreCase(getString(R.string.profile_body_2))) {
                                                bodyTypeName = getString(R.string.profile_body_2);

                                                rbPBodyType1.setChecked(false);
                                                rbPBodyType2.setChecked(true);
                                                rbPBodyType3.setChecked(false);
                                                rbPBodyType4.setChecked(false);
                                            }
                                            if (matrimonyMain.getBodyType().equalsIgnoreCase(getString(R.string.profile_body_3))) {
                                                bodyTypeName = getString(R.string.profile_body_3);

                                                rbPBodyType1.setChecked(false);
                                                rbPBodyType2.setChecked(false);
                                                rbPBodyType3.setChecked(true);
                                                rbPBodyType4.setChecked(false);
                                            }
                                            if (matrimonyMain.getBodyType().equalsIgnoreCase(getString(R.string.profile_body_4))) {
                                                bodyTypeName = getString(R.string.profile_body_4);

                                                rbPBodyType1.setChecked(false);
                                                rbPBodyType2.setChecked(false);
                                                rbPBodyType3.setChecked(false);
                                                rbPBodyType4.setChecked(true);
                                            }
                                        }
                                        //set Skin Tone
                                        if (matrimonyMain.getSkinTone() != null) {
                                            if (matrimonyMain.getSkinTone().equalsIgnoreCase(getString(R.string.profile_skin_1))) {
                                                skinToneName = getString(R.string.profile_skin_1);

                                                rbPSkinTone1.setChecked(true);
                                                rbPSkinTone2.setChecked(false);
                                                rbPSkinTone3.setChecked(false);
                                                rbPSkinTone4.setChecked(false);
                                            }
                                            if (matrimonyMain.getSkinTone().equalsIgnoreCase(getString(R.string.profile_skin_2))) {
                                                skinToneName = getString(R.string.profile_skin_2);

                                                rbPSkinTone1.setChecked(false);
                                                rbPSkinTone2.setChecked(true);
                                                rbPSkinTone3.setChecked(false);
                                                rbPSkinTone4.setChecked(false);
                                            }
                                            if (matrimonyMain.getSkinTone().equalsIgnoreCase(getString(R.string.profile_skin_3))) {
                                                skinToneName = getString(R.string.profile_skin_3);

                                                rbPSkinTone1.setChecked(false);
                                                rbPSkinTone2.setChecked(false);
                                                rbPSkinTone3.setChecked(true);
                                                rbPSkinTone4.setChecked(false);
                                            }
                                            if (matrimonyMain.getSkinTone().equalsIgnoreCase(getString(R.string.profile_skin_4))) {
                                                skinToneName = getString(R.string.profile_skin_4);

                                                rbPSkinTone1.setChecked(false);
                                                rbPSkinTone2.setChecked(false);
                                                rbPSkinTone3.setChecked(false);
                                                rbPSkinTone4.setChecked(true);
                                            }
                                        }

                                        //set Disability
                                        if (matrimonyMain.getDisability() != null) {

                                            if (matrimonyMain.getDisability().equalsIgnoreCase(Constants.IS_NONE)) {
                                                disabilityName = getString(R.string.profile_disabilites_none_profile);

                                                rbPDisability1.setChecked(true);
                                                rbPDisability2.setChecked(false);
                                            }
                                            if (matrimonyMain.getDisability().equalsIgnoreCase(Constants.IS_PHYSICAL)) {

                                                disabilityName = getString(R.string.profile_disabilites_Profile);

                                                rbPDisability1.setChecked(false);
                                                rbPDisability2.setChecked(true);
                                            }
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if ((requestType == Api.MainUrl + Api.ActionUpdateMatrimonyProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
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
            Log.e("Matrimony Profile Failure " + errorMessage);
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

