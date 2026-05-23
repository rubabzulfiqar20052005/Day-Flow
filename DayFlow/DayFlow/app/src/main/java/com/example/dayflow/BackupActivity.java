package com.example.dayflow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BackupActivity extends AppCompatActivity {

    private static final String PREFS = "dayflow_prefs";
    private static final String KEY_LAST_BACKUP = "last_backup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvBackupStatus = findViewById(R.id.tvBackupStatus);

        btnBack.setOnClickListener(v -> finish());

        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
        String last = sp.getString(KEY_LAST_BACKUP, "Not available");
        tvBackupStatus.setText("Last backup: " + last);

        findViewById(R.id.btnBackupNow).setOnClickListener(v -> {
            String now = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                    .format(new Date());

            sp.edit().putString(KEY_LAST_BACKUP, now).apply();
            tvBackupStatus.setText("Last backup: " + now);
            Toast.makeText(this, "Backup completed (demo)", Toast.LENGTH_SHORT).show();
        });
    }
}
