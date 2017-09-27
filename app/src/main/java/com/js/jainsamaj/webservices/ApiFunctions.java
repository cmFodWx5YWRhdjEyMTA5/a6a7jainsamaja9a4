package com.js.jainsamaj.webservices;

import android.content.Context;

import com.google.gson.Gson;
import com.js.jainsamaj.global.Log;
import com.js.jainsamaj.listeners.OnApiCallListener;
import com.js.jainsamaj.models.ChangePassword;
import com.js.jainsamaj.models.DeviceInfo;
import com.js.jainsamaj.models.ForgotPasswordMain;
import com.js.jainsamaj.models.MatrimonialStatus;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Common.GetMatrimony;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.UpdateMatrimonyMain;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Profile.WorkWithIdRequest;
import com.js.jainsamaj.models.Matrimony.ProfileAndSearch.Request.Search.UpdateMatrimonyCriteriaMain;
import com.js.jainsamaj.models.OtpChangePasswordMain;
import com.js.jainsamaj.models.Phonebook.Request.GetPhonebookListMain;
import com.js.jainsamaj.models.Phonebook.Request.PhoneBookDetailsRequestMain;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestAreaId;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestCityId;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestDistrictId;
import com.js.jainsamaj.models.Temple.addTemple.Request.RequestStateId;
import com.js.jainsamaj.models.Temple.searchTemple.Request.SearchTempleMain;
import com.js.jainsamaj.models.UserLogin;
import com.js.jainsamaj.models.UserProfileRequestMain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by arbaz on 6/2/17.
 */

public class ApiFunctions implements Serializable {

    private final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    Builder b = new Builder();
    private OkHttpClient client;
    private Context context;
    private OnApiCallListener acListener;
    private Gson gson;
    private MediaType CONTENT_TYPE = MediaType.parse("multipart/form-data; charset=utf-8");


    /*Constructor with one parameter*/
    public ApiFunctions(Context context) {

        this.client = new OkHttpClient();
        this.context = context;
        this.acListener = (OnApiCallListener) context;
        this.gson = new Gson();
        try {
            b.connectTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
            b.writeTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
            b.readTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
        }

        File cacheDirectory = new File(context.getCacheDir(), "http");
        int cacheSize = 10 * 1024 * 1024;
        try {
            Cache cache = new Cache(cacheDirectory, cacheSize);
            b.cache(cache);
            client = b.build();
        } catch (Exception e) {
            Log.v("Exception " + e.getMessage());
        }

    }


