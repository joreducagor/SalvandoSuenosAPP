package com.unmsm.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.unmsm.myapplication.Network.Models.TokenResponse;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;
import com.unmsm.myapplication.Utils.SharedPreferencesHelper;

import java.io.UnsupportedEncodingException;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;

    SharedPreferencesHelper manager;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "2yZlN4gTUMAD9pTiGcmth0caF";
    public static final String TWITTER_SECRET = "LUXIdua288lSzo04aI4M7L2nz3PCiKDMGx6olQh8nei5PudYFM";
    String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        manager = SharedPreferencesHelper.getInstance(this);

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if(session != null){
            openMain();
        }

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        setTwitterLogin(loginButton);
    }

    private void getBasicToken(String token, String secret) {
        Log.e("token",""+token);
        Log.e("secret",""+secret);
        String newString = token + ":" + secret;

        byte[] data = new byte[0];
        try {
            data = newString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        base64 = Base64.encodeToString(data, Base64.DEFAULT).trim().replace("\n", "");

        manager.setBasicToken(base64);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void setTwitterLogin(TwitterLoginButton login_button_twitter) {
        login_button_twitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                getBasicToken(token,secret);

                manager.setTwitterUserToken(token);
                manager.setTwitterUserSecret(secret);

                openMain();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
