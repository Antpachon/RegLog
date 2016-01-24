package com.suitapps.reglog.events;

/**
 * Created by antonio on 22/01/2016.
 */
public class RegisterCallbackEvent {

    //TODO Customize the data needed in the respose

    public RegisterCallbackEvent(boolean isRegistered, String message){
        mIsRegistered = isRegistered;
        mMessage = message;
    }

    private String mMessage;
    private boolean mIsRegistered;

    public boolean isRegistered(){
        return mIsRegistered;
    }

    public String getMessage(){
        return mMessage;
    }
}
