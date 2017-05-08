package com.unmsm.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RubyMobile-1 on 23/09/2016.
 */

public class SharedPreferencesHelper {


    public static final String SESSION_TOKEN = "Token";
    private static final String USER = "currentUser";
    private static final String NAME = "sportsventing";
    private static final String TWITTER_USER_TOKEN = "twitterUserToken";
    private static final String TWITTER_USER_SECRET = "twitterUserSecret";
    private static final String TWITTER_IMAGE = "twitterImage";
    private static final String TWITTER_NICK = "twitterNick";
    private static final String AUTH_TOKEN = "authToken";
    private static final String BASIC_TOKEN = "basic_token";
    private static SharedPreferencesHelper self;
    private final SharedPreferences mPreferences;

    private SharedPreferencesHelper(Context context) {
        mPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (self == null) {
            self = new SharedPreferencesHelper(context);
        }

        return self;
    }
    public void deleteAllSharedPreferences() {
        mPreferences.edit().clear().apply();
    }


    public void setBasicToken(String basic){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(BASIC_TOKEN, basic);
        editor.apply();
    }

    public String getBasicToken(){
        return mPreferences.getString(BASIC_TOKEN, "");
    }

    public void setTwitterUserToken(String socialToken) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(TWITTER_USER_TOKEN, socialToken);
        editor.apply();
    }

    public String getTwitterUserToken() {
        return mPreferences.getString(TWITTER_USER_TOKEN, "");
    }

    public void setTwitterUserSecret(String userKey) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(TWITTER_USER_SECRET, userKey);
        editor.apply();
    }

    public String getTwitterUserSecret() {
        return mPreferences.getString(TWITTER_USER_SECRET, "");
    }

    public void setTwitterImage(String image){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(TWITTER_IMAGE, image);
        editor.apply();
    }

    public String getTwitterImage(){
        return mPreferences.getString(TWITTER_IMAGE, "");
    }

    public void setTwitterNick(String nick){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(TWITTER_NICK, nick);
        editor.apply();
    }

    public String getTwitterNick(){
        return mPreferences.getString(TWITTER_NICK, "");
    }

    public void setAuthToken(String auth){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AUTH_TOKEN, auth);
        editor.apply();
    }

    public String getAuthToken(){
        return mPreferences.getString(AUTH_TOKEN, "");
    }

}
