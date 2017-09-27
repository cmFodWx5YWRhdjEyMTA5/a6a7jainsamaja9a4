package com.js.jainsamaj.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.SelectionActivity;
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
import com.js.jainsamaj.models.Login.Response.LoginMain;
import com.js.jainsamaj.models.SelectionListMain;
import com.js.jainsamaj.utils.Location.GeocodingLocation;
import com.js.jainsamaj.webservices.Api;
import com.js.jainsamaj.webservices.ApiFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.R.attr.path;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;
import static com.js.jainsamaj.utils.Images.ImageUtilsTemple.getGalleryTemplePathArray;


public class AddTempleFragment extends RootFragment implements View.OnClickListener, OnApiCallListener {

    //Request
    private static final int REQUEST_TEMPLE_CATEGORY = 1111;
    private static final int REQUEST_TEMPLE_COUNTRY = 1122;
    private static final int REQUEST_TEMPLE_STATE = 1133;
    private static final int REQUEST_TEMPLE_DISTRICT = 1144;
    private static final int REQUEST_TEMPLE_CITY = 1155;
    private static final int REQUEST_TEMPLE_AREA = 1166;
    private static final int REQUEST_CAMERA = 1177;
    private static final int REQUEST_GALLERY = 1188;
    //Clickable
    EditText etATCategory, etATName, etATAddress, etATCountry, etATState, etATDistrict, etATCity, etATArea;
    //Editable
    EditText etATDescription, etATSelectVideo, etATMobile;
    Button btnATAdd;
    SimpleDraweeView sdAddTempleImage, sdAddTempleBanner;
    TextView tvATDescription, tvATSelectImage;
    //set Font
    Typeface tfRegular, tfBold;
    //Result
    ArrayList<SelectionListMain> selectionListMainArrayList = new ArrayList<>();


    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;

    //All ids
    String TempleCategoryId, TempleCountryId, TempleStateId, TempleDistrictId, TempleCityId, TempleAreaId;

    //For lat lang
    String[] tempLatLog;
    String lat;
    String lang;


    //For Api
    ApiFunctions apiFunctions;
    String templeIconPath;
    String address,
            countryName,
            stateName,
            districtName,
            cityName,
            areaName;
    String templeCategoryName;
    Gson gson = new Gson();
    ApiResponseMessage apiResponseMessage;

    ArrayList<Uri> mArrayUri;

    LoginMain loginMain;
    String LoginResponse;
    LinearLayout llTempleImg;
    ArrayList<String> tGalleyUrls;

    ImageView tbIvMap;

    public AddTempleFragment() {
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
                R.layout.fragment_add_temple, container, false);
        //All Permission Here
        Global.checkPermission(getActivity());
        apiFunctions = new ApiFunctions(getActivity(), this);
        tGalleyUrls = new ArrayList<>();

        tfRegular = Global.setRegularFont(getActivity());
        tfBold = Global.setBoldFont(getActivity());

        tvATDescription = (TextView) view.findViewById(R.id.tvATDescription);
        tvATSelectImage = (TextView) view.findViewById(R.id.tvATSelectImage);

        etATCategory = (EditText) view.findViewById(R.id.etATCategory);
        etATName = (EditText) view.findViewById(R.id.etATName);
        etATAddress = (EditText) view.findViewById(R.id.etATAddress);
        etATCountry = (EditText) view.findViewById(R.id.etATCountry);
        etATState = (EditText) view.findViewById(R.id.etATState);
        etATDistrict = (EditText) view.findViewById(R.id.etATDistrict);
        etATCity = (EditText) view.findViewById(R.id.etATCity);
        etATArea = (EditText) view.findViewById(R.id.etATArea);
        etATDescription = (EditText) view.findViewById(R.id.etATDescription);
        etATSelectVideo = (EditText) view.findViewById(R.id.etATSelectVideo);
        etATMobile = (EditText) view.findViewById(R.id.etATMobile);

