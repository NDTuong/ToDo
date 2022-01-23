package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import com.example.todo.Adapter.TaskAdapter;
import com.example.todo.Fragment.BottomSheetDialogTask;
import com.example.todo.Model.SpinnerRangeTask;
import com.example.todo.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskActivity extends AppCompatActivity {
    ImageView ivBack2Menu;
    FloatingActionButton fabAddTask;
    RecyclerView rcvTask;
    TaskAdapter taskAdapter;

    Spinner spinnerRange;
    ArrayList<String> range;
    ArrayAdapter<String> arrayAdapterRange;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String UID;

    SimpleDateFormat DEADLINE_FORMAT = new SimpleDateFormat("dd/M/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

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

        // Quay lại MenuFragment
        ivBack2Menu = findViewById(R.id.ivBack2Menu);
        ivBack2Menu.setOnClickListener(v -> finish());

        // Tạo spinner chọn khoảng thời gian để hiển thị task
        range = new ArrayList<String>( Arrays.asList(getString(R.string.all_task),
                getString(R.string.today), getString(R.string.tomorrow),getString(R.string.week),
                getString(R.string.completed)));

        spinnerRange = findViewById(R.id.spinnerRange);
        arrayAdapterRange = new ArrayAdapter<>(this, R.layout.spinner_selected_item_task, range);
        arrayAdapterRange.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRange.setAdapter(arrayAdapterRange);

        fabAddTask = (FloatingActionButton) findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogTask bottomSheet = new BottomSheetDialogTask();
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
        });
        spinnerRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getListTaskNShow(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("TaskActivity", "selected: null");
            }
        });
    }

    private void getListTaskNShow(int flags){
        SpinnerRangeTask sp = new SpinnerRangeTask();
        //lấy từ database
        mDatabase.child("task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task mTask = snapshot.getValue(Task.class);
                    assert mTask != null;
                    if(tasks.contains(mTask)){
                        Log.d("TaskActivity", "isContains: true");
                        continue;
                    }
                    if(flags==0 && !mTask.isComplete()) {
                        tasks.add(mTask);
                    }
                    if(flags==1 && !mTask.isComplete()){
                        if(mTask.getDeadLine().equals(sp.getToday())){
                            tasks.add(mTask);
                        }
                    }
                    if(flags==2 && !mTask.isComplete()){
                        if(mTask.getDeadLine().equals(sp.getTomorrow())){
                                    tasks.add(mTask);
                        }
                    }
                    if(flags==3 && !mTask.isComplete()) {
                        if(sp.getCurrentWeek().contains(mTask.getDeadLine())){
                            tasks.add(mTask);
                        }
                    }
                    if(flags==4 && mTask.isComplete()){
                        tasks.add(mTask);
                    }
                }
                sortByDate(tasks);
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
        taskAdapter = new TaskAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvTask.setLayoutManager(linearLayoutManager);
        taskAdapter.setData(tasks, UID, mDatabase);
        rcvTask.setAdapter(taskAdapter);
    }

    private void sortByDate(ArrayList<Task> tasks){
        Collections.sort(tasks, new Comparator<Task>() {
            public int compare(Task o1, Task o2) {
                try {
                    return o1.getDate().compareTo(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }
}