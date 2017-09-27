package com.js.jainsamaj.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.WebsiteActivity;
import com.js.jainsamaj.adapters.WallListAdapter;
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
import com.js.jainsamaj.models.UserLogin;
import com.js.jainsamaj.models.UserProfileRequestMain;
import com.js.jainsamaj.models.Wall.AdvertisementMain;
import com.js.jainsamaj.models.Wall.WallMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NotificationWallFragment extends RootFragment implements OnApiCallListener {
    SimpleDraweeView sdNotiBanner;

    RecyclerView rvWall;
    TextView tvLEmptyTxt;

    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    Cursor cursorAdvertMain;
    ImagesLinkMain imagesLinkMain;
    Random randomGenerator;
    String companyUrl;
    String companyUrlMedium;
    List<ImagesLinkMain> imagesLinkMainsMedium;
    List<ImagesLinkMain> imagesLinkMainsSmall;

    //For api call
    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    UserLogin userLogin;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;

    WallListAdapter wallListAdapter;
    WallMain wallMain;
    ArrayList<Object> wallMainArrayList;

    AdvertisementMain advertisementMain;

    private ArrayList<AdvertisementMain> arrAdvertisement;

    private int advtPosition = 4;

    public NotificationWallFragment() {
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
                R.layout.fragment_notification_wall, container, false);


        advertisementMain = new AdvertisementMain();
        apiFunctions = new ApiFunctions(getActivity(), this);
        rvWall = (RecyclerView) view.findViewById(R.id.rvWall);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        rvWall.setLayoutManager(mLayoutManager1);

        tvLEmptyTxt = (TextView) view.findViewById(R.id.tvLEmptyTxt);
        sdNotiBanner = (SimpleDraweeView) view.findViewById(R.id.sdNotiBanner);
        sdNotiBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyUrl != null) {try {
                    Intent iCompanyUrl = new Intent(getActivity(), WebsiteActivity.class);
                    iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                    startActivity(iCompanyUrl);} catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            //For Getting Data from database(Samall Table)
            SQLITEHELPER = new SQLiteHelper(getActivity());
            imagesLinkMainsSmall = new ArrayList<>();
            randomGenerator = new Random();

            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + Constants.TABLE_SMALL_IMAGE_LINK + "", null);

            if (cursor.moveToFirst()) {
                do {
                    imagesLinkMain = new ImagesLinkMain(cursor.getString(cursor.getColumnIndex(Constants.SMALL_TABLE_URL)));
                    companyUrl = cursor.getString(cursor.getColumnIndex(Constants.SMALL_TABLE_COMPANY_URL));
                    imagesLinkMainsSmall.add(imagesLinkMain);

                } while (cursor.moveToNext());
            }
            cursor.close();

            //Shuffle array list
            Collections.shuffle(imagesLinkMainsSmall);
            String ImageLink = String.valueOf(imagesLinkMainsSmall.get(0).getImgLink());
//            byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            sdNotiBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdNotiBanner.setImageURI(advURL);


            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }

        apiCallHere();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(R.string.nav_menu_notification_wall));
    }

    private void apiCallHere() {
        if (Global.isNetworkAvailable(getActivity())) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            //Remove after api work properly

            if (loginMain != null && loginMain.getUsername() != null) {
                AppDialog.showProgressDialog(getActivity());
                UserProfileRequestMain userProfileRequestMain = new UserProfileRequestMain(loginMain.getUsername());
                apiFunctions.getWall(Api.MainUrl + Api.ActionGetWall, userProfileRequestMain);
            } else {
                Toast.makeText(getActivity(), "User name invalid!", Toast.LENGTH_SHORT).show();
            }
        } else {
            AppDialog.noNetworkDialog(getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

        try {
            //For Getting Data from database(Medium Table)
            arrAdvertisement = new ArrayList<>();
            AdvertisementMain advertisementMain;
            SQLITEHELPER = new SQLiteHelper(getActivity());
            imagesLinkMainsMedium = new ArrayList<>();
            randomGenerator = new Random();
            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + Constants.TABLE_MEDIUM_IMAGE_LINK + "", null);

            if (cursor.moveToFirst()) {
                do {
                    imagesLinkMain = new ImagesLinkMain(cursor.getString(cursor.getColumnIndex(Constants.MEDIUM_TABLE_URL)));
                    companyUrlMedium = cursor.getString(cursor.getColumnIndex(Constants.MEDIUM_TABLE_COMPANY_URL));
                    cursorAdvertMain = SQLITEDATABASE.rawQuery("SELECT * FROM " + Constants.TABLE_ADVERTISEMENTS + " where id = " + cursor.getString(cursor.getColumnIndex(Constants.MEDIUM_TABLE_ID)), null);

                    if (cursorAdvertMain != null) {
                        if (cursorAdvertMain.moveToFirst()) {
                            advertisementMain = new AdvertisementMain(cursorAdvertMain.getString(cursorAdvertMain.getColumnIndex(Constants.ADVERTISEMENTS_TABLE_KEY_TITLE)),
                                    cursorAdvertMain.getString(cursorAdvertMain.getColumnIndex(Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_NAME)),
                                    cursorAdvertMain.getString(cursorAdvertMain.getColumnIndex(Constants.ADVERTISEMENTS_TABLE_KEY_DESCRIPTION)),
                                    cursorAdvertMain.getString(cursorAdvertMain.getColumnIndex(Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_ADDRESS)),
                                    cursorAdvertMain.getString(cursorAdvertMain.getColumnIndex(Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_CONTACT)),
                                    cursorAdvertMain.getString(cursorAdvertMain.getColumnIndex(Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_EMAIL)),
                                    cursor.getString(cursor.getColumnIndex(Constants.MEDIUM_TABLE_URL)), companyUrlMedium);
                            arrAdvertisement.add(advertisementMain);
                        }
                    }
                    imagesLinkMainsMedium.add(imagesLinkMain);

                } while (cursor.moveToNext());
            }
            cursorAdvertMain.close();
            cursor.close();


            //End database(Medium Table)
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        Log.e("Wall Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;

        String postTypeStr;
        wallMainArrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);

                if ((requestType == Api.MainUrl + Api.ActionGetWall) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirst = jsonArray.getJSONObject(i);
                        wallMain = gson.fromJson(getFirst.toString(), WallMain.class);
                        wallMainArrayList.add(wallMain);


                    }

                } else {

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionGetWall) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            try {
                                if (!wallMainArrayList.isEmpty()) {
                                    rvWall.setVisibility(View.VISIBLE);
                                    tvLEmptyTxt.setVisibility(View.GONE);
                                    int wallSize = wallMainArrayList.size();
                                    int advSize = arrAdvertisement.size();

                                    if (wallSize < advtPosition) {
                                        advtPosition = wallSize;
                                    }

                                    if (wallSize < advSize) {
                                        advSize = wallSize;
                                    }
                                    int j = 0;
                                    if (advSize > 0) {
                                        for (int i = 1; i <= wallSize; i++) {

                                            if (i % advtPosition == 0) {
                                                if (j < advSize) {
                                                    wallMainArrayList.add(i - 1, arrAdvertisement.get(j++));
                                                }
                                            }
                                        }
                                    }
                                    wallListAdapter = new WallListAdapter(wallMainArrayList, imagesLinkMainsMedium, companyUrlMedium, getActivity());
                                    rvWall.setAdapter(wallListAdapter);
                                } else {
                                    rvWall.setVisibility(View.GONE);
                                    tvLEmptyTxt.setVisibility(View.VISIBLE);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

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
        Log.e("Wall Failure" + errorMessage);
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
