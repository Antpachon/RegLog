package com.suitapps.reglog.servicesimpl;

import android.graphics.Bitmap;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.suitapps.reglog.events.RegisterCallbackEvent;
import com.suitapps.reglog.services.RegisterService;

import java.io.ByteArrayOutputStream;

import de.greenrobot.event.EventBus;

/**
 * Created by antonio on 22/01/2016.
 */
public class RegisterServiceParseImpl implements RegisterService {

    @Override
    public void register(String email, String username, String password, Bitmap profilePicture) {

        final ParseUser user = new ParseUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);

        if (profilePicture != null){
            byte[] pfArray = getBytesFromBitmap(profilePicture);
            final ParseFile file = new ParseFile("abc.png", pfArray);
            file.saveInBackground();

            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        user.put("photo", file);
                        signUpInBackground(user);
                    } else {
                        EventBus.getDefault().post(new RegisterCallbackEvent(false,e.getMessage()));
                    }
                }
            });
        } else {
            signUpInBackground(user);
        }
    }

    public void signUpInBackground(ParseUser user){
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    EventBus.getDefault().post(new RegisterCallbackEvent(true,null));
                } else {
                    EventBus.getDefault().post(new RegisterCallbackEvent(true,e.getMessage()));
                }
            }
        });
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream.toByteArray();
    }
}
