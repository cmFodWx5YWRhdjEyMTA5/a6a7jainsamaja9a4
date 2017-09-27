package com.js.jainsamaj.webservices;

/**
 * Created by arbaz on 6/2/17.
 */

public class Api {

    // Header
    public static final String HTTP_HEADER = "httpx-thetatech-accesstoken";

    //Dump Condition
    public static final int ConnectionTimeout = 30000; // = 30 seconds
    public static final int ConnectionSoTimeout = 60000; // = 60 seconds


    public static final int ResponseOk = 200;
    public static final int ResponseError = 201;
    public static final int ResponsePageError = 400;
    public static final int ResponseUnauthorized = 401;
    public static final int ResponseNotFound = 404;
    public static final int ResponseForgotEmail = 422;
    public static final int ResponseServerError = 500;

    public static final String ServerErrorMessage = "We are unable to connect with server, please try again later";

    public static final String data = "data";
    public static final String code = "code";
    public static final String message = "message";
    public static final String error = "error";
    public static final String description = "description";
    public static final String validateKeys = "validate_keys";
    public static final String validateDescription = "validate_description";

    //List Params
    public static final int ListLimit = 10;
    public static final int ApprovedStatus = 4;
    public static final int PendingStatus = 0;


    /*Live Url*/
    //chang 22/04/2014 as per mail
    public static final String MainUrl = "http://42samaj.in/onlineportal_API/rest/";
    // old URL
    //public static final String MainUrl = "http://52.66.175.31:8080/JainSamaj_API/rest/";
    /*Api List*/
    public static final String ActionDeviceRegistration = "deviceRegister";
    public static final String ActionLogin = "login";

    //UserM Profile
    public static final String ActionGetUserProfile = "getProfile";

    public static final String ActionTempleCategory = "gettemplecategory";
    public static final String ActionTempleCountry = "getcountry";
    public static final String ActionTempleState = "getstate";
    public static final String ActionTempleDistrict = "getdistrict";
    public static final String ActionTempleCity = "getcity";
    public static final String ActionTempleArea = "getarea";

    //Temple Related Api
    public static final String ActionAddTemple = "insertTemple";
    public static final String ActionSearchTemple = "searchtemple";
    //End Temple Related Api

    //Matrimony Related Api
    //For Profile
    public static final String ActionGetMatrimonyProfile = "getmatrimonial";
    public static final String ActionUpdateMatrimonyProfile = "updateMatrimonial";
    // For Search
    public static final String ActionGetMatrimonyCriteria = "getMatrimonialCriteria";
    public static final String ActionInsertMatrimonyCriteria = "insertMatrimonialCriteria";
    public static final String ActionSearchMatrimonyByProfile = "searchMatrimonialByProfile";

    public static final String ActionWorkWith = "getWorkWith";
    public static final String ActionWorkAs = "getWorkAs";

    public static final String ActionEducationLevel = "getEducationLevelList";
    public static final String ActionEducationField = "getProfessionAreaList";
    public static final String ActionHeight = "getHeightList";
    //End Matrimony Related Api

    //PhoneBook List And Details
    public static final String ActionGetPhoneBookList = "getPhoneBookList";
    public static final String ActionGetPhoneBookDetails = "getPhoneBookDetails";
    public static final String ActionUpdateMyPhoneBook = "updatePhonebook";
    //End PhoneBook List And Details

    //AdvertisementMain
    public static final String ActionGetAdvertisement = "advertisement";

    //Notification wall
    public static final String ActionGetWall = "wall";

    //Forgot Password
    public static final String ActionForgotPass = "forgetPassword";

    // Change Password
    public static final String ActionChangePass = "changePassword";

    //Otp With Change Password
    public static final String ActionOTPChangePass = "otpConfirm";

    //Matrimonial Status
    public static final String ActionMatrimonialStatus = "getMatrimonialStatus";

}
