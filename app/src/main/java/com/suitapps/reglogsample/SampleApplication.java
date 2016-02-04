package com.suitapps.reglogsample;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;
import com.parse.Parse;

/**
 * Created by antonio on 22/01/2016.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //If using Parse.com
        Parse.enableLocalDatastore(this);
        //TODO Customize parse_app_id & parse_client_key values in strings
        Parse.initialize(this);

        //If Using Firebase.
        Firebase.setAndroidContext(this);
    }


}
