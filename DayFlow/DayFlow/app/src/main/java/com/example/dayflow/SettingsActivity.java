package com.example.dayflow;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dayflow.notifications.NotificationHelper;
import com.example.dayflow.notifications.ReminderScheduler;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS = "dayflow_prefs";
    private static final String KEY_NOTIF = "notif_enabled";
    private static final int REQ_NOTIF = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView btnBack = findViewById(R.id.btnBack);

        // Profile
        TextView tvUserName = findViewById(R.id.tvName);
        TextView tvUserEmail = findViewById(R.id.tvEmail);

        LinearLayout rowEditProfile = findViewById(R.id.rowEditProfile);
        LinearLayout rowChangePassword = findViewById(R.id.rowChangePassword);
        LinearLayout rowPrivacyPolicy = findViewById(R.id.rowPrivacyPolicy);
        LinearLayout rowBackup = findViewById(R.id.rowBackup);
        LinearLayout rowRestore = findViewById(R.id.rowRestore);
        LinearLayout rowAbout = findViewById(R.id.rowAbout);
        LinearLayout rowDelete = findViewById(R.id.rowDelete);

        Switch switchNotifications = findViewById(R.id.switchNotifications);
        TextView tvVersion = findViewById(R.id.tvVersion);

        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);

        // Set name + email
        tvUserName.setText(sp.getString("user_name", "User"));
        tvUserEmail.setText(sp.getString("user_email", ""));

        btnBack.setOnClickListener(v -> finish());

        tvVersion.setText("Version 1.0");

        // Load switch
        boolean enabled = sp.getBoolean(KEY_NOTIF, true);
        switchNotifications.setChecked(enabled);

        // 🔔 NOTIFICATION SWITCH LOGIC
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sp.edit().putBoolean(KEY_NOTIF, isChecked).apply();

            if (isChecked) {
                if (Build.VERSION.SDK_INT >= 33 &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                                != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            REQ_NOTIF
                    );
                    return;
                }

                NotificationHelper.createChannel(this);
                ReminderScheduler.scheduleDaily(this, 9, 0); // ⏰ 9 AM
                Toast.makeText(this, "Daily reminder ON", Toast.LENGTH_SHORT).show();

            } else {
                ReminderScheduler.cancel(this);
                Toast.makeText(this, "Notifications OFF", Toast.LENGTH_SHORT).show();
            }
        });

        // Pages
        rowEditProfile.setOnClickListener(v ->
                startActivity(new Intent(this, EditProfileActivity.class)));

        rowChangePassword.setOnClickListener(v ->
                startActivity(new Intent(this, ChangePasswordActivity.class)));

        rowPrivacyPolicy.setOnClickListener(v ->
                startActivity(new Intent(this, PrivacyPolicyActivity.class)));

        rowBackup.setOnClickListener(v ->
                startActivity(new Intent(this, BackupActivity.class)));

        rowRestore.setOnClickListener(v ->
                startActivity(new Intent(this, RestoreActivity.class)));

        rowAbout.setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class)));

        // Delete Account
        rowDelete.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("This will remove your account data from this device. Are you sure?")
                .setPositiveButton("Delete", (d, w) -> {
                    sp.edit().clear().apply();
                    SessionManager.logout(this);
                    goToLoginAndClearStack();
                })
                .setNegativeButton("Cancel", null)
                .show());

        // Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            sp.edit().clear().apply();
            SessionManager.logout(this);
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            goToLoginAndClearStack();
        });
    }

    private void goToLoginAndClearStack() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
