package com.js.jainsamaj.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.ForgotPasswordMain;
import com.js.jainsamaj.models.OtpChangePasswordMain;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener, OnApiCallListener {

    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    EditText etOTP, etNewPass, etConfirmPass;
    Button btnResend, btnSubmit;
    Bundle bEmail;
    String Email;


    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        tbTitle.setText(getString(R.string.otp_title_txt));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bEmail = new Bundle(getIntent().getExtras());
        Email = (String) bEmail.getSerializable("UserEmail");

        apiFunctions = new ApiFunctions(this, this);
        etOTP = (EditText) findViewById(R.id.etOTP);
        etNewPass = (EditText) findViewById(R.id.etNewPass);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPass);
        btnResend = (Button) findViewById(R.id.btnResend);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        tbIvBack.setOnClickListener(this);
        btnResend.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tbIvBack:
                finish();

                break;
            case R.id.btnResend:
                AppDialog.showProgressDialog(OtpActivity.this);
                ForgotPasswordMain forgotPasswordMain = new ForgotPasswordMain(Email);
                apiFunctions.forgotPassword(Api.MainUrl + Api.ActionForgotPass, forgotPasswordMain);
                break;
            case R.id.btnSubmit:
                String otp = etOTP.getText().toString();
                String newPass = etNewPass.getText().toString();
                String confirmPass = etConfirmPass.getText().toString();

                if (!TextUtils.isEmpty(otp)) {
                    if (!TextUtils.isEmpty(newPass) && !TextUtils.isEmpty(confirmPass)) {
                        if (newPass.equals(confirmPass)) {
                            AppDialog.showProgressDialog(OtpActivity.this);
                            OtpChangePasswordMain otpChangePasswordMain = new OtpChangePasswordMain(otp, Email, confirmPass);
                            apiFunctions.otpChangePassword(Api.MainUrl + Api.ActionOTPChangePass, otpChangePasswordMain);

                        } else {
                            AppDialog.showAlertDialog(OtpActivity.this, null, getString(R.string.error_new_password),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        }

                    } else {
                        AppDialog.showAlertDialog(OtpActivity.this, null, getString(R.string.otp_pass_error),
                                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                    }

                } else {
                    AppDialog.showAlertDialog(OtpActivity.this, null, getString(R.string.otp_error_msg),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
                break;
            default:
                break;

        }
    }

    @Override
    public void onSuccess(final int responseCode, String responseString, final String requestType) {
        Log.e("Otp Success Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionForgotPass) && apiResponseMessage.getStatus() == Api.ResponseOk) {

                } else if ((requestType == Api.MainUrl + Api.ActionOTPChangePass) && apiResponseMessage.getStatus() == Api.ResponseOk) {

                } else {

                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionForgotPass) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            AppDialog.showAlertDialog(OtpActivity.this, null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();

                                        }
                                    });

                        } else if ((requestType == Api.MainUrl + Api.ActionOTPChangePass) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            AppDialog.showAlertDialog(OtpActivity.this, null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();

                                        }
                                    });
                        } else {
                            AppDialog.showAlertDialog(OtpActivity.this, null, apiResponseMessage.getMessage(),
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
            Log.e("Otp Success Failure" + errorMessage);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(OtpActivity.this, null, errorMessage,
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
