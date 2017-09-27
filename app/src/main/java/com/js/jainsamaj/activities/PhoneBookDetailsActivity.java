package com.js.jainsamaj.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.js.jainsamaj.models.Phonebook.Request.PhoneBookDetailsRequestMain;
import com.js.jainsamaj.models.Phonebook.Response.PhoneboookDetails.FamilyMembersItem;
import com.js.jainsamaj.models.Phonebook.Response.PhoneboookDetails.PhonebookDetailResponseMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.js.jainsamaj.R.id.tvMobileNo;
import static com.js.jainsamaj.R.id.tvMobiletxt;

public class    PhoneBookDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnApiCallListener, LocationListener {
    /**
     * Set positive click listener for dialog
     */
    public DialogInterface.OnClickListener onPositiveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * Set Negative click listener for dialog
     */
    public DialogInterface.OnClickListener onNegativeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            dialogInterface.dismiss();
        }
    };
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;
    SimpleDraweeView sdUserProfile, sdUserProfilebanner, sdPBDetailsBanner;
    TextView tvPBDName;
    ImageView ivPBDCAll, ivPBDSMS, ivPBDEmail;
    LinearLayout llMobileEmpty, llEmailEmpty, llAddressEmpty, llFamilyEmpty;
    LinearLayout llMobileNumber, llEmailAddress, llFamilyMember, llFamilyMemberMain, llAddress;
    LinearLayout llMobileLayout, llEmailLayout, llFamilyLayout, llAddressLayout;
    Bundle bDetails;
    int PhoneBookUserId;
    String MemberName;
    boolean MemberData;
    ApiFunctions apiFunctions;
    Gson gson = new Gson();
    ApiResponseMessage apiResponseMessage;
    PhonebookDetailResponseMain phonebookDetailResponseMain;
    String pEmail, pMobile;
    LocationManager locationManager;
    String provider;
    String CurrentLatStr, CurrentLangStr;
    //set Font
    Typeface tfRegular, tfBold;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_book_details);


        bDetails = new Bundle(getIntent().getExtras());
        PhoneBookUserId = (int) bDetails.getSerializable("PhoneBookUserId");
        MemberName = bDetails.getString("MemberName");
        MemberData = bDetails.getBoolean("MemberData");
        apiFunctions = new ApiFunctions(this, this);

        bindHere();

        clickHere();

        getPhoneBookDetails();

        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {
                Location location = locationManager.getLastKnownLocation(provider);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(provider, 20000, 1, this);

                if (location != null)
                    onLocationChanged(location);
                else
                    Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getBaseContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        if (MemberData) {

            tbTitle.setText(MemberName);
        } else {
            tbTitle.setText(getString(R.string.phone_book_details_title_txt));
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sdUserProfile = (SimpleDraweeView) findViewById(R.id.sdUserProfile);
        sdUserProfilebanner = (SimpleDraweeView) findViewById(R.id.sdUserProfilebanner);
        sdPBDetailsBanner = (SimpleDraweeView) findViewById(R.id.sdPBDetailsBanner);
        tvPBDName = (TextView) findViewById(R.id.tvPBDName);

        ivPBDCAll = (ImageView) findViewById(R.id.ivPBDCAll);
        ivPBDSMS = (ImageView) findViewById(R.id.ivPBDSMS);
        ivPBDEmail = (ImageView) findViewById(R.id.ivPBDEmail);

        llMobileNumber = (LinearLayout) findViewById(R.id.llMobileNumber);
        llEmailAddress = (LinearLayout) findViewById(R.id.llEmailAddress);
        llFamilyMember = (LinearLayout) findViewById(R.id.llFamilyMember);
        llFamilyMemberMain = (LinearLayout) findViewById(R.id.llFamilyMemberMain);
        llAddress = (LinearLayout) findViewById(R.id.llAddress);

        llMobileEmpty = (LinearLayout) findViewById(R.id.llMobileEmpty);
        llEmailEmpty = (LinearLayout) findViewById(R.id.llEmailEmpty);
        llAddressEmpty = (LinearLayout) findViewById(R.id.llAddressEmpty);
        llFamilyEmpty = (LinearLayout) findViewById(R.id.llFamilyEmpty);

        //For Set Font
        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);

        tvPBDName.setTypeface(tfRegular);


    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);
        ivPBDCAll.setOnClickListener(this);
        ivPBDSMS.setOnClickListener(this);
        ivPBDEmail.setOnClickListener(this);
        sdPBDetailsBanner.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tbIvBack:
                finish();
                break;
            case R.id.ivPBDCAll:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + pMobile));
                    if (ActivityCompat.checkSelfPermission(PhoneBookDetailsActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.ivPBDSMS:
                try {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setData(Uri.parse("sms:" + pMobile));
                    startActivity(sendIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.ivPBDEmail:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{pEmail});
                    /*i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                    i.putExtra(Intent.EXTRA_TEXT, "body of email");*/
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sdPBDetailsBanner:

                if (companyUrl != null) {
                    Intent iCompanyUrl = new Intent(PhoneBookDetailsActivity.this, WebsiteActivity.class);
                    iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                    startActivity(iCompanyUrl);
                }

                break;

            default:
                break;
        }
    }

    //Get Current Location
    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(getApplicationContext(), location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        Log.e("lat long" + location.getLatitude() + "," + location.getLongitude());
        CurrentLatStr = String.valueOf(location.getLatitude());
        CurrentLangStr = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //End Current Location
    private void getPhoneBookDetails() {
        if (Global.isNetworkAvailable(PhoneBookDetailsActivity.this)) {
            if (PhoneBookUserId != -1) {
                AppDialog.showProgressDialog(PhoneBookDetailsActivity.this);
                String id = String.valueOf(PhoneBookUserId);
                PhoneBookDetailsRequestMain phoneBookDetailsMain = new PhoneBookDetailsRequestMain(id);
                apiFunctions.getPhoneBookDetails(Api.MainUrl + Api.ActionGetPhoneBookDetails, phoneBookDetailsMain);
            }
        } else {
            AppDialog.noNetworkDialog(PhoneBookDetailsActivity.this, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

    }

    @Override
    public void onSuccess(final int responseCode, String responseString, final String requestType) {
        AppDialog.dismissProgressDialog();
        Log.e("Phonebook Details Success" + responseString);
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionGetPhoneBookDetails) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                    phonebookDetailResponseMain = gson.fromJson(getFirst.toString(), PhonebookDetailResponseMain.class);
                } else {

                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionGetPhoneBookDetails) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (phonebookDetailResponseMain != null) {
                                sdUserProfile.setImageURI(Uri.parse(phonebookDetailResponseMain.getUserImage()));
                                // sdUserProfilebanner.setImageURI(Uri.parse(phonebookDetailResponseMain.getCoverImage()));
                                tvPBDName.setText(phonebookDetailResponseMain.getName());

                                try {
                                    //For Mobile Number
                                    if (phonebookDetailResponseMain.getMobileNumber().size() > 0) {
                                        int mobileNumberSize = phonebookDetailResponseMain.getMobileNumber().size();
                                        if (mobileNumberSize > 0) {
                                            for (int i = 0; i < mobileNumberSize; i++) {
                                                llMobileLayout = (LinearLayout) View.inflate(PhoneBookDetailsActivity.this, R.layout.mobile_number_row, null);

                                                pMobile = phonebookDetailResponseMain.getMobileNumber().get(0);
                                                ((TextView) llMobileLayout.findViewById(tvMobiletxt)).setText(String.format(getResources().getString(R.string.phone_book_details_mobile_txt) + " " + (i + 1)));
                                                ((TextView) llMobileLayout.findViewById(tvMobileNo)).setText(phonebookDetailResponseMain.getMobileNumber().get(i));
                                                llMobileNumber.addView(llMobileLayout);
                                            }
                                        }
                                    } else {
                                        llMobileEmpty.setVisibility(View.VISIBLE);
                                    }
                                    //For Email Address
                                    if (phonebookDetailResponseMain.getEmailAddress().size() > 0) {
                                        int emailAddressSize = phonebookDetailResponseMain.getEmailAddress().size();
                                        if (emailAddressSize > 0) {
                                            for (int i = 0; i < emailAddressSize; i++) {
                                                llEmailLayout = (LinearLayout) View.inflate(PhoneBookDetailsActivity.this, R.layout.email_address_row, null);
                                                pEmail = phonebookDetailResponseMain.getEmailAddress().get(0);
                                                ((TextView) llEmailLayout.findViewById(R.id.tvEmailtxt)).setText(String.format(getResources().getString(R.string.phone_book_details_email_txt) + " " + (i + 1)));
                                                ((TextView) llEmailLayout.findViewById(R.id.tvEmailAddress)).setText(phonebookDetailResponseMain.getEmailAddress().get(i));
                                                llEmailAddress.addView(llEmailLayout);
                                            }
                                        }

                                    } else {
                                        llEmailEmpty.setVisibility(View.VISIBLE);
                                    }
                                    //For Address
                                    if (phonebookDetailResponseMain.getAddress().size() > 0) {
                                        int addressSize = phonebookDetailResponseMain.getAddress().size();
                                        for (int i = 0; i < addressSize; i++) {
                                            llAddressLayout = (LinearLayout) View.inflate(PhoneBookDetailsActivity.this, R.layout.address_row, null);
                                            ((TextView) llAddressLayout.findViewById(R.id.tvAddresstxt)).setText(String.format(getResources().getString(R.string.phone_book_details_address) + " " + (i + 1)));
                                            ((TextView) llAddressLayout.findViewById(R.id.tvAddress)).setText(phonebookDetailResponseMain.getAddress().get(i).getAddress());

                                            final int finalI = i;

                                            llAddressLayout.findViewById(R.id.ivAddressMap).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    if (!TextUtils.isEmpty(CurrentLatStr) && !TextUtils.isEmpty(CurrentLangStr)) {//22.9894468,72.517316"
                                                        String addLat = phonebookDetailResponseMain.getAddress().get(finalI).getLat(), addLang = phonebookDetailResponseMain.getAddress().get(finalI).getLang();
                                                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                                /*Uri.parse("http://maps.google.com/maps?saddr=" + CurrentLatStr + "," + CurrentLangStr + "&daddr=" + addLat + "," + addLang));*/
                                                                Uri.parse("http://maps.google.com/maps?q=" + addLat + "," + addLang));
                                                        startActivity(intent);
                                                    } else {

                                                        AppDialog.showAlertDialog(PhoneBookDetailsActivity.this, null, getString(R.string.error_lat_long_not_found),
                                                                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                    }
                                                                });
                                                    }

                                                }
                                            });
                                            llAddress.addView(llAddressLayout);
                                        }

                                    } else {
                                        llAddressEmpty.setVisibility(View.VISIBLE);
                                    }

                                    //For Family Member
                                    if (phonebookDetailResponseMain.getFamilyMembers().size() > 0) {
                                        llFamilyMemberMain.setVisibility(View.VISIBLE);
                                        int id = 0;
                                        int familyMenber = phonebookDetailResponseMain.getFamilyMembers().size();
                                        if (familyMenber > 0) {
                                            for (int i = 0; i < familyMenber; i++) {
                                                llFamilyLayout = (LinearLayout) View.inflate(PhoneBookDetailsActivity.this, R.layout.family_member_row, null);
                                                Uri uri = Uri.parse(phonebookDetailResponseMain.getFamilyMembers().get(i).getUserImage());
                                                ((SimpleDraweeView) llFamilyLayout.findViewById(R.id.sdFamilyMember)).setImageURI(uri);
                                                ((TextView) llFamilyLayout.findViewById(R.id.tvFamilyMName)).setText(phonebookDetailResponseMain.getFamilyMembers().get(i).getName());
                                                ((TextView) llFamilyLayout.findViewById(R.id.tvFamilyMRelation)).setText(phonebookDetailResponseMain.getFamilyMembers().get(i).getRelationship());
                                                llFamilyLayout.setTag(phonebookDetailResponseMain.getFamilyMembers().get(i));
                                                final int finalI = i;
                                                llFamilyLayout.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        FamilyMembersItem familyMembersItem = (FamilyMembersItem) v.getTag();
                                                        if (phonebookDetailResponseMain.getFamilyMembers().get(finalI).isPrivate) {
                                                            Toast.makeText(PhoneBookDetailsActivity.this, "This UserM Is Private.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            try {
                                                                int memberId = familyMembersItem.getUserId();
                                                                Intent iDetails = new Intent(PhoneBookDetailsActivity.this, PhoneBookDetailsActivity.class);
                                                                iDetails.putExtra("PhoneBookUserId", memberId);
                                                                iDetails.putExtra("MemberName", familyMembersItem.getRelationship());
                                                                iDetails.putExtra("MemberData", true);
                                                                startActivity(iDetails);
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                    }
                                                });

                                                llFamilyMember.addView(llFamilyLayout);
                                            }

                                        }
                                    } else {
                                        llFamilyMemberMain.setVisibility(View.GONE);
                                        llFamilyEmpty.setVisibility(View.VISIBLE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            AppDialog.showAlertDialog(PhoneBookDetailsActivity.this, null, apiResponseMessage.getMessage(),
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
            Log.e("PhoneBook Details Failure" + errorMessage);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(PhoneBookDetailsActivity.this, null, errorMessage,
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

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(PhoneBookDetailsActivity.this);
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
//            sdPBDetailsBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL = Uri.parse(ImageLink);
            sdPBDetailsBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
