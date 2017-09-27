package com.js.jainsamaj.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.js.jainsamaj.R;

public class WebsiteActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tbTitle;
    ImageView tbIvBack;

    WebView wvWebsite;

    Bundle bUrl;
    String CompanyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        bUrl = getIntent().getExtras();
        CompanyUrl = bUrl.getString("CompanyUrl");
        //Custom Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tbTitle = (TextView) toolbar.findViewById(R.id.tbTitle);
        tbIvBack = (ImageView) toolbar.findViewById(R.id.tbIvBack);
        tbIvBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        wvWebsite = (WebView) findViewById(R.id.wvWebsite);
        tbIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //WebView

        WebSettings settings = wvWebsite.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        wvWebsite.setWebViewClient(new WebViewController());

//        tbTitle.setText(getResources().getString(R.string.website_txt));
        if (!TextUtils.isEmpty(CompanyUrl)) {
            if (CompanyUrl.contains("http://") || CompanyUrl.contains("https://")) {
                wvWebsite.loadUrl(CompanyUrl);
            } else {
                wvWebsite.loadUrl("http://"+CompanyUrl);
            }

        }
    }

    public class WebViewController extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
