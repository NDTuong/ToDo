package com.example.todo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.Adapter.GroupTaskAdapter;
import com.example.todo.Model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuFragment extends Fragment {
    View view;

    private TextView tvTimeTable, tvTask, tvShareTask, tvImportantTask, tvGroupTask;

    List<String> groupTask;
    List<Task> listTask;
    Map<String, List<Task>> taskCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_menu, container, false);

        tvTimeTable = (TextView) view.findViewById(R.id.tvTimeTableMenu);
        tvTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeTableActivity.class);
                startActivity(intent);
            }
        });


        createGroupList();
        createCollection();
        expandableListView = view.findViewById(R.id.elvTaskGroup);
        expandableListAdapter = new GroupTaskAdapter(view.getContext(), groupTask, taskCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                Toast.makeText(view.getContext(), "Selected: " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }








    public void createCollection(){
        taskCollection = new HashMap<String, List<Task>>();
        taskCollection.put("Group 1", getListTask());
        taskCollection.put("Group 2", getListTask());
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

    private void createGroupList() {
        groupTask = new ArrayList<>();
        groupTask.add("Group 1");
        groupTask.add("Group 2");
    }
}