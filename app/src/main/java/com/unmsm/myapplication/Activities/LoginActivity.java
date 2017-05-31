package com.unmsm.myapplication.Activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.Models.CreateUserResponse;
import com.unmsm.myapplication.Network.Models.DetailUser;
import com.unmsm.myapplication.Network.Models.TokenResponse;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;
import com.unmsm.myapplication.Utils.SharedPreferencesHelper;

import java.io.UnsupportedEncodingException;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Response;
import com.google.android.gms.common.GoogleApiAvailability;

public class LoginActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;

    User current;

    SharedPreferencesHelper manager;

    TwitterSession activeSession;

    MyTwitterApiClient myApiClient;

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
            return;
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
                activeSession = Twitter.getSessionManager().getActiveSession();
                myApiClient = new MyTwitterApiClient(activeSession);
                TwitterAuthToken authToken = activeSession.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                getBasicToken(token,secret);

                manager.setTwitterUserToken(token);
                manager.setTwitterUserSecret(secret);

                getUser();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this,"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getUser() {

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(activeSession.getId()).enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    current = response.body();
                    createUserService();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"La cagaste imbecil",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createUserService() {
        CreateUserResponse body = new CreateUserResponse();
        body.setUsername(current.screenName);
        body.setFirst_name(current.name);
        body.setLast_name(current.name);
        body.setEmail(current.email);

        DetailUser detailUser = new DetailUser();
        detailUser.setTwitter_token(activeSession.getAuthToken().token);
        detailUser.setLocation(current.location);
        detailUser.setDescription(current.description);

        body.setDetailuser(detailUser);


        Call<CreateUserResponse> call = SalvandoSuenosApplication.getInstance().getServices().createUser(body);

        call.enqueue(new retrofit2.Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                if(response.isSuccessful()){

                    manager = SharedPreferencesHelper.getInstance(LoginActivity.this);

                    manager.setUserIdDb(response.body().getId());

                    manager.setUserName(response.body().getFirst_name());

                    manager.setTwitterNick(response.body().getUsername());

                    manager.setTwitterImage(current.profileImageUrl);

                    openMain();
                }
            }

            @Override
            public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    private void openMain() {
        startActivity(new Intent(this,MainActivity.class));
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Jorgito", "Token: " + token);
        finish();
    }

    /**
     * This method validate if the mobile has google play Service to date
     * @return is to date or not
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        Log.e("josh", "resultCode : "+resultCode);
        //values to result code:
        // SUCCESS = 0
        // SERVICE_MISSING = 1
        // SERVICE_VERSION_UPDATE_REQUIRED = 2
        // SERVICE_DISABLED = 3
        // SERVICE_INVALID = 9
        // SERVICE_MISSING_PERMISSION = 19
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                setupAlertPlayService();
            } else {
                Log.e("josh", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * this alert is show when google play service is out of date.
     */
    private void setupAlertPlayService(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Google Play services out of date, please update it")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //receive the token from service
//    BroadcastReceiver tokenReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String token = intent.getStringExtra("token");
//            if(token != null && checkPlayServices())
//            {
//                MainActivity.this.hideProgressDialog();
//            }
//            if(press){
//                et_token.setText(token);
//            }
//
//        }
//    };

}
