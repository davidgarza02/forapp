package com.davidgarza.forapp;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by davidgarza on 09/10/16.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);


    }
}
