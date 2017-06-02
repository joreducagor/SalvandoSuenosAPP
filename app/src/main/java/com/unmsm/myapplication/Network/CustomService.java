package com.unmsm.myapplication.Network;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Network.Models.CreateUserResponse;
import com.unmsm.myapplication.Network.Models.DeleteLinkedAccountResponse;
import com.unmsm.myapplication.Network.Models.DeleteLinkedAccountbody;
import com.unmsm.myapplication.Network.Models.DeviceBody;
import com.unmsm.myapplication.Network.Models.DeviceResponse;
import com.unmsm.myapplication.Network.Models.LinkedAccountBody;
import com.unmsm.myapplication.Network.Models.LinkedAccountsResponse;
import com.unmsm.myapplication.Network.Models.ResultListResponse;
import com.unmsm.myapplication.Network.Models.VerifyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @POST(Urls.LINK_USER)
    Call<Void> linkUser(@Body LinkedAccountBody linkedAccountBody);

    @GET(Urls.LINKED_ACCOUNTS)
    Call<LinkedAccountsResponse> linkedAccounts(@Path("user_id") String user_id);

    @HTTP(method = "DELETE", path = Urls.DELETE_LINKED_ACCOUNT, hasBody = true)
    Call<DeleteLinkedAccountResponse> deleteLinkedAccount(@Body DeleteLinkedAccountbody deleteLinkedAccountbody);

    @POST(Urls.VERIFY)
    Call<VerifyResponse> verifyLinkedAccount(@Body DeleteLinkedAccountbody deleteLinkedAccountbody);

    @POST(Urls.CREATE_DEVICE)
    Call<DeviceResponse> createDevice(@Body DeviceBody deviceBody);

    @GET(Urls.RESULTS)
    Call<ResultListResponse> getResults(@Path("user_id") String user_id);
}
