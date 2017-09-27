package com.js.jainsamaj.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.UserLogin;
import com.js.jainsamaj.models.UserProfileRequestMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener, OnApiCallListener {
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    SimpleDraweeView sdUpCover, sdUpImg, sdUpADImg,sdUPBanner;
    TextView tvUpPDName, tvUpPDAge,
    //Personal Details TexViews
    tvUpPDGender, tvUpPDDob, tvUpPDBGroup,
            tvUpPDBDonor, tvUpPDNPlace, tvUpPDGGFather,
            tvUpPDGFather, tvUpFHName, tvUpPDKShurksh,
            tvUpPDMediclaim, tvUpPDMobile, tvUpPDGotra, tvUpPDDisability,

    //Marriage Details TextViews
    tvUpMDEngDate, tvUpMDEngWith, tvUpMDMrgStatus,
            tvUpMDMrgWith, tvUpMDMrgAnni, tvUpMDFInLaw,
            tvUpMDCCityInLaw, tvUpMDNPlaceInLaw,
            lblUpMDEngDate, lblUpMDEngWith, lblUpMDMrgStatus,
            lblUpMDMrgWith, lblUpMDMrgAnni, lblUpMDFInLaw,
            lblUpMDCCityInLaw, lblUpMDNPlaceInLaw,

    //Education Details TextViews
    tvUpEDCSName, tvUpEDPYear,

    //Seminar Details TextViews
    tvUpSDSubName, tvUpSDSubDes, tvUpSDInsName,
            tvUpSDInsAddress, tvUpSDSMonth, tvUpSDSYear,
            tvUpSDEMonth, tvUpSDEYear,

    //Training Details TextViews
    tvUpTDSubName, tvUpTDSubDes, tvUpTDInsName,
            tvUpTDInsAddress, tvUpTDSMonth, tvUpTDSYear,
            tvUpTDEMonth, tvUpTDEYear,


    //Project Details TextViews
    tvUpPDPOcc, tvUpPDPName, tvUpPDPDes,
            tvUpPDPMonth, tvUpPDPYear, tvUpPDPUrl,
            tvUpPDPMember,

    //Achievement Details TextViews
    tvUpADTitle, tvUpADDes, tvUpADAuthority,
            tvUpADExpired, tvUpADCMonth, tvUpADCYear,
            tvUpADEMonth, tvUpADEYear;

    LinearLayout llUDisabilitytxt,
            llAddress, llUAddress, llUAddressEmpty
            ,llUpMDEngDate,llUpMDEngWith,llUpMDMrgStatus,llUpMDMrgWith,llUpMDMrgAnni,llUpMDFInLaw,llUpMDCCityInLaw,llUpMDNPlaceInLaw;
    //set Font
    Typeface tfRegular, tfBold;
    //For api call
    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    UserLogin userLogin;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        apiFunctions = new ApiFunctions(this, this);

        //For Set Font

        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);
        bindHere();
        clickHere();
        apiCallHere();
    }


    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        tbTitle.setText(R.string.user_profile_details_txt);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sdUpCover = (SimpleDraweeView) findViewById(R.id.sdUpCover);
        sdUpImg = (SimpleDraweeView) findViewById(R.id.sdUpImg);
        sdUpADImg = (SimpleDraweeView) findViewById(R.id.sdUpADImg);
        sdUPBanner = (SimpleDraweeView) findViewById(R.id.sdUPBanner);

        tvUpPDName = (TextView) findViewById(R.id.tvUpPDName);
        tvUpPDAge = (TextView) findViewById(R.id.tvUpPDAge);

        //Personal Details TexViews

        tvUpPDGender = (TextView) findViewById(R.id.tvUpPDGender);
        tvUpPDDob = (TextView) findViewById(R.id.tvUpPDDob);
        tvUpPDBGroup = (TextView) findViewById(R.id.tvUpPDBGroup);
        tvUpPDBDonor = (TextView) findViewById(R.id.tvUpPDBDonor);
        tvUpPDNPlace = (TextView) findViewById(R.id.tvUpPDNPlace);
        tvUpPDGGFather = (TextView) findViewById(R.id.tvUpPDGGFather);
        tvUpPDGFather = (TextView) findViewById(R.id.tvUpPDGFather);
        tvUpFHName = (TextView) findViewById(R.id.tvUpFHName);
        tvUpPDKShurksh = (TextView) findViewById(R.id.tvUpPDKShurksh);
        tvUpPDMediclaim = (TextView) findViewById(R.id.tvUpPDMediclaim);
        tvUpPDMobile = (TextView) findViewById(R.id.tvUpPDMobile);
        tvUpPDGotra = (TextView) findViewById(R.id.tvUpPDGotra);
        tvUpPDDisability = (TextView) findViewById(R.id.tvUpPDDisability);

        llUAddress = (LinearLayout) findViewById(R.id.llUAddress);
        llUAddressEmpty = (LinearLayout) findViewById(R.id.llUAddressEmpty);


        //Marriage Details TextViews
        llUpMDEngDate = (LinearLayout)findViewById(R.id.llUpMDEngDate);
        llUpMDEngWith = (LinearLayout)findViewById(R.id.llUpMDEngWith);

        llUpMDMrgStatus = (LinearLayout)findViewById(R.id.llUpMDMrgStatus);
        llUpMDMrgAnni = (LinearLayout)findViewById(R.id.llUpMDMrgAnni);
        llUpMDMrgWith = (LinearLayout)findViewById(R.id.llUpMDMrgWith);
        llUpMDCCityInLaw = (LinearLayout)findViewById(R.id.llUpMDCCityInLaw);
        llUpMDFInLaw = (LinearLayout)findViewById(R.id.llUpMDFInLaw);
        llUpMDNPlaceInLaw = (LinearLayout)findViewById(R.id.llUpMDNPlaceInLaw);

        tvUpMDEngDate = (TextView) findViewById(R.id.tvUpMDEngDate);
        tvUpMDEngWith = (TextView) findViewById(R.id.tvUpMDEngWith);
        tvUpMDMrgStatus = (TextView) findViewById(R.id.tvUpMDMrgStatus);
        tvUpMDMrgWith = (TextView) findViewById(R.id.tvUpMDMrgWith);
        tvUpMDMrgAnni = (TextView) findViewById(R.id.tvUpMDMrgAnni);
        tvUpMDFInLaw = (TextView) findViewById(R.id.tvUpMDFInLaw);
        tvUpMDCCityInLaw = (TextView) findViewById(R.id.tvUpMDCCityInLaw);
        tvUpMDNPlaceInLaw = (TextView) findViewById(R.id.tvUpMDNPlaceInLaw);

        lblUpMDEngDate = (TextView) findViewById(R.id.lblUpMDEngDate);
        lblUpMDEngWith = (TextView) findViewById(R.id.lblUpMDEngWith);
