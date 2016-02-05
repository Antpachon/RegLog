package com.suitapps.reglog;

import com.firebase.client.Firebase;

/**
 * Class used to set references to the different backends from the main application
 */
public class RegLog {

    static Firebase sFireBaseRef;

    public static void setFirebaseReference(Firebase firebase){
        sFireBaseRef = firebase;
    }

    public static void setRetrofitReference(){

    }

    public static Firebase getFirebase(){
        return sFireBaseRef;
    }
}

