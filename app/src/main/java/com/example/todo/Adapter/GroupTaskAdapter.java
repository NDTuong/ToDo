package com.example.todo.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todo.Model.Task;
import com.example.todo.R;

import java.util.List;
import java.util.Map;

public class GroupTaskAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<Task>> taskCollection;
    private List<String> groupTask;

    public GroupTaskAdapter(Context context, List<String> groupTask,
                            Map<String, List<Task>> taskCollection){
        this.context = context;
        this.taskCollection = taskCollection;
        this.groupTask = groupTask;
    }

    @Override
    public int getGroupCount() {
        return taskCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return taskCollection.get(groupTask.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupTask.get(i);
    }

    @Override
    public Task getChild(int i, int i1) {
        return taskCollection.get(groupTask.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String groupName = getGroup(i).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_task_item, null);
        }
        TextView item = view.findViewById(R.id.groupTaskName);
        item.setText(groupName);
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        Task task = getChild(i, i1);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_task, null);
        }
        TextView taskName = view.findViewById(R.id.tvTaskName);
        TextView deadline = view.findViewById(R.id.tvDeadline);
        TextView process = view.findViewById(R.id.tvProcess);
        taskName.setText(task.getTaskName());
        deadline.setText(task.getEndTime());
        process.setText(task.getProcess());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

