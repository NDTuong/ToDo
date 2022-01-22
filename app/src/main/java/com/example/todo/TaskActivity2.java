package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import com.example.todo.Adapter.TaskAdapter2;
import com.example.todo.Fragment.BottomSheetDialogTask;
import com.example.todo.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskActivity2 extends AppCompatActivity {
    ImageView ivBack2Menu;
    FloatingActionButton fabAddTask;
    RecyclerView rcvTask;
    TaskAdapter2 taskAdapter;

    Spinner spinnerRange;
    ArrayList<String> range;
    ArrayAdapter<String> arrayAdapterRange;
    ArrayList<Task> tasks;

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
        tasks = new ArrayList<>();

        fabAddTask = (FloatingActionButton) findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogTask bottomSheet = new BottomSheetDialogTask();
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
        });

        getListTaskNShow();
    }

    private void getListTaskNShow(){

        //lấy từ database
        mDatabase.child("task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task mTask = snapshot.getValue(Task.class);
                    assert mTask != null;
                    tasks.add(mTask);
                }
                showTask(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showTask(ArrayList<Task> tasks){
        // Hiển thị danh sách task ra màn hình
        rcvTask = (RecyclerView) findViewById(R.id.recycleViewTask);
        taskAdapter = new TaskAdapter2(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvTask.setLayoutManager(linearLayoutManager);
        taskAdapter.setData(tasks, UID);
        rcvTask.setAdapter(taskAdapter);
    }
}