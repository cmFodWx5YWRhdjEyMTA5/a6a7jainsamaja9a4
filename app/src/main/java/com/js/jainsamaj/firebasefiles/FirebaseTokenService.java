package com.js.jainsamaj.firebasefiles;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.DeviceInfo;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

/**
 * Created by arbaz on 10/2/17.
 */
public class FirebaseTokenService extends FirebaseInstanceIdService implements OnApiCallListener{

    ApiFunctions apiFunctions;
    DeviceInfo deviceInfo;
    private static final String TAG = "JainSamajFirebase";
    String refreshedToken;
    @Override
    public void onTokenRefresh() {
        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        sendRegistrationToServer(refreshedToken);
        Log.e("Refreshed token:"+ refreshedToken);
       /* Global.storePreference("FirebaseToken",refreshedToken);*/


    }

    private void sendRegistrationToServer(String token) {
        apiFunctions = new ApiFunctions(this, this);
        deviceInfo = new DeviceInfo(this);
        deviceInfo.setDeviceToken(token);
        apiFunctions.userDeviceRegistration(Api.MainUrl + Api.ActionDeviceRegistration, deviceInfo);
    }

    @Override
    public void onSuccess(int responseCode, String responseString, String requestType) {
        Log.e("Device Registration success"+responseString);
        //condition not set here when this file is call or etc..
        Global.storePreference("FirebaseToken",refreshedToken);
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.e("Device Registration Failure"+errorMessage);
    }
}