//        lblUpMDMrgStatus = (TextView) findViewById(R.id.lblUpMDMrgStatus);
        lblUpMDMrgWith = (TextView) findViewById(R.id.lblUpMDMrgWith);
        lblUpMDMrgAnni = (TextView) findViewById(R.id.lblUpMDMrgAnni);
        lblUpMDFInLaw = (TextView) findViewById(R.id.lblUpMDFInLaw);
        lblUpMDCCityInLaw = (TextView) findViewById(R.id.lblUpMDCCityInLaw);
        lblUpMDNPlaceInLaw = (TextView) findViewById(R.id.lblUpMDNPlaceInLaw);


        //Education Details TextViews
        tvUpEDCSName = (TextView) findViewById(R.id.tvUpEDCSName);
        tvUpEDPYear = (TextView) findViewById(R.id.tvUpEDPYear);

        //Seminar Details TextViews
        tvUpSDSubName = (TextView) findViewById(R.id.tvUpSDSubName);
        tvUpSDSubDes = (TextView) findViewById(R.id.tvUpSDSubDes);
        tvUpSDInsName = (TextView) findViewById(R.id.tvUpSDInsName);
        tvUpSDInsAddress = (TextView) findViewById(R.id.tvUpSDInsAddress);
        tvUpSDSMonth = (TextView) findViewById(R.id.tvUpSDSMonth);
        tvUpSDSYear = (TextView) findViewById(R.id.tvUpSDSYear);
        tvUpSDEMonth = (TextView) findViewById(R.id.tvUpSDEMonth);
        tvUpSDEYear = (TextView) findViewById(R.id.tvUpSDEYear);


        //Training Details TextViews
        tvUpTDSubName = (TextView) findViewById(R.id.tvUpTDSubName);
        tvUpTDSubDes = (TextView) findViewById(R.id.tvUpTDSubDes);
        tvUpTDInsName = (TextView) findViewById(R.id.tvUpTDInsName);
        tvUpTDInsAddress = (TextView) findViewById(R.id.tvUpTDInsAddress);
        tvUpTDSMonth = (TextView) findViewById(R.id.tvUpTDSMonth);
        tvUpTDSYear = (TextView) findViewById(R.id.tvUpTDSYear);
        tvUpTDEMonth = (TextView) findViewById(R.id.tvUpTDEMonth);
        tvUpTDEYear = (TextView) findViewById(R.id.tvUpTDEYear);

        //Project Details TextViews
        tvUpPDPOcc = (TextView) findViewById(R.id.tvUpPDPOcc);
        tvUpPDPName = (TextView) findViewById(R.id.tvUpPDPName);
        tvUpPDPDes = (TextView) findViewById(R.id.tvUpPDPDes);
        tvUpPDPMonth = (TextView) findViewById(R.id.tvUpPDPMonth);
        tvUpPDPYear = (TextView) findViewById(R.id.tvUpPDPYear);
        tvUpPDPUrl = (TextView) findViewById(R.id.tvUpPDPUrl);
        tvUpPDPMember = (TextView) findViewById(R.id.tvUpPDPMember);

        //Achievement Details TextViews
        tvUpADTitle = (TextView) findViewById(R.id.tvUpADTitle);
        tvUpADDes = (TextView) findViewById(R.id.tvUpADDes);
        tvUpADAuthority = (TextView) findViewById(R.id.tvUpADAuthority);
        tvUpADExpired = (TextView) findViewById(R.id.tvUpADExpired);
        tvUpADCMonth = (TextView) findViewById(R.id.tvUpADCMonth);
        tvUpADCYear = (TextView) findViewById(R.id.tvUpADCYear);
        tvUpADEMonth = (TextView) findViewById(R.id.tvUpADEMonth);
        tvUpADEYear = (TextView) findViewById(R.id.tvUpADEYear);

        //Font Setting Here
        tvUpPDName.setTypeface(tfBold);
        tvUpPDAge.setTypeface(tfRegular);

        //Personal Details TexViews
        tvUpPDGender.setTypeface(tfRegular);
        tvUpPDDob.setTypeface(tfRegular);
        tvUpPDBGroup.setTypeface(tfRegular);
        tvUpPDBDonor.setTypeface(tfRegular);
        tvUpPDNPlace.setTypeface(tfRegular);
        tvUpPDGGFather.setTypeface(tfRegular);
        tvUpPDGFather.setTypeface(tfRegular);
        tvUpFHName.setTypeface(tfRegular);
        tvUpPDKShurksh.setTypeface(tfRegular);
        tvUpPDMediclaim.setTypeface(tfRegular);
        tvUpPDMobile.setTypeface(tfRegular);
        tvUpPDGotra.setTypeface(tfRegular);
        tvUpPDDisability.setTypeface(tfRegular);

        //Marriage Details TextViews
        tvUpMDEngDate.setTypeface(tfRegular);
        tvUpMDEngWith.setTypeface(tfRegular);
        tvUpMDMrgStatus.setTypeface(tfRegular);
        tvUpMDMrgWith.setTypeface(tfRegular);
        tvUpMDMrgAnni.setTypeface(tfRegular);
        tvUpMDFInLaw.setTypeface(tfRegular);
        tvUpMDCCityInLaw.setTypeface(tfRegular);
        tvUpMDNPlaceInLaw.setTypeface(tfRegular);

        //Education Details TextViews
        tvUpEDCSName.setTypeface(tfRegular);
        tvUpEDPYear.setTypeface(tfRegular);

        //Seminar Details TextViews
        tvUpSDSubName.setTypeface(tfRegular);
        tvUpSDSubDes.setTypeface(tfRegular);
        tvUpSDInsName.setTypeface(tfRegular);
        tvUpSDInsAddress.setTypeface(tfRegular);
        tvUpSDSMonth.setTypeface(tfRegular);
        tvUpSDSYear.setTypeface(tfRegular);
        tvUpSDEMonth.setTypeface(tfRegular);
        tvUpSDEYear.setTypeface(tfRegular);

        //Training Details TextViews
        tvUpTDSubName.setTypeface(tfRegular);
        tvUpTDSubDes.setTypeface(tfRegular);
        tvUpTDInsName.setTypeface(tfRegular);
        tvUpTDInsAddress.setTypeface(tfRegular);
        tvUpTDSMonth.setTypeface(tfRegular);
        tvUpTDSYear.setTypeface(tfRegular);
        tvUpTDEMonth.setTypeface(tfRegular);
        tvUpTDEYear.setTypeface(tfRegular);

        //Project Details TextViews
        tvUpPDPOcc.setTypeface(tfRegular);
        tvUpPDPName.setTypeface(tfRegular);
        tvUpPDPDes.setTypeface(tfRegular);
        tvUpPDPMonth.setTypeface(tfRegular);
        tvUpPDPYear.setTypeface(tfRegular);
        tvUpPDPUrl.setTypeface(tfRegular);
        tvUpPDPMember.setTypeface(tfRegular);

        //Achievement Details TextViews
        tvUpADTitle.setTypeface(tfBold);
        tvUpADDes.setTypeface(tfRegular);
        tvUpADAuthority.setTypeface(tfRegular);
        tvUpADExpired.setTypeface(tfRegular);
        tvUpADCMonth.setTypeface(tfRegular);
        tvUpADCYear.setTypeface(tfRegular);
        tvUpADEMonth.setTypeface(tfRegular);
        tvUpADEYear.setTypeface(tfRegular);

        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(this);
            imagesLinkMains = new ArrayList<>();
            randomGenerator = new Random();

            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + Constants.TABLE_ATTACHMENT_LINK + "", null);

            if (cursor.moveToFirst()) {
                do {
                    imagesLinkMain = new ImagesLinkMain(cursor.getString(cursor.getColumnIndex(Constants.ATTACHMENT_TABLE_URL)));
                    imagesLinkMains.add(imagesLinkMain);

                } while (cursor.moveToNext());
            }
            cursor.close();

            //Shuffle array list
            Collections.shuffle(imagesLinkMains);
            String ImageLink = String.valueOf(imagesLinkMains.get(0).getImgLink());

            //change as per backend dev. change BASE64 TO URL 24-05-2017
