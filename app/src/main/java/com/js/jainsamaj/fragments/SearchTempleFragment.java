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
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.SelectionActivity;
import com.js.jainsamaj.activities.TempleListActivity;
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
import com.js.jainsamaj.models.SelectionListMain;
import com.js.jainsamaj.models.Temple.searchTemple.Request.SearchTempleMain;
import com.js.jainsamaj.models.Temple.searchTemple.Response.TempleMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class SearchTempleFragment extends RootFragment implements View.OnClickListener, OnApiCallListener {
    //Request Code
    private static final int REQUEST_TEMPLE_COUNTRY = 2222;
    private static final int REQUEST_TEMPLE_STATE = 2211;
    private static final int REQUEST_TEMPLE_DISTRICT = 2244;
    private static final int REQUEST_TEMPLE_CITY = 2255;
    private static final int REQUEST_TEMPLE_CATEGORY = 2266;
    EditText etSTCountry, etSTState, etSTDistrict, etSTCity, etSTName, etSTCat;
    Button btnSTSearch;
    SimpleDraweeView sdSearchTempleBanner;
    //set Font
    Typeface tfRegular, tfBold;
    //All ids
    String TempleCountryId = "0", TempleStateId = "0", TempleDistrictId = "0", TempleCityId = "0";

    //Result
    ArrayList<SelectionListMain> selectionListMainArrayList = new ArrayList<>();

    //Api call
    ApiFunctions apiFunctions;
    Gson gson = new Gson();
    ApiResponseMessage apiResponseMessage;
    TempleMain templeMain;

    ArrayList<TempleMain> templeMainArrayList;

    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;

    String companyUrl;
    //for api request
    String templeName;
    int countryId = 0;
    int stateId = 0;
    int districtId;
    int cityId;

    String TempleCategoryId = "0";

    public SearchTempleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_temple, container, false);
        apiFunctions = new ApiFunctions(getActivity(), this);
        etSTCountry = (EditText) view.findViewById(R.id.etSTCountry);
        etSTState = (EditText) view.findViewById(R.id.etSTState);
        etSTDistrict = (EditText) view.findViewById(R.id.etSTDistrict);
        etSTCity = (EditText) view.findViewById(R.id.etSTCity);
        etSTName = (EditText) view.findViewById(R.id.etSTName);
        etSTCat = (EditText) view.findViewById(R.id.etSTCat);

        btnSTSearch = (Button) view.findViewById(R.id.btnSTSearch);

        //set banner
        sdSearchTempleBanner = (SimpleDraweeView) view.findViewById(R.id.sdSearchTempleBanner);
        sdSearchTempleBanner.setOnClickListener(new View.OnClickListener() {
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
        //For set Font
        etSTCountry.setTypeface(tfRegular);
        etSTState.setTypeface(tfRegular);
        etSTDistrict.setTypeface(tfRegular);
        etSTCity.setTypeface(tfRegular);
        etSTName.setTypeface(tfRegular);
        etSTCat.setTypeface(tfRegular);

        btnSTSearch.setTypeface(tfBold);


        //Onclick here
        etSTCountry.setOnClickListener(this);
        etSTState.setOnClickListener(this);
        etSTDistrict.setOnClickListener(this);
        etSTCity.setOnClickListener(this);
        etSTName.setOnClickListener(this);
        etSTCat.setOnClickListener(this);

        btnSTSearch.setOnClickListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(R.string.nav_menu_search_temple));

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
//            sdSearchTempleBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdSearchTempleBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etSTCountry:
                Intent iTCounty = new Intent(getActivity(), SelectionActivity.class);
                iTCounty.putExtra("isTCountry", true);
                startActivityForResult(iTCounty, REQUEST_TEMPLE_COUNTRY);
                break;

            case R.id.etSTState:
                if (TempleCountryId != null) {
                    int id = Integer.parseInt(TempleCountryId);
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

            case R.id.etSTDistrict:
                if (TempleStateId != null) {
                    int id = Integer.parseInt(TempleStateId);
                    Intent iTState = new Intent(getActivity(), SelectionActivity.class);
                    iTState.putExtra("isTDistrict", true);
                    iTState.putExtra("state", id);
                    startActivityForResult(iTState, REQUEST_TEMPLE_DISTRICT);
                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.add_temple_state_error),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
                break;

            case R.id.etSTCity:
                if (TempleDistrictId != null) {
                    int id = Integer.parseInt(TempleDistrictId);
                    Intent iTState = new Intent(getActivity(), SelectionActivity.class);
                    iTState.putExtra("isTCity", true);
                    iTState.putExtra("district", id);
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
            case R.id.etSTCat:
                Intent iTCategory = new Intent(getActivity(), SelectionActivity.class);
                iTCategory.putExtra("isTCategory", true);
                iTCategory.putExtra("isTCategoryAll", true);
                startActivityForResult(iTCategory, REQUEST_TEMPLE_CATEGORY);
                break;
            case R.id.btnSTSearch:
                searchTemple();
                break;


        }
    }

    private void searchTemple() {
        if (Global.isNetworkAvailable(getActivity())) {
            try {
                templeName = etSTName.getText().toString();
                if ((!etSTCat.getText().toString().isEmpty()) || (!etSTName.getText().toString().isEmpty()) || (!etSTCountry.getText().toString().isEmpty())) {
                    AppDialog.showProgressDialog(getActivity());
                    SearchTempleMain searchTempleMain = new SearchTempleMain(TempleCountryId, TempleStateId, TempleCityId, templeName, TempleCategoryId);
                    apiFunctions.searchTemple(Api.MainUrl + Api.ActionSearchTemple, searchTempleMain);

                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_enter_search_temple_info),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    /***************************************
     * onActivity Result
     ************************************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            /******************************Result For Country**************************************/
            if (requestCode == REQUEST_TEMPLE_COUNTRY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCountryName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleCountryId = id;
                }
                etSTCountry.setText(name);
            }
            /******************************Result For State****************************************/
            if (requestCode == REQUEST_TEMPLE_STATE && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getStateName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleStateId = id;

                }
                etSTState.setText(name);
            }
            /******************************Result For District*************************************/
            if (requestCode == REQUEST_TEMPLE_DISTRICT && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getDistrictName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleDistrictId = id;

                }
                etSTDistrict.setText(name);
            }
            /******************************Result For City*****************************************/
            if (requestCode == REQUEST_TEMPLE_CITY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCityName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleCityId = id;

                }
                etSTCity.setText(name);
            }

        }

        if (requestCode == REQUEST_TEMPLE_CATEGORY && resultCode == RESULT_OK) {
            selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
            String name = "";
            String id;
            for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                name = selectionListMainArrayList.get(i).getCategoryName();
                id = String.valueOf(selectionListMainArrayList.get(i).getId());
                TempleCategoryId = id;
            }
            etSTCat.setText(name);
        }
    }

    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        AppDialog.dismissProgressDialog();
        Log.e("Search Temple Success" + responseString);
        templeMainArrayList = new ArrayList<>();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionSearchTemple) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getFirst = jsonArray.getJSONObject(i);
                        templeMain = gson.fromJson(getFirst.toString(), TempleMain.class);
                        templeMainArrayList.add(templeMain);


                    }


                } else {

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionSearchTemple) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            Intent iTemple = new Intent(getActivity(), TempleListActivity.class);
                            iTemple.putExtra("TempleList", templeMainArrayList);
                            startActivity(iTemple);
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
        Log.e("Search Temple Failure" + errorMessage);
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

