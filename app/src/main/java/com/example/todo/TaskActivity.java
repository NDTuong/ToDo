package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.todo.Adapter.TaskAdapter;
import com.example.todo.Fragment.AddTaskFragment;
import com.example.todo.Model.Task;
import com.example.todo.Model.TimeTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    Spinner spinnerRange;
    ArrayList<String> range;
    ArrayAdapter<String> arrayAdapterRange;

    ImageView ivBack2Menu;
    RecyclerView rcvTask;
    TaskAdapter taskAdapter;

    FloatingActionButton fabAddTask;
    private int count = 0;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Quay lại MenuFragment
        ivBack2Menu = findViewById(R.id.iconBack2Menu);
        ivBack2Menu.setOnClickListener(v -> finish());

        // Get current user ID
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            UID = currentUser.getUid();
        } else {
            Intent intent = new Intent(TaskActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        // Kết nối database
        mDatabase = FirebaseDatabase.getInstance().getReference(UID);

        // Tạo spinner chọn khoảng thời gian để hiển thị task
        range = new ArrayList<>();
        range.add(getString(R.string.all_task));
        range.add(getString(R.string.today));
        range.add(getString(R.string.tomorrow));
        range.add(getString(R.string.week));
        range.add(getString(R.string.expired));
        range.add(getString(R.string.completed));

        spinnerRange = findViewById(R.id.spinnerRange);
        arrayAdapterRange = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, range);
        arrayAdapterRange.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRange.setAdapter(arrayAdapterRange);

        // Set sự kiện khi click fab
        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(v -> {
            fabAddTask.setVisibility(View.GONE);
            loadFragment(new AddTaskFragment());

        });

        getListTaskAndShow();
    }

    // khi bấm quay lại mà có fagment thì đóng
    @Override
    public void onBackPressed() {
        if (count >= 1) {
            closeFragment();
            count = 0;
        } else {
            super.onBackPressed();
        }
    }


    private void showTask(List<Task> tasks){
        // Hiển thị danh sách task ra màn hình
        rcvTask = (RecyclerView) findViewById(R.id.recycleViewTask);
        taskAdapter = new TaskAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvTask.setLayoutManager(linearLayoutManager);
        taskAdapter.setData(tasks);
        rcvTask.setAdapter(taskAdapter);
    }

    private void getListTaskAndShow(){
        List<Task> tasks = new ArrayList<>();
        //lấy từ database
        mDatabase.child("task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task mTask = snapshot.getValue(Task.class);
                    assert mTask != null;
                    if (tasks.contains(mTask)) {
                        continue;
                    }
//                    if (checkUpdateTimeTable(mTimeTable, listTimeTable)) {
//                        showTimeTable(mTimeTable);
//                        continue;
//                    }
                    tasks.add(mTask);
                    showTask(tasks);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Hàm load fragment
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment, "AddTask");
        fragmentTransaction.commit(); // save the changes
        count++;

    }

    // Hàm ẩn bàn phím
    private void hideKeyboard() {
        View v = TaskActivity.this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    // Hàm đóng fragment
    private void closeFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("AddTask");
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
            hideKeyboard();
            fabAddTask.setVisibility(View.VISIBLE);
        }
    }
}