//            byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //sdAdvertisement.setImageBitmap(decodedByte);


            //set Full image here
            Uri advURL = Uri.parse(ImageLink);
            sdUPBanner.setImageURI(advURL);
            //End database(Attachment Table)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);

    }

    private void apiCallHere() {
        if (Global.isNetworkAvailable(UserProfileActivity.this)) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            if (loginMain.getUsername() != null) {
                AppDialog.showProgressDialog(UserProfileActivity.this);
                UserProfileRequestMain userProfileRequestMain = new UserProfileRequestMain(loginMain.getUsername());
                apiFunctions.getUserProfile(Api.MainUrl + Api.ActionGetUserProfile, userProfileRequestMain);
            }
        } else {
            AppDialog.noNetworkDialog(UserProfileActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

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
        Log.e("Get UserM Profile Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);

                if ((requestType == Api.MainUrl + Api.ActionGetUserProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                    loginMain = gson.fromJson(getFirst.toString(), LoginMain.class);
                } else {

                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionGetUserProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            try {

                                if (loginMain != null) {

                                    sdUpImg.setImageURI(Uri.parse(loginMain.getUserDetails().getUserProfileImageLink()));
                                    //change after Parameter is come from api
                                    sdUpCover.setImageURI(Uri.parse(loginMain.getUserDetails().getUserCoverImageLink()));

                                    //Personal Details TexViews
                                    if (loginMain.getUserDetails() != null) {
                                        tvUpPDName.setText(loginMain.getUserDetails().getName() + " " + loginMain.getUserDetails().getSurname());
                                        String tempAge = loginMain.getUserDetails().getDateofBirth();
                                        String[] separated = tempAge.split("-");
                                        if (separated != null) {
                                            int year = Integer.parseInt(separated[0]);
                                            int month = Integer.parseInt(separated[1]);
                                            int day = Integer.parseInt(separated[2]);

                                            tvUpPDAge.setText(getAge(year, month, day));
                                        }


                                        tvUpPDGender.setText(loginMain.getUserDetails().getGender());
                                        tvUpPDDob.setText(loginMain.getUserDetails().getDateofBirth());
                                        tvUpPDBGroup.setText(loginMain.getUserDetails().getBloodGroup());
                                        tvUpPDBDonor.setText(loginMain.getUserDetails().getBloodDonor());
                                        tvUpPDNPlace.setText(loginMain.getUserDetails().getNativePlace());
                                        tvUpPDGGFather.setText(loginMain.getUserDetails().getGreatGrandFatherName());
                                        tvUpPDGFather.setText(loginMain.getUserDetails().getGrandFatherName());
                                        tvUpFHName.setText(loginMain.getUserDetails().getFatherOrhusbandName());
                                        tvUpPDKShurksh.setText(loginMain.getUserDetails().getKutumbSuraksha());
                                        tvUpPDMediclaim.setText(loginMain.getUserDetails().getMediClaim());
                                        tvUpPDMobile.setText(loginMain.getUserDetails().getMobileNumber());
                                        tvUpPDGotra.setText(loginMain.getUserDetails().getGotraName());
                                        if (loginMain.getUserDetails().getDisability().equalsIgnoreCase("true")) {
                                            tvUpPDDisability.setText(loginMain.getUserDetails().getDisability());
                                            llUDisabilitytxt.setVisibility(View.VISIBLE);
                                        } else {
                                            tvUpPDDisability.setText(loginMain.getUserDetails().getDisability());
                                        }
                                        tvUpPDDisability.setText(loginMain.getUserDetails().getDisability());
                                    }
                                    //Marriage Details TextViews
                                    if (loginMain.getMarriageDetails() != null) {

                                        tvUpMDMrgStatus.setText(loginMain.getMarriageDetails().getMaritalstatus());


                                        if (loginMain.getMarriageDetails().getMaritalstatus().equalsIgnoreCase("Married")){
                                            llUpMDEngDate.setVisibility(View.GONE);
                                            llUpMDEngWith.setVisibility(View.GONE);


                                            tvUpMDMrgWith.setText(loginMain.getMarriageDetails().getMarriedWith());
                                            tvUpMDMrgAnni.setText(loginMain.getMarriageDetails().getAnniversaryDate());

                                        }else if (loginMain.getMarriageDetails().getMaritalstatus().equalsIgnoreCase("Engaged")){
                                            llUpMDMrgWith.setVisibility(View.GONE);
                                            llUpMDMrgAnni.setVisibility(View.GONE);


                                            tvUpMDEngDate.setText(loginMain.getMarriageDetails().getEngagementDate());
                                            tvUpMDEngWith.setText(loginMain.getMarriageDetails().getEngagedWith());

                                        }else{
                                            llUpMDEngDate.setVisibility(View.GONE);
                                            llUpMDEngWith.setVisibility(View.GONE);

                                            llUpMDMrgWith.setVisibility(View.GONE);
                                            llUpMDMrgAnni.setVisibility(View.GONE);


                                            llUpMDFInLaw.setVisibility(View.GONE);
                                            llUpMDCCityInLaw.setVisibility(View.GONE);
                                            llUpMDNPlaceInLaw.setVisibility(View.GONE);


                                        }

                                        tvUpMDFInLaw.setText(loginMain.getMarriageDetails().getFatherInLaw());
                                        tvUpMDCCityInLaw.setText(loginMain.getMarriageDetails().getCurrentCityInLaw());
                                        tvUpMDNPlaceInLaw.setText(loginMain.getMarriageDetails().getNativePlaceInLaw());
                                    }
                                    //Education Details TextViews
                                    if (loginMain.getEducationalDetails() != null) {
                                        tvUpEDCSName.setText(loginMain.getEducationalDetails().getSchoolCollege());
                                        tvUpEDPYear.setText(loginMain.getEducationalDetails().getPassingYear());
                                    }

                                    //Seminar Details TextViews
                                    if (loginMain.getSeminarDetails() != null) {
                                        tvUpSDSubName.setText(loginMain.getSeminarDetails().getInstituteName());
                                        tvUpSDSubDes.setText(loginMain.getSeminarDetails().getDescription());
                                        tvUpSDInsName.setText(loginMain.getSeminarDetails().getInstituteName());
                                        tvUpSDInsAddress.setText(loginMain.getSeminarDetails().getInstituteAddress());
                                        tvUpSDSMonth.setText(loginMain.getSeminarDetails().getSeminarStartMonth());
                                        tvUpSDSYear.setText(loginMain.getSeminarDetails().getSeminarStartYear());
                                        tvUpSDEMonth.setText(loginMain.getSeminarDetails().getSeminarEndMonth());
                                        tvUpSDEYear.setText(loginMain.getSeminarDetails().getSeminarEndYear());
                                    }

                                    //Training Details TextViews
                                    if (loginMain.getTrainingDetails() != null) {
                                        tvUpTDSubName.setText(loginMain.getTrainingDetails().getInstituteName());
                                        tvUpTDSubDes.setText(loginMain.getTrainingDetails().getDescription());
                                        tvUpTDInsName.setText(loginMain.getTrainingDetails().getInstituteName());
                                        tvUpTDInsAddress.setText(loginMain.getTrainingDetails().getInstituteAddress());
                                        tvUpTDSMonth.setText(loginMain.getTrainingDetails().getTrainingStartMonth());
                                        tvUpTDSYear.setText(loginMain.getTrainingDetails().getTrainingStartYear());
                                        tvUpTDEMonth.setText(loginMain.getTrainingDetails().getTrainingEndMonth());
                                        tvUpTDEYear.setText(loginMain.getTrainingDetails().getTrainingEndYear());
                                    }
                                    //Project Details TextViews
                                    if (loginMain.getProjectDetails() != null) {
                                        tvUpPDPOcc.setText(loginMain.getProjectDetails().getOccupation());
                                        tvUpPDPName.setText(loginMain.getProjectDetails().getProjectName());
                                        tvUpPDPDes.setText(loginMain.getProjectDetails().getDescription());
                                        tvUpPDPMonth.setText(loginMain.getProjectDetails().getMonth());
                                        tvUpPDPYear.setText(loginMain.getProjectDetails().getYear());
                                        tvUpPDPUrl.setText(loginMain.getProjectDetails().getProjectURL());
                                        tvUpPDPMember.setText(loginMain.getProjectDetails().getTeamMember());
                                    }

                                    //Achievement Details TextViews
                                    if (loginMain.getAchievements() != null) {
                                        tvUpADTitle.setText(loginMain.getAchievements().getAchievementTitle());
                                        tvUpADDes.setText(loginMain.getAchievements().getDescription());
                                        tvUpADAuthority.setText(loginMain.getAchievements().getAuthority());
                                        tvUpADExpired.setText(loginMain.getAchievements().getExpired());
                                        tvUpADCMonth.setText(loginMain.getAchievements().getCertificationMonthDetails());
                                        tvUpADCYear.setText(loginMain.getAchievements().getCertificationYear());
                                        tvUpADEMonth.setText(loginMain.getAchievements().getExpirationMonthDetails());
                                        tvUpADEYear.setText(loginMain.getAchievements().getExpirationYear());

                                        sdUpADImg.setImageURI(Uri.parse(loginMain.getAchievements().getAchievementPhotoLink()));
                                    }

                                    //For Address
                                    if (loginMain.getAddress().size() > 0) {
                                        int addressSize = loginMain.getAddress().size();
                                        for (int i = 0; i < addressSize; i++) {
                                            llAddress = (LinearLayout) View.inflate(UserProfileActivity.this, R.layout.profile_address_row, null);
                                            ((TextView) llAddress.findViewById(R.id.tvMAddresstxt)).setText(String.format(getResources().getString(R.string.phone_book_details_address) + " " + (i + 1)));
                                            ((TextView) llAddress.findViewById(R.id.tvMAddress)).setText(loginMain.getAddress().get(i).getFullAddress());

                                            final int finalI = i;

                                            llAddress.findViewById(R.id.ivMAddressMap).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    String addLat = loginMain.getAddress().get(finalI).getLat(), addLang = loginMain.getAddress().get(finalI).getLang();
                                                    if (!TextUtils.isEmpty(addLat) && !TextUtils.isEmpty(addLang)) {//22.9894468,72.517316"
                                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                                /*Uri.parse("http://maps.google.com/maps?saddr=" + CurrentLatStr + "," + CurrentLangStr + "&daddr=" + addLat + "," + addLang));*/
                                                                Uri.parse("http://maps.google.com/maps?q=" + addLat + "," + addLang));
                                                        startActivity(intent);
                                                    } else {

                                                        AppDialog.showAlertDialog(UserProfileActivity.this, null, getString(R.string.error_lat_long_not_found),
                                                                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                    }
                                                                });
                                                    }

                                                }
                                            });


                                            llUAddress.addView(llAddress);
                                        }

                                    } else {
                                        llUAddressEmpty.setVisibility(View.VISIBLE);
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            AppDialog.showAlertDialog(UserProfileActivity.this, null, apiResponseMessage.getMessage(),
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
            Log.e("Get User Profile Failure" + errorMessage);
            AppDialog.dismissProgressDialog();

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(UserProfileActivity.this, null, errorMessage,
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

    //Getting Age From DOB
    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

}
