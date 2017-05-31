package com.unmsm.myapplication.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Adapter.TabsAdapter;
import com.unmsm.myapplication.Adapter.UserAdapter;
import com.unmsm.myapplication.Fragment.DrawerFragment;
import com.unmsm.myapplication.Fragment.LinkedAccountsFragment;
import com.unmsm.myapplication.Fragment.ResultsFragment;
import com.unmsm.myapplication.Fragment.SearchFragment;
import com.unmsm.myapplication.Network.CustomService;
import com.unmsm.myapplication.Network.Models.CreateUserResponse;
import com.unmsm.myapplication.Network.Models.DetailUser;
import com.unmsm.myapplication.Network.MyTwitterApiClient;
import com.unmsm.myapplication.R;
import com.unmsm.myapplication.SalvandoSuenosApplication;
import com.unmsm.myapplication.Utils.SharedPreferencesHelper;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

//    ImageView iv_image;
//    TextView tv_user_screen;
//    TextView tv_user_name;

    SharedPreferencesHelper manager;

    public static final String TWITTER_KEY = "2yZlN4gTUMAD9pTiGcmth0caF";
    public static final String TWITTER_SECRET = "LUXIdua288lSzo04aI4M7L2nz3PCiKDMGx6olQh8nei5PudYFM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = SharedPreferencesHelper.getInstance(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerFragment drawerFragment = (DrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        drawerFragment.setupMenu(R.id.fragment_drawer, (DrawerLayout)findViewById(R.id.drawer_layout),toolbar);
        drawerFragment.setFragmentDrawerListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, new SearchFragment());
        fragmentTransaction.commit();

    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
        Log.e("Marisco", "opcion "+position);
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new SearchFragment();
                break;
            case 1:
                fragment = new LinkedAccountsFragment();
                break;
            case 2:
                fragment = new ResultsFragment();
                break;
            case 3:
                manager.logout();
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            default:
                fragment = new SearchFragment();
                break;
        }

            if(fragment != null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_container, fragment);
                fragmentTransaction.commit();

            }
    }
}
