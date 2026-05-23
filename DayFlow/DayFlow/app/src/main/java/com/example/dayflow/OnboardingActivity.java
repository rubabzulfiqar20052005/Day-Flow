package com.example.dayflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private static final String PREFS = "dayflow_prefs";
    private static final String KEY_ONBOARDING_DONE = "onboarding_done";

    private ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        pager = findViewById(R.id.onboardingPager);
        Button btnNext = findViewById(R.id.btnNext);
        Button btnSkip = findViewById(R.id.btnSkip);

        List<OnboardingItem> list = new ArrayList<>();
        list.add(new OnboardingItem("Plan Your Day", "Create daily tasks quickly."));
        list.add(new OnboardingItem("Stay on Track", "Reminders and schedules."));
        list.add(new OnboardingItem("See Progress", "Insights and stats."));

        pager.setAdapter(new OnboardingAdapter(list));

        // Skip -> onboarding complete
        btnSkip.setOnClickListener(v -> completeOnboarding());

        btnNext.setOnClickListener(v -> {
            if (pager.getCurrentItem() < list.size() - 1) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            } else {
                // Last screen -> onboarding complete
                completeOnboarding();
            }
        });
    }

    private void completeOnboarding() {
        // Save flag so onboarding shows only once
        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
        sp.edit().putBoolean(KEY_ONBOARDING_DONE, true).apply();

        // Go to Login
        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }
}
