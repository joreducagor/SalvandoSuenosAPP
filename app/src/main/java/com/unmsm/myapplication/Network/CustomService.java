package com.unmsm.myapplication.Network;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Activities.UserDetail;
import com.unmsm.myapplication.Network.Models.CreateUserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// example users/search service endpoint
public interface CustomService {
    @GET(Urls.USER_SEARCH)
    Call<List<User>> searchUsers(@Query("q") String q, @Query("page") int page, @Query("count") int count);

    @GET(Urls.USER_SHOW)
    Call<User> showCurrentUser(@Query("user_id") long id);

    @GET(Urls.USER_TIMELINE)
    Call<List<Tweet>> getUserTimeline(@Query("user_id") long id, @Query("count") int count);

    @POST(Urls.CREATE_USER)
    Call<CreateUserResponse> createUser(@Body CreateUserResponse body);
}
