package com.js.jainsamaj.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.PhoneBookDetailsActivity;
import com.js.jainsamaj.activities.PhonebookMyProfileActivity;
import com.js.jainsamaj.activities.WebsiteActivity;
import com.js.jainsamaj.adapters.PhoneBookAdapter;
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
import com.js.jainsamaj.models.Phonebook.Request.GetPhonebookListMain;
import com.js.jainsamaj.models.Phonebook.Response.Phonebook.MyContactList;
import com.js.jainsamaj.models.Phonebook.Response.Phonebook.OtherContactListItem;
import com.js.jainsamaj.utils.PhoneBook.IndexFastScrollRecyclerView;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class PhoneBookFragment extends RootFragment implements OnApiCallListener {

    /**
     * Set positive click listener for dialog
     */
    public DialogInterface.OnClickListener onPositiveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    };
    //RecyclerView rvPhone;
    /**
     * Set Negative click listener for dialog
     */
    public DialogInterface.OnClickListener onNegativeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            dialogInterface.dismiss();
        }
    };
    EditText etPBSearch;
    IndexFastScrollRecyclerView rvPhone;
    SimpleDraweeView sdPBtBanner, sdPBMPDMeImg;
    TextView tvPBEmptyTxt, tvPBMPDMeName;
    LinearLayout llPhoneBookMe;
    PhoneBookAdapter phoneBookAdapter;
    ArrayList<OtherContactListItem> otherContactListItemArrayList;
    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;
  //  OtherContactListItem otherContactListItem;
    MyContactList myContactList;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;
    private String json = null;

    public static final int REQUEST_MY_CONTACT = 7777;

    public PhoneBookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_phone_book, container, false);
        apiFunctions = new ApiFunctions(getActivity(), this);

        rvPhone = (IndexFastScrollRecyclerView) view.findViewById(R.id.rvPhone);
