package com.example.todo.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo.Adapter.TaskAdapter;
import com.example.todo.Model.Task;
import com.example.todo.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    private RecyclerView rcvHomeTask;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        rcvHomeTask = (RecyclerView) view.findViewById(R.id.recycleViewHomeTask);
        taskAdapter = new TaskAdapter(view.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        rcvHomeTask.setLayoutManager(linearLayoutManager);

//        taskAdapter.setData(getListTask(), UID);
//        rcvHomeTask.setAdapter(taskAdapter);
        return view;
    }

    private List<Task> getListTask(){
        List<Task> tasks = new ArrayList<>();
//        tasks.add(new Task("Task 1", Calendar.getInstance()));
//        tasks.add(new Task("Task 2",Calendar.getInstance()));
//        tasks.add(new Task("Task 3",Calendar.getInstance()));
//        tasks.add(new Task("Task 4",Calendar.getInstance()));
//        tasks.add(new Task("Task 5",Calendar.getInstance()));
//        tasks.add(new Task("Task 6",Calendar.getInstance()));
//        tasks.add(new Task("Task 7",Calendar.getInstance()));
        return tasks;
    }
}