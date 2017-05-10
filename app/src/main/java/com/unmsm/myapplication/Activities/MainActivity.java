package com.unmsm.myapplication.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Adapter.TabsAdapter;
import com.unmsm.myapplication.Adapter.UserAdapter;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.Models.CreateUserResponse;
import com.unmsm.myapplication.Network.Models.DetailUser;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView iv_image;
    TextView tv_user_screen;
    TextView tv_user_name;




    TwitterSession activeSession;
    MyTwitterApiClient myApiClient;
    User current;

    ProgressBar pb;


    TabLayout tabs;
    ViewPager pager;
    TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_screen = (TextView) findViewById(R.id.tv_user_screen);
        pb = (ProgressBar) findViewById(R.id.pb);
        setupTabs();

        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        myApiClient = new MyTwitterApiClient(activeSession);

        getUser();
    }

    private void setupTabs() {
        tabs = (TabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        pager.setAdapter(tabsAdapter);
        tabs.setupWithViewPager(pager);

        //nombre a pestañas
        tabs.getTabAt(0).setText("Search");
        tabs.getTabAt(1).setText("Linked Accounts");

        tabs.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white ));

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
    }

    public void setupViews(){

        tv_user_screen.setText("@" + activeSession.getUserName());
        Picasso.with(this).load(current.profileImageUrl).into(iv_image);


    }



    private void getUser() {
        pb.setVisibility(View.VISIBLE);

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(activeSession.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    current = response.body();
                    tv_user_name.setText(current.name);
                    setupViews();
                    createUserService();
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pb.setVisibility(View.GONE);
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

        call.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"User created",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
