package com.unmsm.myapplication.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Adapter.TweetAdapter;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetail extends AppCompatActivity {

    ProgressBar pb;
    MyTwitterApiClient myApiClient;
    TwitterSession activeSession;
    RecyclerView rv_tweets;

    User current;
    int count = 10;

    TweetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Long user_id = getIntent().getLongExtra("USER_ID", 0);
        String user_id_s = String.valueOf(user_id);
        Toast.makeText(this, user_id_s, Toast.LENGTH_LONG).show();
        pb = (ProgressBar)findViewById(R.id.pb);
        rv_tweets = (RecyclerView) findViewById(R.id.rv_tweets);
        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        myApiClient = new MyTwitterApiClient(activeSession);
        getUser(user_id_s);
    }

    private void getUser(final String user_id) {
        pb.setVisibility(View.VISIBLE);

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(Long.parseLong(user_id)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    current = response.body();
                    getUserTweets(user_id);
                // setupViews();
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    private void getUserTweets(String user_id) {
        CustomService call = myApiClient.getCustomService();

        call.getUserTimeline(Long.parseLong(user_id),count).enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if(response.isSuccessful()){
                    setDataToRv(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(UserDetail.this,"failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataToRv(List<Tweet> data) {
        adapter = new TweetAdapter(data,this);
        rv_tweets.setLayoutManager(new LinearLayoutManager(this));
        rv_tweets.setAdapter(adapter);
    }
}