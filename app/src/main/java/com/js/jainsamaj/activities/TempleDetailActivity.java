package com.js.jainsamaj.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.Temple.searchTemple.Response.TempleMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.js.jainsamaj.R.id.sdAdvertisement;

public class TempleDetailActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    SimpleDraweeView sdTempleImage, sdTDBanner;
    TextView tvTDName, tvTDAddress;
    Button btnTDCall, btnTDInfo;

    Bundle bTempleDetails;
    TempleMain templeMain;
    //google map
    GoogleMap gMap;
    LatLng latlong;
    double latitude = 0, longitude = 0;
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


    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_detail);
        bTempleDetails = new Bundle(getIntent().getExtras());
        templeMain = (TempleMain) bTempleDetails.getSerializable("TempleDetails");
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
        tbTitle.setText(getString(R.string.search_temple_txt));
        tbIvBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sdTempleImage = (SimpleDraweeView) findViewById(R.id.sdTempleImage);
        sdTDBanner = (SimpleDraweeView) findViewById(R.id.sdTDBanner);
        tvTDName = (TextView) findViewById(R.id.tvTDName);
        tvTDAddress = (TextView) findViewById(R.id.tvTDAddress);
        btnTDCall = (Button) findViewById(R.id.btnTDCall);
        btnTDInfo = (Button) findViewById(R.id.btnTDInfo);

        //For Set Font
        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);

        tvTDName.setTypeface(tfRegular);
        tvTDAddress.setTypeface(tfRegular);

        btnTDCall.setTypeface(tfBold);
        btnTDInfo.setTypeface(tfBold);

    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);
        btnTDCall.setOnClickListener(this);
        btnTDInfo.setOnClickListener(this);
        sdTDBanner.setOnClickListener(this);
    }

    private void setData() {
        try {
            sdTempleImage.setImageURI(Uri.parse(templeMain.getLogoLink().get(0)));
            tvTDName.setText(templeMain.getTempleName());
            tvTDAddress.setText(templeMain.getFullAddress());

            //google map here
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapTD);
            mapFragment.getMapAsync(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tbIvBack:
                finish();
                break;
            case R.id.btnTDCall:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + templeMain.getContact()));
                    if (ActivityCompat.checkSelfPermission(TempleDetailActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnTDInfo:  try {
                Intent iView = new Intent(TempleDetailActivity.this, TempleViewDetailsActivity.class);
                iView.putExtra("TempleInfo", templeMain);
                startActivity(iView);} catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.sdTDBanner:


                if (companyUrl != null) {  try {
                    Intent iCompanyUrl = new Intent(TempleDetailActivity.this, WebsiteActivity.class);
                    iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                    startActivity(iCompanyUrl);} catch (Exception e) {
                    e.printStackTrace();
                }
                }

                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            try {


                gMap = googleMap;
                latitude = Double.parseDouble(templeMain.getLat());
                longitude = Double.parseDouble(templeMain.getLang());
                latlong = new LatLng(latitude, longitude);
                marker = gMap.addMarker(new MarkerOptions()
                        .position(latlong));

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                gMap.setMyLocationEnabled(true);
                gMap.animateCamera(CameraUpdateFactory.zoomTo(60));
                gMap.getUiSettings().setMapToolbarEnabled(false);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latlong));


                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        finish();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(TempleDetailActivity.this);
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
//            sdTDBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdTDBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
