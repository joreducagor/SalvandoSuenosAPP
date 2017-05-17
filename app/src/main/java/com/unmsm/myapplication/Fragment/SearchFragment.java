package com.unmsm.myapplication.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Activities.MainActivity;
import com.unmsm.myapplication.Adapter.UserAdapter;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rubymobile on 10/05/17.
 */

public class SearchFragment extends Fragment{

    SearchView sv_search;
    RecyclerView rv;
    ProgressBar progress;
    LinearLayoutManager layoutManager;
    UserAdapter adapter;
    int page = 1;
    int count = 20;
    String query;
    boolean isLoading = false;
    List<User> data = new ArrayList<>();

    TwitterSession activeSession;
    MyTwitterApiClient myApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets,container,false);

        activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();

        myApiClient = new MyTwitterApiClient(activeSession);

        sv_search = (SearchView) v.findViewById(R.id.sv_search);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        progress = (ProgressBar) v.findViewById(R.id.progress);

        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                data = new ArrayList<>();
                SearchFragment.this.query = query;
                page = 1;
                count = 20;

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                progress.setVisibility(View.VISIBLE);

                CustomService call = myApiClient.getCustomService();

                call.searchUsers(query,page,count).enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            List<User> temp = new ArrayList(response.body());
                            data.addAll(temp);
                            setDataIntoRv();
                        }
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        setScrollListenerToRv();

        return v;
    }

    private void setScrollListenerToRv() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    if(layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                        if(!isLoading){
                            progress.setVisibility(View.VISIBLE);

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
                                    progress.setVisibility(View.GONE);
                                    isLoading = false;
                                }

                                @Override
                                public void onFailure(Call<List<User>> call, Throwable t) {
                                    progress.setVisibility(View.GONE);
                                    isLoading = false;
                                }
                            });


                        }

                    }
                }
            }
        });
    }

    private void setDataIntoRv() {
        adapter = new UserAdapter(data,getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}
