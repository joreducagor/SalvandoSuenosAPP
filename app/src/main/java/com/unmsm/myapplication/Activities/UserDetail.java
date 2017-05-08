package com.unmsm.myapplication.Activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;
import com.unmsm.myapplication.Network.CustomCallback;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;


import retrofit2.Call;
import retrofit2.Response;

public class UserDetail extends ListActivity {

    ProgressBar pb;
    MyTwitterApiClient myApiClient;
    TwitterSession activeSession;
    TextView tv_full_name;
    TextView tv_user_name;
    ImageView iv_user_image;
    User current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Long user_id = getIntent().getLongExtra("USER_ID", 0);
        String user_id_s = String.valueOf(user_id);
        pb = (ProgressBar)findViewById(R.id.pb);
        iv_user_image = (ImageView)findViewById(R.id.iv_user_image);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_full_name = (TextView) findViewById(R.id.tv_full_name);
        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        myApiClient = new MyTwitterApiClient(activeSession);
        getUser(user_id_s);
    }

    private void getUser(final String user_id) {
        pb.setVisibility(View.VISIBLE);

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(Long.parseLong(user_id)).enqueue(new CustomCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    current = response.body();

                    UserTimeline userTimeLine = new UserTimeline.Builder().userId(current.getId()).build();

                    Picasso.with(UserDetail.this).load(response.body().profileImageUrl).into(iv_user_image);

                    tv_user_name.setText("@" + response.body().screenName);
                    tv_full_name.setText(response.body().name);

                    final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(UserDetail.this)
                            .setTimeline(userTimeLine)
                            .build();
                    UserDetail.this.setListAdapter(adapter);

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
