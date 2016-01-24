package com.suitapps.reglog.services;

import android.graphics.Bitmap;

/**
 * Created by antonio on 21/01/2016.
 */
public interface RegisterService {

    public void register(String email, String username, String password, Bitmap profilePicture);

}
