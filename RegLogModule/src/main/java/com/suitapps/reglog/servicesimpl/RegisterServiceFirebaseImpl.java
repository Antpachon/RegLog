package com.suitapps.reglog.servicesimpl;

import android.graphics.Bitmap;
import android.util.Base64;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.suitapps.reglog.RegLog;
import com.suitapps.reglog.events.LoginCallbackEvent;
import com.suitapps.reglog.events.RegisterCallbackEvent;
import com.suitapps.reglog.services.RegisterService;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by antonio on 22/01/2016.
 */
public class RegisterServiceFirebaseImpl implements RegisterService {

    @Override
    public void register(String email, final String username, String password, final Bitmap profilePicture) {

        final Firebase myFirebaseRef = RegLog.getFirebase();

        myFirebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                final String myUUID = stringObjectMap.get("uid").toString();

                myFirebaseRef.child("users").child(myUUID).setValue(new MyFireBaseUSer( imageToBase64(profilePicture), username),
                        new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError == null) {
                            //User created correctly
                            EventBus.getDefault().post(new RegisterCallbackEvent(true, null));
                        } else {
                            EventBus.getDefault().post(new RegisterCallbackEvent(false, firebaseError.getMessage()));
                        }
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                EventBus.getDefault().post(new RegisterCallbackEvent(false, firebaseError.getMessage()));
            }
        });
    }


    private class MyFireBaseUSer implements Serializable {
        private String userName;
        private String picture;

        public MyFireBaseUSer(String picture, String userName) {
            this.picture = picture;
            this.userName = userName;
        }

        public String getPicture() {
            return picture;
        }

        public String getUserName() {
            return userName;
        }
    }

    public String imageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }
}
