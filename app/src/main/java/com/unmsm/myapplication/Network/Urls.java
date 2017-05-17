package com.unmsm.myapplication.Network;

/**
 * Created by RubyMobile-1 on 19/09/2016.
 */
interface Urls {
    String USER_SHOW = "1.1/users/show.json";
    String USER_SEARCH = "1.1/users/search.json";
    String USER_TIMELINE = "1.1/statuses/user_timeline.json";
    String CREATE_USER = "users/";
    String LINK_USER = "linked_accounts/";
    String LINKED_ACCOUNTS = "users/{user_id}/linked_accounts/";
    String DELETE_LINKED_ACCOUNT = "linked_accounts/";
    String VERIFY = "linked_accounts/verify/";
}