//        rvPhone.setHasFixedSize(true);
        rvPhone.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPhone.setIndexTextSize(12);
        rvPhone.setIndexBarColor("#80ffffff");
        rvPhone.setIndexBarCornerRadius(0);
        rvPhone.setIndexBarTransparentValue((float) 0.4);
        rvPhone.setIndexbarMargin(0);
        rvPhone.setIndexbarWidth(40);
        rvPhone.setPreviewPadding(0);
        rvPhone.setIndexBarTextColor("#0631b4");

        llPhoneBookMe = (LinearLayout) view.findViewById(R.id.llPhoneBookMe);

        sdPBMPDMeImg = (SimpleDraweeView) view.findViewById(R.id.sdPBMPDMeImg);
        sdPBtBanner = (SimpleDraweeView) view.findViewById(R.id.sdPBtBanner);
        sdPBtBanner.setOnClickListener(new View.OnClickListener() {
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
        tvPBMPDMeName = (TextView) view.findViewById(R.id.tvPBMPDMeName);
        tvPBEmptyTxt = (TextView) view.findViewById(R.id.tvPBEmptyTxt);
        etPBSearch = (EditText) view.findViewById(R.id.etPBSearch);


        etPBSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = etPBSearch.getText().toString().toLowerCase(Locale.getDefault());
                phoneBookAdapter.searchRecord(str);
            }
        });

        rvPhone.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                OtherContactListItem objOtherContactListItem = otherContactListItemArrayList.get(position);

                if (position == 0) {
                    Intent iDetails = new Intent(getActivity(), PhonebookMyProfileActivity.class);
                    iDetails.putExtra("MyContactList", myContactList);
                    startActivityForResult(iDetails, REQUEST_MY_CONTACT);
                    /*startActivity(iDetails);*/
                } else {
                    if (objOtherContactListItem.isPrivate()) {
                        Toast.makeText(getActivity(), "This User Is Private.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (objOtherContactListItem.getUserId() > 0) {
                            Intent iDetails = new Intent(getActivity(), PhoneBookDetailsActivity.class);
                            iDetails.putExtra("PhoneBookUserId", objOtherContactListItem.getUserId());
                            startActivity(iDetails);
                        }
                    }
                }
            }
        }));

        getPhoneBookData();
        //Gps Dialog
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }


        return view;
    }

    //getting PhoneBook List
    private void getPhoneBookData() {
        if (Global.isNetworkAvailable(getActivity())) {
            LoginResponse = Global.getPreference("userResponse", "");
            loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
            if (loginMain != null && loginMain.getUsername() != null) {
                String email = loginMain.getUsername();
                AppDialog.showProgressDialog(getActivity());
                GetPhonebookListMain getPhonebookListMain = new GetPhonebookListMain(email);
                apiFunctions.getPhoneBookList(Api.MainUrl + Api.ActionGetPhoneBookList, getPhonebookListMain);
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
        setTitle(getString(R.string.nav_menu_phone_book));
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
//            sdPBtBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL = Uri.parse(ImageLink);
            sdPBtBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {

        Log.e("PhoneBook List Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        otherContactListItemArrayList = new ArrayList<>();
        try {
            if (!TextUtils.isEmpty(responseString)) {

                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);

                if ((requestType == Api.MainUrl + Api.ActionGetPhoneBookList) && apiResponseMessage.getStatus() == Api.ResponseOk) {

                    JSONObject getDataList = jsonObject.getJSONObject(Api.data);
                    JSONArray jsonArray = getDataList.getJSONArray("otherContactList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject getFirst = jsonArray.getJSONObject(i);

                    //    otherContactListItem = gson.fromJson(getFirst.toString(), OtherContactListItem.class);

                        int userId = getFirst.getInt("userId");
                        String name = getFirst.getString("name");
                        Boolean isPrivate = getFirst.getBoolean("isPrivate");

                        Log.e("userId===" + userId);
                        Log.e("name===" + name);
                        // if (userId != 0) {
                        OtherContactListItem objOtherContactListItem = new OtherContactListItem();
                        objOtherContactListItem.setUserId(userId);
                        objOtherContactListItem.setName(name);
                        objOtherContactListItem.setPrivate(isPrivate);
                        otherContactListItemArrayList.add(objOtherContactListItem);
                        //    } else {
                        //        Log.e("Zero" + name);
                        //   }
                    }
                    //get MyContact data
                    JSONObject getMyContact = getDataList.getJSONObject("myContactList");
                    myContactList = gson.fromJson(getMyContact.toString(), MyContactList.class);
                    loginMain.getUserDetails().setUserProfileImageLink(myContactList.getUserImage());
                    loginMain.getUserDetails().setUserCoverImageLink(myContactList.getCoverImage());
                    LoginResponse = gson.toJson(loginMain, LoginMain.class);
                    Global.storePreference("userResponse", LoginResponse);
                    Intent intent = new Intent(Constants.USER_IMAGE);
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                } else {

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if ((requestType == Api.MainUrl + Api.ActionGetPhoneBookList) && apiResponseMessage.getStatus() == Api.ResponseOk) {

                            if (!otherContactListItemArrayList.isEmpty()) {

                                etPBSearch.setVisibility(View.VISIBLE);
                                rvPhone.setVisibility(View.VISIBLE);
                                tvPBEmptyTxt.setVisibility(View.GONE);

                                try {

                                    //Sort ArrayList Here
                                    Collections.sort(otherContactListItemArrayList, new Comparator<OtherContactListItem>() {
                                        @Override
                                        public int compare(OtherContactListItem s1, OtherContactListItem s2) {
                                            return s1.getName().compareToIgnoreCase(s2.getName());
                                        }
                                    });

                                    phoneBookAdapter = new PhoneBookAdapter(otherContactListItemArrayList, myContactList, getActivity());
                                    rvPhone.setAdapter(phoneBookAdapter);
                                    setData();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                etPBSearch.setVisibility(View.GONE);
                                rvPhone.setVisibility(View.GONE);
                                tvPBEmptyTxt.setVisibility(View.VISIBLE);
                            }

                        } else {
                            etPBSearch.setVisibility(View.GONE);
                            rvPhone.setVisibility(View.GONE);
                            tvPBEmptyTxt.setVisibility(View.VISIBLE);
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
            Log.e("PhoneBook List Failure" + errorMessage);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etPBSearch.setVisibility(View.GONE);
                    rvPhone.setVisibility(View.GONE);
                    tvPBEmptyTxt.setVisibility(View.VISIBLE);
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

    private void setData() {
        try {
            if (myContactList != null) {
                // tvPBMPDMeName.setText(getString(R.string.phone_book_my_details_me_txt));
                if (sdPBMPDMeImg != null) {
                    Uri uri = Uri.parse(myContactList.getUserImage());
                    sdPBMPDMeImg.setImageURI(uri);

                }


              /*  llPhoneBookMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iDetails = new Intent(getActivity(), PhonebookMyProfileActivity.class);
                        iDetails.putExtra("MyContactList", myContactList);
                        startActivity(iDetails);
                    }
                });
*/

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //GPS Enable Dialog
    private void showGPSDisabledAlertToUser() {

        AppDialog.showAlertDialog(getActivity(), null, getString(R.string.gps_dialog_custom_msg), getString(R.string.txt_ok), getString(R.string.txt_cancel), onPositiveClickListener, onNegativeClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        phoneBookAdapter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MY_CONTACT && resultCode == RESULT_OK) {
            if (data != null) {
                myContactList = (MyContactList) data.getExtras().getSerializable("MyContactDetails");

            }
        }
    }
}
