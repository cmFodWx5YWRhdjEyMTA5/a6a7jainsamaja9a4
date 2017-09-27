package com.js.jainsamaj.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.Global;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdvertisementActivity extends AppCompatActivity {

    SimpleDraweeView sdAdvertisement;
    ImageView iv_cancel;
    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_advertisement);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        sdAdvertisement = (SimpleDraweeView) findViewById(R.id.sdAdvertisement);

        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(AdvertisementActivity.this);
            imagesLinkMains = new ArrayList<>();
            randomGenerator = new Random();

            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + Constants.TABLE_ATTACHMENT_LINK + "", null);

            if (cursor.moveToFirst()) {
                do {
                    imagesLinkMain = new ImagesLinkMain(cursor.getString(cursor.getColumnIndex(Constants.ATTACHMENT_TABLE_URL)));
                    imagesLinkMains.add(imagesLinkMain);

                } while (cursor.moveToNext());
            }
            cursor.close();

            //Shuffle array list
            Collections.shuffle(imagesLinkMains);
            String ImageLink = String.valueOf(imagesLinkMains.get(0).getImgLink());

            //change as per backend dev. change BASE64 TO URL 24-05-2017
//            byte[] decodedString = Base64.decode(ImageLink, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //sdAdvertisement.setImageBitmap(decodedByte);


            //set Full image here
            Uri advURL = Uri.parse(ImageLink);
            sdAdvertisement.setImageURI(advURL);
            //End database(Attachment Table)
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent iHome = new Intent(AdvertisementActivity.this, HomeActivity.class);
                    startActivity(iHome);
                    finish();
                    Global.activityTransition(AdvertisementActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);

        //button click Here
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent iHome = new Intent(AdvertisementActivity.this, HomeActivity.class);
                    startActivity(iHome);
                    finish();
                    Global.activityTransition(AdvertisementActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
