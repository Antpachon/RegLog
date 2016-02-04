package com.suitapps.reglog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.suitapps.reglog.events.RegisterCallbackEvent;
import com.suitapps.reglog.servicesimpl.RegisterServiceFirebaseImpl;
import com.suitapps.reglog.servicesimpl.RegisterServiceParseImpl;
import com.suitapps.reglog.utils.ImagePicker;

import de.greenrobot.event.EventBus;


/**
 * Registration Screen
 */
public class RegistrationActivity extends RegLogBaseActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;
    private EditText mUserNameView;
    private ImageView mUserImage;
    private View mProgressView;
    private View mLoginFormView;

    private Bitmap mProfilePicutreBitmap;

    private boolean mIsRegistering = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mUserNameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordConfirmView = (EditText) findViewById(R.id.passwordConfirm);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mUserImage = (ImageView) findViewById(R.id.profileImageView);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        findViewById(R.id.pictureAdd).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(RegistrationActivity.this);
                startActivityForResult(chooseImageIntent, ImagePicker.PICK_IMAGE_ID);
            }
        });

    }



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        if (!mIsRegistering){
            mIsRegistering = true;
            // Reset errors.
            mEmailView.setError(null);
            mPasswordView.setError(null);

            // Store values at the time of the login attempt.
            String email = mEmailView.getText().toString();
            String username = mUserNameView.getText().toString();
            String password = mPasswordView.getText().toString();
            String passwordConfirm = mPasswordConfirmView.getText().toString();

            boolean cancel = false;
            View focusView = null;

            // Check for a valid password, if the user entered one.
            if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                focusView = mPasswordView;
                cancel = true;
            }

            if (TextUtils.isEmpty(passwordConfirm) || !passwordConfirm.equals(password)) {
                mPasswordView.setError(getString(R.string.error_invalid_password_match));
                focusView = mPasswordConfirmView;
                cancel = true;
            }

            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                focusView = mEmailView;
                cancel = true;
            }

            // Check for a valid username.
            if (TextUtils.isEmpty(username) || !isUsernameValid(username)) {
                mUserNameView.setError(getString(R.string.error_invalid_username));
                focusView = mUserNameView;
                cancel = true;
            }

            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
                mIsRegistering = false;
            } else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                showProgress(true);
                new RegisterServiceFirebaseImpl().register(email,username,password,mProfilePicutreBitmap);
            }
        }

    }



    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ImagePicker.PICK_IMAGE_ID:
                mProfilePicutreBitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                mUserImage.setImageBitmap(mProfilePicutreBitmap);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    /**
     * Listen for the SignUp callback
     * @param registerCallbackEvent
     */
    public void onEvent(RegisterCallbackEvent registerCallbackEvent){
        showProgress(false);
        closeKeyboard();
        mIsRegistering = false;

        if (registerCallbackEvent.isRegistered()){
            Toast.makeText(RegistrationActivity.this, getString(R.string.registration_completed), Toast.LENGTH_LONG).show();
            //Go back to login activity.
            finish();
        } else {
            Toast.makeText(RegistrationActivity.this, registerCallbackEvent.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}



