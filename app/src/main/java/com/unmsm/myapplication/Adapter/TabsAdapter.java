package com.unmsm.myapplication.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.unmsm.myapplication.Fragment.LinkedAccountsFragment;
import com.unmsm.myapplication.Fragment.SearchFragment;

/**
 * Created by rubymobile on 10/05/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {

    int TAB_COUNT = 2;

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SearchFragment();
            case 1:
                return new LinkedAccountsFragment();
            default:
                return new SearchFragment();
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