        btnATAdd = (Button) view.findViewById(R.id.btnATAdd);

        llTempleImg = (LinearLayout) view.findViewById(R.id.llTempleImg);

        //For set Font
        tvATDescription.setTypeface(tfRegular);
        tvATSelectImage.setTypeface(tfRegular);

        etATCategory.setTypeface(tfRegular);
        etATName.setTypeface(tfRegular);
        etATAddress.setTypeface(tfRegular);
        etATCountry.setTypeface(tfRegular);
        etATState.setTypeface(tfRegular);
        etATDistrict.setTypeface(tfRegular);
        etATCity.setTypeface(tfRegular);
        etATArea.setTypeface(tfRegular);
        etATDescription.setTypeface(tfRegular);
        etATSelectVideo.setTypeface(tfRegular);

        btnATAdd.setTypeface(tfBold);


        sdAddTempleImage = (SimpleDraweeView) view.findViewById(R.id.sdAddTempleImage);

        //set banner
        sdAddTempleBanner = (SimpleDraweeView) view.findViewById(R.id.sdAddTempleBanner);
        sdAddTempleBanner.setOnClickListener(new View.OnClickListener() {
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
        //Onclick here
        etATCategory.setOnClickListener(this);
        etATCountry.setOnClickListener(this);
        etATState.setOnClickListener(this);
        etATDistrict.setOnClickListener(this);
        etATCity.setOnClickListener(this);
        etATArea.setOnClickListener(this);

        btnATAdd.setOnClickListener(this);
        sdAddTempleImage.setOnClickListener(this);

        //Gps Dialog
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(R.string.nav_menu_add_temple));
        displayMap();
        tbIvMap = displayMap();
        tbIvMap.setOnClickListener(this);


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
                    Log.e("URl" + companyUrl);

                } while (cursor.moveToNext());
            }
            cursor.close();

            //Shuffle array list
            Collections.shuffle(imagesLinkMains);
            String ImageLink = String.valueOf(imagesLinkMains.get(0).getImgLink());
//            byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            sdAddTempleBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdAddTempleBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etATCategory:
                Intent iTCategory = new Intent(getActivity(), SelectionActivity.class);
                iTCategory.putExtra("isTCategory", true);
                startActivityForResult(iTCategory, REQUEST_TEMPLE_CATEGORY);
                break;

            case R.id.etATCountry:
                Intent iTCounty = new Intent(getActivity(), SelectionActivity.class);
                iTCounty.putExtra("isTCountry", true);
                startActivityForResult(iTCounty, REQUEST_TEMPLE_COUNTRY);
                break;

            case R.id.etATState:
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

            case R.id.etATDistrict:
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

            case R.id.etATCity:
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


            case R.id.etATArea:
                if (TempleCityId != null) {
                    int id = Integer.parseInt(TempleCityId);
                    Intent iTState = new Intent(getActivity(), SelectionActivity.class);
                    iTState.putExtra("isTArea", true);
                    iTState.putExtra("city", id);
                    startActivityForResult(iTState, REQUEST_TEMPLE_AREA);
                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.add_temple_city_error),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }
                break;

            case R.id.btnATAdd:
                addTempleRequest();
                break;
            case R.id.sdAddTempleImage:

                //if (tGalleyUrls.size() != 4) {
                selectImage();
