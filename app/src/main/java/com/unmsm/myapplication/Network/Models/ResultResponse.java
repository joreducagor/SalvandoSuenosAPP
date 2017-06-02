package com.unmsm.myapplication.Network.Models;

/**
 * Created by rubymobile on 02/06/17.
 */

public class ResultResponse {
    String tweet;
    String owner_screen;
    String owner_name;
    String created_at;

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getOwner_screen() {
        return owner_screen;
    }

    public void setOwner_screen(String owner_screen) {
        this.owner_screen = owner_screen;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
