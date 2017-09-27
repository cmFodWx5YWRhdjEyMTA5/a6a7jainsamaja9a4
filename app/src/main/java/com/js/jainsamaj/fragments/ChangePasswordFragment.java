package com.js.jainsamaj.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.js.jainsamaj.models.ChangePassword;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChangePasswordFragment extends RootFragment implements OnApiCallListener {
    SimpleDraweeView sdAboutBanner;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;


    private ApiFunctions apiFunctions;
    private ApiResponseMessage apiResponseMessage;
    private ChangePassword changePassword;


    private EditText edOldPassword;
    private EditText edNewPassword;
    private EditText edConfirmPassword;
    private Button btnChangePassword;

    private LoginMain loginMain;
    private String LoginResponse;

    private Gson gson = new Gson();

    public ChangePasswordFragment() {
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
                R.layout.fragment_change_password, container, false);
        apiFunctions = new ApiFunctions(getActivity(), this);

        init(view);

        return view;
    }

    private void init(View view) {
        sdAboutBanner = (SimpleDraweeView) view.findViewById(R.id.sdAboutBanner);
        sdAboutBanner.setOnClickListener(new View.OnClickListener() {
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

        edOldPassword = (EditText) view.findViewById(R.id.edOldPassword);
        edNewPassword = (EditText) view.findViewById(R.id.edNewPassword);
        edConfirmPassword = (EditText) view.findViewById(R.id.edConfirmNewPassword);
        btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptChangePassword();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(R.string.nav_menu_change_passwd));
        try {
            //For Database
            SQLiteHelper SQLITEHELPER;
            SQLiteDatabase SQLITEDATABASE;
            Cursor cursor;
            ImagesLinkMain imagesLinkMain;
            List<ImagesLinkMain> imagesLinkMains;
            Random randomGenerator;

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
//            sdAboutBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdAboutBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void attemptChangePassword() {

        String strOldPassword = edOldPassword.getText().toString();
        String strNewPassword = edNewPassword.getText().toString();
        String strConfirmPassword = edConfirmPassword.getText().toString();

        if (!TextUtils.isEmpty(strOldPassword)) {

            if (!TextUtils.isEmpty(strNewPassword)){

                if (strNewPassword.equals(strConfirmPassword)) {

                    if (Global.isNetworkAvailable(getActivity())) {
                        LoginResponse = Global.getPreference("userResponse", "");
                        loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);

                        if (loginMain != null && loginMain.getUsername() != null) {
                            changePassword = new ChangePassword(loginMain.getUsername(), strNewPassword, strOldPassword);
                            AppDialog.showProgressDialog(getActivity());
                            apiFunctions.changePassword(Api.MainUrl + Api.ActionChangePass, changePassword);
                        }


                    } else {
                        AppDialog.noNetworkDialog(getActivity(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                    }


                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getString(R.string.error_new_password),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
        }else {
                AppDialog.showAlertDialog(getActivity(), null, getString(R.string.error_enter_new_password),
                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
            }
        } else {
            AppDialog.showAlertDialog(getActivity(), null, getString(R.string.error_old_password),
                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
        }
    }

    @Override
    public void onSuccess(int responseCode, String responseString, String requestType) {
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(apiResponseMessage != null){
                        if(apiResponseMessage.getStatus() ==  Api.ResponseOk){
                            edOldPassword.setText("");
                            edNewPassword.setText("");
                            edConfirmPassword.setText("");
                        }
                    }

                    AppDialog.showAlertDialog(getActivity(), null, apiResponseMessage.getMessage(),
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
    public void onFailure(final String errorMessage) {
        try {
            AppDialog.dismissProgressDialog();
            Log.e("Change Password Failure" + errorMessage);
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
