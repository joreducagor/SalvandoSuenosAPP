package com.unmsm.myapplication.Network;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unmsm.myapplication.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USUARIO on 17/11/2016.
 */

public class RequestManager {

    private static CustomService defaultRequestManager;
    private static Retrofit retrofit;

    public RequestManager() {
        retrofit = generateRetrofit();
        defaultRequestManager = retrofit.create(CustomService.class);
    }

    public RequestManager(Context context, boolean doRefreshToken) {
        retrofit = doRefreshToken ? generateRetrofit(context) : generateSimpleRetrofit(context);
        defaultRequestManager = retrofit.create(CustomService.class);
    }


    public CustomService getWebServices(){
        return defaultRequestManager;
    }

    private static Retrofit generateRetrofit() {
        Gson gson = new GsonBuilder().create();

        final OkHttpClient client = getOkHttpClient();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL);
        builder = builder.addConverterFactory(GsonConverterFactory.create(gson));
        return builder.client(client).build();
    }

    /**
     * Generates a Retrofit API client the corresponding headers, URl, and converter
     *
     * @return personalized Retrofit.
     */
    private static Retrofit generateRetrofit(final Context context) {
        Gson gson = new GsonBuilder().create();

        final OkHttpClient client = getOkHttpClient(context);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL);
        builder = builder.addConverterFactory(GsonConverterFactory.create(gson));
        return builder.client(client).build();
    }


    /**
     * Generates a Retrofit API client the corresponding headers, URl, and converter.
     * With an OkHttpClient without the option to refresh token if it expires.
     *
     * @return personalized Retrofit.
     */
    private static Retrofit generateSimpleRetrofit(final Context context) {
        Gson gson = new GsonBuilder().create();

        final OkHttpClient client = getSimpleOkHttpClient(context);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL);
        builder = builder.addConverterFactory(GsonConverterFactory.create(gson));
        return builder.client(client).build();
    }

    /**
     * Generates OkHttpClient instance with configured timeouts and auth/logging interceptors
     *
     * @return OkHttpClient
     */
    @NonNull
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(12, TimeUnit.SECONDS)
                .connectTimeout(12, TimeUnit.SECONDS);

        //For adding logs of APIs requests & responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        //General interceptor
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .method(original.method(), original.body());
                return chain.proceed(requestBuilder.build());
            }
        });

        return builder.build();
    }

    private static OkHttpClient getOkHttpClient(final Context context) {
        RetrofitAuthenticator authAuthenticator = new RetrofitAuthenticator(context);

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(12, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .authenticator(authAuthenticator);

        //For adding logs of APIs requests & responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        //General interceptor with authorization token
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String token = getAuthToken(context);
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization","Bearer " + token)
                        .method(original.method(), original.body());
                return chain.proceed(requestBuilder.build());
            }
        });

        return builder.build();
    }

    /**
     * Generates an OkHttpClient without the option to refresh token if it expires.
     * Useful in case you want to get the new refreshed token
     * @param context Context where the method was called from
     * @return OkHttpClient without refresh token option
     */
    private static OkHttpClient getSimpleOkHttpClient(final Context context) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(12, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS);

        //For adding logs of APIs requests & responses
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        //General interceptor with authorization token
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String token = getAuthToken(context);

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization","Bearer " + token)
                        .method(original.method(), original.body());
                return chain.proceed(requestBuilder.build());
            }
        });

        return builder.build();
    }

    private static String getAuthToken(Context context) {
        return null;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}