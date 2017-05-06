package com.unmsm.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetail extends AppCompatActivity {

    ProgressBar pb;
    MyTwitterApiClient myApiClient;
    TwitterSession activeSession;
    User current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Long user_id = getIntent().getLongExtra("USER_ID", 0);
        String user_id_s = String.valueOf(user_id);
        Toast.makeText(this, user_id_s, Toast.LENGTH_LONG).show();
        pb = (ProgressBar)findViewById(R.id.pb);
        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        myApiClient = new MyTwitterApiClient(activeSession);
        getUser(user_id_s);
    }

    private void getUser(String user_id) {
        pb.setVisibility(View.VISIBLE);

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(Long.parseLong(user_id)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    current = response.body();
//                    setupViews();
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pb.setVisibility(View.GONE);
            }
        });
    }

}
