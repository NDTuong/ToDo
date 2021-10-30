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

        taskAdapter.setData(getListTask());
        rcvHomeTask.setAdapter(taskAdapter);
        return view;
    }

    private List<Task> getListTask(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 2",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 3",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 4",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 5",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 6",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        tasks.add(new Task("Task 7",null, false, "1/1/2000", "2/1/2000", "0/0", "1/1/2000", "2/1/2000"));
        return tasks;
    }
}