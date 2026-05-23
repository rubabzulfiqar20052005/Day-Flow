package com.example.dayflow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayflow.db.UserRepository;

public class SignupActivity extends AppCompatActivity {

    private UserRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        repo = new UserRepository(this);

        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);

        Button btnCreate = findViewById(R.id.btnCreate);
        TextView tvLoginBack = findViewById(R.id.tvLoginBack);

        tvLoginBack.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

        btnCreate.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString();
            String confirm = etConfirmPassword.getText().toString();

            if (name.isEmpty()) {
                etName.setError("Name required");
                return;
            }
            if (email.isEmpty()) {
                etEmail.setError("Email required");
                return;
            }
            if (pass.length() < 6) {
                etPassword.setError("Password must be at least 6 characters");
                return;
            }
            if (!pass.equals(confirm)) {
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            repo.signup(name, email, pass, success ->
                    runOnUiThread(() -> {
                        if (success) {
                            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "Email already exists", Toast.LENGTH_LONG).show();
                        }
                    })
            );
        });
    }
}
