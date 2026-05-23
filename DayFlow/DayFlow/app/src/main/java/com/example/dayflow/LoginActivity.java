package com.example.dayflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayflow.db.UserRepository;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS = "dayflow_prefs";
    private UserRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // auto login
        if (SessionManager.getUserId(this) != -1) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        repo = new UserRepository(this);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);

        TextView tvForgot = findViewById(R.id.tvForgot);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));

        // ✅ FORGOT PASSWORD
        tvForgot.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();

            if (email.isEmpty()) {
                etEmail.setError("Enter your email first");
                return;
            }

            repo.findUserByEmail(email, user -> runOnUiThread(() -> {
                if (user != null) {
                    Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                    i.putExtra("email", email);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }));
        });

        // ✅ LOGIN
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString();

            if (email.isEmpty()) { etEmail.setError("Email required"); return; }
            if (pass.isEmpty()) { etPassword.setError("Password required"); return; }

            repo.login(email, pass, user -> runOnUiThread(() -> {
                if (user != null) {
                    // ✅ Session save
                    SessionManager.saveUserId(this, user.id);

                    // ✅ SharedPreferences me name/email save (Settings screen ke liye)
                    SharedPreferences sp = getSharedPreferences(PREFS, MODE_PRIVATE);
                    sp.edit()
                            .putBoolean("is_logged_in", true)
                            .putString("user_email", email)
                            .putString("user_name", user.name)
                            .apply();

                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show();
                }
            }));
        });
    }
}
