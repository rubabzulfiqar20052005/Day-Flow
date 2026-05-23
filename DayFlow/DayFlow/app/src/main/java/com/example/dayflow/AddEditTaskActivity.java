package com.example.dayflow;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dayflow.db.TaskEntity;
import com.example.dayflow.db.TaskRepository;

public class AddEditTaskActivity extends AppCompatActivity {

    private TaskRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        repo = new TaskRepository(this);

        ImageView btnBack = findViewById(R.id.btnBack);
        EditText etTitle = findViewById(R.id.etTitle);
        EditText etNotes = findViewById(R.id.etNotes);
        RadioGroup rgPriority = findViewById(R.id.rgPriority);
        Button btnSave = findViewById(R.id.btnSave);

        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                etTitle.setError("Title required");
                return;
            }

            int selectedId = rgPriority.getCheckedRadioButtonId();
            RadioButton rb = findViewById(selectedId);
            String priority = (rb != null) ? rb.getText().toString() : "Medium";

            TaskEntity t = new TaskEntity(
                    title,
                    notes,
                    priority,
                    false,
                    System.currentTimeMillis()
            );

            repo.insert(t);

            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
