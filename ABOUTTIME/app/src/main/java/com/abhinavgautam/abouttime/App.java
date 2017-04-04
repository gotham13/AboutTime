package com.abhinavgautam.abouttime;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by New on 19-12-2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fbd.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