    public ApiFunctions(Context context, OnApiCallListener acListener) {

        this.client = new OkHttpClient();
        this.context = context;
        this.acListener = acListener;
        this.gson = new Gson();


        try {
            b.connectTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
            b.writeTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
            b.readTimeout(Api.ConnectionTimeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.v("Exception " + e.getMessage());
        }

        File cacheDirectory = new File(context.getCacheDir(), "http");
        int cacheSize = 10 * 1024 * 1024;
        try {
            Cache cache = new Cache(cacheDirectory, cacheSize);
            b.cache(cache);
            client = b.build();
        } catch (Exception e) {
            Log.v("Exception " + e.getMessage());
        }
    }
    public void executeRequest(final String url, Request request) {

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    acListener.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int responseCode = response.code();
                    String responseString = response.body().string();

                    acListener.onSuccess(responseCode, responseString, url);

                }


            });

        }




    // API Function For Devices Registration
    public void userDeviceRegistration(String url, DeviceInfo deviceInfo) {
        try {
            String jsonData = gson.toJson(deviceInfo);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // API Function For UserRequest Login
    public void userLogin(String url, UserLogin userLogin) {
        try {
            String jsonData = gson.toJson(userLogin);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Get UserM Profile
    public void getUserProfile(String url, UserProfileRequestMain userProfileRequestMain) {
        try {
            String jsonData = gson.toJson(userProfileRequestMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For getList
    public void getList(String url) {
        try {
            Request request = new Request.Builder().url(url).get().build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Get StateList
    public void getStateList(String url, RequestStateId requestStateId) {
        try {
            String jsonData = gson.toJson(requestStateId);

            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Get District
    public void getDistrictList(String url, RequestDistrictId requestDistrictId) {
        try {
            String jsonData = gson.toJson(requestDistrictId);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Get City
    public void getCityList(String url, RequestCityId requestCityId) {
        try {
            String jsonData = gson.toJson(requestCityId);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Get Area
    public void getAreaList(String url, RequestAreaId requestAreaId) {
        try {
            String jsonData = gson.toJson(requestAreaId);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Api Function For Get WorkAs
    public void getWorkAsList(String url, WorkWithIdRequest  workWithIdRequest) {
        try {
            String jsonData = gson.toJson(workWithIdRequest);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addTemple(String url, String id, String templeName, String description, String countryId,
                          String templeCategory, String stateId, String districtId, String cityId,
                          String areaId, String templeVideo, String address, String createdUser,
                          ArrayList<String> image, String lat, String lang, String contact) {
        try {
            try {

                MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(CONTENT_TYPE);
                final MediaType MEDIA_TYPE = MediaType.parse("image/*");
                int urlSize = image.size();
                for (int i = 0; i < urlSize; i++) {
                    String imageUrls = image.get(i);
                    File sourceFile = new File(imageUrls);
                    if (sourceFile.exists()) {
                        multipartBody.addFormDataPart("image", sourceFile.getName(), RequestBody.create(MEDIA_TYPE, sourceFile));
                    }
                }
              /*  if (image.startsWith("http://") || image.startsWith("https://"))
                    multipartBody.addFormDataPart("image", image);
                else {*/

                //}


                RequestBody postData = multipartBody
                        .addFormDataPart("id", id)
                        .addFormDataPart("templeName", templeName)
                        .addFormDataPart("description", description)
                        .addFormDataPart("countryId", countryId)
                        .addFormDataPart("templeCategory", templeCategory)
                        .addFormDataPart("stateId", stateId)
                        .addFormDataPart("districtId", districtId)
                        .addFormDataPart("cityId", cityId)
                        .addFormDataPart("areaId", areaId)
                        .addFormDataPart("templeVideo", templeVideo)
                        .addFormDataPart("address", address)
                        .addFormDataPart("createdUser", createdUser)
                        .addFormDataPart("lat", lat)
                        .addFormDataPart("lang", lang)
                        .addFormDataPart("contact", contact).build();
                Request request = new Request.Builder().url(url).post(postData).build();
                executeRequest(url, request);

            } catch (Exception e) {
                Log.e("Error: " + e.getLocalizedMessage());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Search Temple
    public void searchTemple(String url, SearchTempleMain searchTempleMain) {
        try {
            String jsonData = gson.toJson(searchTempleMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Get Matrimony Profile And Criteria
    public void getMatrimony(String url, GetMatrimony getMatrimony) {
        try {
            String jsonData = gson.toJson(getMatrimony);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Api Function For Get List  Matrimony
    public void getMatrimonyList(String url, GetMatrimony getMatrimony) {
        try {
            String jsonData = gson.toJson(getMatrimony);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Insert Matrimony  Profile
    public void updateMatrimony(String url, UpdateMatrimonyMain updateMatrimonyMain) {
        try {
            String jsonData = gson.toJson(updateMatrimonyMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For Insert Matrimony  Criteria
    public void updateMatrimonyCriteria(String url, UpdateMatrimonyCriteriaMain updateMatrimonyCriteriaMain) {
        try {
            String jsonData = gson.toJson(updateMatrimonyCriteriaMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For PhoneBook List
    public void getPhoneBookList(String url, GetPhonebookListMain getPhonebookListMain) {
        try {

            String jsonData = gson.toJson(getPhonebookListMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Api Function For PhoneBook Details
    public void getPhoneBookDetails(String url, PhoneBookDetailsRequestMain phoneBookDetailsMain) {
        try {
            String jsonData = gson.toJson(phoneBookDetailsMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Api Function For MyPhoneBook Update
    public void updateMyPhonebook(String url, String emailAddress, String mobileNumber,
                                  String userImage, String coverImage, String createdUser, String address) {
        try {
            try {

                MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(CONTENT_TYPE);
                final MediaType MEDIA_TYPE = MediaType.parse("image/*");

                //For UserM Image
                if (userImage.startsWith("http://") || userImage.startsWith("https://")) {
                    //convert image url to path
                    multipartBody.addFormDataPart("userImage", userImage, RequestBody.create(MEDIA_TYPE, userImage));
                } else {
                    File userImgFile = new File(userImage);
                    if (userImgFile.exists()) {
                        multipartBody.addFormDataPart("userImage", userImgFile.getName(), RequestBody.create(MEDIA_TYPE, userImgFile));
                    }
                }

                //For Cover Image
                if (coverImage.startsWith("http://") || coverImage.startsWith("https://")) {
                    multipartBody.addFormDataPart("coverImage", coverImage, RequestBody.create(MEDIA_TYPE, coverImage));
                } else {
                    File coverImgFile = new File(coverImage);
                    if (coverImgFile.exists()) {
                        multipartBody.addFormDataPart("coverImage", coverImgFile.getName(), RequestBody.create(MEDIA_TYPE, coverImgFile));
                    }
                }

                RequestBody postData = multipartBody
                        .addFormDataPart("emailAddress", emailAddress)
                        .addFormDataPart("mobileNumber", mobileNumber)
                        .addFormDataPart("createdUser", createdUser)
                        .addFormDataPart("address", address).build();
                Request request = new Request.Builder().url(url).post(postData).build();
                executeRequest(url, request);

            } catch (Exception e) {
                Log.e("Error: " + e.getLocalizedMessage());
            }


        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

    }

    //Api Function For Get AdvertisementMain

    public void getAdvertisement(String url, String timeStamp, String isFirstTime) {
        try {
            MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(CONTENT_TYPE);

            try {
                RequestBody postData = multipartBody
                        .addFormDataPart("timeStamp", timeStamp)
                        .addFormDataPart("isFirstTime", isFirstTime).build();
                Request request = new Request.Builder().url(url).post(postData).build();
                executeRequest(url, request);
            } catch (Exception e) {
                Log.e("Error: " + e.getLocalizedMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Api Function For Get Wall
    public void getWall(String url, UserProfileRequestMain userProfileRequestMain) {
        try {
            String jsonData = gson.toJson(userProfileRequestMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Forgot Password
    public void forgotPassword(String url, ForgotPasswordMain forgotPasswordMain) {
        try {
            String jsonData = gson.toJson(forgotPasswordMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Change Password
    public void changePassword(String url, ChangePassword changePassword) {
        try {
            String jsonData = gson.toJson(changePassword);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // OTP Change Password
    public void otpChangePassword(String url, OtpChangePasswordMain otpChangePasswordMain) {
        try {
            String jsonData = gson.toJson(otpChangePasswordMain);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Matrimonial status
    public void getStatus(String url, MatrimonialStatus matrimonialStatus) {
        try {
            String jsonData = gson.toJson(matrimonialStatus);
            RequestBody postData = RequestBody.create(JSON, jsonData);
            Request request = new Request.Builder().url(url).post(postData).build();
            executeRequest(url, request);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
