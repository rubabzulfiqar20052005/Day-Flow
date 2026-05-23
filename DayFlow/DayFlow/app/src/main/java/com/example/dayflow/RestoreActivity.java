package com.example.dayflow;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RestoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvRestoreStatus = findViewById(R.id.tvRestoreStatus);

        btnBack.setOnClickListener(v -> finish());

        findViewById(R.id.btnRestoreNow).setOnClickListener(v -> {
            tvRestoreStatus.setText("Restore completed (demo)");
            Toast.makeText(this, "Restore completed (demo)", Toast.LENGTH_SHORT).show();
        });
    }
}
