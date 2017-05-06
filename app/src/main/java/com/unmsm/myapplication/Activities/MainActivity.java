package com.unmsm.myapplication.Activities;

import android.content.Intent;
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
import com.unmsm.myapplication.Adapter.UserAdapter;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView iv_image;
    TextView tv_user;
    SearchView sv_search;
    RecyclerView rv;
    ProgressBar pb;

    List<User> data = new ArrayList<>();
    UserAdapter adapter;
    TwitterSession activeSession;
    MyTwitterApiClient myApiClient;
    User current;
    LinearLayoutManager layoutManager;

    int page = 1;
    int count = 20;
    String query;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_user = (TextView) findViewById(R.id.tv_user);
        sv_search = (SearchView) findViewById(R.id.sv_search);
        rv = (RecyclerView) findViewById(R.id.rv);
        pb = (ProgressBar) findViewById(R.id.pb);

        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        myApiClient = new MyTwitterApiClient(activeSession);

        getUser();
    }

    public void setupViews(){

        tv_user.setText(activeSession.getUserName());

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                data = new ArrayList<>();
                MainActivity.this.query = query;
                page = 1;
                count = 20;

                pb.setVisibility(View.VISIBLE);

                CustomService call = myApiClient.getCustomService();

                call.searchUsers(query,page,count).enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            List<User> temp = new ArrayList(response.body());
                            data.addAll(temp);
                            setDataIntoRv();
                        }
                        pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        pb.setVisibility(View.GONE);
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Picasso.with(this).load(current.profileImageUrl).into(iv_image);

        setScrollListenerToRv();
    }

    private void setScrollListenerToRv() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    if(layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                        if(!isLoading){
                            pb.setVisibility(View.VISIBLE);

                            CustomService call = myApiClient.getCustomService();
                            page++;
                            count += 20 ;
                            isLoading = true;

                            call.searchUsers(query,page,count).enqueue(new Callback<List<User>>() {
                                @Override
                                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                    if(response.isSuccessful()){
                                        List<User> temp = new ArrayList(response.body());

                                        data.addAll(temp);
                                        adapter.notifyDataSetChanged();
                                        rv.scrollToPosition(adapter.getItemCount() - count -1);
                                    }
                                    pb.setVisibility(View.GONE);
                                    isLoading = false;
                                }

                                @Override
                                public void onFailure(Call<List<User>> call, Throwable t) {
                                    pb.setVisibility(View.GONE);
                                    isLoading = false;
                                }
                            });


                        }

                    }
                }
            }
        });
    }

    private void getUser() {
        pb.setVisibility(View.VISIBLE);

        CustomService call = myApiClient.getCustomService();
        call.showCurrentUser(activeSession.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    current = response.body();
                    setupViews();
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pb.setVisibility(View.GONE);
            }
        });
    }

    private void setDataIntoRv() {
        adapter = new UserAdapter(data,this);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
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
