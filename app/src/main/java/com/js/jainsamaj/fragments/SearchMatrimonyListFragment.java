package com.js.jainsamaj.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.MatrimonyListDetailsActivity;
import com.js.jainsamaj.activities.SearchActivity;
import com.js.jainsamaj.activities.WebsiteActivity;
import com.js.jainsamaj.adapters.MatrimonySearchListAdapter;
import com.js.jainsamaj.fragments.common.RootFragment;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.listeners.RecyclerItemClickListener;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Common.GetMatrimony;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search.MatrimonyCriteriaMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Response.Search.MatrimonyListMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.app.Activity.RESULT_OK;


public class SearchMatrimonyListFragment extends RootFragment implements View.OnClickListener, OnApiCallListener {

    EditText etTSearch;
    RecyclerView rvMatrimonyList;
    TextView tvSMEmptyTxt;
    SimpleDraweeView sdMLBanner;

    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;

    //For APi
    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    MatrimonyCriteriaMain matrimonyCriteriaMain;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;
    ImageView tbIvMatrimonyFilter;

    ArrayList<MatrimonyListMain> matrimonyListMainArrayList;
    MatrimonyListMain matrimonyListMain;
    //Request
    private static final int REQUEST_MATRIMONY_LIST = 8188;
    MatrimonySearchListAdapter matrimonySearchListAdapter;

    public SearchMatrimonyListFragment() {
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
                R.layout.search_matrimony_list_fragment, container, false);
        apiFunctions = new ApiFunctions(getActivity(), this);
        etTSearch = (EditText) view.findViewById(R.id.etTSearch);
        etTSearch.setVisibility(View.GONE);
        try {


            etTSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String str = etTSearch.getText().toString().toLowerCase(Locale.getDefault());
                    matrimonySearchListAdapter.searchRecord(str);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        rvMatrimonyList = (RecyclerView) view.findViewById(R.id.rvMatrimonyList);
        rvMatrimonyList.setLayoutManager(mLayoutManager1);

        tvSMEmptyTxt = (TextView) view.findViewById(R.id.tvSMEmptyTxt);
        sdMLBanner = (SimpleDraweeView) view.findViewById(R.id.sdMLBanner);
        sdMLBanner.setOnClickListener(this);

        //Recycler view Click
        rvMatrimonyList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                matrimonyListMain = matrimonyListMainArrayList.get(position);
                if (matrimonyListMain != null) {
                    Intent iTempleDetails = new Intent(getActivity(), MatrimonyListDetailsActivity.class);
                    iTempleDetails.putExtra("mDetails", matrimonyListMain);
                    startActivity(iTempleDetails);
                }
            }
        }));

        getMatrimonyList();


        return view;
    }

    private void getMatrimonyList() {
        if (Global.isNetworkAvailable(getActivity())) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            if (loginMain != null && loginMain.getUsername() != null) {
                AppDialog.showProgressDialog(getActivity());
                GetMatrimony getMatrimony = new GetMatrimony(loginMain.getUsername());
                apiFunctions.getMatrimonyList(Api.MainUrl + Api.ActionSearchMatrimonyByProfile, getMatrimony);
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
        setTitle(getString(R.string.nav_menu_search_List));
        displayMatrimonyFilter();
        tbIvMatrimonyFilter = displayMatrimonyFilter();
        tbIvMatrimonyFilter.setOnClickListener(this);

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
//            sdMLBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdMLBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hideMatrimonyFilter();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sdMLBanner:
                Intent iCompanyUrl = new Intent(getActivity(), WebsiteActivity.class);
                iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                startActivity(iCompanyUrl);
                break;
            case R.id.tbIvMatrimonyFilter:
                Intent iSearchMatrimony = new Intent(getActivity(), SearchActivity.class);
                startActivityForResult(iSearchMatrimony, REQUEST_MATRIMONY_LIST);
                break;

            default:
                break;
        }
    }


    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        Log.e("Matrimony List Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        matrimonyListMainArrayList = new ArrayList<>();

        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                // Get Matrimony List Criteria
                if ((requestType == Api.MainUrl + Api.ActionSearchMatrimonyByProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONArray jsonArray = jsonObject.getJSONArray(Api.data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject getObject = jsonArray.getJSONObject(i);
                        matrimonyListMain = gson.fromJson(getObject.toString(), MatrimonyListMain.class);
                        matrimonyListMainArrayList.add(matrimonyListMain);
                    }

                } else {
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionSearchMatrimonyByProfile) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            try {
                                if (!matrimonyListMainArrayList.isEmpty()) {
                                    rvMatrimonyList.setVisibility(View.VISIBLE);
                                    tvSMEmptyTxt.setVisibility(View.GONE);
                                    etTSearch.setVisibility(View.VISIBLE);
                                    matrimonySearchListAdapter = new MatrimonySearchListAdapter(matrimonyListMainArrayList, getActivity());
                                    rvMatrimonyList.setAdapter(matrimonySearchListAdapter);
                                } else {

                                    rvMatrimonyList.setVisibility(View.GONE);
                                    tvSMEmptyTxt.setVisibility(View.VISIBLE);
                                    etTSearch.setVisibility(View.GONE);
                                    Intent iSearchMatrimony = new Intent(getActivity(), SearchActivity.class);
                                    startActivityForResult(iSearchMatrimony, REQUEST_MATRIMONY_LIST);
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
        Log.e("Matrimony List Failure " + errorMessage);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            /******************************Result For Matrimony List**************************************/
            if (requestCode == REQUEST_MATRIMONY_LIST && resultCode == RESULT_OK) {
                boolean isListUpdate = data.getExtras().getBoolean("isListUpdate");
                if (isListUpdate) {
                    getMatrimonyList();
                }
            }
        }
    }
}