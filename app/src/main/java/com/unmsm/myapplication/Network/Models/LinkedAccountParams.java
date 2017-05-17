package com.unmsm.myapplication.Network.Models;

/**
 * Created by rubymobile on 12/05/17.
 */

public class LinkedAccountParams{
    String twitter_user_id;
    String twitter_screen_name;

    public String getTwitter_screen_name() {
        return twitter_screen_name;
    }

    public void setTwitter_screen_name(String twitter_screen_name) {
        this.twitter_screen_name = twitter_screen_name;
    }

    public String getTwitter_user_id() {
        return twitter_user_id;
    }

    public void setTwitter_user_id(String twitter_user_id) {
        this.twitter_user_id = twitter_user_id;
    }
}