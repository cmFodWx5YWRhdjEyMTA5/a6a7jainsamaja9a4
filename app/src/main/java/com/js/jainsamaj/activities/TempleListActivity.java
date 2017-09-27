package com.js.jainsamaj.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.js.jainsamaj.R;
import com.js.jainsamaj.adapters.TempleListAdapter;
import com.js.jainsamaj.global.Constants;
import com.js.jainsamaj.global.SQLiteHelper;
import com.js.jainsamaj.listeners.RecyclerItemClickListener;
import com.js.jainsamaj.models.Advertisement.ImagesLinkMain;
import com.js.jainsamaj.models.Temple.searchTemple.Response.TempleMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.js.jainsamaj.R.id.sdAdvertisement;

public class TempleListActivity extends AppCompatActivity implements View.OnClickListener {
    //Custom toolbar
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    RecyclerView rvSearchTemple;
    TextView tvSTEmptyTxt;
    EditText etTSearch;
    SimpleDraweeView sdTLBanner;

    ArrayList<TempleMain> templeMainArrayList = new ArrayList<>();
    TempleListAdapter templeListAdapter;
    TempleMain templeMain;

    //For Database
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    ImagesLinkMain imagesLinkMain;
    List<ImagesLinkMain> imagesLinkMains;
    Random randomGenerator;

    String companyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_list);

        //Data Collecting Here
        templeMainArrayList = (ArrayList<TempleMain>) getIntent().getSerializableExtra("TempleList");

        bindHere();
        clickHere();
    }

    public void bindHere() {
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbTitle.setText(getString(R.string.search_temple_txt));
        tbIvBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvSearchTemple = (RecyclerView) findViewById(R.id.rvSearchTemple);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this);
        rvSearchTemple.setLayoutManager(mLayoutManager1);
        tvSTEmptyTxt = (TextView) findViewById(R.id.tvSTEmptyTxt);
        etTSearch = (EditText) findViewById(R.id.etTSearch);
        //For banner
        sdTLBanner = (SimpleDraweeView) findViewById(R.id.sdTLBanner);


        if (!templeMainArrayList.isEmpty()) {
            rvSearchTemple.setVisibility(View.VISIBLE);
            tvSTEmptyTxt.setVisibility(View.GONE);
            etTSearch.setVisibility(View.VISIBLE);
            templeListAdapter = new TempleListAdapter(templeMainArrayList, this);
            rvSearchTemple.setAdapter(templeListAdapter);

            //Recycler view Click
            rvSearchTemple.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    templeMain = templeMainArrayList.get(position);
                    if (templeMain != null) {  try {
                        Intent iTempleDetails = new Intent(TempleListActivity.this, TempleDetailActivity.class);
                        iTempleDetails.putExtra("TempleDetails", templeMain);
                        startActivity(iTempleDetails);} catch (Exception e) {
                    e.printStackTrace();
                }
                    }
                }
            }));
        } else {
            rvSearchTemple.setVisibility(View.GONE);
            tvSTEmptyTxt.setVisibility(View.VISIBLE);
            etTSearch.setVisibility(View.GONE);

        }

        etTSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = etTSearch.getText().toString().toLowerCase(Locale.getDefault());
                templeListAdapter.searchRecord(text);
            }
        });

    }

    public void clickHere() {
        tbIvBack.setOnClickListener(this);
        sdTLBanner.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tbIvBack:
                finish();
                break;
            case R.id.sdTLBanner:
                if (companyUrl != null) {  try {
                    Intent iCompanyUrl = new Intent(TempleListActivity.this, WebsiteActivity.class);
                    iCompanyUrl.putExtra("CompanyUrl", companyUrl);
                    startActivity(iCompanyUrl);} catch (Exception e) {
                    e.printStackTrace();
                }
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //For Getting Data from database(Attachment Table)
            SQLITEHELPER = new SQLiteHelper(TempleListActivity.this);
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
//            sdTLBanner.setImageBitmap(decodedByte);
            //set Banner Image Here
            Uri advURL=Uri.parse(ImageLink);
            sdTLBanner.setImageURI(advURL);

            //End database(Attachment Table)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
