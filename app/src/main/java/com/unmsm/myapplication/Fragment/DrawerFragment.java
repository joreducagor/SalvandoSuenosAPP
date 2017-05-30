package com.unmsm.myapplication.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unmsm.myapplication.R;
public class DrawerFragment extends Fragment {

    private View drawerFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentDrawerListener fragmentDrawerListener;


    public FragmentDrawerListener getFragmentDrawerListener() {
        return fragmentDrawerListener;
    }

    public void setFragmentDrawerListener(FragmentDrawerListener fragmentDrawerListener) {
        this.fragmentDrawerListener = fragmentDrawerListener;
    }

    public DrawerFragment() {
        // Required empty public constructor
    }


    public static DrawerFragment newInstance() {
        DrawerFragment fragment = new DrawerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    public void setupMenu(int fragmentId, DrawerLayout mDrawerLayout, final Toolbar toolbar){
        drawerFragment = getActivity().findViewById(fragmentId);
        drawerLayout = mDrawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,toolbar,R.string.open,R.string.close){

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1-slideOffset/2);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);
        TextView tv_option1 =  (TextView)v.findViewById(R.id.tv_option1);
        TextView tv_option2 =  (TextView)v.findViewById(R.id.tv_option2);

        tv_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDrawerListener.onDrawerItemSelected(v,0);
                drawerLayout.closeDrawers();
            }
        });

        tv_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDrawerListener.onDrawerItemSelected(v,1);
                drawerLayout.closeDrawers();
            }
        });

        return v;
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(View view, int position);
    }
}