//                } else {
//                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_img_greater_then4),
//                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            });
//                }


                break;

            case R.id.tbIvMap:

                countryName = etATCountry.getText().toString();
                stateName = etATState.getText().toString();
                districtName = etATDistrict.getText().toString();
                cityName = etATCity.getText().toString();
                areaName = etATArea.getText().toString();
                address = etATAddress.getText().toString();

                if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(stateName) && !TextUtils.isEmpty(districtName)
                        && !TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(areaName)) {
                    String latlangAddress = address + "," + areaName + "," + cityName + "," + districtName + "," + stateName + "," + countryName;
                    GeocodingLocation.getAddressFromLocation(latlangAddress, getActivity(), new GeocoderHandler());
                    if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lang)) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                                /*Uri.parse("http://maps.google.com/maps?saddr=" + CurrentLatStr + "," + CurrentLangStr + "&daddr=" + addLat + "," + addLang));*/
                                Uri.parse("http://maps.google.com/maps?q=" + lat + "," + lang));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Unable to find latitude and langitude for this address", Toast.LENGTH_LONG).show();
                    }

                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_enter_add_temple_info),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }

                ///do write here

                break;

            default:
                break;
        }
    }


    /**************************************
     * For Pick Icon
     **************************************/
    private void selectImage() {
        final CharSequence[] items = getResources().getStringArray(R.array.photo_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle((getString(R.string.add_temple_icon_text)));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Global.checkPermission(getActivity());
                if (items[item].equals(getString(R.string.camera_photo))) {
                    cameraIntent();
                } else if (items[item].equals((getString(R.string.gallery_photo)))) {
                    galleryIntent();
                } else if (items[item].equals((getString(R.string.cancel_photo)))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    /**
     * **************************** Get Image from Camera*******************************************
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * ***********************************************Get Image From Gallery ***********************
     */
    private void galleryIntent() {

        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_GALLERY);
        }

    }


    /***************************************
     * onActivity Result
     ************************************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Getting image from camera and gallery
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
                if (data != null) {
                    onCaptureImageResult(data, sdAddTempleImage);
                }
            } else if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
                if (data != null) {

                    mArrayUri = new ArrayList<Uri>();
                    String imageEncoded;
                    List<String> imagesEncodedList;
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    imagesEncodedList = new ArrayList<String>();

                    //For single image from gallery
                    if (data.getData() != null) {
                        Uri mImageUri = data.getData();
                        mArrayUri.add(mImageUri);
                        // Get the cursor
                        Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded = cursor.getString(columnIndex);
                        cursor.close();
//                        onSelectFromGalleryResultSingle(data, sdAddTempleImage, requestCode, mImageUri);
                        onSelectFromGalleryResult(data, sdAddTempleImage, requestCode, mArrayUri);
                    }
                    //For multiple image from gallery
                    else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                // Get the cursor
                                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                imageEncoded = cursor.getString(columnIndex);
                                imagesEncodedList.add(imageEncoded);
                                cursor.close();

                            }
                            Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());

                            onSelectFromGalleryResult(data, sdAddTempleImage, requestCode, mArrayUri);


                        }
                    }

                }
            }
        }
        //end image

        if (data != null) {
            /******************************Result For Category*************************************/
            if (requestCode == REQUEST_TEMPLE_CATEGORY && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getCategoryName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleCategoryId = id;
                }
                etATCategory.setText(name);
            }
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
                etATCountry.setText(name);
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
                etATState.setText(name);
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
                etATDistrict.setText(name);
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
                etATCity.setText(name);
            }
            /******************************Result For Area*****************************************/
            if (requestCode == REQUEST_TEMPLE_AREA && resultCode == RESULT_OK) {
                selectionListMainArrayList = (ArrayList<SelectionListMain>) data.getSerializableExtra("SelectionList");
                String name = "";
                String id;
                for (int i = 0; i < selectionListMainArrayList.size(); i++) {
                    name = selectionListMainArrayList.get(i).getAreaName();
                    id = String.valueOf(selectionListMainArrayList.get(i).getId());
                    TempleAreaId = id;
                }
                etATArea.setText(name);
            }
        }

    }


    //Camera code
    private void onCaptureImageResult(Intent data, SimpleDraweeView imageView) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            ArrayList<String> tempCamera = new ArrayList<>();
            Uri uri = Uri.parse("file://" + destination.getAbsolutePath());
           /* imageView.setImageURI(uri);*/

            String cameraStr = String.valueOf(uri);
            tempCamera.add(cameraStr);
            setImages(tempCamera);

            /*templeIconPath = destination.getAbsolutePath();*/
           /* String path_camera = getPath(uri);
            beginUpload(path_camera);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //imageView.setImageBitmap(thumbnail);
        imageView.setTag(destination.getAbsolutePath());
        //arrPhotos.add(destination.getAbsolutePath());
    }

    //Gallery code Multiple Images
    private void onSelectFromGalleryResult(Intent data, SimpleDraweeView imageView, int requestCode, ArrayList<Uri> uris) {


        ArrayList<String> tempArray = new ArrayList<>();
        if (data != null) {
            try {
//                tGalleyUrls = getGalleryTemplePathArray(getActivity(), data, mArrayUri);
//                Log.e("Temple Urls= " + tGalleyUrls);
                tempArray = getGalleryTemplePathArray(getActivity(), data, mArrayUri);
                setImages(tempArray);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Uri uri = Uri.parse("file://" + path);
            imageView.setImageURI(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setTag(path);
    }

    //Set Temple Images Here
    LinearLayout llImgLayout;

    public void setImages(ArrayList<String> imagesUrl) {
        int temCont = imagesUrl.size() + tGalleyUrls.size();
        //if (temCont <= 4) {v
        for (int i = 0; i < imagesUrl.size(); i++) {
            tGalleyUrls.add(imagesUrl.get(i));
        }

        for (int i = 0; i < imagesUrl.size(); i++) {
            String strUrl = imagesUrl.get(i);
            llImgLayout = (LinearLayout) View.inflate(getActivity(), R.layout.temple_image_row, null);


            try {
                Uri uri = Uri.parse("file://" + strUrl);
                ((SimpleDraweeView) llImgLayout.findViewById(R.id.sdTempleImg)).setImageURI(uri);

                ((SimpleDraweeView) llImgLayout.findViewById(R.id.sdTempleImg)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            llTempleImg.addView(llImgLayout);

        }
//        } else {
//            AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_img_greater_then4),
//                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//        }


    }

//    //Gallery code Single image
//    private void onSelectFromGalleryResultSingle(Intent data, SimpleDraweeView imageView, int requestCode, Uri singleUri) {
//
//        String path = "";
//        if (data != null) {
//            try {
//                path = getGalleryTemplePath(getActivity(), data, singleUri);
//                Log.e("Temple Url = " + path);
//                //templeIconPath = path;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            Uri uri = Uri.parse("file://" + path);
//            imageView.setImageURI(uri);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        imageView.setTag(path);
//    }

    public void addTempleRequest() {

        if (Global.isNetworkAvailable(getActivity())) {
            countryName = etATCountry.getText().toString();
            stateName = etATState.getText().toString();
            districtName = etATDistrict.getText().toString();
            cityName = etATCity.getText().toString();
            areaName = etATArea.getText().toString();
            address = etATAddress.getText().toString();

//Add After Client Conformation
            if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(stateName) && !TextUtils.isEmpty(districtName)
                    && !TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(areaName)) {
                String latlangAddress = address + "," + areaName + "," + cityName + "," + districtName + "," + stateName + "," + countryName;

                GeocodingLocation.getAddressFromLocation(latlangAddress, getActivity(), new GeocoderHandler());
            } else {
                AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_enter_add_temple_info),
                        getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
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
    public void onSuccess(int responseCode, String responseString, final String requestType) {
        AppDialog.dismissProgressDialog();
        Log.e("Add Temple Success" + responseString);
        JSONObject jsonObject;

        try {
            if (!TextUtils.isEmpty(responseString)) {
                jsonObject = new JSONObject(responseString);
                apiResponseMessage = gson.fromJson(jsonObject.toString(), ApiResponseMessage.class);
                if ((requestType == Api.MainUrl + Api.ActionAddTemple) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                } else {

                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((requestType == Api.MainUrl + Api.ActionAddTemple) && apiResponseMessage.getStatus() == Api.ResponseOk) {
                            AppDialog.showAlertDialog(getActivity(), null, apiResponseMessage.getMessage(),
                                    getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();

                                            etATCategory.setText("");
                                            etATName.setText("");
                                            etATAddress.setText("");
                                            etATCountry.setText("");
                                            etATState.setText("");
                                            etATDistrict.setText("");
                                            etATCity.setText("");
                                            etATArea.setText("");
                                            etATDescription.setText("");
                                            etATSelectVideo.setText("");
                                            etATMobile.setText("");
                                            tGalleyUrls.clear();
                                            llTempleImg.removeAllViews();
                                            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.ic_add_image).build();
                                            sdAddTempleImage.setImageURI(imageRequest.getSourceUri());

                                            SearchTempleFragment searchTempleFragment = new SearchTempleFragment();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.matrimonial_container, searchTempleFragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();


                                        }
                                    });


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
    public void onFailure(String errorMessage) {
         try {
        AppDialog.dismissProgressDialog();
        Log.e("Add Temple Failure" + errorMessage);
        AppDialog.showAlertDialog(getActivity(), null, errorMessage,
                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

 } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //For Getting Lat lang
    private class GeocoderHandler extends Handler {

        @Override
        public void handleMessage(Message message) {

            String locationAddress;
            boolean InvalidLatLong = false;


            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    InvalidLatLong = bundle.getBoolean("InvalidLatLong");
                    break;
                default:


                    locationAddress = null;
            }
            if (InvalidLatLong) {
                Toast.makeText(getActivity(), "Unable to find latitude and langitude for this address", Toast.LENGTH_LONG).show();
            } else {
                tempLatLog = locationAddress.split(",");
                lat = tempLatLog[0];
                lang = tempLatLog[1];


                String id = "0";
                templeCategoryName = etATCategory.getText().toString();
                String templeName = etATName.getText().toString();
                String description = etATDescription.getText().toString();
                String countryId = TempleCountryId;
                String templeCategory = TempleCategoryId;
                String stateId = TempleStateId;
                String districtId = TempleDistrictId;
                String cityId = TempleCityId;
                String areaId = TempleAreaId;
                String templeVideo = etATSelectVideo.getText().toString();
                String mobile = etATMobile.getText().toString();

                LoginResponse = Global.getPreference("userResponse", "");
                loginMain = gson.fromJson(LoginResponse.toString(), LoginMain.class);
                if (loginMain.getUsername() != null && !TextUtils.isEmpty(templeName) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(countryName)
                        && !TextUtils.isEmpty(templeCategoryName) && !TextUtils.isEmpty(stateId) && !TextUtils.isEmpty(districtId)
                        && !TextUtils.isEmpty(cityId) && !TextUtils.isEmpty(areaId) && !TextUtils.isEmpty(templeVideo) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(mobile)) {
                    if (tGalleyUrls.size() > 0) {
                        AppDialog.showProgressDialog(getActivity());
                        apiFunctions.addTemple(Api.MainUrl + Api.ActionAddTemple, id, templeName, description,
                                countryId, templeCategory, stateId, districtId, cityId, areaId,
                                templeVideo, address, loginMain.getUsername(), tGalleyUrls, lat, lang, mobile);
                    } else {
                        AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_enter_add_temple_img),
                                getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                    }


                } else {
                    AppDialog.showAlertDialog(getActivity(), null, getResources().getString(R.string.error_enter_add_temple_info),
                            getString(R.string.txt_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                }


            }
        }
    }

    //GPS Enable Dialog
    private void showGPSDisabledAlertToUser() {

        AppDialog.showAlertDialog(getActivity(), null, getString(R.string.gps_dialog_custom_msg), getString(R.string.txt_ok), getString(R.string.txt_cancel), onPositiveClickListener, onNegativeClickListener);
    }

    /**
     * Set Negative click listener for dialog
     */
    public DialogInterface.OnClickListener onNegativeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            dialogInterface.dismiss();
        }
    };

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
}