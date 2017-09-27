package com.js.jainsamaj.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.activities.WebsiteActivity;
import com.js.jainsamaj.fragments.common.RootFragment;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AboutUsFragment extends RootFragment {
    SimpleDraweeView sdAboutBanner;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;
    String companyUrl;

    WebView wvAboutUs;
    
    public AboutUsFragment() {
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
                R.layout.fragment_about_us, container, false);

        
        
        sdAboutBanner = (SimpleDraweeView) view.findViewById(R.id.sdAboutBanner);

        //WebView
        wvAboutUs = (WebView) view.findViewById(R.id.wvAboutUs);
        wvAboutUs.getSettings().setJavaScriptEnabled(true);
        wvAboutUs.getSettings().setLoadWithOverviewMode(true);
        wvAboutUs.getSettings().setUseWideViewPort(true);
        wvAboutUs.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
            }
        });

        tbTitle.setText(getResources().getString(R.string.termsAndConditions_txt));
        wvAboutUs.loadUrl("http://42samaj.in/onlineportal/aboutUs.html");


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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(getString(R.string.nav_menu_about_us));
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
}
