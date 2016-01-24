package com.suitapps.reglogsample;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by antonio on 22/01/2016.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        //TODO Customize parse_app_id & parse_client_key values in strings
        Parse.initialize(this);
    }
}
