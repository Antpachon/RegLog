package com.suitapps.reglogsample;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;
import com.parse.Parse;
import com.suitapps.reglog.RegLog;

/**
 * Created by antonio on 22/01/2016.
 */
public class SampleApplication extends Application {

    Firebase mFireBaseRef;

    @Override
    public void onCreate() {
        super.onCreate();

        //If using Parse.com
        Parse.enableLocalDatastore(this);
        //TODO Customize parse_app_id & parse_client_key values in strings
        Parse.initialize(this);

        //If Using Firebase.
        Firebase.setAndroidContext(this);
        //General Firebase reference used allong all your app
        mFireBaseRef = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com/");
        //Use that same reference to perform login and register with Reglog.
        RegLog.setFirebaseReference(mFireBaseRef);
    }


}
