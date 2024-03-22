package com.jsrinfotech.wallpaparapp.activity;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.ads.AudienceNetworkAds;
import com.jsrinfotech.wallpaparapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Jack sparrow on 29-12-2019.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/GT-Walsheim-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        SharedPreferences sharedPreferences;
        SharedPreferences.Editor mEditor;
        int cnt;
        boolean flag;
        sharedPreferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
        cnt = sharedPreferences.getInt("PREF_NOTIFY", 0);
        flag = sharedPreferences.getBoolean("FLAG", true);
        if (flag) {
            cnt++;
        } else {
            cnt = 0;
        }
        mEditor.putInt("PREF_NOTIFY", cnt).apply();
        mEditor.putInt("AD_VIEW_COUNT", 0).apply();

        AudienceNetworkAds.initialize(this);
    }
}
