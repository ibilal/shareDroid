package com.mbeez.nugshare2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import static com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Google Sign-In Activity";
    private static final int RC_SIGN_IN = 9001;
    private SignInButton googleLoginButton;
    private TextView textView;
    private Intent userProfileIntent;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(GOOGLE_SIGN_IN_API, gso)
                .build();

        textView = (TextView) findViewById(R.id.nameField);
        googleLoginButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        googleLoginButton.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
          } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    /* Handle all login button clicks (Facebook, Twitter, Google)
     *
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in_button:
                signIn();
                break;
        }
    }

    /* Handle sign in button taps by creating a sign in intent with getSignInInten method, and
     * start the intent with startActivityForResult
     */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    /* Retrieve the sign in result, and pass to the handler handleSignInResult
     *
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    /* After retrieving the sign in result, check if it was successful. If sign in was successful
    * call getSignInAccount to retrieve the object that contains the signed in user's information.
    * Else, display login screen again
    */
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            account = result.getSignInAccount();
            textView.setText(account.getEmail());
            handleLoginAttempt(true);
        } else {
            // Signed out
            handleLoginAttempt(false);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }



    private void handleLoginAttempt(boolean signedIn) {
        if (signedIn) {
            Bundle sendBundle = new Bundle();
            sendBundle.putCharSequence("email", account.getEmail());
            sendBundle.putCharSequence("name", account.getDisplayName());
            sendBundle.putCharSequence("profilePicture", account.getPhotoUrl().toString());
            sendBundle.putCharSequence("id", account.getId());
            sendBundle.putCharSequence("idToken", account.getIdToken());

            // go to next activity (Setup User Profile)
            userProfileIntent = new Intent(this, SetupNewUserProfileActivity.class);
            userProfileIntent.putExtras(sendBundle);
            startActivity(userProfileIntent);
            finish();
        } else {
            findViewById(R.id.google_sign_in_button).setVisibility(View.VISIBLE);
            textView.setText("Unsuccessful Login");
        }
    }


}
