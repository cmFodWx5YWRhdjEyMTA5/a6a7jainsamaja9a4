package com.js.jainsamaj.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.adapters.PhonebookMyProfile.AddressListAdapter;
import com.js.jainsamaj.adapters.PhonebookMyProfile.EmailListAdapter;
import com.js.jainsamaj.adapters.PhonebookMyProfile.MobileListAdapter;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.Phonebook.Request.AddressListMainRequest;
import com.js.jainsamaj.models.Phonebook.Request.AreaIdP;
import com.js.jainsamaj.models.Phonebook.Request.CityIdP;
import com.js.jainsamaj.models.Phonebook.Request.CountryIdP;
import com.js.jainsamaj.models.Phonebook.Request.DistrictIdP;
import com.js.jainsamaj.models.Phonebook.Request.StateIdP;
import com.js.jainsamaj.models.Phonebook.Response.AddressItem;
import com.js.jainsamaj.models.Phonebook.Response.Phonebook.MyContactList;
import com.js.jainsamaj.models.Temple.searchTemple.Response.AreaId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CityId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.CountryId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.DistrictId;
import com.js.jainsamaj.models.Temple.searchTemple.Response.StateId;
import com.js.jainsamaj.models.UserLogin;
import com.js.jainsamaj.utils.Location.GeocodingLocationForAddress;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.js.jainsamaj.utils.Images.ImageUtils.getGalleryImagePath;


public class PhonebookMyProfileActivity extends AppCompatActivity implements View.OnClickListener, OnApiCallListener {

    //For getting image
    private static final int REQUEST_CAMERA = 3331;
    private static final int REQUEST_GALLERY = 3332;
    private static final int REQUEST_CAMERA_COVER = 3333;
    private static final int REQUEST_GALLERY_COVER = 3334;
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;
    //set Font
    Typeface tfRegular, tfBold;
    SimpleDraweeView sdMyProfileCover, sdMyProfile, sdMYProfileBanner;
    TextView tvPBMPDName;
    ImageView ivMobileAdd, ivAddressAdd;
    RecyclerView rvMobile, rvEmail, rvAddress;
    LinearLayout llAddMobileNumber, llAddEmailAddress, llAddAddress;
    LinearLayout llAddMobileEmpty, llAddEmailEmpty, llAddAddressEmpty;
    Button btnSave;
    LinearLayout llAddMobileLayout, llAddEmailLayout, llAddAddressLayout;
    MobileListAdapter mobileListAdapter;
    ArrayList<String> stringMobileArrayList = new ArrayList<>();
    EmailListAdapter emailListAdapter;
    ArrayList<String> stringEmailArrayList = new ArrayList<>();
    AddressListAdapter addressListAdapter;
    ArrayList<AddressItem> stringAddressArrayList = new ArrayList<>();
    AddressItem addressItem;
    EditText etMobileDialog;
    List<String> mobiledata;
    List<String> emaildata;
    List<AddressItem> addressdata;
    MyContactList myContactList;
    Bundle bMyContactList;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;
    ApiFunctions apiFunctions;
    UserLogin userLogin;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;
    String userImgPath;
    String coverImgPath;
    ApiResponseMessage apiResponseMessage;
    private ArrayList<EditText> alMobileNumber;
    private ArrayList<EditText> alEmail;
    private ArrayList<TextView> alAddress;
    //For Recycler swipe
    private int edit_position;
    private Paint p = new Paint();
    private View view;
    private AlertDialog.Builder mobileDialog;
    private boolean addMobile = false;
    private boolean addAddress = false;


    //For lat lang
    String[] tempLatLog;
    String lat;
    String lang;

    //fro make api call
    int countLL;

    //get current location
    LocationManager locationManager;
    String provider;
    String CurrentLatStr, CurrentLangStr;

    //For Request
    ArrayList<AddressListMainRequest> addressListMainRequests;

    private ScrollView slMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phonebook_my_profile);

