package com.example.dayflow;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private void setupPasswordToggle(EditText editText, ImageView eyeIcon) {
        eyeIcon.setOnClickListener(v -> {
            if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                eyeIcon.setImageResource(R.drawable.ic_eye);
            } else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                eyeIcon.setImageResource(R.drawable.ic_eye_off);
            }
            editText.setSelection(editText.getText().length());
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // ✅ ADDED: email received from LoginActivity (Forgot Password)
        String email = getIntent().getStringExtra("email");

        ImageView btnBack = findViewById(R.id.btnBack);
        EditText etCurrent = findViewById(R.id.etCurrent);
        EditText etNew = findViewById(R.id.etNew);
        EditText etConfirm = findViewById(R.id.etConfirm);

        ImageView ivToggleCurrent = findViewById(R.id.ivToggleCurrent);
        ImageView ivToggleNew = findViewById(R.id.ivToggleNew);
        ImageView ivToggleConfirm = findViewById(R.id.ivToggleConfirm);

        // ✅ Toggle setup should be here (not inside update click)
        setupPasswordToggle(etCurrent, ivToggleCurrent);
        setupPasswordToggle(etNew, ivToggleNew);
        setupPasswordToggle(etConfirm, ivToggleConfirm);

        btnBack.setOnClickListener(v -> finish());

        findViewById(R.id.btnUpdate).setOnClickListener(v -> {
            String current = etCurrent.getText().toString().trim();
            String n = etNew.getText().toString().trim();
            String c = etConfirm.getText().toString().trim();

            if (current.isEmpty()) {
                etCurrent.setError("Required");
                return;
            }
            if (n.length() < 6) {
                etNew.setError("Min 6 characters");
                return;
            }
            if (!n.equals(c)) {
                etConfirm.setError("Passwords do not match");
                return;
            }

            // ✅ Demo only (later connect real update using email)
            Toast.makeText(this,
                    "Password updated for: " + (email == null ? "" : email),
                    Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}
