package com.mhit.davis.contactloader;

import android.app.Application;
import android.content.Context;

import com.mhit.davis.contactloader.utils.ContactLoader;


public class ContactLoaderApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ContactLoader.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
