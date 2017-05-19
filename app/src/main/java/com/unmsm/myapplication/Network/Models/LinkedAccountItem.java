package com.unmsm.myapplication.Network.Models;

/**
 * Created by NriKe on 16/05/2017.
 */

public class LinkedAccountItem {
    int id;
    String twitter_user_id;
    String twitter_screen_name;
    String twitter_user_name;
    String twitter_image_url;
    String created_at;

    public String getTwitter_user_name() {
        return twitter_user_name;
    }

    public void setTwitter_user_name(String twitter_user_name) {
        this.twitter_user_name = twitter_user_name;
    }

    public String getTwitter_image_url() {
        return twitter_image_url;
    }

    public void setTwitter_image_url(String twitter_image_url) {
        this.twitter_image_url = twitter_image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTwitter_user_id() {
        return twitter_user_id;
    }

    public void setTwitter_user_id(String twitter_user_id) {
        this.twitter_user_id = twitter_user_id;
    }

    public String getTwitter_screen_name() {
        return twitter_screen_name;
    }

    public void setTwitter_screen_name(String twitter_screen_name) {
        this.twitter_screen_name = twitter_screen_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
