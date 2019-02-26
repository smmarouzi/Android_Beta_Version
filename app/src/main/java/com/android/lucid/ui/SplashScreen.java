package com.android.lucid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1000;
    private final Handler mHandler = new Handler();
    private final Launcher mLauncher = new Launcher();

    private class Launcher implements Runnable {
        @Override
        public void run() {
            launchMainView();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(mLauncher, SPLASH_DELAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mLauncher);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
    }

    private void launchMainView() {
        if (!isFinishing()) {
            Intent intent = new Intent(SplashScreen.this, MainView.class);
            startActivity(intent);
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

}