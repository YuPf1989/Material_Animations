package com.rain.material_animations;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Author:rain
 * Date:2018/10/19 14:27
 * Description:
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
