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
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search.MatrimonyListMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.js.jainsamaj.R.id.sdAdvertisement;

public class MatrimonyListDetailsActivity extends AppCompatActivity {
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;
    SimpleDraweeView sdMpCover, sdMpImg, sdUPBanner;
    TextView
            tvUpPDName,
    //Personal Details
    tvMpPDGender,
            tvMpPDDob,
            tvMpPDBGroup,
            tvMpPDBDonor,
            tvMpPDNPlace,
            tvMpPDGGFather,
            tvMpPDGFather,
            tvMpFHName,
            tvMpPDKShurksh,
            tvMpPDMediclaim,
            tvMpPDMobile,
            tvMpPDGotra,
            tvMpPDDisability,
            tvMpPDDisabilityTxt,

    //Marriage Details
    tvMpMDEngDate,
            tvMpMDEngWith,
            tvMpMDMrgStatus,
            tvMpMDMrgWith,
            tvMpMDMrgAnni,
            tvMpMDFInLaw,
            tvMpMDCCityInLaw,
            tvMpMDNPlaceInLaw,
    //Education
    tvMpEDCSName,
            tvMpEDPYear;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;

    LinearLayout llDisabilitytxt, llAddress, llAddressEmpty, llAddressLayout;
    //set Font
    Typeface tfRegular, tfBold;

