package com.suitapps.reglog.servicesimpl;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.suitapps.reglog.events.LoginCallbackEvent;
import com.suitapps.reglog.services.LoginService;

import de.greenrobot.event.EventBus;

/**
 * SignIn implementation using Parse.com as backend
 */
public class LoginServiceParseImpl implements LoginService {

    private ParseUser mUser;

    @Override
    public void login(String username, String password) {

        //Make sure parse is initialized in the Application class of the main app
        ParseUser.logInInBackground(username, password,
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null){
                            //User exist and authenticated, send user to Welcome.class
                            EventBus.getDefault().post(new LoginCallbackEvent(true,null));
                        } else {
                            EventBus.getDefault().post(new LoginCallbackEvent(false, e.getMessage()));
                        }

                    }
                });

    }


    //TODO Methods that depend on the implementation can be added here, for instance getUser()
    public ParseUser getUser(){
        return mUser;
    }

    // When using Parse.com we don't need to send the user back to the application
    // We can do ParseUser.getCurrentUser() once we are logged in.



}
