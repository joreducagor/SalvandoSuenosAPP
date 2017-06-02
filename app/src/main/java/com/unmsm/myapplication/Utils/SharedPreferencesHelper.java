package com.unmsm.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RubyMobile-1 on 23/09/2016.
 */

public class SharedPreferencesHelper {


    public static final String SESSION_TOKEN = "Token";
    private static final String USER_NAME = "user_name";
    private static final String NAME = "SalvandoSueñosApp";
    private static final String TWITTER_USER_TOKEN = "twitterUserToken";
    private static final String TWITTER_USER_SECRET = "twitterUserSecret";
    private static final String TWITTER_IMAGE = "twitterImage";
    private static final String TWITTER_NICK = "twitterNick";
    private static final String AUTH_TOKEN = "authToken";
    private static final String BASIC_TOKEN = "basic_token";
    private static final String USER_ID_DB = "user_id_db";
    private static final String FCM_TOKEN = "fcm_token";
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
    public void logout() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_NAME, "");
        editor.putString(TWITTER_NICK, "");
        editor.putString(TWITTER_IMAGE, "");
        editor.putString(USER_ID_DB, "");
        editor.apply();
    }

    public void setUserName(String userName){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public String getUserName(){
        return mPreferences.getString(USER_NAME, "");
    }

    public void setFcmToken(String fcmToken){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(FCM_TOKEN, fcmToken);
        editor.apply();
    }

    public String getFcmToken(){
        return mPreferences.getString(FCM_TOKEN, "");
    }

    public void setUserIdDb(int id){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_DB, id);
        editor.apply();
    }

    public int getUserIdDb(){
        return mPreferences.getInt(USER_ID_DB, 0);
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
