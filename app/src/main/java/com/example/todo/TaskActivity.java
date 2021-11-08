package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.todo.Adapter.TaskAdapter;
import com.example.todo.Fragment.AddTaskFragment;
import com.example.todo.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    Spinner spinnerRange;
    ArrayList<String> range;
    ArrayAdapter<String> arrayAdapterRange;

    ImageView ivBack2Menu;
    RecyclerView rcvTask;
    TaskAdapter taskAdapter;

    FloatingActionButton fabAddTask;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Quay lại MenuFragment
        ivBack2Menu = findViewById(R.id.iconBack2Menu);
        ivBack2Menu.setOnClickListener(v -> finish());

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
        // Đóng Fragment Add Task khi click ra ngoài (nếu có thể)
//        constraintLayout = findViewById(R.id.constraintLayout);
//        constraintLayout.setOnClickListener(this::closeFragment);

        //

        //

        rcvTask = (RecyclerView) findViewById(R.id.recycleViewTask);
        taskAdapter = new TaskAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvTask.setLayoutManager(linearLayoutManager);

        taskAdapter.setData(getListTask());
        rcvTask.setAdapter(taskAdapter);
    }

    private List<Task> getListTask(){
        List<Task> tasks = new ArrayList<>();
        //lấy từ database

        tasks.add(new Task("Task 1",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 2",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 3",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 4",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 5",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 6",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 7",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        return tasks;
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
    }

    // Hàm đóng fragment
    private void closeFragment(View v) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("AddTask");
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
//            hideKeyboard(v);
            fabAddTask.setVisibility(View.VISIBLE);
        }
    }
}