    Bundle bMdetails;
    MatrimonyListMain matrimonyListMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony_list_details);
        bMdetails = new Bundle(getIntent().getExtras());
        if (bMdetails != null) {
            matrimonyListMain = (MatrimonyListMain) bMdetails.getSerializable("mDetails");
        }
        //For Set Font
        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);
        bindHere();

    }


    private void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbTitle.setText(getResources().getString(R.string.search_matrimony_details));
        tbIvBack.setVisibility(View.VISIBLE);
        tbIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvUpPDName = (TextView) findViewById(R.id.tvUpPDName);

        sdMpCover = (SimpleDraweeView) findViewById(R.id.sdMpCover);
        sdMpImg = (SimpleDraweeView) findViewById(R.id.sdMpImg);
        sdUPBanner = (SimpleDraweeView) findViewById(R.id.sdUPBanner);

        tvMpPDGender = (TextView) findViewById(R.id.tvMpPDGender);
        tvMpPDDob = (TextView) findViewById(R.id.tvMpPDDob);
        tvMpPDBGroup = (TextView) findViewById(R.id.tvMpPDBGroup);
        tvMpPDBDonor = (TextView) findViewById(R.id.tvMpPDBDonor);
        tvMpPDNPlace = (TextView) findViewById(R.id.tvMpPDNPlace);
        tvMpPDGGFather = (TextView) findViewById(R.id.tvMpPDGGFather);
        tvMpPDGFather = (TextView) findViewById(R.id.tvMpPDGFather);
        tvMpFHName = (TextView) findViewById(R.id.tvMpFHName);
        tvMpPDKShurksh = (TextView) findViewById(R.id.tvMpPDKShurksh);
        tvMpPDMediclaim = (TextView) findViewById(R.id.tvMpPDMediclaim);
        tvMpPDMobile = (TextView) findViewById(R.id.tvMpPDMobile);
        tvMpPDGotra = (TextView) findViewById(R.id.tvMpPDGotra);
        tvMpPDDisability = (TextView) findViewById(R.id.tvMpPDDisability);
        tvMpPDDisabilityTxt = (TextView) findViewById(R.id.tvMpPDDisabilityTxt);

        tvMpMDEngDate = (TextView) findViewById(R.id.tvMpMDEngDate);
        tvMpMDEngWith = (TextView) findViewById(R.id.tvMpMDEngWith);
        tvMpMDMrgStatus = (TextView) findViewById(R.id.tvMpMDMrgStatus);
        tvMpMDMrgWith = (TextView) findViewById(R.id.tvMpMDMrgWith);
        tvMpMDMrgAnni = (TextView) findViewById(R.id.tvMpMDMrgAnni);
        tvMpMDFInLaw = (TextView) findViewById(R.id.tvMpMDFInLaw);
        tvMpMDCCityInLaw = (TextView) findViewById(R.id.tvMpMDCCityInLaw);
        tvMpMDNPlaceInLaw = (TextView) findViewById(R.id.tvMpMDNPlaceInLaw);

        tvMpEDCSName = (TextView) findViewById(R.id.tvMpEDCSName);
        tvMpEDPYear = (TextView) findViewById(R.id.tvMpEDPYear);

        llAddress = (LinearLayout) findViewById(R.id.llAddress);
        llAddressEmpty = (LinearLayout) findViewById(R.id.llAddressEmpty);
        llDisabilitytxt = (LinearLayout) findViewById(R.id.llDisabilitytxt);


        /*For Setting Font Here*/
        tvUpPDName.setTypeface(tfRegular);

        tvMpPDGender.setTypeface(tfRegular);
        tvMpPDDob.setTypeface(tfRegular);
        tvMpPDBGroup.setTypeface(tfRegular);
        tvMpPDBDonor.setTypeface(tfRegular);
        tvMpPDNPlace.setTypeface(tfRegular);
        tvMpPDGGFather.setTypeface(tfRegular);
        tvMpPDGFather.setTypeface(tfRegular);
        tvMpFHName.setTypeface(tfRegular);
        tvMpPDKShurksh.setTypeface(tfRegular);
        tvMpPDMediclaim.setTypeface(tfRegular);
        tvMpPDMobile.setTypeface(tfRegular);
        tvMpPDGotra.setTypeface(tfRegular);
        tvMpPDDisability.setTypeface(tfRegular);
        tvMpPDDisabilityTxt.setTypeface(tfBold);

        tvMpMDEngDate.setTypeface(tfRegular);
        tvMpMDEngWith.setTypeface(tfRegular);
        tvMpMDMrgStatus.setTypeface(tfRegular);
        tvMpMDMrgWith.setTypeface(tfRegular);
        tvMpMDMrgAnni.setTypeface(tfRegular);
        tvMpMDFInLaw.setTypeface(tfRegular);
        tvMpMDCCityInLaw.setTypeface(tfRegular);
        tvMpMDNPlaceInLaw.setTypeface(tfRegular);

        tvMpEDCSName.setTypeface(tfRegular);
        tvMpEDPYear.setTypeface(tfRegular);


        setData();


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

    private void setData() {
        if (matrimonyListMain != null) {

            if (matrimonyListMain.getUserDetails() != null) {
                sdMpCover.setImageURI(Uri.parse(matrimonyListMain.getUserDetails().getUserCoverImageLink()));
                sdMpImg.setImageURI(Uri.parse(matrimonyListMain.getUserDetails().getUserProfileImageLink()));
                tvUpPDName.setText(matrimonyListMain.getUserDetails().getName());


                tvMpPDGender.setText(matrimonyListMain.getUserDetails().getGender());
                tvMpPDDob.setText(matrimonyListMain.getUserDetails().getDateofBirth());
                tvMpPDBGroup.setText(matrimonyListMain.getUserDetails().getBloodGroup());
                tvMpPDBDonor.setText(matrimonyListMain.getUserDetails().getBloodDonor());
                tvMpPDNPlace.setText(matrimonyListMain.getUserDetails().getNativePlace());
                tvMpPDGGFather.setText(matrimonyListMain.getUserDetails().getGreatGrandFatherName());
                tvMpPDGFather.setText(matrimonyListMain.getUserDetails().getGrandFatherName());
                tvMpFHName.setText(matrimonyListMain.getUserDetails().getFatherOrhusbandName());
                tvMpPDKShurksh.setText(matrimonyListMain.getUserDetails().getKutumbSuraksha());
                tvMpPDMediclaim.setText(matrimonyListMain.getUserDetails().getMediClaim());
                tvMpPDMobile.setText(matrimonyListMain.getUserDetails().getMobileNumber());
                tvMpPDGotra.setText(matrimonyListMain.getUserDetails().getGotraName());

                if (matrimonyListMain.getUserDetails().getDisability().equalsIgnoreCase("true")) {
                    tvMpPDDisability.setText(matrimonyListMain.getUserDetails().getDisability());
                    llDisabilitytxt.setVisibility(View.VISIBLE);
                } else {
                    tvMpPDDisability.setText(matrimonyListMain.getUserDetails().getDisability());
                }

            }
            if (matrimonyListMain.getMarriageDetails() != null) {
                tvMpMDEngDate.setText(matrimonyListMain.getMarriageDetails().getEngagementDate());
                tvMpMDEngWith.setText(matrimonyListMain.getMarriageDetails().getEngagedWith());
                tvMpMDMrgStatus.setText(matrimonyListMain.getMarriageDetails().getMaritalstatus());
                tvMpMDMrgWith.setText(matrimonyListMain.getMarriageDetails().getMarriedWith());
                tvMpMDMrgAnni.setText(matrimonyListMain.getMarriageDetails().getAnniversaryDate());
                tvMpMDFInLaw.setText(matrimonyListMain.getMarriageDetails().getFatherInLaw());
                tvMpMDCCityInLaw.setText(matrimonyListMain.getMarriageDetails().getCurrentCityInLaw());
                tvMpMDNPlaceInLaw.setText(matrimonyListMain.getMarriageDetails().getNativePlaceInLaw());
            }

            if (matrimonyListMain.getEducationalDetails() != null) {
                tvMpEDCSName.setText(matrimonyListMain.getEducationalDetails().getSchoolCollege());
                tvMpEDPYear.setText(matrimonyListMain.getEducationalDetails().getPassingYear());
            }

            //For Address
            if (matrimonyListMain.getAddress().size() > 0) {
                int addressSize = matrimonyListMain.getAddress().size();
                for (int i = 0; i < addressSize; i++) {
                    llAddressLayout = (LinearLayout) View.inflate(MatrimonyListDetailsActivity.this, R.layout.profile_address_row, null);
                    ((TextView) llAddressLayout.findViewById(R.id.tvMAddresstxt)).setText(String.format(getResources().getString(R.string.phone_book_details_address) + " " + (i + 1)));
                    ((TextView) llAddressLayout.findViewById(R.id.tvMAddress)).setText(matrimonyListMain.getAddress().get(i).getFullAddress());

                    final int finalI = i;

                    llAddressLayout.findViewById(R.id.ivMAddressMap).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String addLat = matrimonyListMain.getAddress().get(finalI).getLat(), addLang = matrimonyListMain.getAddress().get(finalI).getLang();
                            if (!TextUtils.isEmpty(addLat) && !TextUtils.isEmpty(addLang)) {//22.9894468,72.517316"
                                try {
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                                /*Uri.parse("http://maps.google.com/maps?saddr=" + CurrentLatStr + "," + CurrentLangStr + "&daddr=" + addLat + "," + addLang));*/
                                            Uri.parse("http://maps.google.com/maps?q=" + addLat + "," + addLang));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {

                                AppDialog.showAlertDialog(MatrimonyListDetailsActivity.this, null, getString(R.string.error_lat_long_not_found),
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

        }
    }


}
