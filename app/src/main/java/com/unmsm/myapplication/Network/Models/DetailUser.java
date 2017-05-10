package com.unmsm.myapplication.Network.Models;

/**
 * Created by rubymobile on 10/05/17.
 */

public class DetailUser{
    String twitter_token;
    String dob;
    String phone;
    boolean enabled;
    String created_at;
    String location;
    String description;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitter_token() {
        return twitter_token;
    }

    public void setTwitter_token(String twitter_token) {
        this.twitter_token = twitter_token;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}