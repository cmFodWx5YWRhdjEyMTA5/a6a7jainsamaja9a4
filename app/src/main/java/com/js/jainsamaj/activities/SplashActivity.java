package com.js.jainsamaj.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.Advertisement.Response.AdvertisementResponseMain;
import com.js.jainsamaj.models.Advertisement.Response.InsertListItem;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity implements OnApiCallListener {

    //For Api Call
    ApiResponseMessage apiResponseMessage;
    AdvertisementResponseMain advertisementResponseMain;
    Gson gson = new Gson();
    ApiFunctions apiFunctions;

    //For DataBase

    String TimeStampQuery;
    String AdvertisementQuery;
    String AttachmentQuery;
    String MediumQuery;
    String SmallQuery;

    InsertListItem insertListItem;


    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase mSqLiteDatabase;
    Cursor cursor;
    String timeStampStr;
    int isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        apiFunctions = new ApiFunctions(this, this);
       // startActivity(new Intent(this,LoginActivity.class));
        // AdvertisementMain Api call

        try {
            if (Global.isNetworkAvailable(SplashActivity.this)) {
                AppDialog.showProgressDialog(SplashActivity.this);
                if (Global.getPreference("isFirstTime", false)) {
                    apiFunctions.getAdvertisement(Api.MainUrl + Api.ActionGetAdvertisement, Global.getPreference("timeStamp", ""), "0");
                } else {
                    apiFunctions.getAdvertisement(Api.MainUrl + Api.ActionGetAdvertisement, "01/01/1947 11:27:05", "1");
                }
            } else {
                AppDialog.noNetworkDialog(SplashActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        AppDialog.dismissProgressDialog();
        /* storePreference("timeStamp", "06/07/1994 05:00:00");storePreference("isFirstTime", "1");*/
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                if (responseCode == Api.ResponseOk) {
                    jsonObject = new JSONObject(responseString);
                    apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                    if ((requestType == Api.MainUrl + Api.ActionGetAdvertisement) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                        JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                        advertisementResponseMain = gson.fromJson(getFirst.toString(), AdvertisementResponseMain.class);

                    } else {

                    }

                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ((requestType == Api.MainUrl + Api.ActionGetAdvertisement) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                                try {
                                    //Start Related to Database
                                    if (advertisementResponseMain.getTimestamp() != null) {
                                        SQLITEHELPER = new SQLiteHelper(SplashActivity.this);
                                        mSqLiteDatabase = SQLITEHELPER.getmSqLiteDatabase();
                                        if (mSqLiteDatabase != null) {
                                            if (advertisementResponseMain.getTimestamp() != null) {
                                                TimeStampQuery = "INSERT INTO " + Constants.TABLE_TIME_STAMP + "("
                                                        + Constants.TABLE_TIME_STAMP_DATE + ","
                                                        + Constants.TABLE_TIME_STAMP_IS_FIRST_TIME + ")"
                                                        + "VALUES('"
                                                        + advertisementResponseMain.getTimestamp() +
                                                        "','"
                                                        + 0 +
                                                        "');";
                                                mSqLiteDatabase.execSQL(TimeStampQuery);
                                                //mSqLiteDatabase = SQLITEHELPER.getWritableDatabase();
                                                cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + Constants.TABLE_TIME_STAMP + "", null);

                                                if (cursor.moveToFirst()) {
                                                    do {
                                                        timeStampStr = cursor.getString(cursor.getColumnIndex(Constants.TABLE_TIME_STAMP_DATE));
                                                        isFirstTime = cursor.getInt(cursor.getColumnIndex(Constants.TABLE_TIME_STAMP_IS_FIRST_TIME));

                                                    } while (cursor.moveToNext());
                                                }
                                                cursor.close();
                                                if (!timeStampStr.equals("") && isFirstTime != 0) {
                                                    Global.storePreference("timeStamp", advertisementResponseMain.getTimestamp());
                                                    Global.storePreference("isFirstTime", true);
                                                }
                                            }

                                            //Insert in to table (AdvertisementMain)
                                            if (advertisementResponseMain.getInsertList().size() > 0) {
                                                for (int i = 0; i < advertisementResponseMain.getInsertList().size(); i++) {
                                                    insertListItem = advertisementResponseMain.getInsertList().get(i);
                                                    String AttachcompanyUrl = insertListItem.getCompanyURL();

                                                    AdvertisementQuery = "INSERT INTO " + Constants.TABLE_ADVERTISEMENTS + "("
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_ID + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_C_DATETIME + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_C_USER + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_U_DATETIME + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_STATUS + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_TITLE + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_DESCRIPTION + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_NAME + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_ADDRESS + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_CONTACT + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_EMAIL + ","
                                                            + Constants.ADVERTISEMENTS_TABLE_KEY_ACTIVE + ")"
                                                            + "VALUES('"
                                                            + insertListItem.getId() +
                                                            "','"
                                                            + insertListItem.getCreatedDateTime() +
                                                            "','"
                                                            + insertListItem.getCreatedUser() +
                                                            "','"
                                                            + insertListItem.getUpdatedDateTime() +
                                                            "','"
                                                            + insertListItem.isStatus() +
                                                            "','"
                                                            + insertListItem.getTitle() +
                                                            "','"
                                                            + insertListItem.getDescription() +
                                                            "','"
                                                            + insertListItem.getCompanyName() +
                                                            "','"
                                                            + insertListItem.getCompanyAddress() +
                                                            "','"
                                                            + insertListItem.getCompanyContact() +
                                                            "','"
                                                            + insertListItem.getCompanyEmail() +
                                                            "','"
                                                            + insertListItem.isActive() +
                                                            "');";
                                                    mSqLiteDatabase.execSQL(AdvertisementQuery);


                                                    //Insert in to table (Attachment)
                                                    for (int j = 0; j < insertListItem.getAttachmentLink().size(); j++) {
                                                        String AttachmentUrl = insertListItem.getAttachmentLink().get(j);
                                                        AttachmentQuery = "INSERT INTO " + Constants.TABLE_ATTACHMENT_LINK + "("
                                                                + Constants.ATTACHMENT_TABLE_ID + ","
                                                                + Constants.ATTACHMENT_TABLE_COMPANY_URL + ","
                                                                + Constants.ATTACHMENT_TABLE_URL + ")"
                                                                + "VALUES('"
                                                                + insertListItem.getId() +
                                                                "','"
                                                                + AttachcompanyUrl +
                                                                "','"
                                                                + AttachmentUrl +
                                                                "');";
                                                        mSqLiteDatabase.execSQL(AttachmentQuery);
                                                    }

                                                    //End table (Attachment)

                                                    //Insert in to table (Medium)
                                                    for (int j = 0; j < insertListItem.getMediumImageLink().size(); j++) {
                                                        String MediumUrl = insertListItem.getMediumImageLink().get(j);
                                                        String MediumcompanyUrl = insertListItem.getCompanyURL();

                                                        MediumQuery = "INSERT INTO " + Constants.TABLE_MEDIUM_IMAGE_LINK + "("
                                                                + Constants.MEDIUM_TABLE_ID + ","
                                                                + Constants.MEDIUM_TABLE_COMPANY_URL + ","
                                                                + Constants.MEDIUM_TABLE_URL + ")"
                                                                + "VALUES('"
                                                                + insertListItem.getId() +
                                                                "','"
                                                                + MediumcompanyUrl +
                                                                "','"
                                                                + MediumUrl +
                                                                "');";
                                                        mSqLiteDatabase.execSQL(MediumQuery);

                                                    }
                                                    //End table (Medium)

                                                    //Insert in to table (Small)
                                                    for (int j = 0; j < insertListItem.getSmallImageLink().size(); j++) {
                                                        String SmallUrl = insertListItem.getSmallImageLink().get(j);
                                                        String SmallcompanyUrl = insertListItem.getCompanyURL();
                                                        SmallQuery = "INSERT INTO " + Constants.TABLE_SMALL_IMAGE_LINK + "("
                                                                + Constants.SMALL_TABLE_ID + ","
                                                                + Constants.SMALL_TABLE_COMPANY_URL + ","
                                                                + Constants.SMALL_TABLE_URL + ")"
                                                                + "VALUES('"
                                                                + insertListItem.getId() +
                                                                "','"
                                                                + SmallcompanyUrl +
                                                                "','"
                                                                + SmallUrl +
                                                                "');";
                                                        mSqLiteDatabase.execSQL(SmallQuery);

                                                    }
                                                    //End table (Small)



                                                }
                                                //End table (AdvertisementMain)
                                            } else {
                                                // Toast.makeText(SplashActivity.this, "Record not found", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    //End Related Database
                                    if (Global.getPreference("IsLogin", false)) {
                                        Intent iHome = new Intent(SplashActivity.this, AdvertisementActivity.class);
                                        startActivity(iHome);
                                        finish();
                                        Global.activityTransition(SplashActivity.this);

                                    } else {
                                        Intent iLogin = new Intent(SplashActivity.this, LoginActivity.class);
                                        startActivity(iLogin);
                                        finish();
                                        Global.activityTransition(SplashActivity.this);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                AppDialog.showAlertDialog(SplashActivity.this, null, apiResponseMessage.getMessage(),
                                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });

                            }
                        }
                    });
                } else {
                    //    Toast.makeText(SplashActivity.this,getString(R.string.something_wrong_txt),Toast.LENGTH_LONG).show();
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppDialog.showAlertDialog(SplashActivity.this, null, getString(R.string.something_wrong_txt),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            if (Global.getPreference("IsLogin", false)) {
                                                //Start Activity
                                                Intent iHome = new Intent(SplashActivity.this, AdvertisementActivity.class);
                                                startActivity(iHome);
                                                finish();
                                                Global.activityTransition(SplashActivity.this);

                                            } else {
                                                Intent iLogin = new Intent(SplashActivity.this, LoginActivity.class);
                                                startActivity(iLogin);
                                                finish();
                                                Global.activityTransition(SplashActivity.this);
                                            }
                                        }
                                    });
                        }
                    });


                }
            } else {
                Log.e("Empty String" + "");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(final String errorMessage) {
        try {
            AppDialog.dismissProgressDialog();
            Log.e("Login Failure" + errorMessage);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(SplashActivity.this, null, errorMessage,
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
    protected void onPause() {
        AppDialog.dismissProgressDialog();
        super.onPause();
    }
}

//Extra code
/*     //Create DataBase
//                                    jainSamajDB = openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);



                                   /* //Create AdvertisementMain Main Table
                                    jainSamajDB.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_ADVERTISEMENTS + "("
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_ID + " INTEGER,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_C_DATETIME + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_C_USER + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_U_DATETIME + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_STATUS + " boolean,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_TITLE + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_DESCRIPTION + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_NAME + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_ADDRESS + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_CONTACT + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_COMPANY_EMAIL + " VARCHAR,"
                                            + Constants.ADVERTISEMENTS_TABLE_KEY_ACTIVE + " boolean);"
                                    );
                                    //Create Attachment Table
                                    jainSamajDB.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_ATTACHMENT_LINK + "("
                                            + Constants.ATTACHMENT_TABLE_ID + " INTEGER,"
                                            + Constants.ATTACHMENT_TABLE_COMPANY_URL + " VARCHAR,"
                                            + Constants.ATTACHMENT_TABLE_URL + " BLOB);"
                                    );
                                    //Create Medium Table
                                    jainSamajDB.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_MEDIUM_IMAGE_LINK + "("
                                            + Constants.MEDIUM_TABLE_ID + " INTEGER,"
                                            + Constants.MEDIUM_TABLE_COMPANY_URL + " VARCHAR,"
                                            + Constants.MEDIUM_TABLE_URL + " BLOB);"
                                    );
                                    //Create Small Table
                                    jainSamajDB.execSQL("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_SMALL_IMAGE_LINK + "("
                                            + Constants.SMALL_TABLE_ID + " INTEGER,"
                                            + Constants.SMALL_TABLE_COMPANY_URL + " VARCHAR,"
                                            + Constants.SMALL_TABLE_URL + " BLOB);"
                                    );
*/
