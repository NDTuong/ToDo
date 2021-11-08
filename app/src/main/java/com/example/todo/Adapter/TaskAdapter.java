package com.example.todo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.todo.Model.Task;
import com.example.todo.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context mContext;
    private List<Task> mListTask;

    public TaskAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Task> tasks){
        this.mListTask = tasks;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = mListTask.get(position);
        if(task == null){
            return;
        }
        holder.taskName.setText(task.getTaskName());
        holder.Process.setText(task.getProcess());
        holder.Deadline.setText(task.getEndTime());
    }

    @Override
    public int getItemCount() {
        if(mListTask != null){
            return mListTask.size();
        }
        return 0;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskName, Deadline, Process;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.tvTaskName);
            Deadline =  itemView.findViewById(R.id.tvDeadline);
            Process =  itemView.findViewById(R.id.tvProcess);
        }
    }
}
