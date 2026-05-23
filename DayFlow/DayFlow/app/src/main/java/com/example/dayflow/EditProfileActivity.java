package com.example.dayflow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private static final String PREFS = "dayflow_prefs";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageView btnBack = findViewById(R.id.btnBack);
        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);

        SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
        etName.setText(sp.getString(KEY_NAME, ""));
        etEmail.setText(sp.getString(KEY_EMAIL, ""));

        btnBack.setOnClickListener(v -> finish());

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Name required");
                return;
            }
            if (email.isEmpty()) {
                etEmail.setError("Email required");
                return;
            }

            sp.edit()
                    .putString(KEY_NAME, name)
                    .putString(KEY_EMAIL, email)
                    .apply();

            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
