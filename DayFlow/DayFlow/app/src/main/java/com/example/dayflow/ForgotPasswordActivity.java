package com.example.dayflow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayflow.db.AuthUtil;
import com.example.dayflow.db.UserRepository;

public class ForgotPasswordActivity extends AppCompatActivity {

    private UserRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        repo = new UserRepository(this);

        ImageView btnBack = findViewById(R.id.btnBack);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etNew = findViewById(R.id.etNew);
        EditText etConfirm = findViewById(R.id.etConfirm);
        Button btnReset = findViewById(R.id.btnreset);

        btnBack.setOnClickListener(v -> finish());

        btnReset.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String n = etNew.getText().toString().trim();
            String c = etConfirm.getText().toString().trim();

            if (email.isEmpty()) { etEmail.setError("Email required"); return; }
            if (n.length() < 6) { etNew.setError("Min 6 characters"); return; }
            if (!n.equals(c)) { etConfirm.setError("Passwords do not match"); return; }

            // email exists?
            repo.findUserByEmail(email, user -> runOnUiThread(() -> {
                if (user == null) {
                    Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                String hash = AuthUtil.sha256(n);
                repo.updatePasswordByEmail(email, hash, ok -> runOnUiThread(() -> {
                    if (ok) {
                        Toast.makeText(this, "Password reset successful ✅", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                    }
                }));
            }));
        });
    }
}
