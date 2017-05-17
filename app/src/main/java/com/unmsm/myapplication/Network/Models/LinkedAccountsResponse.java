package com.unmsm.myapplication.Network.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NriKe on 16/05/2017.
 */

public class LinkedAccountsResponse {
    List<LinkedAccountItem> linked_accounts = new ArrayList<>();

    public List<LinkedAccountItem> getLinked_accounts() {
        return linked_accounts;
    }

    public void setLinked_accounts(List<LinkedAccountItem> linked_accounts) {
        this.linked_accounts = linked_accounts;
    }
}
