package com.suitapps.reglogsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.suitapps.reglog.LoginActivity;

/**
 * This is just a wrapper for the RegLog Module
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Launch the Login in your app starting the LoginActivity.
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}
