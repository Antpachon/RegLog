package com.suitapps.reglog.events;

/**
 * Created by antonio on 22/01/2016.
 */
public class LoginCallbackEvent {

    //TODO Customize the data needed in the respose

    public LoginCallbackEvent(boolean isLoggedIn, String message){
        mIsLoggedIn = isLoggedIn;
        mMessage = message;
    }

    private String mMessage;
    private boolean mIsLoggedIn;

    public boolean isLoggedIn(){
        return mIsLoggedIn;
    }

    public String getMessage(){
        return mMessage;
    }
}
