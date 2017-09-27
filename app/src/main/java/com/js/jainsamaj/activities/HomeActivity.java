package com.js.jainsamaj.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.adapters.NavMenuAdapter;
import com.js.jainsamaj.fragments.AboutUsFragment;
import com.js.jainsamaj.fragments.AddTempleFragment;
import com.js.jainsamaj.fragments.ChangePasswordFragment;
import com.js.jainsamaj.fragments.NotificationWallFragment;
import com.js.jainsamaj.fragments.PhoneBookFragment;
import com.js.jainsamaj.fragments.ProfileFragment;
import com.js.jainsamaj.fragments.SearchMatrimonyListFragment;
import com.js.jainsamaj.fragments.SearchTempleFragment;
import com.js.jainsamaj.fragments.TempleFragment;
import com.js.jainsamaj.fragments.TutorialsFragment;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.listeners.RecyclerItemClickListener;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.IsRegisterMain;
import com.js.jainsamaj.models.LeftMenuMain;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.MatrimonialStatus;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class HomeActivity extends AppCompatActivity implements OnApiCallListener {

    //Header
    SimpleDraweeView sdUserProfile, sdUserCover;
    TextView hdrTvUserName;
    RelativeLayout hdr_layout_view_profile;
    //Custom Toolbar
    Toolbar toolbar;
    TextView tbTitle;
    RecyclerView rvLeftMenu;
    SimpleDraweeView sdNavBanner;

    //custom Navbar
    NavMenuAdapter navMenuAdapter;
    ArrayList<LeftMenuMain> leftMenuMainArrayList;
    LeftMenuMain leftMenuMain;

    Fragment fragment;

    //Set Font
    Typeface tfRegular, tfBold;

    LoginMain loginMain;
    String LoginResponse;
    Gson gson = new Gson();

    //For database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    String companyUrl;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;


    //Api
    ApiFunctions apiFunctions;
    MatrimonialStatus matrimonialStatus;
    ApiResponseMessage apiResponseMessage;
    IsRegisterMain isRegisterMain;
    boolean isProfile;
    boolean isSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        apiFunctions = new ApiFunctions(this, this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.USER_IMAGE));

        //All Permission Here
        Global.checkPermission(HomeActivity.this);
        //get Login UserM Data
        LoginResponse = Global.getPreference("userResponse", "");
        loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
        //Custom toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbTitle.setText(getString(R.string.nav_menu_notification_wall));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //For Set Font
        tfBold = Global.setBoldFont(this);
        tfRegular = Global.setRegularFont(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /****************************************Header View***************************************/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        hdr_layout_view_profile = (RelativeLayout) headerView.findViewById(R.id.hdr_layout_view_profile);
        sdUserCover = (SimpleDraweeView) headerView.findViewById(R.id.sdUserCover);
        hdrTvUserName = (TextView) headerView.findViewById(R.id.hdrTvUserName);
        sdUserProfile = (SimpleDraweeView) headerView.findViewById(R.id.sdUserProfile);
        //setting Header Data here
        if (loginMain != null) {
            try {
                sdUserCover.setImageURI(Uri.parse(loginMain.getUserDetails().getUserCoverImageLink()));

//                Uri imageUri = Uri.parse((loginMain.getUserDetails().getUserProfileImageLink().toString()));
//
//                ImageRequest request = ImageRequest.fromUri(imageUri);
//
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setImageRequest(request)
//                        .setOldController(sdUserProfile.getController()).build();
//                sdUserProfile.setController(controller);
                sdUserProfile.setImageURI(Uri.parse(loginMain.getUserDetails().getUserProfileImageLink().toString()));
                hdrTvUserName.setTypeface(tfBold);
                hdrTvUserName.setText(loginMain.getUserDetails().getName() + " " + loginMain.getUserDetails().getSurname());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /*Hader View Click*/
        hdr_layout_view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent iUDetails = new Intent(HomeActivity.this, UserProfileActivity.class);
                    startActivity(iUDetails);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        //set Default Fragment Here
        fragment = new NotificationWallFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.matrimonial_container, fragment)
                .commit();

        /***************************************for Nav Menu***************************************/
        sdNavBanner = (SimpleDraweeView) findViewById(R.id.sdNavBanner);

        //set banner image
        setBanner();
        sdNavBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyUrl != null) {
                    try {
                        String staticURl = "http://www.polarbattery.in/";
                        Intent iCompanyUrl = new Intent(HomeActivity.this, WebsiteActivity.class);
                        iCompanyUrl.putExtra("CompanyUrl", staticURl);
                        startActivity(iCompanyUrl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rvLeftMenu = (RecyclerView) findViewById(R.id.rvLeftMenu);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        rvLeftMenu.setLayoutManager(mLayoutManager1);


        leftMenuMainArrayList = new ArrayList<>();
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_notification_wall), R.drawable.ic_notification_wall, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_matrimony), R.drawable.ic_matrinony, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_search), R.drawable.ic_matrinony_search, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_profile), R.drawable.ic_matrinony_profile, "--"));
        // leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_temple), R.drawable.ic_temple, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_temple_finder), R.drawable.ic_temple_finder, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_search_temple), R.drawable.ic_search_temple, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_add_temple), R.drawable.ic_add_temple, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_phone_book), R.drawable.ic_phone_book, ""));
