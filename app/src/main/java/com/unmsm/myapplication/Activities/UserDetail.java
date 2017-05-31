package com.unmsm.myapplication.Activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.unmsm.myapplication.Network.Models.DeleteLinkedAccountbody;
import com.unmsm.myapplication.Network.Models.LinkedAccountBody;
import com.unmsm.myapplication.Network.Models.LinkedAccountParams;
import com.unmsm.myapplication.Network.Models.VerifyResponse;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;


import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetail extends ListActivity {

    ProgressBar pb;
    MyTwitterApiClient myApiClient;
    TwitterSession activeSession;
    TextView tv_full_name;
    TextView tv_user_name;
    CircleImageView iv_user_image;
    User current;
    User sessionUser;
    Button bt_vincular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        String user_id_s = getIntent().getStringExtra("USER_ID");
        pb = (ProgressBar)findViewById(R.id.pb);
        iv_user_image = (CircleImageView)findViewById(R.id.iv_user_image);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_full_name = (TextView) findViewById(R.id.tv_full_name);
        bt_vincular = (Button) findViewById(R.id.bt_vincular);

        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        myApiClient = new MyTwitterApiClient(activeSession);
        //get Other user Data
        getUser(user_id_s,true);
        //Get session user data
        getUser(String.valueOf(activeSession.getUserId()),false);

        bt_vincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedAccountBody linkedAccountBody = new LinkedAccountBody();
                linkedAccountBody.setUsername(sessionUser.screenName);

                LinkedAccountParams linkedAccountParams = new LinkedAccountParams();
                linkedAccountParams.setTwitter_user_id(current.idStr);
                linkedAccountParams.setTwitter_screen_name(current.screenName);
                linkedAccountParams.setTwitter_user_name(current.name);
                linkedAccountParams.setTwitter_image_url(current.profileImageUrl);

                linkedAccountBody.setLinked_account_params(linkedAccountParams);

                Call<Void> call = SalvandoSuenosApplication.getInstance().getServices().linkUser(linkedAccountBody);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(UserDetail.this,"Usuario vinculado con éxito.",Toast.LENGTH_SHORT).show();
                            bt_vincular.setText("Usuario vinculado");
                            bt_vincular.setEnabled(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void verifyLinkedAccount() {
        DeleteLinkedAccountbody body = new DeleteLinkedAccountbody();
        body.setUsername(activeSession.getUserName());
        body.setTwitter_user_id(current.idStr);

        Call<VerifyResponse> call = SalvandoSuenosApplication.getInstance().getServices().verifyLinkedAccount(body);

        call.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isLinked_account()){
                        bt_vincular.setText("Cuenta Vinculada");
                        bt_vincular.setEnabled(false);
                    }else{
                        bt_vincular.setText("Vincular Cuenta");
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {

            }
        });
    }

    private void getUser(final String user_id, final boolean showTweets) {
        pb.setVisibility(View.VISIBLE);

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(Long.parseLong(user_id)).enqueue(new CustomCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){

                    if(showTweets){

                        current = response.body();
                        UserTimeline userTimeLine = new UserTimeline.Builder().userId(current.getId()).build();

                        Picasso.with(UserDetail.this).load(response.body().profileImageUrl).into(iv_user_image);

                        tv_user_name.setText("@" + response.body().screenName);
                        tv_full_name.setText(response.body().name);
                        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(UserDetail.this)
                                .setTimeline(userTimeLine)
                                .build();
                        UserDetail.this.setListAdapter(adapter);

                        verifyLinkedAccount();
                    }else{
                        sessionUser = response.body();
                    }


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
