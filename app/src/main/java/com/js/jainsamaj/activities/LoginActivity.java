package com.js.jainsamaj.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.AppDialog;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.ApiResponseMessage;
import com.js.jainsamaj.models.DeviceInfo;
import com.js.jainsamaj.models.ForgotPasswordMain;
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.UserLogin;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnApiCallListener {
    EditText etLoginEmail, etLoginPassWord;
    Button btnLogin;
    TextView tvLoginTC;

    //For Api Call
    DeviceInfo deviceInfo;
    ApiFunctions apiFunctions;
    ApiResponseMessage apiResponseMessage;
    UserLogin userLogin;
    Gson gson = new Gson();
    LoginMain loginMain;
    String LoginResponse;
    //set Font
    Typeface tfRegular, tfBold;
    Dialog dialogForgot;


    private ImageView ivForgotPassword;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //All Permission Here
        Global.checkPermission(LoginActivity.this);
        //For Api
        apiFunctions = new ApiFunctions(this, this);
        deviceInfo = new DeviceInfo(LoginActivity.this);
        //For Set Font
        tfRegular = Global.setRegularFont(this);
        tfBold = Global.setBoldFont(this);

        bindHere();
        clickHere();

    }

    public void bindHere() {
        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
//        etLoginEmail.setText("janak@gmail.com");
        etLoginEmail.setTypeface(tfRegular);
        etLoginEmail.setSelection(etLoginEmail.length());


        etLoginPassWord = (EditText) findViewById(R.id.etLoginPassWord);
//        etLoginPassWord.setText("janak");
        etLoginPassWord.setTypeface(tfRegular);
        etLoginPassWord.setSelection(etLoginPassWord.length());

        tvLoginTC = (TextView) findViewById(R.id.tvLoginTC);
        tvLoginTC.setTypeface(tfRegular);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setTypeface(tfBold);

        ivForgotPassword = (ImageView) findViewById(R.id.ivForgotPassword);

    }

    public void clickHere() {
        btnLogin.setOnClickListener(this);
        tvLoginTC.setOnClickListener(this);
        ivForgotPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:

                String email = etLoginEmail.getText().toString();
                String password = etLoginPassWord.getText().toString();
                String deviceUdId = deviceInfo.getDeviceUDID();
                if (Global.isNetworkAvailable(this)) {
                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                        userLogin = new UserLogin(email, password, deviceUdId);
                        AppDialog.showProgressDialog(LoginActivity.this);
                        apiFunctions.userLogin(Api.MainUrl + Api.ActionLogin, userLogin);
                        /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        Global.activityTransition(LoginActivity.this);*/
                    } else {
                        AppDialog.showAlertDialog(LoginActivity.this, null, getString(R.string.error_enter_login_info),
                                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });

                    }

                } else {
                    AppDialog.noNetworkDialog(this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    });
                }
                break;
            case R.id.tvLoginTC:
                Intent iTandC = new Intent(LoginActivity.this, TermAndConditionsActivity.class);
                iTandC.putExtra("TCDetails", true);
                startActivity(iTandC);
                break;
            case R.id.ivForgotPassword:
                forgotPassword();

                break;
            default:
                break;
        }

    }


    @Override
    public void onSuccess(final int responseCode, String responseString, final String requestType) {
        Log.e("Login Success" + responseString);
        AppDialog.dismissProgressDialog();
        JSONObject jsonObject;
        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionLogin) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                    JSONObject getFirst = jsonObject.getJSONObject(Api.data);
                    loginMain = gson.fromJson(getFirst.toString(), LoginMain.class);
                    LoginResponse = gson.toJson(loginMain, LoginMain.class);
                  /*  Global.storePreference("userEmail", getFirst.getString("username"));
                        Global.storePreference("userId", getFirst.getString("id"));*/

                } else if ((requestType == Api.MainUrl + Api.ActionForgotPass) && apiResponseMessage.getStatus() == Api.ResponseOk) {

                } else {

                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionLogin) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            Intent intent = new Intent(LoginActivity.this, AdvertisementActivity.class);
                            startActivity(intent);
                            finish();
                            Global.storePreference("userResponse", LoginResponse);
                            Global.storePreference("IsLogin", true);
                            Global.activityTransition(LoginActivity.this);

                        } else if ((requestType == Api.MainUrl + Api.ActionForgotPass) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            AppDialog.showAlertDialog(LoginActivity.this, null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent iOtp = new Intent(LoginActivity.this,OtpActivity.class);
                                            iOtp.putExtra("UserEmail", email);
                                            startActivity(iOtp);
                                            dialogInterface.dismiss();
                                            dialogForgot.dismiss();


                                        }
                                    });

                        } else {
                            AppDialog.showAlertDialog(LoginActivity.this, null, apiResponseMessage.getMessage(),
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
            Log.e("Login Failure" + errorMessage);
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialog.showAlertDialog(LoginActivity.this, null, errorMessage,
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

    //Forgot dialog Here
    public void forgotPassword() {


        dialogForgot = new Dialog(LoginActivity.this, R.style.CustomDialogAnimation);
        Window window = dialogForgot.getWindow();
        window.setGravity(Gravity.CENTER);
        dialogForgot.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForgot.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogForgot.setContentView(R.layout.custom_forgot_dialog);

        final EditText etForgotEmail = (EditText) dialogForgot.findViewById(R.id.etForgotEmail);

        Button btnForgotOk = (Button) dialogForgot.findViewById(R.id.btnForgotOk);
        Button btnForgotCancel = (Button) dialogForgot.findViewById(R.id.btnForgotCancel);

        btnForgotOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etForgotEmail.getText().toString();
                if (Global.isNetworkAvailable(LoginActivity.this)) {
                    if (!TextUtils.isEmpty(email)) {
                        AppDialog.showProgressDialog(LoginActivity.this);
                        ForgotPasswordMain forgotPasswordMain = new ForgotPasswordMain(email);
                        apiFunctions.forgotPassword(Api.MainUrl + Api.ActionForgotPass, forgotPasswordMain);

                    } else {
                        etForgotEmail.setError(getResources().getString(R.string.forgot_dialog_custom_msg));

                    }
                } else {

                    AppDialog.noNetworkDialog(LoginActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogForgot.dismiss();
                        }
                    });
                }
            }
        });
        btnForgotCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForgot.dismiss();
            }
        });
        dialogForgot.show();
    }

}