//        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_tutorials), R.drawable.ic_tutorials, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_about_us), R.drawable.ic_about_us, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_change_passwd), R.drawable.ic_password_new, ""));
        leftMenuMainArrayList.add(new LeftMenuMain(getString(R.string.nav_menu_logout), R.drawable.ic_action_logout, ""));

        navMenuAdapter = new NavMenuAdapter(leftMenuMainArrayList, getApplicationContext());
        rvLeftMenu.setAdapter(navMenuAdapter);

        rvLeftMenu.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                leftMenuMain = leftMenuMainArrayList.get(position);


                try {
                    if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_notification_wall))) {
                        changeFragment(new NotificationWallFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_profile))) {
                        apiCall();
                        isProfile = true;

                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_search))) {
                        apiCall();
                        isSearch = true;

                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_temple))) {
                        changeFragment(new TempleFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_add_temple))) {
                        changeFragment(new AddTempleFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_search_temple))) {
                        changeFragment(new SearchTempleFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_phone_book))) {
                        changeFragment(new PhoneBookFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_tutorials))) {
                        changeFragment(new TutorialsFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_about_us))) {
                        changeFragment(new AboutUsFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_change_passwd))) {
                        changeFragment(new ChangePasswordFragment());
                        DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer_home.closeDrawer(GravityCompat.START);
                    } else if (leftMenuMain.getItemName().equals(getString(R.string.nav_menu_logout))) {
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        Global.clearPreferences();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    void changeFragment(Fragment fragment) {
        try {
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;

            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);


            if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.matrimonial_container, fragment, fragmentTag);
                fragmentTransaction.addToBackStack(backStateName);
                fragmentTransaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void apiCall() {
        if (loginMain.getUsername() != null) {
            AppDialog.showProgressDialog(this);
            matrimonialStatus = new MatrimonialStatus(loginMain.getUsername());
            apiFunctions.getStatus(Api.MainUrl + Api.ActionMatrimonialStatus, matrimonialStatus);
        }
    }


    //Change banner image
    public void setBanner() {
        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(HomeActivity.this);
            imagesLinkMains = new ArrayList<>();
            randomGenerator = new Random();

            //SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            SQLITEDATABASE = SQLITEHELPER.getmSqLiteDatabase();
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
            //change as per backend dev. change BASE64 TO URL 24-05-2017
//            byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            sdNavBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL = Uri.parse(ImageLink);
//            sdNavBanner.setImageURI(advURL);


            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //for Clear Screen from lead screen
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            if (loginMain != null) {
                try {
                    setBanner();
                    sdUserCover.setImageURI(Uri.parse(loginMain.getUserDetails().getUserCoverImageLink()));
                    sdUserProfile.setImageURI(Uri.parse(loginMain.getUserDetails().getUserProfileImageLink()));
                    hdrTvUserName.setTypeface(tfBold);
                    hdrTvUserName.setText(loginMain.getUserDetails().getName() + " " + loginMain.getUserDetails().getSurname());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    @Override
    public void onSuccess(final int responseCode, String responseString,
                          final String requestType) {

        Log.e("Matrimonial Status Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionMatrimonialStatus) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getData = jsonObject.getJSONObject(Api.data);
                    isRegisterMain = gson.fromJson(getData.toString(), IsRegisterMain.class);

                } else {

                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionMatrimonialStatus) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            if (isProfile && isRegisterMain.isRegistered) {
                                changeFragment(new ProfileFragment());
                                DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer_home.closeDrawer(GravityCompat.START);
                            } else if (isSearch && isRegisterMain.isRegistered) {
                                changeFragment(new SearchMatrimonyListFragment());
                                DrawerLayout drawer_home = (DrawerLayout) findViewById(R.id.drawer_layout);
                                drawer_home.closeDrawer(GravityCompat.START);
                            } else {
                                AppDialog.showAlertDialog(HomeActivity.this, null, getString(R.string.profile_user_not_register),
                                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                            }
                        } else {
                            AppDialog.showAlertDialog(HomeActivity.this, null, apiResponseMessage.getMessage(),
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
            Log.e("Matrimonial Status Failure" + errorMessage);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(HomeActivity.this, null, errorMessage,
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