/*

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

*/

        apiFunctions = new ApiFunctions(this, this);
        bMyContactList = new Bundle(getIntent().getExtras());
        myContactList = (MyContactList) bMyContactList.getSerializable("MyContactList");
        LoginResponse = Global.getPreference("userResponse", "");
        loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
        loginMain.getUserDetails().setUserProfileImageLink(myContactList.getUserImage());
        loginMain.getUserDetails().setUserCoverImageLink(myContactList.getCoverImage());
        LoginResponse = gson.toJson(loginMain, LoginMain.class);
        Global.storePreference("userResponse", LoginResponse);
        Intent intent = new Intent(Constants.USER_IMAGE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


        bindHere();
        clickHere();
        // initDialog();

        //Permission Here
        Global.checkPermission(PhonebookMyProfileActivity.this);

    }

    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        tbTitle.setText(R.string.phone_book_my_details_txt);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //For Set Font
        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);

        sdMyProfileCover = (SimpleDraweeView) findViewById(R.id.sdMyProfileCover);
        sdMyProfile = (SimpleDraweeView) findViewById(R.id.sdMyProfile);
        sdMYProfileBanner = (SimpleDraweeView) findViewById(R.id.sdMYProfileBanner);

        tvPBMPDName = (TextView) findViewById(R.id.tvPBMPDName);

        ivMobileAdd = (ImageView) findViewById(R.id.ivMobileAdd);
        ivAddressAdd = (ImageView) findViewById(R.id.ivAddressAdd);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(this);

        rvMobile = (RecyclerView) findViewById(R.id.rvMobile);
        rvEmail = (RecyclerView) findViewById(R.id.rvEmail);
        rvAddress = (RecyclerView) findViewById(R.id.rvAddress);

        rvMobile.setLayoutManager(mLayoutManager1);
        rvEmail.setLayoutManager(mLayoutManager2);
        rvAddress.setLayoutManager(mLayoutManager3);


        llAddMobileNumber = (LinearLayout) findViewById(R.id.llAddMobileNumber);
        llAddEmailAddress = (LinearLayout) findViewById(R.id.llAddEmailAddress);
        llAddAddress = (LinearLayout) findViewById(R.id.llAddAddress);

        llAddMobileEmpty = (LinearLayout) findViewById(R.id.llAddMobileEmpty);
        llAddEmailEmpty = (LinearLayout) findViewById(R.id.llAddEmailEmpty);
        llAddAddressEmpty = (LinearLayout) findViewById(R.id.llAddAddressEmpty);

        btnSave = (Button) findViewById(R.id.btnSave);

        slMain = (ScrollView) findViewById(R.id.slMain);

        setData();


    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);
        ivMobileAdd.setOnClickListener(this);
        ivAddressAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        sdMYProfileBanner.setOnClickListener(this);
        sdMyProfileCover.setOnClickListener(this);
        sdMyProfile.setOnClickListener(this);

    }

    public void setData() {
        try {

            if (myContactList != null) {

                tvPBMPDName.setText(myContactList.getName());
                sdMyProfileCover.setImageURI(Uri.parse(myContactList.getCoverImage()));
                sdMyProfile.setImageURI(Uri.parse(myContactList.getUserImage()));
                coverImgPath = myContactList.getCoverImage();
                userImgPath = myContactList.getUserImage();

                //For Mobile List
                if ((!myContactList.getMobileNumber().isEmpty()) && (myContactList.getMobileNumber().size() > 0)) {
                    rvMobile.setVisibility(View.VISIBLE);
                    llAddMobileEmpty.setVisibility(View.GONE);
                    for (int i = 0; i < myContactList.getMobileNumber().size(); i++) {
                        String mobileStr = myContactList.getMobileNumber().get(i);
                        stringMobileArrayList.add(mobileStr);
                    }
                    if (!stringMobileArrayList.isEmpty()) {
                        for (int i = 0; i < stringMobileArrayList.size(); i++) {
                            mobileListAdapter = new MobileListAdapter(stringMobileArrayList, PhonebookMyProfileActivity.this);
                            rvMobile.setAdapter(mobileListAdapter);
                            mobileListAdapter.notifyDataSetChanged();
                            //rvMobileSwipe();
                        }
                    }
                } else {
                    rvMobile.setVisibility(View.GONE);
                    llAddMobileEmpty.setVisibility(View.VISIBLE);
                }


                //For Email List
                if ((!myContactList.getEmailAddress().isEmpty()) && (myContactList.getEmailAddress().size() > 0)) {
                    rvEmail.setVisibility(View.VISIBLE);
                    llAddEmailEmpty.setVisibility(View.GONE);
                    for (int i = 0; i < myContactList.getEmailAddress().size(); i++) {
                        String emailStr = myContactList.getEmailAddress().get(i);
                        stringEmailArrayList.add(emailStr);
                    }
                    if (!stringEmailArrayList.isEmpty()) {
                        for (int i = 0; i < stringEmailArrayList.size(); i++) {
                            emailListAdapter = new EmailListAdapter(stringEmailArrayList, PhonebookMyProfileActivity.this);
                            rvEmail.setAdapter(emailListAdapter);
                            emailListAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    rvEmail.setVisibility(View.GONE);
                    llAddEmailEmpty.setVisibility(View.VISIBLE);
                }

                //For Address List
                if ((!myContactList.getAddress().isEmpty()) && (myContactList.getAddress().size() > 0)) {
                    rvAddress.setVisibility(View.VISIBLE);
                    llAddAddressEmpty.setVisibility(View.GONE);
                    for (int i = 0; i < myContactList.getAddress().size(); i++) {
                        addressItem = myContactList.getAddress().get(i);
                        stringAddressArrayList.add(addressItem);
                    }
                    if (!stringAddressArrayList.isEmpty()) {

                        for (int i = 0; i < stringAddressArrayList.size(); i++) {
                            addressListAdapter = new AddressListAdapter(stringAddressArrayList, PhonebookMyProfileActivity.this, CurrentLatStr, CurrentLangStr);
                            rvAddress.setAdapter(addressListAdapter);
                            addressListAdapter.notifyDataSetChanged();
                            //rvAddressSwipe();
                        }
                    }
                } else {
                    rvAddress.setVisibility(View.GONE);
                    llAddAddressEmpty.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //For Recycler View Mobile
    private void rvMobileSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    mobileListAdapter.removeItem(position);
                }/* else {
                    removeView();
                    edit_position = position;
                    mobileDialog.setTitle("Enter Mobile Number");
                    etMobileDialog.setText(stringMobileArrayList.get(position));
                    mobileDialog.show();
                }*/
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
/*
                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }*/
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, p);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvMobile);
    }

    //For Recycler View Address
    private void rvAddressSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    addressListAdapter.removeItem(position);
                }/* else {
                    removeView();
                    edit_position = position;
                    mobileDialog.setTitle("Enter Mobile Number");
                    etMobileDialog.setText(stringMobileArrayList.get(position));
                    mobileDialog.show();
                }*/
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    /*if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }*/
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, p);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvAddress);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tbIvBack:
                try {
                    Intent intent = new Intent();
                    intent.putExtra("MyContactDetails", myContactList);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case R.id.ivMobileAdd:
                addMobile = true;
                if (addMobile) {
                    addMobile = false;
                    mobileListAdapter.addItem("");

                }
                break;
            case R.id.ivAddressAdd:
                rvAddress.setVisibility(View.VISIBLE);
                llAddAddressEmpty.setVisibility(View.GONE);
                AddressItem emptyAddresss = new AddressItem();
                emptyAddresss.setId(0);
                emptyAddresss.setFullAddress("");
                emptyAddresss.setAddress("");
                emptyAddresss.setCountryId(new CountryId());
                emptyAddresss.setStateId(new StateId());
                emptyAddresss.setDistrictId(new DistrictId());
                emptyAddresss.setCityId(new CityId());
                emptyAddresss.setAreaId(new AreaId());
                emptyAddresss.setLang("0.0");
                emptyAddresss.setLat("0.0");
                emptyAddresss.setAddressType("primary");
                emptyAddresss.setPincode("");

                stringAddressArrayList.add(emptyAddresss);
                if (addressListAdapter != null) {
                    addressListAdapter.addItem(emptyAddresss);
                    addressListAdapter.notifyDataSetChanged();
                    rvAddress.smoothScrollToPosition(stringAddressArrayList.size() - 1);

                } else {
                    addressListAdapter = new AddressListAdapter(stringAddressArrayList, PhonebookMyProfileActivity.this, CurrentLatStr, CurrentLangStr);
                    rvAddress.setAdapter(addressListAdapter);
                }

                try {
                    scrollToView(slMain, rvAddress);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case R.id.sdMyProfileCover:
                selectCoverImage();
                break;
            case R.id.sdMyProfile:
                    selectUserImage();
                break;
            case R.id.btnSave:
                try {
                    String emailStr = "";
                    String mobileStr = "";

                    mobiledata = new ArrayList<>();
                    emaildata = new ArrayList<>();
                    addressdata = new ArrayList<>();

                    View view;
                    //For Mobile (Getting Data)
                    EditText etMCMobile;
                    for (int i = 0; i < stringMobileArrayList.size(); i++) {
                        view = rvMobile.findViewHolderForAdapterPosition(i).itemView;
                        etMCMobile = (EditText) view.findViewById(R.id.etMCMobile);
                        mobiledata.add(etMCMobile.getText().toString());


                        Log.e("Mobile Number:-" + mobiledata.get(i));
                    }
                    mobileStr = TextUtils.join(",", mobiledata);

                    //For Email(Getting Data)
                    EditText etMCEmail;
                    for (int i = 0; i < stringEmailArrayList.size(); i++) {
                        view = rvEmail.findViewHolderForAdapterPosition(i).itemView;
                        etMCEmail = (EditText) view.findViewById(R.id.etMCEmail);
                        emaildata.add(etMCEmail.getText().toString());
                        emailStr = emaildata.get(i);
                        Log.e("Email:-" + emaildata.get(i));
                    }

                    //Get Address Array Here(Getting Data)
                    addressdata = addressListAdapter.multipleAddress();

                    //check lat-lang for all addresses
                    for (int i = 0; i < addressdata.size(); i++) {
                        String latStr = addressdata.get(i).getLat();
                        String langStr = addressdata.get(i).getLang();


                        if ((latStr.equals("0.0")) || (langStr.equals("0.0"))) {

                            String streetStr = addressdata.get(i).getAddress();
                            String pinStr = addressdata.get(i).getPincode();
                            if (streetStr.equals("") ||
                                    (addressdata.get(i).getCountryId().getCountryName() == null) ||
                                    (addressdata.get(i).getStateId().getStateName() == null) ||
                                    (addressdata.get(i).getDistrictId().getDistrictName() == null) ||
                                    (addressdata.get(i).getCityId().getCityName() == null) ||
                                    (addressdata.get(i).getAreaId().getAreaName() == null) ||
                                    pinStr.equals("")) {

                                int addPos = i + 1;
                                AppDialog.showAlertDialog(PhonebookMyProfileActivity.this, null, "Please Fill All The Fields Of Address " + addPos,
                                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                rvAddress.scrollToPosition(i);
                                                dialogInterface.dismiss();
                                            }
                                        });

                                break;
                            } else {
                                String streetStr1 = addressdata.get(i).getAddress();
                                String countryStr1 = addressdata.get(i).getCountryId().getCountryName();
                                String stateStr1 = addressdata.get(i).getStateId().getStateName();
                                String districtStr1 = addressdata.get(i).getDistrictId().getDistrictName();
                                String cityStr1 = addressdata.get(i).getCityId().getCityName();
                                String areaStr1 = addressdata.get(i).getAreaId().getAreaName();
                                String pinStr1 = addressdata.get(i).getPincode();
                                //addressdata.get(i).getCountryId().setStatus(true);
                                String MainAddress = streetStr1 + "," + countryStr1 + "," + stateStr1 + "," + districtStr1 + "," + cityStr1 + "," + areaStr1 + "," + pinStr1;
                                GeocodingLocationForAddress.getAddressFromLocation(MainAddress, PhonebookMyProfileActivity.this, new GeocoderHandler(), i);
                                break;
                            }

                        } else {
                            countLL++;
                            Log.e("LL Count= " + countLL);
                        }
                    }
                    if (countLL == addressdata.size()) {
                        AddressListMainRequest addressListMainRequest;
                        CountryIdP countryIdP;
                        StateIdP stateIdP;
                        DistrictIdP districtIdP;
                        CityIdP cityIdP;
                        AreaIdP areaIdP;

                        addressListMainRequests = new ArrayList<>();
                        if (Global.isNetworkAvailable(this)) {
                            LoginResponse = Global.getPreference("userResponse", "");
                            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
                            if (loginMain != null && loginMain.getUsername() != null) {
                                String currentUser = loginMain.getUsername();
                                for (int i = 0; i < addressdata.size(); i++) {

                                    addressListMainRequest = new AddressListMainRequest();

                                    addressListMainRequest.setId(String.valueOf(addressdata.get(i).getId()));
                                    addressListMainRequest.setAddress(addressdata.get(i).getAddress());

                                    countryIdP = new CountryIdP(addressdata.get(i).getCountryId().getId());
                                    addressListMainRequest.setCountryId(countryIdP);
                                    addressListMainRequest.setCountryName(addressdata.get(i).getCountryId().getCountryName());

                                    stateIdP = new StateIdP(addressdata.get(i).getStateId().getId());
                                    addressListMainRequest.setStateId(stateIdP);
                                    addressListMainRequest.setStateName(addressdata.get(i).getStateId().getStateName());

                                    districtIdP = new DistrictIdP(addressdata.get(i).getDistrictId().getId());
                                    addressListMainRequest.setDistrictId(districtIdP);
                                    addressListMainRequest.setDistrictName(addressdata.get(i).getDistrictId().getDistrictName());

                                    cityIdP = new CityIdP(addressdata.get(i).getCityId().getId());
                                    addressListMainRequest.setCityId(cityIdP);
                                    addressListMainRequest.setCityName(addressdata.get(i).getCityId().getCityName());

                                    areaIdP = new AreaIdP(addressdata.get(i).getAreaId().getId());
                                    addressListMainRequest.setAreaId(areaIdP);
                                    addressListMainRequest.setAreaName(addressdata.get(i).getAreaId().getAreaName());

                                    addressListMainRequest.setPincode(addressdata.get(i).getPincode());
                                    addressListMainRequest.setLat(addressdata.get(i).getLat());
                                    addressListMainRequest.setLang(addressdata.get(i).getLang());


                                    addressListMainRequest.setAddressType(addressdata.get(i).getAddressType());
                                    addressListMainRequests.add(addressListMainRequest);


                                }

                                if (addressListMainRequests.size() == addressdata.size()) {
                                    //Covert address list to Json_Array;
                                    String jsonArray = gson.toJson(addressListMainRequests);
                                    AppDialog.showProgressDialog(PhonebookMyProfileActivity.this);
                                    apiFunctions.updateMyPhonebook(Api.MainUrl + Api.ActionUpdateMyPhoneBook, emailStr, mobileStr.toString(), userImgPath, coverImgPath, currentUser, jsonArray);
                                }
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


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.sdMYProfileBanner:

                if (companyUrl != null) {
                    try {
                        Intent iCompanyUrl = new Intent(PhonebookMyProfileActivity.this, WebsiteActivity.class);
                        iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                        startActivity(iCompanyUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;


            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(PhonebookMyProfileActivity.this);
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
//            sdMYProfileBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdMYProfileBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**************************************
     * For Pick image for user Profile
     **************************************/
    private void selectUserImage() {
        final CharSequence[] items = getResources().getStringArray(R.array.photo_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((getString(R.string.add_profile_image_text)));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.camera_photo))) {
                    cameraIntent();
                } else if (items[item].equals((getString(R.string.gallery_photo)))) {
                    galleryIntent();
                } else if (items[item].equals((getString(R.string.cancel_photo)))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    /**
     * **************************** Get Image from Camera*******************************************
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * ***********************************************Get Image From Gallery ***********************
     */
    private void galleryIntent() {

        if (Build.VERSION.SDK_INT < 19) {
            try {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALLERY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            addressListAdapter.onActivityResult(requestCode, resultCode, data);

            //Getting image from camera and gallery
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
                    if (data != null) {
                        onCaptureImageResult(data, sdMyProfile);
                    }
                } else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
                    if (data != null) {
                        onSelectFromGalleryResult(data, sdMyProfile, requestCode);
                    }
                } else if (requestCode == REQUEST_CAMERA_COVER && resultCode == RESULT_OK) {
                    if (data != null) {
                        onCaptureImageResultCover(data, sdMyProfileCover);
                    }
                } else if (requestCode == REQUEST_GALLERY_COVER && resultCode == RESULT_OK) {
                    if (data != null) {
                        onSelectFromGalleryResultCover(data, sdMyProfileCover, requestCode);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //end image
    }


    //Camera code
    private void onCaptureImageResult(Intent data, SimpleDraweeView imageView) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            Uri uri = Uri.parse("file://" + destination.getAbsolutePath());
            imageView.setImageURI(uri);

            userImgPath = destination.getAbsolutePath();
           /* String path_camera = getPath(uri);
            beginUpload(path_camera);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //imageView.setImageBitmap(thumbnail);
        imageView.setTag(destination.getAbsolutePath());
        //arrPhotos.add(destination.getAbsolutePath());
    }

    //Gallery code
    private void onSelectFromGalleryResult(Intent data, SimpleDraweeView imageView, int requestCode) {

        //Bitmap bm = null;
        String path = "";
        if (data != null) {
            try {
                //bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                path = getGalleryImagePath(this, data);
                userImgPath = path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Uri uri = Uri.parse("file://" + path);
            imageView.setImageURI(uri);

          /*  String path_gallery = getPath(uri);
            beginUpload(path_gallery);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        //imageView.setImageBitmap(bm);
        imageView.setTag(path);
    }
/*******************************End User Image Here******************************/


    /************************************** For Pick image for Cover*************************************/
    private void selectCoverImage() {
        final CharSequence[] items = getResources().getStringArray(R.array.photo_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((getString(R.string.add_cover_image_text)));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.camera_photo))) {
                    cameraIntentCover();
                } else if (items[item].equals((getString(R.string.gallery_photo)))) {
                    galleryIntentCover();
                } else if (items[item].equals((getString(R.string.cancel_photo)))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    /**
     * **************************** Get Image from Camera*******************************************
     */
    private void cameraIntentCover() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_COVER);
    }

    /**
     * ***********************************************Get Image From Gallery ***********************
     */
    private void galleryIntentCover() {

        if (Build.VERSION.SDK_INT < 19) {
            try {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_COVER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_GALLERY_COVER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //Camera code
    private void onCaptureImageResultCover(Intent data, SimpleDraweeView imageView) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            Uri uri = Uri.parse("file://" + destination.getAbsolutePath());
            imageView.setImageURI(uri);

            coverImgPath = destination.getAbsolutePath();
           /* String path_camera = getPath(uri);
            beginUpload(path_camera);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //imageView.setImageBitmap(thumbnail);
        imageView.setTag(destination.getAbsolutePath());
        //arrPhotos.add(destination.getAbsolutePath());
    }

    //Gallery code
    private void onSelectFromGalleryResultCover(Intent data, SimpleDraweeView imageView, int requestCode) {

        //Bitmap bm = null;
        String path = "";
        if (data != null) {
            try {
                //bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                path = getGalleryImagePath(this, data);
                coverImgPath = path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Uri uri = Uri.parse("file://" + path);
            imageView.setImageURI(uri);

          /*  String path_gallery = getPath(uri);
            beginUpload(path_gallery);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        //imageView.setImageBitmap(bm);
        imageView.setTag(path);
    }

    /*******************************End Cover Image Here******************************/


    @Override
    public void onSuccess(final int responseCode, String responseString, final String requestType) {
        AppDialog.dismissProgressDialog();
        Log.e("Phonebook Update Success" + responseString);
        JSONObject jsonObject;
        countLL = 0;
        try {
            if (!TextUtils.isEmpty(responseString)) {

                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionUpdateMyPhoneBook) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                    myContactList = gson.fromJson(getFirst.toString(), MyContactList.class);
                    loginMain.getUserDetails().setUserProfileImageLink(myContactList.getUserImage());
                    loginMain.getUserDetails().setUserCoverImageLink(myContactList.getCoverImage());
                    LoginResponse = gson.toJson(loginMain, LoginMain.class);
                    Global.storePreference("userResponse", LoginResponse);
                    Intent intent = new Intent(Constants.USER_IMAGE);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                } else {

                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionUpdateMyPhoneBook) && apiResponseMessage.getStatus() == Api.ResponseOk) {

                            AppDialog.showAlertDialog(PhonebookMyProfileActivity.this, null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            tbIvBack.performClick();
                                            dialogInterface.dismiss();
                                        }
                                    });

                        } else {
                            AppDialog.showAlertDialog(PhonebookMyProfileActivity.this, null, apiResponseMessage.getMessage(),
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
        countLL = 0;
        Log.e("Phonebook Update Failure" + errorMessage);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppDialog.showAlertDialog(PhonebookMyProfileActivity.this, null, errorMessage,
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

    //For Getting Lat lang
    private class GeocoderHandler extends Handler {
        int llPosition;

        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            boolean InvalidLatLong = false;
            int llPos = 0;
            countLL = 0;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    InvalidLatLong = bundle.getBoolean("InvalidLatLong");
                    llPos = bundle.getInt("Position");

                    break;
                default:


                    locationAddress = null;
            }
            if (InvalidLatLong) {
                countLL = 0;
                rvAddress.scrollToPosition(llPosition);
                addressdata.get(llPosition).setLat("0.0");
                addressdata.get(llPosition).setLang("0.0");
                AppDialog.showAlertDialog(PhonebookMyProfileActivity.this, null, "Geocode failed: Please enter valid Address!",
                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                //Toast.makeText(PhonebookMyProfileActivity.this, "Unable to find latitude and langitude for this address", Toast.LENGTH_LONG).show();
            } else {
                tempLatLog = locationAddress.split(",");
                lat = tempLatLog[0];
                lang = tempLatLog[1];
                addressdata.get(llPos).setLat(lat);
                addressdata.get(llPos).setLang(lang);
                //for check any lat-lang is remain or not
                countLL = 0;
                btnSave.performClick();

                //Toast.makeText(PhonebookMyProfileActivity.this, lat + "-" + lang, Toast.LENGTH_LONG).show();


            }
        }

    }

   /* //Get Current Location
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

    }*/

    @Override
    public void onBackPressed() {
        tbIvBack.performClick();
        super.onBackPressed();
    }

    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }


    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

}
