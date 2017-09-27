package com.js.jainsamaj.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.adapters.TempleImagesAdapter;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.Temple.searchTemple.Response.TempleMain;
import com.js.jainsamaj.utils.Indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.js.jainsamaj.R.id.sdAdvertisement;

public class TempleViewDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    TextView tvTVDName, tvTVDADetails;
    ViewPager vpTVDImg;
    int count = 0;
    CirclePageIndicator mIndicator;

    Bundle bTempleInfo;
    TempleMain templeMain;

    TempleImagesAdapter templeImagesAdapter;
    ArrayList<String> stringArrayList;

    SimpleDraweeView sdTVBanner;
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
        setContentView(R.layout.activity_temple_view_details);
        bTempleInfo = new Bundle(getIntent().getExtras());
        templeMain = (TempleMain) bTempleInfo.getSerializable("TempleInfo");
        bindHere();
        clickHere();
        setData();
    }


    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbTitle.setText(getString(R.string.search_temple_view_details_txt));
        tbIvBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sdTVBanner = (SimpleDraweeView) findViewById(R.id.sdTVBanner);
//        tvTVDName = (TextView) findViewById(R.id.tvTVDName);
        tvTVDADetails = (TextView) findViewById(R.id.tvTVDADetails);
        tvTVDADetails.setMovementMethod(new ScrollingMovementMethod());
        vpTVDImg = (ViewPager) findViewById(R.id.vpTVDImg);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        //For Set Font
        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);

//        tvTVDName.setTypeface(tfRegular);
        tvTVDADetails.setTypeface(tfRegular);

    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);
        sdTVBanner.setOnClickListener(this);
    }

    private void setData() {

        //tvTVDName.setText(templeMain.getTempleName());
        tvTVDADetails.setText(templeMain.getDescription());

        vpTVDImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                count = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                stringArrayList = new ArrayList<String>();
                for (int i = 0; i < templeMain.getLogoLink().size(); i++) {
                    String ImgLink = templeMain.getLogoLink().get(i);
                    stringArrayList.add(ImgLink);
                }
                templeImagesAdapter = new TempleImagesAdapter(TempleViewDetailsActivity.this, stringArrayList);
                vpTVDImg.setAdapter(templeImagesAdapter);

                mIndicator.setViewPager(vpTVDImg);

                /*vpHotelIamge.setCurrentItem(0);*/
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbIvBack:
                finish();
                break;

            case R.id.sdTVBanner:
                if (companyUrl != null) {
                    try {
                        Intent iCompanyUrl = new Intent(TempleViewDetailsActivity.this, WebsiteActivity.class);
                        iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                        startActivity(iCompanyUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(TempleViewDetailsActivity.this);
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
//            sdTVBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdTVBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
