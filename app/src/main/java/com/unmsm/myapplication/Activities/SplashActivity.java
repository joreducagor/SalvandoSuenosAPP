package com.unmsm.myapplication.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unmsm.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                goNext();
            }
        }, 3500);
    }

    private void goNext() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
