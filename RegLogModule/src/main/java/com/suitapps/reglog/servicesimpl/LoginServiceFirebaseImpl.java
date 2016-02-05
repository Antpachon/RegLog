package com.suitapps.reglog.servicesimpl;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.suitapps.reglog.RegLog;
import com.suitapps.reglog.events.LoginCallbackEvent;
import com.suitapps.reglog.services.LoginService;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * SignIn implementation using Firebase.io as backend
 */
public class LoginServiceFirebaseImpl implements LoginService {

    @Override
    public void login(String username, String password) {

        final Firebase myFirebaseRef = RegLog.getFirebase();

        myFirebaseRef.authWithPassword(username, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("Successfully authenticated with uid: " + authData.getUid());
                EventBus.getDefault().post(new LoginCallbackEvent(true, null));
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                EventBus.getDefault().post(new LoginCallbackEvent(false, firebaseError.getMessage()));
            }
        });
    }


}
