package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskActivity2 extends AppCompatActivity {
    ImageView ivBack2Menu;
    FloatingActionButton fabAddTask;

    Spinner spinnerRange;
    ArrayList<String> range;
    ArrayAdapter<String> arrayAdapterRange;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        // Get current user ID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
        } else {
            Intent intent = new Intent(TaskActivity2.this, LoginActivity.class);
            startActivity(intent);
        }

        // Kết nối database
        mDatabase = FirebaseDatabase.getInstance().getReference(UID);

        // Quay lại MenuFragment
        ivBack2Menu = findViewById(R.id.ivBack2Menu);
        ivBack2Menu.setOnClickListener(v -> finish());

        // Tạo spinner chọn khoảng thời gian để hiển thị task
        range = new ArrayList<String>( Arrays.asList(getString(R.string.all_task),
                getString(R.string.today), getString(R.string.tomorrow),getString(R.string.week),
                getString(R.string.completed),getString(R.string.uncompleted)));

        spinnerRange = findViewById(R.id.spinnerRange);
        arrayAdapterRange = new ArrayAdapter<>(this, R.layout.spinner_selected_item_task, range);
        arrayAdapterRange.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRange.setAdapter(arrayAdapterRange);


    }
}