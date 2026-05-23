package com.example.dayflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String PREFS = "dayflow_prefs";
    private static final String KEY_ONBOARDING_DONE = "onboarding_done";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);

            boolean onboardingDone = sp.getBoolean(KEY_ONBOARDING_DONE, false);
            boolean isLoggedIn = sp.getBoolean(KEY_IS_LOGGED_IN, false);

            Intent intent;
            if (!onboardingDone) {
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            } else {
                if (isLoggedIn) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
            }

            startActivity(intent);
            finish();

        }, 2000);
    }
}
