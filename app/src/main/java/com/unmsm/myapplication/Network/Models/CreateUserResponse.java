package com.unmsm.myapplication.Network.Models;

/**
 * Created by rubymobile on 10/05/17.
 */


public class CreateUserResponse {
    int id;
    String username;
    String first_name;
    String last_name;
    String email;
    DetailUser detailuser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DetailUser getDetailuser() {
        return detailuser;
    }

    public void setDetailuser(DetailUser detailuser) {
        this.detailuser = detailuser;
    }
}
