package com.unmsm.myapplication.Network.Models;

/**
 * Created by rubymobile on 12/05/17.
 */

public class LinkedAccountBody {
    String username;
    LinkedAccountParams linked_account_params;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LinkedAccountParams getLinked_account_params() {
        return linked_account_params;
    }

    public void setLinked_account_params(LinkedAccountParams linked_account_params) {
        this.linked_account_params = linked_account_params;
    }
}
