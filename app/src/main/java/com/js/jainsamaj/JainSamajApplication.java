package com.js.jainsamaj;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.js.jainsamaj.models.DeviceInfo;

import io.fabric.sdk.android.Fabric;

/**
 * Created by arbaz on 6/2/17.
 */

public class JainSamajApplication extends Application{

    public static DeviceInfo deviceInfo;
    public static SharedPreferences sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //fresco Image viewer
        Fresco.initialize(this);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();

        deviceInfo = new DeviceInfo(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
